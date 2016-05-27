package cloud.cn.applicationtest.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.entity.NewsMenuData;
import cloud.cn.applicationtest.entity.TabMessageEvent;

/**
 * Created by Cloud on 2016/5/24.
 */
@ContentView(R.layout.fragment_left_menu)
public class LeftMenuFragment extends BaseFragment{
    @ViewInject(R.id.lv_menu_list)
    private ListView lv_menu_list;
    private List<NewsMenuData> menuDatas;
    private LeftMenuAdapter adapter;
    private int currentPosition;

    @Override
    protected void initVariables() {
        menuDatas = new ArrayList<>();
        adapter = new LeftMenuAdapter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        lv_menu_list.setAdapter(adapter);
        lv_menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                adapter.notifyDataSetChanged();
                EventBus.getDefault().post(new TabMessageEvent(TabMessageEvent.TYPE_CHANGE_TAB, position));
            }
        });
    }

    @Override
    protected void loadData() {

    }

    public void setMenuDatas(List<NewsMenuData> menuDatas) {
        this.menuDatas.clear();
        this.menuDatas.addAll(menuDatas);
        adapter.notifyDataSetChanged();
    }

    class LeftMenuAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return menuDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return menuDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return menuDatas.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NewsMenuData data = menuDatas.get(position);
            View view = View.inflate(x.app(), R.layout.item_left_menu, null);
            TextView tv = (TextView)view.findViewById(R.id.tv_title);
            tv.setText(data.getTitle());
            if(position == currentPosition) {
                tv.setEnabled(true);
            } else {
                tv.setEnabled(false);
            }
            return view;
        }
    }
}

