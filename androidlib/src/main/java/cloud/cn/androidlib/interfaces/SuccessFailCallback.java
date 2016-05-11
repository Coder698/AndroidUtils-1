package cloud.cn.androidlib.interfaces;

import org.xutils.common.Callback;
import org.xutils.x;

/**
 * Created by Cloud on 2016/4/15.
 */
public abstract class SuccessFailCallback<ResultType> implements Callback.CommonCallback<ResultType>{
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
