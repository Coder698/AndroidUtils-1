package cloud.cn.applicationtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.utils.FileUtils;
import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/3/26.
 */
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
    @ViewInject(R.id.splash_iv)
    private ImageView splash_iv;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        startAnimation();
    }

    private void startAnimation() {
        //通过代码实现动画
        /*AnimationSet animationSet = new AnimationSet(false);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setDuration(1000);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);*/
        //通过xml实现动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_splash);
        splash_iv.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gotoNextPage();
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void gotoNextPage() {
        Intent intent = new Intent();
        if(PrefUtils.getBoolean(AppConstants.PREF.GUIDE_DISPLAY, true)) {
            intent.setClass(this, GuideActivity.class);
        } else {
            intent.setClass(this, MainActivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void loadData() {
        copyDb();
    }

    private void copyDb() {
        final File dbFile = new File(getFilesDir(), "address.db");
        if(!dbFile.exists()) {
            new Thread() {
                @Override
                public void run() {
                    FileUtils.copyAssets("address.db", dbFile);
                }
            }.start();
        }
    }
}
