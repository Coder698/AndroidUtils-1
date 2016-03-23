package cloud.cn.applicationtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.applicationtest.R;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.main_start_btn)
    private Button main_start_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        main_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivityB();
            }
        });
    }

    private void gotoActivityB() {
        Intent intent = new Intent(MainActivity.this, ActivityB.class);
        startActivity(intent);
    }

    @Override
    protected void loadData() {

    }
}
