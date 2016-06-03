package cloud.cn.androidlib.download;

import org.xutils.common.Callback;

import java.io.File;

/**
 * Created by Cloud on 2016/4/20.
 */
public abstract class SimpleDownloader implements IDownloader{
    private String url;
    private String savePath;
    private boolean isReplace;
    private boolean isAutoRename;

    public SimpleDownloader(String url, String savePath) {
        this.url = url;
        this.savePath = savePath;
        this.isAutoRename = false;
        this.isReplace = false;
    }

    @Override
    public String getDownloaderId() {
        return url;
    }

    @Override
    public String getDownloadUrl() {
        return url;
    }

    @Override
    public String getSavePath() {
        return savePath;
    }

    @Override
    public boolean isAutoRename() {
        return this.isAutoRename;
    }

    @Override
    public boolean isReplace() {
        return isReplace;
    }

    @Override
    public void onWaiting() {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public void setReplace(boolean replace) {
        isReplace = replace;
    }

    public void setAutoRename(boolean autoRename) {
        isAutoRename = autoRename;
    }
}
