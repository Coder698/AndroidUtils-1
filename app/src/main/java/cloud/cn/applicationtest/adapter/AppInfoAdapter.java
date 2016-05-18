package cloud.cn.applicationtest.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import cloud.cn.androidlib.entity.AppInfo;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.entity.Tab;

/**
 * Created by Cloud on 2016/5/18.
 */
public class AppInfoAdapter extends BaseAdapter{
    List appInfos;

    public AppInfoAdapter(List appInfos) {
        this.appInfos = appInfos;
    }

    @Override
    public int getCount() {
        return appInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return appInfos.get(position);
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
            AppInfo appInfo = (AppInfo)getItem(position);
            if(convertView == null || !(convertView instanceof LinearLayout)) {
                convertView = View.inflate(x.app(), R.layout.item_app_info, null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (ImageView)convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
                viewHolder.tv_install_location = (TextView)convertView.findViewById(R.id.tv_install_location);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.iv_icon.setImageDrawable(appInfo.getIcon());
            viewHolder.tv_name.setText(appInfo.getName());
            viewHolder.tv_install_location.setText(appInfo.isInRom()? "手机内存" : "外部存储");
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_install_location;
    }
}
