package cloud.cn.androidlib.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

/**
 * Created by Cloud on 2016/3/31.
 */
public abstract class BaseFragment extends Fragment {
    private boolean injected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        View view = x.view().inject(this, inflater, container);
        initVariables();
        initViews(savedInstanceState);
        loadData();
        return view;
    }

    //初始化变量，包括解析Intent带的数据和activity内变量
    protected abstract void initVariables();

    //初始化view,比如恢复view的数据,添加事件
    protected abstract void initViews(Bundle savedInstanceState);

    //调用mobileAPI方法
    protected abstract void loadData();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }
}
