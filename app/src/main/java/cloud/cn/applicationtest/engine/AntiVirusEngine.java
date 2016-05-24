package cloud.cn.applicationtest.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cloud.cn.applicationtest.db.DbUtils;

/**
 * Created by Cloud on 2016/5/24.
 */
public class AntiVirusEngine {
    public static boolean isVirus(String sign) {
        SQLiteDatabase db = DbUtils.getAntiVirusDb();
        Cursor cursor = db.rawQuery("select count(*) from datable where md5=?", new String[]{sign});
        long count = 0;
        if(cursor.moveToNext()) {
            count = cursor.getLong(0);
        }
        return count > 0? true : false;
    }
}
