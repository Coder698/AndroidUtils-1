package cloud.cn.androidlib.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cloud on 2016/4/15.
 */
public class FileUtils {
    public static void copyFile(byte[] srcData, File destFile) {
        File parentFile = destFile.getParentFile();
        if(parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(destFile);
            outputStream.write(srcData);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 删除文件夹和里面所有内容
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除指定文件夹下所有文件
    public static boolean delAllFile(String folderPath) {
        return delAllFileExceptForSpecialFile(folderPath, null);
    }

    public static boolean delAllFileExceptForSpecialFile(String folderPath, String specialFileName) {
        return delAllFileExceptForSpecialFiles(folderPath, null);
    }

    public static boolean delAllFileExceptForSpecialFiles(String folderPath,
                                                          Set<String> specialFileNames) {
        if (TextUtils.isEmpty(folderPath)) {
            return false;
        }
        if (specialFileNames == null) {
            specialFileNames = new HashSet<>();
        }
        boolean flag = false;
        File file = new File(folderPath);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (folderPath.endsWith(File.separator)) {
                temp = new File(folderPath + tempList[i]);
            } else {
                temp = new File(folderPath + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                if (!specialFileNames.contains(temp.getName())) {
                    // 不能删除指定要保留的文件
                    temp.delete();
                }
            }
            if (temp.isDirectory()) {
                delAllFile(folderPath + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(folderPath + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    public static String getAbsolutePath(String dir, String name) {
        if(!dir.endsWith("/") && !name.startsWith("/")) {
            return dir + "/" + name;
        } else if(dir.endsWith("/") && name.startsWith("/")){
            return dir + name.substring(1);
        } else {
            return dir + name;
        }
    }
}
