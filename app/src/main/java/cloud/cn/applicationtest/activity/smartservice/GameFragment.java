package cloud.cn.applicationtest.activity.smartservice;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xutils.ex.HttpException;

import java.util.List;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.androidlib.interfaces.SuccessFailCallback;
import cloud.cn.applicationtest.adapter.MyBaseAdapter;
import cloud.cn.applicationtest.engine.SubjectEngine;
import cloud.cn.applicationtest.entity.SubjectInfo;
import cloud.cn.applicationtest.holder.BaseHolder;
import cloud.cn.applicationtest.holder.SubjectHolder;
import cloud.cn.applicationtest.view.LoadingPage;
import cloud.cn.applicationtest.view.RefreshListView;

/**
 * Created by Cloud on 2016/6/10 0010.
 */
public class GameFragment extends TemplateBaseFragment{
    private RefreshListView refreshListView;
    private SubjectAdapter subjectAdapter;

    @Override
    protected View createSuccessView() {
        refreshListView = new RefreshListView(getContext());
        return refreshListView;
    }

    @Override
    protected void initVariables() {
        subjectAdapter = new SubjectAdapter(null);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        refreshListView.setAdapter(subjectAdapter);
        refreshListView.setOnBeginRefreshListener(new RefreshListView.OnBeginRefreshListener() {
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
    protected void loadData() {
        SubjectEngine.querySubject(0, new SuccessFailCallback<List<SubjectInfo>>() {
            @Override
            public void onSuccess(List<SubjectInfo> result) {
                refreshListView.setRefreshData(result);
                loadingPage.showRightPage(LoadingPage.STATE_LOAD_SUCCESS);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                refreshListView.setRefreshData(null);
                loadingPage.showRightPage(LoadingPage.STATE_LOAD_ERROR);
            }
        });
    }

    @Override
    protected void loadMore(int pageNum) {
        SubjectEngine.querySubject(pageNum, new SuccessFailCallback<List<SubjectInfo>>() {
            @Override
            public void onSuccess(List<SubjectInfo> result) {
                refreshListView.setLoadMoreData(result);
                loadingPage.showRightPage(LoadingPage.STATE_LOAD_SUCCESS);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                refreshListView.setLoadMoreData(null);
                if(ex instanceof HttpException && ((HttpException) ex).getCode() == 404) {

                } else {
                    loadingPage.showRightPage(LoadingPage.STATE_LOAD_ERROR);
                }
            }
        });
    }

    class SubjectAdapter extends MyBaseAdapter<SubjectInfo> {

        public SubjectAdapter(List<SubjectInfo> list) {
            super(list);
        }

        @Override
        public BaseHolder<SubjectInfo> getHolder(int position) {
            return new SubjectHolder();
        }
    }
}
