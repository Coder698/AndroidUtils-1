package cloud.cn.applicationtest.engine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import cloud.cn.androidlib.download.DownloadManager;
import cloud.cn.androidlib.download.IDownloader;
import cloud.cn.androidlib.download.SimpleDownloader;
import cloud.cn.androidlib.net.SuccessCallback;
import cloud.cn.androidlib.utils.DeviceInfoUtils;
import cloud.cn.androidlib.utils.DialogUtils;
import static cloud.cn.applicationtest.AppConstants.DATA_SAVE_PATH.*;

import cloud.cn.androidlib.utils.FileUtils;
import cloud.cn.applicationtest.entity.UpgradeInfo;

import static cloud.cn.applicationtest.AppConstants.MOBILE_API.UPGRADE_INFO;

/**
 * Created by Cloud on 2016/4/21.
 */
public class UpgradeEngine {
    public static void checkUpgrade(final Activity context) {
        RequestParams requestParams = new RequestParams(UPGRADE_INFO);
        x.http().get(requestParams, new SuccessCallback<String>() {
            @Override
            public void onSuccess(String result) {
                final UpgradeInfo upgradeInfo = JSON.parseObject(result, UpgradeInfo.class);
                if (upgradeInfo.getVersion() > DeviceInfoUtils.getVersionCode()) {
                    DialogUtils.showConfirmDialog(context, "升级app", "更新内容：" + upgradeInfo.getDesc(),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    downloadAppWithProgressbar(context, upgradeInfo.getUrl());
                                }
                            }, null);
                } else {
                    LogUtil.d("不升级");
                }
            }
        });
    }

    /**
     * 从指定url下载app并以progressbar展示进度
     * @param context
     * @param url
     */
    private static void downloadAppWithProgressbar(final Activity context, String url) {
        final ProgressDialog progressDialog = DialogUtils.showProgressDialog(context);
        //使用kb展示进度
        progressDialog.setProgressNumberFormat("%1d kb/%2d kb");
        final IDownloader downloader = new SimpleDownloader(url,
                FileUtils.getAbsolutePath(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), APK_NAME)) {
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressDialog.setMax((int)(total / 1000));
                progressDialog.setProgress((int)(current / 1000));
            }

            @Override
            public void onSuccess(File result) {
                //dismiss不会触发progressdialog的oncancellistener,cancel方法会
                progressDialog.dismiss();
                //开启安装apk
                installApk(context, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                progressDialog.dismiss();
                Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
            }
        };
        //当用户按返回键时触发oncancel
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                LogUtil.d("progressDialog cancel");
                DownloadManager.getInstance().stopDownload(downloader);
            }
        });
        DownloadManager.getInstance().startDownload(downloader);
    }

    private static void installApk(Activity context, File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
