package cloud.cn.applicationtest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import cloud.cn.applicationtest.holder.BaseHolder;

/**
 * Created by Cloud on 2016/6/13.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private List<T> list;

    public MyBaseAdapter(List<T> list) {
        this.list = list;
    }

    public void setDataSource(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(list == null) return 0;
        return list.size();// 加载更多布局也占一个位置,所以数量加1
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    // getView封装，getView功能主要4个步骤
    // 1.加载布局文件,baseHolder中initView处理
    // 2.初始化控件,baseHolder中initView处理
    // 3.setTag(holder),baseHolder中处理，自动加入
    // 4.根据数据刷新界面,baseHolder中refreshView处理
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder = null;
        if (convertView == null) {
            // 在初始化holder的同时,已经对布局进行了加载,也给view设置了tag
            holder = getHolder(position);
        } else {
            holder = (BaseHolder) convertView.getTag();
        }

        // 刷新界面,更新数据
        holder.setData(getItem(position));

        return holder.getRootView();
    }

    // 返回BaseHolder的子类,必须实现
    public abstract BaseHolder<T> getHolder(int position);
}
