package cloud.cn.androidlib.download;

import org.xutils.common.Callback;

import java.io.File;

/**
 * Created by Cloud on 2016/4/19.
 */
public interface IDownloader extends Callback.ProgressCallback<File>{
    /**
     * 获取唯一标识
     * @return
     */
    String getDownloaderId();

    /**
     * 获取下载url
     * @return
     */
    String getDownloadUrl();

    /**
     * 获取下载保存全路径
     * @return
     */
    String getSavePath();

    /**
     * 是否使用服务器返回的文件名称
     * @return
     */
    boolean isAutoRename();

    /**
     * 如果文件已存在本地是否替换
     * @return
     */
    boolean isReplace();
}
