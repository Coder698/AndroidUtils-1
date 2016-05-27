package cloud.cn.applicationtest.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import cloud.cn.applicationtest.R;

/**
 * Created by Cloud on 2016/5/27.
 */
public class RefreshListView extends ListView
{
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
        View headerView = View.inflate(getContext(), R.layout.header_refresh, null);
        headerView.measure(0, 0);
        int height = headerView.getMeasuredHeight();
        headerView.setPadding(0, -height, 0, 0);
        this.addHeaderView(headerView);
    }
}
