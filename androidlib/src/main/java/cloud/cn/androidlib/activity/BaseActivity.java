package cloud.cn.androidlib.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

/**
 * Created by Cloud on 2016/3/22.
 */
public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    /**
     * 初始化变量，包括解析Intent带的数据和activity内变量
     */
    protected abstract void initVariables();

    /**
     * 初始化view,比如恢复view的数据,添加事件
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 调用mobileAPI方法
     */
    protected abstract void loadData();
}
