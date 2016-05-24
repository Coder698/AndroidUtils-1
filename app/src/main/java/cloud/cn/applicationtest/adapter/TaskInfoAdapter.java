package cloud.cn.applicationtest.adapter;

import android.graphics.Color;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import cloud.cn.androidlib.entity.AppInfo;
import cloud.cn.androidlib.entity.TaskInfo;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.entity.Tab;
import cloud.cn.applicationtest.entity.TaskInfoBean;

/**
 * Created by Cloud on 2016/5/20.
 */
public class TaskInfoAdapter extends BaseAdapter{
    List taskInfos;

    public TaskInfoAdapter(List taskInfos) {
        this.taskInfos = taskInfos;
    }

    @Override
    public int getCount() {
        return taskInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return taskInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItem(position) instanceof Tab) {
            Tab tab = (Tab)getItem(position);
            if(convertView == null || !(convertView instanceof TextView)) {
                TextView tv = new TextView(x.app());
                tv.setBackgroundColor(Color.GRAY);
                tv.setTextColor(Color.WHITE);
                convertView = tv;
            }
            ((TextView) convertView).setText(tab.getMessage());
        } else {
            ViewHolder viewHolder;
            TaskInfoBean taskInfo = (TaskInfoBean)getItem(position);
            if(convertView == null || !(convertView instanceof LinearLayout)) {
                convertView = View.inflate(x.app(), R.layout.item_task_info, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
                viewHolder.tv_mem_size = (TextView)convertView.findViewById(R.id.tv_mem_size);
                viewHolder.cb_checked = (CheckBox ) convertView.findViewById(R.id.cb_checked);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.iv_icon.setImageDrawable(taskInfo.getIcon());
            viewHolder.tv_name.setText(taskInfo.getName());
            viewHolder.tv_mem_size.setText("使用内存：" + Formatter.formatFileSize(x.app(), taskInfo.getMemBytes()));
            viewHolder.cb_checked.setChecked(taskInfo.isChecked());
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_mem_size;
        CheckBox cb_checked;
    }
}
