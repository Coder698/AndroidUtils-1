package cloud.cn.applicationtest.engine;

import com.alibaba.fastjson.JSON;

import org.xutils.http.RequestParams;
import org.xutils.x;

import cloud.cn.androidlib.interfaces.SuccessFailCallback;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.entity.HomeInfo;

/**
 * Created by Cloud on 2016/6/29.
 */
public class HomeEngine {
    public static void queryAppInfo(int pageNum, final SuccessFailCallback<HomeInfo> callback) {
        RequestParams params = new RequestParams(AppConstants.MOBILE_API.APP_ORIGIN + "app/homelist" + pageNum);
        x.http().get(params, new SuccessFailCallback<String>() {
            @Override
            public void onSuccess(String result) {
                HomeInfo appInfo = JSON.parseObject(result, HomeInfo.class);
                callback.onSuccess(appInfo);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex, isOnCallback);
            }
        });
    }
}
