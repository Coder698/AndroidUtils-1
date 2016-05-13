package cloud.cn.applicationtest.activity.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.BlacklistAdapter;
import cloud.cn.applicationtest.db.DbUtils;
import cloud.cn.applicationtest.engine.Blacklist;

/**
 * Created by Cloud on 2016/5/13.
 */
@ContentView(R.layout.activity_commu_guard)
public class CommuGuardActivity extends BaseActivity {
    @ViewInject(R.id.pb_waiting)
    private ProgressBar pb_waiting;
    @ViewInject(R.id.lv_blacklist)
    private ListView lv_blacklist;
    private List<Blacklist> blacklists;
    private BlacklistAdapter adapter;
    private int startIndex;
    private int maxNumber;
    private long maxCount;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pb_waiting.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            lv_blacklist.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void initVariables() {
        startIndex = 0;
        maxNumber = 10;
        blacklists = new ArrayList<>();
        adapter = new BlacklistAdapter(blacklists);
        try {
            maxCount = DbUtils.getInstance().selector(Blacklist.class).count();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        lv_blacklist.setAdapter(adapter);
        lv_blacklist.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: //scroll空闲状态
                        if (lv_blacklist.getLastVisiblePosition() == blacklists.size() - 1) {
                            loadData();
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    @Override
    protected void loadData() {
        if (blacklists.size() == maxCount) {
            Toast.makeText(CommuGuardActivity.this, "已经到最后", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    blacklists.addAll(DbUtils.getInstance().selector(Blacklist.class).limit(maxNumber).offset(startIndex).findAll());
                    startIndex += maxNumber;
                    handler.sendEmptyMessage(0);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
