package cloud.cn.applicationtest.activity.smartservice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.applicationtest.view.LoadingPage;

/**
 * Created by Cloud on 2016/6/10 0010.
 */
public abstract class MyBaseFragment extends BaseFragment{
    private LoadingPage mLoadingPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLoadingPage = new LoadingPage(getContext()) {

            @Override
            public View onCreateSuccessView() {
                return MyBaseFragment.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return MyBaseFragment.this.onLoad();
            }

        };
        return mLoadingPage;
    }

    // 由子类实现创建布局的方法
    public abstract View onCreateSuccessView();

    // 由子类实现加载网络数据的逻辑, 该方法运行在子线程
    public abstract LoadingPage.ResultState onLoad();

    // 开始加载网络数据
    @Override
    public void loadData() {
        if (mLoadingPage != null) {
            mLoadingPage.loadData();
        }
    }

    /**
     * 校验数据的合法性,返回相应的状态
     * @param data
     * @return
     */
    public LoadingPage.ResultState check(Object data) {
        if (data != null) {
            if (data instanceof List) {//判断当前对象是否是一个集合
                List list = (List) data;
                if (!list.isEmpty()) {//数据不为空,访问成功
                    return LoadingPage.ResultState.STATE_SUCCESS;
                } else {//空集合
                    return LoadingPage.ResultState.STATE_EMPTY;
                }
            }
        }

        return LoadingPage.ResultState.STATE_ERROR;
    }
}
