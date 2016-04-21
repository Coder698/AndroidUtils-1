package cloud.cn.androidlib.download;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Cloud on 2016/4/19.
 */
public class DownloadManager {
    private static DownloadManager instance;

    private final static int MAX_DOWNLOAD_THREAD = 2; // 有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.
    private final List<IDownloader> waitingList = new ArrayList<IDownloader>();
    private final List<IDownloader> downloadingList = new ArrayList<>();
    private final HashMap<String, Callback.Cancelable>
            callbackMap = new HashMap<String, Callback.Cancelable>(5);

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public synchronized void startDownload(IDownloader iDownloader) {
        if(!containsDownload(iDownloader)) {
            waitingList.add(iDownloader);
            iDownloader.onWaiting();
            startDownloadSchedule();
        }
    }

    private synchronized void startDownloadSchedule() {
        if(downloadingList.size() < MAX_DOWNLOAD_THREAD) {
            if(waitingList.isEmpty()) return;
            final IDownloader iDownloader = waitingList.get(0);
            downloadingList.add(iDownloader);
            waitingList.remove(0);
            Callback.ProgressCallback progressCallback = new Callback.ProgressCallback<File>() {
                @Override
                public void onSuccess(File result) {
                    LogUtil.d(iDownloader.getDownloaderId() + " success");
                    iDownloader.onSuccess(result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    LogUtil.d(iDownloader.getDownloaderId() + " error");
                    iDownloader.onError(ex, isOnCallback);
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    LogUtil.d(iDownloader.getDownloaderId() + " cancelled");
                    iDownloader.onCancelled(cex);
                }

                @Override
                public void onFinished() {
                    LogUtil.d(iDownloader.getDownloaderId() + " finished");
                    removeDownloadInfo(iDownloader);
                    startDownloadSchedule();
                    iDownloader.onFinished();
                }

                @Override
                public void onWaiting() {
                }

                @Override
                public void onStarted() {
                    LogUtil.d(iDownloader.getDownloaderId() + " started");
                    iDownloader.onStarted();
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    iDownloader.onLoading(total, current, isDownloading);
                }
            };
            File saveFile = new File(iDownloader.getSavePath());
            if(!iDownloader.isReplace() && saveFile.exists()) {
                progressCallback.onSuccess(saveFile);
                progressCallback.onFinished();
            } else {
                String fileSavePath = saveFile.getAbsolutePath();
                RequestParams requestParams = new RequestParams(iDownloader.getDownloadUrl());
                requestParams.setAutoResume(true);
                requestParams.setAutoRename(iDownloader.isAutoRename());
                requestParams.setSaveFilePath(fileSavePath);
                requestParams.setCancelFast(true);

                Callback.Cancelable cancelable = x.http().get(requestParams, progressCallback);
                callbackMap.put(iDownloader.getDownloaderId(), cancelable);
            }
        }
    }

    public synchronized void stopAll() {
        for(String key : callbackMap.keySet()) {
            Callback.Cancelable cancelable = callbackMap.get(key);
            if(cancelable != null) {
                cancelable.cancel();
            }
        }
        waitingList.clear();
        downloadingList.clear();
        callbackMap.clear();
    }

    public synchronized void stopDownload(IDownloader iDownloader) {
        Callback.Cancelable cancelable = callbackMap.get(iDownloader.getDownloaderId());
        if(cancelable != null) {
            cancelable.cancel();
        }
        removeDownloadInfo(iDownloader);
        startDownloadSchedule();
    }

    private synchronized void removeDownloadInfo(IDownloader iDownloader) {
        for(IDownloader downloader : waitingList) {
            if(downloader.getDownloaderId().equals(iDownloader.getDownloaderId())) {
                waitingList.remove(downloader);
                break;
            }
        }
        for(IDownloader downloader : downloadingList) {
            if(downloader.getDownloaderId().equals(iDownloader.getDownloaderId())) {
                downloadingList.remove(downloader);
                break;
            }
        }
        callbackMap.remove(iDownloader.getDownloaderId());
    }

    private synchronized boolean containsDownload(IDownloader iDownloader) {
        for(IDownloader downloader : waitingList) {
            if(downloader.getDownloaderId().equals(iDownloader.getDownloaderId())) {
                return true;
            }
        }
        for(IDownloader downloader : downloadingList) {
            if(downloader.getDownloaderId().equals(iDownloader.getDownloaderId())) {
                return true;
            }
        }
        return false;
    }
}
