package cloud.cn.androidlib.utils;

import android.text.TextUtils;

import org.xutils.common.util.IOUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Cloud on 2016/4/15.
 */
public class FileUtils {
    /**
     * 拷贝assets中资源到指定文件
     *
     * @param assetFileName
     * @param destFile
     */
    public static boolean copyAssets(String assetFileName, File destFile) {
        boolean isSuccess = false;
        createParentDirs(destFile);
        InputStream assetIS = null;
        BufferedOutputStream bos = null;
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            assetIS = x.app().getAssets().open(assetFileName);
            bos = new BufferedOutputStream(new FileOutputStream(destFile));
            while ((len = assetIS.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            isSuccess = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (assetIS != null) {
                try {
                    assetIS.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    /**
     * 拷贝字节流到文件
     *
     * @param srcData
     * @param destFile
     */
    public static boolean copyFile(byte[] srcData, File destFile) {
        boolean isSuccess = false;
        createParentDirs(destFile);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(destFile);
            outputStream.write(srcData);
            isSuccess = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    /**
     * 创建父文件夹
     *
     * @param file
     */
    public static void createParentDirs(File file) {
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
    }

    // 删除文件夹和里面所有内容
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            File myFilePath = new File(folderPath);
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
        if (!dir.endsWith("/") && !name.startsWith("/")) {
            return dir + "/" + name;
        } else if (dir.endsWith("/") && name.startsWith("/")) {
            return dir + name.substring(1);
        } else {
            return dir + name;
        }
    }

    public static byte[] readBytes(InputStream in) throws IOException {
        return IOUtil.readBytes(in);
    }

    public static String readStr(InputStream in) throws IOException {
        return IOUtil.readStr(in);
    }

    public static String readStr(InputStream in, String charset) throws IOException {
        return IOUtil.readStr(in, charset);
    }

    public static void writeStr(OutputStream out, String str) throws IOException {
        IOUtil.writeStr(out, str);
    }

    public static void writeStr(OutputStream out, String str, String charset) throws IOException {
        IOUtil.writeStr(out, str, charset);
    }

    /**
     * 解压文件到当前目录，默认不自动创建zip包对应包名文件夹
     * @param zipFile
     * @return
     */
    public static boolean unZipFiles(File zipFile) {
        return unZipFiles(zipFile, false);
    }

    /**
     * 解压文件到当前目录，根据createDir判断是否创建对应zip包名文件夹
     * @param zipFile
     * @throws IOException
     */
    public static boolean unZipFiles(File zipFile, boolean createDir) {
        if(zipFile.isDirectory() || !zipFile.exists()) {
            LogUtil.d(zipFile.getName() + "不存在或是个文件夹，无法解压");
            return false;
        }
        String fileName = zipFile.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(!suffix.equalsIgnoreCase("zip")) {
            LogUtil.d(zipFile.getName() + "不是zip文件，无法解压");
            return false;
        }

        String parentPath = zipFile.getParent();
        //如果parentPath不存在则返回空
        if(parentPath == null) {
            parentPath = "";
        }
        if(createDir) {
            String prefix = fileName.substring(0, fileName.lastIndexOf("."));//得到不包含后缀的文件名
            parentPath = parentPath + "/" + prefix;
        }
        return unZipFiles(zipFile, parentPath); //解压到当前目录，文件夹为zip包名
    }

    /**
     * 解压到指定文件夹,不会创建对应zip包名
     * @param zipFile
     * @param descDir
     * @throws IOException
     */
    public static boolean unZipFiles(File zipFile, String descDir) {
        LogUtil.d("开始解压" + zipFile.getName() + ",解压到" + descDir);
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        descDir.replaceAll("\\*", "/");
        if(!descDir.endsWith("/")) {
            descDir += "/";
        }
        ZipFile zip = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            zip = new ZipFile(zipFile);
            byte[] buf1 = new byte[1024];
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                in = zip.getInputStream(entry);
                String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                if (!file.exists()) {
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if (new File(outPath).isDirectory()) {
                    in.close();
                    in = null;
                    continue;
                }
                out = new FileOutputStream(outPath);

                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
                in.close();
                in = null;
                out.close();
                out = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtil.closeQuietly(in);
            IOUtil.closeQuietly(out);
        }
        LogUtil.d("解压完毕" + zipFile.getName());
        return true;
    }
}
