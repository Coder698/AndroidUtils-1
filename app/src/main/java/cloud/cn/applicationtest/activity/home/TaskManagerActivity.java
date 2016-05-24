package cloud.cn.applicationtest.activity.home;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.entity.TaskInfo;
import cloud.cn.androidlib.utils.SystemInfoUtils;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.TaskInfoAdapter;
import cloud.cn.applicationtest.entity.Tab;
import cloud.cn.applicationtest.entity.TaskInfoBean;

/**
 * Created by Cloud on 2016/5/19.
 */
@ContentView(R.layout.activity_task_manager)
public class TaskManagerActivity extends BaseActivity{
    @ViewInject(R.id.tv_count)
    private TextView tv_count;
    @ViewInject(R.id.tv_memory)
    private TextView tv_memory;
    @ViewInject(R.id.lv_task_info)
    private ListView lv_task_info;
    @ViewInject(R.id.tv_tab_title)
    private TextView tv_tab_title;
    @ViewInject(R.id.btn_select_opper)
    private Button btn_select_opper;
    @ViewInject(R.id.btn_select_all)
    private Button btn_select_all;
    @ViewInject(R.id.btn_kill_all)
    private Button btn_kill_all;
    @ViewInject(R.id.btn_setting)
    private Button btn_setting;
    private TaskInfoAdapter adapter;
    private List taskInfos;
    private int lastPosition;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void initVariables() {
        taskInfos = new ArrayList();
        adapter = new TaskInfoAdapter(taskInfos);
        lastPosition = -1;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initTitle();
        lv_task_info.setAdapter(adapter);

        lv_task_info.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem >= taskInfos.size()) return;
                if(lastPosition != firstVisibleItem) {
                    Tab tab = findNearestTab(firstVisibleItem);
                    tv_tab_title.setText(tab.getMessage());
                    lastPosition = firstVisibleItem;
                }
            }
        });

        lv_task_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(taskInfos.get(position) instanceof TaskInfoBean) {
                    TaskInfoBean taskInfo = (TaskInfoBean)taskInfos.get(position);
                    taskInfo.setChecked(!taskInfo.isChecked());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btn_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAll();
            }
        });
        btn_select_opper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOpper();
            }
        });
        btn_kill_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                killAll();
            }
        });
    }

    private void initTitle() {
        tv_count.setText("运行进程：" + SystemInfoUtils.getRunningProcessCount());
        String availMem = Formatter.formatFileSize(this, SystemInfoUtils.getAvailMemBytes());
        String totalMem = Formatter.formatFileSize(this, SystemInfoUtils.getTotalMenBytes());
        tv_memory.setText("可用/总内存：" + availMem + "/" + totalMem);
    }

    private void selectAll() {
        for(Object taskInfo : taskInfos) {
            if(taskInfo instanceof TaskInfoBean) {
                TaskInfoBean tb = (TaskInfoBean)taskInfo;
                tb.setChecked(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void selectOpper() {
        for(Object taskInfo : taskInfos) {
            if(taskInfo instanceof TaskInfoBean) {
                TaskInfoBean tb = (TaskInfoBean)taskInfo;
                tb.setChecked(!tb.isChecked());
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void killAll() {
        int count = 0;
        long memCount = 0;
        List killlist = new ArrayList();
        ActivityManager am = (ActivityManager)getSystemService(ACTIVITY_SERVICE);

        for(Object taskInfo : taskInfos) {
            if(taskInfo instanceof TaskInfoBean) {
                TaskInfoBean tb = (TaskInfoBean)taskInfo;
                if(tb.isChecked()) {
                    am.killBackgroundProcesses(tb.getPackname());
                    count++;
                    memCount += tb.getMemBytes();
                    killlist.add(tb);
                }
            }
        }
        taskInfos.removeAll(killlist);
        Toast.makeText(this, "杀死进程数：" + count + " 共释放内存：" + Formatter.formatFileSize(this, memCount), Toast.LENGTH_LONG).show();
        adapter.notifyDataSetChanged();
        initTitle();
    }

    @Override
    protected void loadData() {
        new Thread() {
            @Override
            public void run() {
                List<TaskInfo> taskList = SystemInfoUtils.getRunningTask();
                List<TaskInfoBean> userTasks = new ArrayList<TaskInfoBean>();
                List<TaskInfoBean> sysTasks = new ArrayList<TaskInfoBean>();
                for(TaskInfo taskInfo : taskList) {
                    if(taskInfo.isUsertask()) {
                        userTasks.add(new TaskInfoBean(taskInfo));
                    } else {
                        sysTasks.add(new TaskInfoBean(taskInfo));
                    }
                }
                taskInfos.clear();
                taskInfos.add(new Tab("用户进程数：" + userTasks.size()));
                taskInfos.addAll(userTasks);
                taskInfos.add(new Tab("系统进程数：" + sysTasks.size()));
                taskInfos.addAll(sysTasks);
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    private Tab findNearestTab(int position) {
        Tab tab = null;
        while(position > -1) {
            if(taskInfos.get(position) instanceof  Tab) {
                tab = (Tab)taskInfos.get(position);
                break;
            }
            position--;
        }
        return tab;
    }
}
