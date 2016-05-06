package cloud.cn.applicationtest.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.entity.ContactBean;
import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;

/**
 * Created by john on 2016/5/5.
 */
@ContentView(R.layout.activity_safe_setup3)
public class SafeSetup3Activity extends BaseActivity{
    @ViewInject(R.id.et_safe_phone_num)
    private EditText et_safe_phone_num;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        et_safe_phone_num.setText(PrefUtils.getString(AppConstants.PREF.SAFE_PHONE_NUM, ""));
    }

    @Override
    protected void loadData() {

    }

    @Event(R.id.tv_safe_select_contact)
    private void selectContact(View view) {
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //如果activity没调用setResult直接返回则data为空
        if(data != null) {
            ContactBean contactBean = (ContactBean)data.getSerializableExtra(AppConstants.EXTRA_KEY.CONTACT);
            et_safe_phone_num.setText(contactBean.getPhoneNum());
        }
    }

    @Event(R.id.safe_setup_next_btn)
    private void next(View view) {
        String phoneNum = et_safe_phone_num.getText().toString();
        if(TextUtils.isEmpty(phoneNum)) {
            Toast.makeText(this, "安全号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        PrefUtils.putString(AppConstants.PREF.SAFE_PHONE_NUM, phoneNum);
        Intent intent = new Intent(this, SafeSetup4Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.safe_next_in, R.anim.safe_next_out);
    }

    @Event(R.id.safe_setup_previous_btn)
    private void previous(View view) {
        Intent intent = new Intent(this, SafeSetup2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.safe_prev_in, R.anim.safe_prev_out);
    }
}
