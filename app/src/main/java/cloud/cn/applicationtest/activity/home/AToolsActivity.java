package cloud.cn.applicationtest.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.entity.ContactBean;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.engine.AToolsEngine;

/**
 * Created by john on 2016/5/9.
 */
@ContentView(R.layout.activity_atools)
public class AToolsActivity extends BaseActivity{
    @ViewInject(R.id.et_phone_num)
    private EditText et_phone_num;
    @ViewInject(R.id.tv_show_attribution)
    private TextView tv_show_attribution;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null) {
            ContactBean contactBean = (ContactBean) data.getSerializableExtra(AppConstants.EXTRA_KEY.CONTACT);
            et_phone_num.setText(contactBean.getPhoneNum());
        }
    }

    @Event(R.id.tv_select_contact)
    private void onSelectContactClicked(View view) {
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivityForResult(intent, 0);
    }

    @Event(R.id.btn_query)
    private void onQueryClicked(View view) {
        String phoneNum = et_phone_num.getText().toString();
        if(TextUtils.isEmpty(phoneNum)) {
            //开始震动动画
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            et_phone_num.startAnimation(shake);
            //开始手机震动
            Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(100);
        } else {
            String location = AToolsEngine.getLocation(phoneNum);
            tv_show_attribution.setText(location);
        }
    }
}
