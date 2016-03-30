package cloud.cn.applicationtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cloud.cn.androidlib.activity.BaseActivity;
import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.GuidePagerAdapter;

/**
 * Created by Cloud on 2016/3/29.
 */
@ContentView(R.layout.activity_guide)
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @ViewInject(R.id.vp_guide)
    private ViewPager vp_guide;
    @ViewInject(R.id.ll_page_pointer)
    private LinearLayout ll_page_pointer;
    @ViewInject(R.id.tv_guide_pointer)
    private TextView tv_guide_pointer;
    @ViewInject(R.id.btn_guide_confirm)
    private Button btn_guide_confirm;
    private int[] guideImagesId;
    private List<View> imageList;
    private GuidePagerAdapter pagerAdapter;
    private int mPointWidth;
    private int mPointOffset;
    private float mFadeInThreshold;

    @Override
    protected void initVariables() {
        guideImagesId = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        imageList = new ArrayList<>();
        mPointWidth = DensityUtil.dip2px(10);
        mPointOffset = mPointWidth * 2;
        mFadeInThreshold = 0.8f;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        for(int i = 0; i < guideImagesId.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(guideImagesId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageList.add(imageView);

            TextView tv = new TextView(this);
            tv.setBackgroundResource(R.drawable.gray_round_bg);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mPointWidth, mPointWidth);
            if(i > 0) {
                layoutParams.leftMargin = mPointWidth;
            }
            tv.setLayoutParams(layoutParams);
            ll_page_pointer.addView(tv);
        }
        pagerAdapter = new GuidePagerAdapter(imageList);
        vp_guide.setAdapter(pagerAdapter);
        vp_guide.addOnPageChangeListener(this);
        btn_guide_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMain();
            }
        });
    }

    private void gotoMain() {
        PrefUtils.putBoolean(AppConstants.PREF.GUIDE_DISPLAY, false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void loadData() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)tv_guide_pointer.getLayoutParams();
        int marginLeft = position * mPointOffset + (int)(positionOffset * mPointOffset);
        layoutParams.leftMargin = marginLeft;
        tv_guide_pointer.setLayoutParams(layoutParams);
        if(position == guideImagesId.length - 2 && positionOffset >= mFadeInThreshold) {
            float alpha = (positionOffset - mFadeInThreshold) * 10;
            btn_guide_confirm.setAlpha(alpha);
        } else if(position == guideImagesId.length - 2 && positionOffset < mFadeInThreshold) {
            btn_guide_confirm.setAlpha(0);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
