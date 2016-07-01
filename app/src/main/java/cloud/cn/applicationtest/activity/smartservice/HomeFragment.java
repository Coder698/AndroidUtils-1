package cloud.cn.applicationtest.activity.smartservice;

import android.os.Bundle;
import android.view.View;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import cloud.cn.androidlib.interfaces.SuccessFailCallback;
import cloud.cn.androidlib.utils.UiUtils;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.MyBaseAdapter;
import cloud.cn.applicationtest.engine.HomeEngine;
import cloud.cn.applicationtest.entity.AppInfo;
import cloud.cn.applicationtest.entity.HomeInfo;
import cloud.cn.applicationtest.holder.BaseHolder;
import cloud.cn.applicationtest.holder.HomeHolder;
import cloud.cn.applicationtest.view.LoadingPage;
import cloud.cn.applicationtest.view.RefreshListView;

/**
 * Created by Cloud on 2016/6/10 0010.
 */
public class HomeFragment extends TemplateBaseFragment {
    @ViewInject(R.id.rlv_apps)
    private RefreshListView rlv_apps;
    private AppListAdaper appListAdaper;

    @Override
    protected View createSuccessView() {
        return UiUtils.inflate(R.layout.fragment_store_home);
    }

    @Override
    protected void initVariables() {
        appListAdaper = new AppListAdaper(null);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        rlv_apps.setAdapter(appListAdaper);
        rlv_apps.setOnBeginRefreshListener(new RefreshListView.OnBeginRefreshListener() {
            @Override
            public void onRefreshBegin() {
                loadData();
            }

            @Override
            public void onLoadMoreBegin(int pageNum) {
                loadMore(pageNum);
            }
        });
    }

    @Override
    protected void loadMore(int pageNum) {
        HomeEngine.queryAppInfo(pageNum, new SuccessFailCallback<HomeInfo>() {
            @Override
            public void onSuccess(HomeInfo result) {
                rlv_apps.setLoadMoreData(result.getList());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                showRightPage(LoadingPage.STATE_LOAD_ERROR);
                rlv_apps.setLoadMoreData(null);
            }
        });
    }

    @Override
    protected void loadData() {
        HomeEngine.queryAppInfo(0, new SuccessFailCallback<HomeInfo>() {
            @Override
            public void onSuccess(HomeInfo result) {
                showRightPage(LoadingPage.STATE_LOAD_SUCCESS);
                rlv_apps.setRefreshData(result.getList());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                rlv_apps.setRefreshData(null);
                showRightPage(LoadingPage.STATE_LOAD_ERROR);
            }
        });
    }

    class AppListAdaper extends MyBaseAdapter<AppInfo> {

        public AppListAdaper(ArrayList<AppInfo> list) {
            super(list);
        }

        @Override
        public BaseHolder<AppInfo> getHolder(int position) {
            return new HomeHolder();
        }
    }
}
