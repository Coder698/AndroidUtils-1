package cloud.cn.applicationtest.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import org.xutils.x;

/**
 * Created by john on 2016/5/9.
 */
public class AToolsEngine {
    public static String getLocation(String phoneNum) {
        phoneNum = phoneNum.replaceAll("\\s", ""); //去空白字符
        String dbPath = x.app().getFilesDir().getAbsolutePath() + "/" + "address.db";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        String location = null;
        if (phoneNum.matches("^1[3458]\\d{9}$")) {
            //手机号码的场合
            location = getMobilePhoneLocation(db, phoneNum);
        } else {
            //其他电话
            switch (phoneNum.length()) {
                case 3:
                    location = "报警电话";
                    break;
                case 4:
                    location = "模拟器";
                    break;

                case 5:
                    location = "客服号码";
                    break;

                case 7:
                    location = "本地电话";
                    break;
                case 8:
                    location = "本地电话";
                    break;

                default:
                    if (phoneNum.length() >= 11 && phoneNum.startsWith("0")) {
                        //例如025,取后面两位比较
                        location = getOtherPhoneLocation(db, phoneNum.substring(1, 3));
                        //找不到就取三位比较
                        if(TextUtils.isEmpty(location)) {
                            location = getOtherPhoneLocation(db, phoneNum.substring(1, 4));
                        }
                    }
                    break;
            }
        }

        db.close();
        if (TextUtils.isEmpty(location)) {
            location = "归属地未知";
        }
        return location;
    }

    private static String getMobilePhoneLocation(SQLiteDatabase db, String phoneNum) {
        String sql = "select location from data2 where id = (select outkey from data1 where id = ?)";
        Cursor cursor = db.rawQuery(sql, new String[]{phoneNum.substring(0, 7)});
        String location = null;
        if (cursor.moveToNext()) {
            location = cursor.getString(cursor.getColumnIndex("location"));
        }
        cursor.close();
        return location;
    }

    private static String getOtherPhoneLocation(SQLiteDatabase db, String area) {
        String location = null;
        Cursor cursor = db.rawQuery("select location from data2 where area =?", new String[]{area});
        if (cursor.moveToNext()) {
            String address = cursor.getString(0);
            if(!TextUtils.isEmpty(address)) {
                //例如上海移动 上海联通 上海电信,需要去掉后两位
                location = address.substring(0, address.length() - 2);
            }
        }
        cursor.close();
        return location;
    }
}
