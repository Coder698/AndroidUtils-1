package cloud.cn.androidlib.interfaces;

import org.xutils.common.Callback;
import org.xutils.x;

/**
 * Created by Cloud on 2016/4/15.
 */
public abstract class SuccessCallback<ResultType> implements Callback.CommonCallback<ResultType>{
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        if(x.isDebug()) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {
        if(x.isDebug()) {
            cex.printStackTrace();
        }
    }

    @Override
    public void onFinished() {

    }
}
