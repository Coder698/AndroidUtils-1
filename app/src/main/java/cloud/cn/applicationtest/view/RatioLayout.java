package cloud.cn.applicationtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/7/1.
 */
public class RatioLayout extends FrameLayout{
    private float ratio = -1;
    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ratio = attrs.getAttributeFloatValue(R.styleable.RatioLayout_ratio, -1);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1 获取宽度
        //2 根据宽度和比例ratio,计算控件的高度
        //3 重新测量控件
        int width = MeasureSpec.getSize(widthMeasureSpec);
        //模式有MeasureSpec.AT_MOST,至多模式，控件有多大就显示多大,wrap_content
        //MeasureSpec.EXACTLY,确定模式，类似宽高写死,match_parent
        //MeasureSpec.UNSPECIFIED,未指定模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
