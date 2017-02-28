package cloud.cn.applicationtest.view;

import android.content.Context;
import android.content.res.TypedArray;
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
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.RatioLayout);
        ratio = a.getFloat(R.styleable.RatioLayout_ratio, 1);
        a.recycle();
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.RatioLayout);
        ratio = a.getFloat(R.styleable.RatioLayout_ratio, 1);
        a.recycle();
    }

    private void initView() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // widthMeasureSpec 宽度的规则 包含了两部分 模式 值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec); // 模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);// 宽度大小
        int width = widthSize - getPaddingLeft() - getPaddingRight();// 去掉左右两边的padding

        int heightMode = MeasureSpec.getMode(heightMeasureSpec); // 模式
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);// 高度大小
        int height = heightSize - getPaddingTop() - getPaddingBottom();// 去掉上下两边的padding

        if (widthMode == MeasureSpec.EXACTLY
                && heightMode != MeasureSpec.EXACTLY) {
            // 修正一下 高度的值 让高度=宽度/比例
            height = (int) (width / ratio + 0.5f); // 保证4舍五入
        } else if (widthMode != MeasureSpec.EXACTLY
                && heightMode == MeasureSpec.EXACTLY) {
            // 由于高度是精确的值 ,宽度随着高度的变化而变化
            width = (int) ((height * ratio) + 0.5f);
        }
        // 重新制作了新的规则
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width + getPaddingLeft() + getPaddingRight(), MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height + getPaddingTop() + getPaddingBottom(), MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
