package cloud.cn.applicationtest.engine;

import com.alibaba.fastjson.JSON;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cloud.cn.androidlib.interfaces.SuccessFailCallback;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.entity.SubjectInfo;

/**
 * Created by Cloud on 2016/7/1.
 */
public class SubjectEngine {
    public static void querySubject(int pageNum, final SuccessFailCallback<List<SubjectInfo>> callback) {
        RequestParams params = new RequestParams(AppConstants.MOBILE_API.APP_ORIGIN + "app/subject" + pageNum);
        x.http().get(params, new SuccessFailCallback<String>() {
            @Override
            public void onSuccess(String result) {
                List<SubjectInfo> subjectInfos = JSON.parseArray(result, SubjectInfo.class);
                for(SubjectInfo info : subjectInfos) {
                    info.setUrl(AppConstants.MOBILE_API.APP_ORIGIN + info.getUrl());
                }
                callback.onSuccess(subjectInfos);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onError(ex, isOnCallback);
            }
        });

    }
}
