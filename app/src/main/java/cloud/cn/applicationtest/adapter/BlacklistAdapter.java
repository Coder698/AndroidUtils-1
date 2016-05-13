package cloud.cn.applicationtest.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.x;

import java.util.List;

import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.engine.Blacklist;

/**
 * Created by Cloud on 2016/5/13.
 */
public class BlacklistAdapter extends BaseAdapter{
    private List<Blacklist> blacklists;

    public BlacklistAdapter(List<Blacklist> blacklists) {
     this.blacklists = blacklists;
    }

    @Override
    public int getCount() {
        return blacklists.size();
    }

    @Override
    public Object getItem(int position) {
        return blacklists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return blacklists.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = View.inflate(x.app(), R.layout.item_blacklist, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
            viewHolder.tv_desc = (TextView)convertView.findViewById(R.id.tv_desc);
            viewHolder.iv_delete = (ImageView)convertView.findViewById(R.id.iv_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Blacklist blacklist = blacklists.get(position);
        viewHolder.tv_title.setText(blacklist.getPhoneNum());
        if(blacklist.getType() == Blacklist.TYPE_INTERCEPTER_PHONE) {
            viewHolder.tv_desc.setText("拦截电话");
        } else if(blacklist.getType() == Blacklist.TYPE_INTERCEPTER_SMS) {
            viewHolder.tv_desc.setText("拦截短信");
        } else {
            viewHolder.tv_desc.setText("拦截所有");
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_desc;
        ImageView iv_delete;
    }
}
