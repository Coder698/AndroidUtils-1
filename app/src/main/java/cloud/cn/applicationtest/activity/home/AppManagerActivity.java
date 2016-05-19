package cloud.cn.applicationtest.activity.home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.entity.AppInfo;
import cloud.cn.androidlib.utils.SystemInfoUtils;
import cloud.cn.androidlib.utils.PhoneUtils;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.AppInfoAdapter;
import cloud.cn.applicationtest.entity.Tab;

/**
 * Created by Cloud on 2016/5/16.
 */
@ContentView(R.layout.activity_app_manager)
public class AppManagerActivity extends BaseActivity implements View.OnClickListener{
    @ViewInject(R.id.tv_avail_mem)
    private TextView tv_avail_mem;
    @ViewInject(R.id.tv_avail_sd)
    private TextView tv_avail_sd;
    @ViewInject(R.id.lv_info_list)
    private ListView lv_info_list;
    @ViewInject(R.id.tv_tab_title)
    private TextView tv_tab_title;
    private LinearLayout ll_uninstall;
    private LinearLayout ll_launch;
    private LinearLayout ll_share;
    private AppInfoAdapter adapter;
    private List myappInfos;
    private int lastPosition;
    private AppInfo appInfo;
    private PopupWindow popupWindow;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void initVariables() {
        myappInfos = new ArrayList();
        lastPosition = -1;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv_avail_mem.setText("内存可用：" + Formatter.formatFileSize(this, SystemInfoUtils.getAvailMemBytes()));
        tv_avail_sd.setText("SD卡可用：" + Formatter.formatFileSize(this, SystemInfoUtils.getExternalAvailableBytes()));
        adapter = new AppInfoAdapter(myappInfos);
        lv_info_list.setAdapter(adapter);

        lv_info_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                dismissWindow();
                if(firstVisibleItem >= myappInfos.size()) return;
                if(lastPosition != firstVisibleItem) {
                    Tab tab = findNearestTab(firstVisibleItem);
                    tv_tab_title.setText(tab.getMessage());
                    lastPosition = firstVisibleItem;
                }
            }
        });

        lv_info_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(myappInfos.get(position) instanceof AppInfo) {
                    appInfo = (AppInfo)myappInfos.get(position);
                    dismissWindow();
                    if(popupWindow == null) {
                        View contentView = View.inflate(getApplicationContext(), R.layout.popup_menu, null);
                        ll_uninstall = (LinearLayout)contentView.findViewById(R.id.ll_uninstall);
                        ll_launch = (LinearLayout)contentView.findViewById(R.id.ll_launch);
                        ll_share = (LinearLayout)contentView.findViewById(R.id.ll_share);
                        ll_uninstall.setOnClickListener(AppManagerActivity.this);
                        ll_launch.setOnClickListener(AppManagerActivity.this);
                        ll_share.setOnClickListener(AppManagerActivity.this);
                        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                    View contentView = popupWindow.getContentView();
                    //dialog挂载在activity,这个挂载在listview上,x,y为离窗口的距离
                    int[] location = new int[2];
                    view.getLocationInWindow(location);
                    popupWindow.showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 50, location[1]);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_popup);
                    contentView.startAnimation(animation);
                }
            }
        });
    }

    private void dismissWindow() {
        if(popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    @Override
    protected void loadData() {
        new Thread() {
            @Override
            public void run() {
                List<AppInfo> appInfos = SystemInfoUtils.getAppInfos();
                List userAppInfos = new ArrayList<>();
                List sysAppInfos = new ArrayList<>();
                for(AppInfo info : appInfos) {
                    if(info.isUserApp()) {
                        userAppInfos.add(info);
                    } else {
                        sysAppInfos.add(info);
                    }
                }
                Tab tab = new Tab("手机应用：" + userAppInfos.size());
                userAppInfos.add(0, tab);
                tab = new Tab("系统应用：" + sysAppInfos.size());
                sysAppInfos.add(0, tab);
                myappInfos.clear();
                myappInfos.addAll(userAppInfos);
                myappInfos.addAll(sysAppInfos);
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

    private Tab findNearestTab(int position) {
        Tab tab = null;
        while(position > -1) {
            if(myappInfos.get(position) instanceof  Tab) {
                tab = (Tab)myappInfos.get(position);
                break;
            }
            position--;
        }
        return tab;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissWindow();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_uninstall:
                uninstallApp();
                break;
            case R.id.ll_launch:
                launchApp();
                break;
            case R.id.ll_share:
                shareApp();
                break;
        }
        dismissWindow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            loadData();
        }
    }

    private void uninstallApp() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DELETE");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("package:" + appInfo.getPackname()));
        startActivityForResult(intent, 0);
    }

    private void launchApp() {
        String packname = appInfo.getPackname();
        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packname, PackageManager.GET_ACTIVITIES);
            ActivityInfo[] activityInfos = packageInfo.activities;
            if(activityInfos != null && activityInfos.length > 0) {
                Intent intent = new Intent();
                intent.setClassName(packname, activityInfos[0].name);
                startActivity(intent);
            } else {
                Toast.makeText(this, "当前程序没有界面", Toast.LENGTH_SHORT).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void shareApp() {
        PhoneUtils.sendSms(this, "推荐使用一款软件," + appInfo.getPackname());
    }
}
