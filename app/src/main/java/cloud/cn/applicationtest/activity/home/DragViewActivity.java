package cloud.cn.applicationtest.activity.home;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.utils.DeviceInfoUtils;
import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/5/12.
 */
@ContentView(R.layout.activity_dragview)
public class DragViewActivity extends BaseActivity{
    @ViewInject(R.id.tv_top_hint)
    private TextView tv_top_hint;
    @ViewInject(R.id.tv_bottom_hint)
    private TextView tv_bottom_hint;
    @ViewInject(R.id.iv_dragview)
    private ImageView iv_dragview;
    private int startX;
    private int startY;
    private int screenWidth;
    private int screenHeight;
    private long[] mHits;


    @Override
    protected void initVariables() {
        screenWidth = DeviceInfoUtils.getScreenWidth();
        screenHeight = 0;
        mHits = new long[2]; //多少击事件数组就多长
        int lastX = PrefUtils.getInt(AppConstants.PREF.LAST_X, 0);
        int lastY = PrefUtils.getInt(AppConstants.PREF.LAST_Y, 0);
        //oncreate阶段不能通过getleft来获得位置,layout来设置位置，这些只有等view经过测量，渲染后才有效
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_dragview.getLayoutParams();
        layoutParams.leftMargin = lastX;
        layoutParams.topMargin = lastY;
        if(lastY > screenHeight / 2) {
            tv_top_hint.setVisibility(View.VISIBLE);
            tv_bottom_hint.setVisibility(View.INVISIBLE);
        } else {
            tv_top_hint.setVisibility(View.INVISIBLE);
            tv_bottom_hint.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        iv_dragview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return  onDragViewMove(event);
            }
        });
        iv_dragview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragViewDbClick();
            }
        });
    }

    private void dragViewDbClick() {
        //数组左移一位
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //得到当前开机时间
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        //500毫秒以内点n次满足条件,n为mHits长度
        if(mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
            int centerX = screenWidth / 2;
            int centerY = screenHeight / 2;
            int halfWidth = iv_dragview.getWidth() / 2;
            int halfHeight = iv_dragview.getHeight() / 2;
            iv_dragview.layout(centerX - halfWidth, centerY - halfHeight, centerX + halfWidth, centerY + halfHeight);
            PrefUtils.putInt(AppConstants.PREF.LAST_X, centerX - halfWidth);
            PrefUtils.putInt(AppConstants.PREF.LAST_Y, centerY - halfHeight);
        }
    }

    private boolean onDragViewMove(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(screenHeight == 0) {
                    screenHeight = DeviceInfoUtils.getScreenHeight() - DeviceInfoUtils.getDisplayRect(this).top;
                }
                //getRawX相对于屏幕,getX相对于监听事件的view,startX要定义成int否则有误差
                startX = (int)event.getRawX();
                startY = (int)event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int)event.getRawX() - startX;
                int dy = (int)event.getRawY() - startY;
                startX = (int)event.getRawX();
                startY = (int)event.getRawY();
                //左上角位置重新定位,getleft等函数都是相对于父视图的坐标
                float left = iv_dragview.getLeft() + dx;
                float top = iv_dragview.getTop() + dy;
                float right = iv_dragview.getRight() + dx;
                float bottom = iv_dragview.getBottom() + dy;
                if(left < 0 || top < 0 || right > screenWidth || bottom > screenHeight) {
                    break;
                }
                if(top > screenHeight / 2) {
                    tv_top_hint.setVisibility(View.VISIBLE);
                    tv_bottom_hint.setVisibility(View.INVISIBLE);
                } else {
                    tv_top_hint.setVisibility(View.INVISIBLE);
                    tv_bottom_hint.setVisibility(View.VISIBLE);
                }
                //layout坐标也是相对于父视图
                iv_dragview.layout((int)left, (int)top, (int)right, (int)bottom);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int lastX = iv_dragview.getLeft();
                int lastY = iv_dragview.getTop();
                PrefUtils.putInt(AppConstants.PREF.LAST_X, lastX);
                PrefUtils.putInt(AppConstants.PREF.LAST_Y, lastY);
                break;
        }
        return false;
    }

    @Override
    protected void loadData() {

    }
}
