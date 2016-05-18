package cloud.cn.applicationtest.activity.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.entity.AppInfo;
import cloud.cn.androidlib.utils.AppInfoUtils;
import cloud.cn.androidlib.utils.FileUtils;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.AppInfoAdapter;
import cloud.cn.applicationtest.entity.Tab;

/**
 * Created by Cloud on 2016/5/16.
 */
@ContentView(R.layout.activity_app_manager)
public class AppManagerActivity extends BaseActivity{
    @ViewInject(R.id.tv_avail_mem)
    private TextView tv_avail_mem;
    @ViewInject(R.id.tv_avail_sd)
    private TextView tv_avail_sd;
    @ViewInject(R.id.lv_info_list)
    private ListView lv_info_list;
    @ViewInject(R.id.tv_tab_title)
    private TextView tv_tab_title;
    private AppInfoAdapter adapter;
    private List myappInfos;
    private int lastPosition;
    private PopupWindow popupWindow;

    @Override
    protected void initVariables() {
        myappInfos = new ArrayList();
        lastPosition = -1;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tv_avail_mem.setText("内存可用：" + Formatter.formatFileSize(this, FileUtils.getAvailMemBytes()));
        tv_avail_sd.setText("SD卡可用：" + Formatter.formatFileSize(this, FileUtils.getExternalAvailableBytes()));

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
                    AppInfo appInfo = (AppInfo)myappInfos.get(position);
                    dismissWindow();
                    if(popupWindow == null) {
                        View contentView = View.inflate(getApplicationContext(), R.layout.popup_menu, null);
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
        List<AppInfo> appInfos = AppInfoUtils.getAppInfos();
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
        myappInfos.addAll(userAppInfos);
        myappInfos.addAll(sysAppInfos);
        adapter = new AppInfoAdapter(myappInfos);
        lv_info_list.setAdapter(adapter);
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
}
