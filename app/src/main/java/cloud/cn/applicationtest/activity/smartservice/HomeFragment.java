package cloud.cn.applicationtest.activity.smartservice;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cloud.cn.applicationtest.view.LoadingPage;

/**
 * Created by Cloud on 2016/6/10 0010.
 */
public class HomeFragment extends MyBaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView tv = new TextView(getContext());
        tv.setText("home");
        tv.setTextColor(Color.BLACK);
        return tv;
    }

    @Override
    public View onCreateSuccessView() {
        return null;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return null;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }
}
