package cloud.cn.applicationtest.view;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 字母索引条
 *
 * @author Administrator
 */
public class QuickAlphabeticBar extends ImageButton {
    public interface DialogText {
        TextView getDialogText();
    }

    private TextView mDialogText; // 中间显示字母的文本框
    private ListView mList; // 列表
    private float mHeight; // 高度
    // 字母列表索引
    private String[] letters = new String[]{"#", "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z"};
    // 字母索引哈希表
    private HashMap<String, Integer> alphaIndexer;
    Paint paint = new Paint();
    boolean showBkg = false;
    int choose = -1;

    public QuickAlphabeticBar(Context context) {
        super(context);
    }

    public QuickAlphabeticBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public QuickAlphabeticBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 初始化
    public void init(DialogText dialogText) {
        mDialogText = dialogText.getDialogText();
        mDialogText.setVisibility(View.INVISIBLE);
    }

    // 设置需要索引的列表
    public void setListView(ListView mList) {
        this.mList = mList;
    }

    // 设置字母索引哈希表
    public void setAlphaIndexer(HashMap<String, Integer> alphaIndexer) {
        this.alphaIndexer = alphaIndexer;
    }

    // 设置字母索引条的高度
    public void setHeight(float height) {
        this.mHeight = height;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int act = event.getAction();
        float y = event.getY();
        final int oldChoose = choose;
        // 计算手指位置，找到对应的段，让mList移动段开头的位置上
        int selectIndex = (int) (y / (mHeight / letters.length));

        if (selectIndex > -1 && selectIndex < letters.length) { // 防止越界
            String key = letters[selectIndex];
            if (oldChoose != selectIndex && alphaIndexer.containsKey(key)) {
                int pos = alphaIndexer.get(key);
                if (mList.getHeaderViewsCount() > 0) { // 防止ListView有标题栏,本例中没有
                    this.mList.setSelectionFromTop(
                            pos + mList.getHeaderViewsCount(), 0);
                } else {
                    this.mList.setSelectionFromTop(pos, 0);
                }
                mDialogText.setText(letters[selectIndex]);
                choose = selectIndex;
            }
        }
        switch (act) {
            case MotionEvent.ACTION_DOWN:
                showBkg = true;
                if (oldChoose != choose) {
                    invalidate();
                    if (mDialogText != null
                            && mDialogText.getVisibility() == View.INVISIBLE) {
                        mDialogText.setVisibility(VISIBLE);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != choose) {
                    invalidate();
                    if (mDialogText != null
                            && mDialogText.getVisibility() == View.INVISIBLE) {
                        mDialogText.setVisibility(VISIBLE);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBkg = false;
                if (mDialogText != null
                        && mDialogText.getVisibility() == View.VISIBLE) {
                    mDialogText.setVisibility(INVISIBLE);
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int sigleHeight = height / letters.length; // 单个字母占的高度
        for (int i = 0; i < letters.length; i++) {
            paint.setColor(Color.WHITE);
            paint.setTextSize(20);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            if (i == choose) {
                paint.setColor(Color.parseColor("#00BFFF")); // 滑动时按下字母颜色
                paint.setFakeBoldText(true);
            }
            // 绘画的位置
            float xPos = width / 2 - paint.measureText(letters[i]) / 2;
            float yPos = sigleHeight * i + sigleHeight;
            canvas.drawText(letters[i], xPos, yPos, paint);
            paint.reset();
        }
    }

}
