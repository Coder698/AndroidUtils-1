package cloud.cn.applicationtest.activity.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/5/23.
 */
@ContentView(R.layout.activity_anti_virus)
public class AntiVirusActivity extends BaseActivity{
    @ViewInject(R.id.iv_scan)
    private ImageView iv_scan;
    @ViewInject(R.id.pb_progress)
    private ProgressBar pb_progress;
    private int count;
    private Thread thread;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate_forever);
        iv_scan.startAnimation(anim);
        pb_progress.setMax(100);
        thread = new Thread() {
            @Override
            public void run() {
                while(count <= 100 && !Thread.interrupted()) {
                    handler.sendEmptyMessage(0);
                    pb_progress.setProgress(count++);
                    LogUtil.d(Thread.currentThread().getName() + "====================");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        };
        thread.start();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.interrupt();
        handler.removeCallbacksAndMessages(null);
    }
}
