package cloud.cn.androidlib.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Cloud on 2016/4/21.
 * 密钥类
 */
public class KeysUtils {
    public static String encodeMD5(String text) {
        String data = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(text.getBytes());
            data = byteToHex(result);
        } catch (NoSuchAlgorithmException e) {
            //can't reach
            e.printStackTrace();
        }
        return data;
    }

    private static String byteToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes) {
            int number = b & 0xff;
            String hexStr = Integer.toHexString(number).toUpperCase();
            if(hexStr.length() == 1) {
                sb.append("0");
            }
            sb.append(hexStr);
        }
        return sb.toString();
    }
}
