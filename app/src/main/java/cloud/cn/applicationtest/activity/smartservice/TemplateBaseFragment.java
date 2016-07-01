package cloud.cn.applicationtest.activity.smartservice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.androidlib.utils.UiUtils;
import cloud.cn.applicationtest.view.LoadingPage;

/**
 * Created by Cloud on 2016/6/10 0010.
 */
public abstract class TemplateBaseFragment extends BaseFragment {
    protected LoadingPage loadingPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (loadingPage == null) {
            loadingPage = new LoadingPage(x.app()) {
                @Override
                protected View onCreateSuccessView() {
                    return TemplateBaseFragment.this.createSuccessView();
                }

                @Override
                protected void loadData() {
                    TemplateBaseFragment.this.loadData();
                }
            };
        } else {
            UiUtils.removeSelfFromParent(loadingPage);// 移除frameLayout之前的爹
        }
        x.view().inject(this, loadingPage.getSuccessView());
        super.onCreateView(inflater, container, savedInstanceState);
        return loadingPage;
    }

    // 由子类实现创建布局的方法
    protected abstract View createSuccessView();

    protected abstract void loadMore(int pageNum);

    protected void showRightPage(int state) {
        loadingPage.showRightPage(state);
    }

    @Override
    public boolean annotationContentView() {
        return false;
    }
}
