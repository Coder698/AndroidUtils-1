package cloud.cn.applicationtest.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import cloud.cn.androidlib.utils.ThreadManager;
import cloud.cn.androidlib.utils.UiUtils;
import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/6/10 0010.
 */
public abstract class LoadingPage extends FrameLayout {

    public static final int STATE_UNLOAD = 0;// 未加载
    public static final int STATE_LOADING = 1;// 正在加载
    public static final int STATE_LOAD_EMPTY = 2;// 数据为空
    public static final int STATE_LOAD_ERROR = 3;// 加载失败
    public static final int STATE_LOAD_SUCCESS = 4;// 访问成功

    private int mCurrentState = STATE_UNLOAD;// 当前状态

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;

    public LoadingPage(Context context) {
        super(context);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        // 正在加载
        if (mLoadingView == null) {
            mLoadingView = onCreateLoadingView();
            addView(mLoadingView);
        }

        // 加载失败
        if (mErrorView == null) {
            mErrorView = onCreateErrorView();
            addView(mErrorView);
        }

        // 数据为空
        if (mEmptyView == null) {
            mEmptyView = onCreateEmptyView();
            addView(mEmptyView);
        }

        //成功界面，显示数据
        if(mSuccessView == null) {
            mSuccessView = onCreateSuccessView();
            if(mSuccessView != null) {
                addView(mSuccessView);
            }
        }

        showRightPage();
    }

    /**
     * 设置当前state,显示对应页面
     * @param state
     */
    public void showRightPage(int state) {
        mCurrentState = state;
        showRightPage();
    }

    /**
     * 根据当前状态,展示正确页面
     */
    public void showRightPage() {
        if (mLoadingView != null) {
            mLoadingView
                    .setVisibility((mCurrentState == STATE_LOADING || mCurrentState == STATE_UNLOAD) ? View.VISIBLE
                            : View.GONE);
        }

        if (mEmptyView != null) {
            mEmptyView
                    .setVisibility(mCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE
                            : View.GONE);
        }

        if (mErrorView != null) {
            mErrorView
                    .setVisibility(mCurrentState == STATE_LOAD_ERROR ? View.VISIBLE
                            : View.GONE);
        }

        if (mSuccessView != null) {
            mSuccessView
                    .setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? View.VISIBLE
                            : View.GONE);
        }
    }

    /**
     * 初始化正在加载布局
     */
    private View onCreateLoadingView() {
        return UiUtils.inflate(R.layout.layout_loading);
    }

    /**
     * 初始化加载失败布局
     */
    private View onCreateErrorView() {
        View view = UiUtils.inflate(R.layout.layout_error);
        // 点击重试
        view.findViewById(R.id.btn_retry).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadData();
                    }
                });
        return view;
    }

    /**
     * 初始化数据为空布局
     */
    private View onCreateEmptyView() {
        return UiUtils.inflate(R.layout.layout_empty);
    }

    /**
     * 初始化访问成功布局, 子类必须实现
     */
    protected abstract View onCreateSuccessView();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 获取成功界面
     * @return
     */
    public View getSuccessView() {
        return mSuccessView;
    }
}
