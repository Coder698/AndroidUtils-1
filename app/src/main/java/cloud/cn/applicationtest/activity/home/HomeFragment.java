package cloud.cn.applicationtest.activity.home;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import cloud.cn.androidlib.activity.BaseFragment;
import cloud.cn.androidlib.ui.SimpleCameraPreview;
import cloud.cn.androidlib.utils.CameraUtils;
import cloud.cn.androidlib.utils.FileUtils;
import cloud.cn.androidlib.utils.PrefUtils;
import cloud.cn.applicationtest.AppConstants;
import cloud.cn.applicationtest.R;
import cloud.cn.applicationtest.engine.SafeEngine;
import cloud.cn.applicationtest.entity.DialogMessageEvent;

/**
 * Created by Cloud on 2016/4/5.
 */
@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {
    @ViewInject(R.id.gv_home)
    private GridView gv_home;
    @ViewInject(R.id.camera_preview)
    private FrameLayout camera_preview;
    private int[] icons;
    private String[] names;
    private Camera camera;
    private int wrongPassNum;
    private SimpleCameraPreview cameraPreview;

    @Override
    protected void initVariables() {
        icons = new int[]{R.drawable.safe, R.drawable.callmsgsafe, R.drawable.app,
                R.drawable.taskmanager, R.drawable.netmanager, R.drawable.trojan, R.drawable.sysoptimize,
                R.drawable.atools, R.drawable.settings};
        names = new String[]{"手机防盗", "通讯卫士", "软件管家", "进程管理", "流量统计", "病毒查杀",
                "缓存清理", "高级工具", "设置中心"};
        wrongPassNum = 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        gv_home.setAdapter(new HomeAdapter());
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("手机防盗".equals(names[position])) {
                    safeClicked();
                } else if("通讯卫士".equals(names[position])) {

                } else if("高级工具".equals(names[position])) {
                    gotoATools();
                } else if("设置中心".equals(names[position])) {
                    gotoSettings();
                }
            }
        });
    }

    private void gotoATools() {
        Intent intent = new Intent(getActivity(), AToolsActivity.class);
        startActivity(intent);
    }

    private void gotoSettings() {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    private void safeClicked() {
        if("".equals(PrefUtils.getString(AppConstants.PREF.SAFE_PASSWORD, ""))) {
            //没有保存密码的场合，保存密码
            SafeEngine.showSavePassDialog(getActivity());
        } else {
            //已经保存密码的场合，输入密码
            SafeEngine.showEnterPassDialog(getActivity());
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        //初始化相机
        camera = CameraUtils.getCameraInstance(1);
        if(camera != null) {
            cameraPreview = new SimpleCameraPreview(getActivity(), camera);
            camera_preview.addView(cameraPreview);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(camera != null) {
            camera_preview.removeView(cameraPreview);
            camera.release();
            camera = null;
        }
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDialogMessage(DialogMessageEvent messageEvent) {
        if(messageEvent.getType() == DialogMessageEvent.TYPE_WRONG_PASS) {
            wrongPassNum++;
            if(wrongPassNum > 2) {
                //输错三次拍照留念
                takePhoto();
            }
        }
    }

    private void takePhoto() {
        if(camera != null) {
            camera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    FileUtils.copyFile(data, new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "aaa.jpg"));
                }
            });
        }
    }

    class HomeAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(x.app(), R.layout.item_gridview_home, null);
            ImageView iv_item_icon = (ImageView)view.findViewById(R.id.iv_item_icon);
            TextView tv_item_name = (TextView)view.findViewById(R.id.tv_item_name);
            iv_item_icon.setImageResource(icons[position]);
            tv_item_name.setText(names[position]);
            return view;
        }
    }
}
