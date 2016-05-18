package cloud.cn.androidlib.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cloud.cn.androidlib.entity.AppInfo;

/**
 * Created by Cloud on 2016/5/16.
 */
public class AppInfoUtils {
    public static List<AppInfo> getAppInfos() {
        PackageManager pm = x.app().getPackageManager();
        List<PackageInfo> infos = pm.getInstalledPackages(0);//packageInfo表示清单文件
        List<AppInfo> appInfos = new ArrayList<>();
        for(PackageInfo info : infos) {
            AppInfo appInfo = new AppInfo();
            appInfo.setPackname(info.packageName);
            appInfo.setIcon(info.applicationInfo.loadIcon(pm)); //应用程序图标
            appInfo.setName(info.applicationInfo.loadLabel(pm).toString()); //应用程序名称
            int flag = info.applicationInfo.flags;
            //0表示改标志位不存在，不等于0表示标志位存在
            appInfo.setUserApp((flag & ApplicationInfo.FLAG_SYSTEM) == 0);
            appInfo.setInRom((flag & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == 0);
            appInfos.add(appInfo);
        }
        return appInfos;
    }
}
