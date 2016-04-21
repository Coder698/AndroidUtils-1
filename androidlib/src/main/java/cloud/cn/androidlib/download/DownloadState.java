package cloud.cn.androidlib.download;

/**
 * Created by Cloud on 2016/4/19.
 */
public interface DownloadState {
    int WAITING = 0;
    int STARTED = 1;
    int LOADING = 2;
    int FINISHED = 3;
    int STOPPED = 4;
    int ERROR = 5;
}
