package cloud.cn.applicationtest.activity.smartservice;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cloud.cn.androidlib.activity.BaseFragment;

/**
 * Created by Cloud on 2016/6/10 0010.
 */
public class HotFragment extends BaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        tv.setText("hot");
        tv.setTextColor(Color.BLACK);
        return tv;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {

    }
}
