package cloud.cn.androidlib.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cloud.cn.androidlib.entity.AppInfo;

/**
 * Created by Cloud on 2016/5/16.
 */
public class SystemInfoUtils {
    /**
     * 得到当前系统所有app信息
     * @return
     */
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

    /**
     * 查询service是否运行
     * @param serviceName service的全限定名字
     * @return
     */
    public static boolean isServiceRunning(String serviceName) {
        ActivityManager am = (ActivityManager) x.app().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
        for(ActivityManager.RunningServiceInfo info : infos) {
            if(info.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    //外部存储是否可用
    public static boolean isExternalStorageAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 得到运行的进程数量
     * @return
     */
    public static int getRunningProcessCount() {
        ActivityManager am = (ActivityManager)x.app().getSystemService(Context.ACTIVITY_SERVICE);
        return am.getRunningAppProcesses().size();
    }

    /**
     * 得到外部存储可用空间大小,字节,可以通过Formatter.formatFileSize获取可取格式
     * @return
     */
    public static long getExternalAvailableBytes() {
        if(isExternalStorageAvailable()) {
            File dir = Environment.getExternalStorageDirectory();
            FileSysData fileSysData = getFileSysData(dir.getPath());
            return fileSysData.availableBytes;
        } else {
            return -1;
        }
    }

    /**
     * 得到外部存储总空间大小，字节,可以通过Formatter.formatFileSize获取可取格式
     * @return
     */
    public static long getExternalTotalBytes() {
        if(isExternalStorageAvailable()) {
            File dir = Environment.getExternalStorageDirectory();
            FileSysData fileSysData = getFileSysData(dir.getPath());
            return fileSysData.totalBytes;
        } else {
            return -1;
        }
    }

    /**
     * 得到内部存储总大小
     * @return
     */
    public static long getInternalTotalBytes() {
        FileSysData fileSysData = getFileSysData(Environment.getDataDirectory().getPath());
        return fileSysData.totalBytes;
    }

    /**
     * 得到外部存储可用大小
     * @return
     */
    public static long getInternalAvailBytes() {
        FileSysData fileSysData = getFileSysData(Environment.getDataDirectory().getPath());
        return fileSysData.availableBytes;
    }

    /**
     * 得到可用内容大小
     * @return
     */
    public static long getAvailMemBytes() {
        ActivityManager am = (ActivityManager)x.app().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }

    /**
     * 得到内存大小
     * @return
     */
    public static long getTotalMenBytes() {
        if(Build.VERSION.SDK_INT >= 16) {
            ActivityManager am = (ActivityManager)x.app().getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            am.getMemoryInfo(mi);
            return mi.totalMem;
        } else {
            String dir = "/proc/meminfo";
            try {
                FileReader fr = new FileReader(dir);
                BufferedReader br = new BufferedReader(fr, 2048);
                String memoryLine = br.readLine();
                String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
                br.close();
                return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024l;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    static FileSysData getFileSysData(String path) {
        long blockSize;
        long totalBlocks;
        long availableBlocks;
        StatFs stat = new StatFs(path);
        //API18以后getBlockSize和getBlockCount改成getBlockSizeLong和getBlockCountLong
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
            availableBlocks = stat.getAvailableBlocks();
        }
        FileSysData fileSysData = new FileSysData();
        fileSysData.totalBytes = totalBlocks * blockSize;
        fileSysData.availableBytes = availableBlocks * blockSize;
        return fileSysData;
    }

    static class FileSysData {
        long totalBytes;
        long availableBytes;
    }
}
