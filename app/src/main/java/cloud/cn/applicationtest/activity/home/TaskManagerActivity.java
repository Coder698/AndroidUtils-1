package cloud.cn.applicationtest.activity.home;

import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.utils.SystemInfoUtils;
import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/5/19.
 */
@ContentView(R.layout.activity_task_manager)
public class TaskManagerActivity extends BaseActivity{
    @ViewInject(R.id.tv_count)
    private TextView tv_count;
    @ViewInject(R.id.tv_memory)
    private TextView tv_memory;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv_count.setText("运行进程：" + SystemInfoUtils.getRunningProcessCount());
        String availMem = Formatter.formatFileSize(this, SystemInfoUtils.getAvailMemBytes());
        String totalMem = Formatter.formatFileSize(this, SystemInfoUtils.getTotalMenBytes());
        tv_memory.setText("可用/总内存：" + availMem + "/" + totalMem);
    }

    @Override
    protected void loadData() {

    }
}
