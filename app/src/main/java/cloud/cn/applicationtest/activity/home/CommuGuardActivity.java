package cloud.cn.applicationtest.activity.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.utils.DialogUtils;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.BlacklistAdapter;
import cloud.cn.applicationtest.db.DbUtils;
import cloud.cn.applicationtest.entity.Blacklist;

/**
 * Created by Cloud on 2016/5/13.
 */
@ContentView(R.layout.activity_commu_guard)
public class CommuGuardActivity extends BaseActivity {
    @ViewInject(R.id.pb_waiting)
    private ProgressBar pb_waiting;
    @ViewInject(R.id.lv_blacklist)
    private ListView lv_blacklist;
    @ViewInject(R.id.btn_add)
    private Button btn_add;
    private List<Blacklist> blacklists;
    private BlacklistAdapter adapter;
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
        maxNumber = 10;
        blacklists = new ArrayList<>();
        try {
            maxCount = DbUtils.getInstance().selector(Blacklist.class).count();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        adapter = new BlacklistAdapter(blacklists, new BlacklistAdapter.ItemDeleteListener() {
            @Override
            public void deleteItem(final Blacklist blacklist) {
                DialogUtils.showConfirmDialog(CommuGuardActivity.this, "确认删除", "是否删除" + blacklist.getPhoneNum()
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    DbUtils.getInstance().delete(blacklist);
                                    blacklists.remove(blacklist);
                                    maxCount--;
                                    adapter.notifyDataSetChanged();
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, null);
            }
        });
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
        lv_blacklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAddOrUpdateDialog(blacklists.get(position));
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddOrUpdateDialog(null);
            }
        });
    }

    private void showAddOrUpdateDialog(final Blacklist blacklist) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_blacklist_edit, null);
        final RadioGroup rg_type = (RadioGroup)view.findViewById(R.id.rg_type);
        final EditText et_phone_num = (EditText)view.findViewById(R.id.et_phone_num);
        if(blacklist != null) {
            et_phone_num.setText(blacklist.getPhoneNum());
            if(blacklist.getType() == Blacklist.TYPE_INTERCEPTER_PHONE) {
                rg_type.check(R.id.rb_intercept_phone);
            } else if(blacklist.getType() == Blacklist.TYPE_INTERCEPTER_SMS) {
                rg_type.check(R.id.rb_intercept_sms);
            } else {
                rg_type.check(R.id.rb_intercept_all);
            }

        }
        Button btn_ok = (Button)view.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button)view.findViewById(R.id.btn_cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Blacklist tempBl = blacklist;
                String phoneNum = et_phone_num.getText().toString().trim();
                if(TextUtils.isEmpty(phoneNum)) {
                    Toast.makeText(getApplication(), "电话不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                int type = 0;
                switch (rg_type.getCheckedRadioButtonId()) {
                    case R.id.rb_intercept_phone:
                        type = Blacklist.TYPE_INTERCEPTER_PHONE;
                        break;
                    case R.id.rb_intercept_sms:
                        type = Blacklist.TYPE_INTERCEPTER_SMS;
                        break;
                    case R.id.rb_intercept_all:
                        type = Blacklist.TYPE_INTERCEPTER_ALL;
                        break;
                }
                if(tempBl == null) {
                    tempBl = new Blacklist();
                    blacklists.add(0, tempBl);
                    maxCount++;
                }
                tempBl.setType(type);
                tempBl.setPhoneNum(phoneNum);
                try {
                    DbUtils.getInstance().saveOrUpdate(tempBl);
                    adapter.notifyDataSetChanged();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
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
                    blacklists.addAll(DbUtils.getInstance().selector(Blacklist.class).orderBy("id", true).limit(maxNumber).offset(blacklists.size()).findAll());
                    handler.sendEmptyMessage(0);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
