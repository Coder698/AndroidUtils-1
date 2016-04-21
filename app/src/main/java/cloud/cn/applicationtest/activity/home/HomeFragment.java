package cloud.cn.applicationtest.activity.home;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.internal.widget.ViewUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.androidlib.download.DownloadManager;
import cloud.cn.androidlib.download.DownloadState;
import cloud.cn.androidlib.download.IDownloader;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.engine.SafeEngine;

/**
 * Created by Cloud on 2016/4/5.
 */
@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {
    @ViewInject(R.id.gv_home)
    private GridView gv_home;
    private int[] icons;
    private String[] names;

    @Override
    protected void initVariables() {
        icons = new int[]{R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
                R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan, R.drawable.sysoptimize,
                R.drawable.atools, R.drawable.settings};
        names = new String[]{"手机防盗", "通讯卫士", "软件管家", "进程管理", "流量统计", "病毒查杀",
                "缓存清理", "高级工具", "设置中心"};
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        gv_home.setAdapter(new HomeAdapter());
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("手机防盗".equals(names[position])) {
                    SafeEngine.showSavePassDialog(getActivity());
                } else if("通讯卫士".equals(names[position])) {

                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    class HomeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(x.app(), R.layout.item_gridview_home, null);
            ImageView iv_item_icon = (ImageView)view.findViewById(R.id.iv_item_icon);
            TextView tv_item_name = (TextView)view.findViewById(R.id.tv_item_name);
            iv_item_icon.setImageResource(icons[position]);
            tv_item_name.setText(names[position]);
            return view;
        }
    }
}
