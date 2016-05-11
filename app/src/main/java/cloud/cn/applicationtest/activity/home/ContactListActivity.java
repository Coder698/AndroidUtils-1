package cloud.cn.applicationtest.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.engine.ContactEngine;
import cloud.cn.androidlib.entity.ContactBean;
import cloud.cn.androidlib.interfaces.SuccessCallback;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.ContactListAdapter;
import cloud.cn.applicationtest.ui.QuickAlphabeticBar;

/**
 * 联系人列表
 *
 * @author Administrator
 *
 */
@ContentView(R.layout.activity_contact_list)
public class ContactListActivity extends BaseActivity implements QuickAlphabeticBar.DialogText{
    @ViewInject(R.id.contact_list)
    private ListView contactList;
    @ViewInject(R.id.fast_scroller)
    private QuickAlphabeticBar alphabeticBar; // 快速索引条
    @ViewInject(R.id.fast_position)
    private TextView fast_position;
    private ContactListAdapter adapter;
    private List<ContactBean> contactBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(AppConstants.EXTRA_KEY.CONTACT, contactBeans.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void loadData() {
        ContactEngine.listContacts(new SuccessCallback<List<ContactBean>>() {
            @Override
            public void onSuccess(List<ContactBean> result) {
                setAdapter(result);
                contactBeans = result;
            }
        });
    }

    @Override
    public TextView getDialogText() {
        return fast_position;
    }

    private void setAdapter(List<ContactBean> list) {
        adapter = new ContactListAdapter(this, list, alphabeticBar);
        contactList.setAdapter(adapter);
        alphabeticBar.init(ContactListActivity.this);
        alphabeticBar.setListView(contactList);
        alphabeticBar.setHeight(alphabeticBar.getHeight());
        alphabeticBar.setVisibility(View.VISIBLE);
    }
}
