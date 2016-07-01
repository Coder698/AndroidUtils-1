package cloud.cn.applicationtest.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.xutils.common.util.LogUtil;

import java.util.List;

import cloud.cn.androidlib.utils.UiUtils;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.adapter.MyBaseAdapter;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Cloud on 2016/6/30.
 */
public class RefreshListView extends LinearLayout{
    public interface OnBeginRefreshListener {
        void onRefreshBegin();
        void onLoadMoreBegin(int pageNum);
    }
    private ListView lv_apps;
    private PtrClassicFrameLayout rotate_header_list_view_frame;
    private LoadMoreListViewContainer load_more_list_view_container;
    private OnBeginRefreshListener onBeginRefreshListener;
    private List datas;
    private MyBaseAdapter adapter;
    private int pageNum = 0;
    private int pageSize = 20;

    public RefreshListView(Context context) {
        super(context);
        initView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View view = UiUtils.inflate(R.layout.view_refresh_listview);
        lv_apps = UiUtils.findViewById(view, R.id.lv_apps);
        rotate_header_list_view_frame = UiUtils.findViewById(view, R.id.rotate_header_list_view_frame);
        load_more_list_view_container = UiUtils.findViewById(view, R.id.load_more_list_view_container);

        rotate_header_list_view_frame.setLastUpdateTimeRelateObject(this);//显示上次更新时间
        rotate_header_list_view_frame.setLoadingMinTime(1000);//最少loading时间
        rotate_header_list_view_frame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lv_apps, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                LogUtil.d("开始刷新");
                if(onBeginRefreshListener != null) {
                    onBeginRefreshListener.onRefreshBegin();
                }
            }
        });

        load_more_list_view_container.useDefaultHeader();
        load_more_list_view_container.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                LogUtil.d("加载更多");
                if(onBeginRefreshListener != null) {
                    pageNum++;
                    onBeginRefreshListener.onLoadMoreBegin(pageNum);
                }
            }
        });
        addView(view);
    }

    public void setRefreshData(List datas) {
        if(datas != null) {
            pageNum = 0;
            adapter.setDataSource(datas);
            this.datas = datas;
            load_more_list_view_container.loadMoreFinish(false, true);
        }
        rotate_header_list_view_frame.refreshComplete();
    }

    public void setLoadMoreData(List datas) {
        if(datas != null) {
            this.datas.addAll(datas);
            adapter.notifyDataSetChanged();
        }
        if(datas == null || (datas != null && datas.size() < pageSize)) {
            load_more_list_view_container.loadMoreFinish(false, false);
        } else {
            load_more_list_view_container.loadMoreFinish(false, true);
        }
    }

    public void setAdapter(MyBaseAdapter adapter) {
        this.adapter = adapter;
        lv_apps.setAdapter(adapter);
    }

    public void setOnBeginRefreshListener(OnBeginRefreshListener onBeginRefreshListener) {
        this.onBeginRefreshListener = onBeginRefreshListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        lv_apps.setOnItemClickListener(onItemClickListener);
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        lv_apps.setOnItemLongClickListener(onItemLongClickListener);
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
