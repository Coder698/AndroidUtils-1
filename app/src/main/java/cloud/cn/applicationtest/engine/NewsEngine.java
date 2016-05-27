package cloud.cn.applicationtest.engine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cloud.cn.androidlib.interfaces.SuccessFailCallback;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.entity.NewsDetail;
import cloud.cn.applicationtest.entity.NewsMenuData;

/**
 * Created by Cloud on 2016/5/24.
 */
public class NewsEngine {
    public static void getCategories(final SuccessFailCallback<List<NewsMenuData>> callback) {
        RequestParams params = new RequestParams(AppConstants.MOBILE_API.CATEGORIES);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSON.parseObject(result);
                List<NewsMenuData> menuDatas = JSON.parseArray(jsonObject.getString("data"), NewsMenuData.class);
                callback.onSuccess(menuDatas);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public static void getDetail(String url, final SuccessFailCallback<NewsDetail> callback) {
        RequestParams params = new RequestParams(AppConstants.MOBILE_API.Z_ORIGIN + url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSON.parseObject(result);
                NewsDetail newsDetail = JSON.parseObject(jsonObject.getString("data"), NewsDetail.class);
                callback.onSuccess(newsDetail);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
