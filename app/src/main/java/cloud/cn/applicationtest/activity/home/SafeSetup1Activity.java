package cloud.cn.applicationtest.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.applicationtest.R;

/**
 * Created by john on 2016/5/5.
 */
@ContentView(R.layout.activity_safe_setup1)
public class SafeSetup1Activity extends BaseActivity{
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }

    @Event(R.id.safe_setup_next_btn)
    private void next(View view) {
        Intent intent = new Intent(this, SafeSetup2Activity.class);
        startActivity(intent);
    }

    private void previous(View view) {

    }
}
