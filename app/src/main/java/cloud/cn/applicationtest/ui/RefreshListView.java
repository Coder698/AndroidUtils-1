package cloud.cn.applicationtest.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/5/27.
 */
public class RefreshListView extends ListView {
    private int headerViewHeight;
    private int startY;
    private View headerView;
    private int currentState;
    @ViewInject(R.id.iv_arrow)
    private ImageView iv_arrow;
    @ViewInject(R.id.pb_progress)
    private ProgressBar pb_progress;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_date)
    private TextView tv_date;
    public final static int STATE_RELEASE_REFRESH =  0;
    public final static int STATE_PULL_REFRESH =  1;
    public final static int STATE_REFRESHING = 2;

    public RefreshListView(Context context) {
        super(context);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }

    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.header_refresh, null);
        x.view().inject(this, headerView);
        headerView.measure(0, 0);
        headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        this.addHeaderView(headerView);
        changeState(STATE_PULL_REFRESH);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int)ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endY = (int)ev.getRawY();
                int dy = endY - startY;
                if(dy > 0 && getFirstVisiblePosition() == 0 && currentState != STATE_REFRESHING) {
                    int padding = dy - headerViewHeight;
                    headerView.setPadding(0, padding, 0, 0);
                    if(padding > 0 && currentState != STATE_RELEASE_REFRESH) {
                        changeState(STATE_RELEASE_REFRESH);
                    } else if(padding < 0 && currentState != STATE_PULL_REFRESH) {
                        changeState(STATE_PULL_REFRESH);
                    }
                    //return true;
                } else {
                    startY = endY;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(currentState == STATE_PULL_REFRESH) {
                    headerView.setPadding(0, -headerViewHeight, 0, 0);//隐藏
                } else if(currentState == STATE_RELEASE_REFRESH) {
                    headerView.setPadding(0, 0, 0, 0);//显示
                    changeState(STATE_REFRESHING);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void changeState(int state) {
        currentState = state;
        switch (currentState) {
            case STATE_PULL_REFRESH://下拉刷新
                tv_title.setText("下拉刷新");
                iv_arrow.setVisibility(View.VISIBLE);
                pb_progress.setVisibility(View.INVISIBLE);
                break;
            case STATE_RELEASE_REFRESH://松开刷新
                tv_title.setText("松开刷新");
                iv_arrow.setVisibility(View.VISIBLE);
                pb_progress.setVisibility(View.INVISIBLE);
                break;
            case STATE_REFRESHING://正在刷新
                tv_title.setText("正在刷新");
                iv_arrow.setVisibility(View.INVISIBLE);
                pb_progress.setVisibility(View.VISIBLE);
                break;
        }
    }
}
