package cloud.cn.applicationtest.db;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * Created by Cloud on 2016/5/13.
 */
public class DbUtils {
    public static String DB_NAME = "mydb.db";
    public static int DB_VERSION = 1;
    public static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName(DB_NAME)
            .setDbVersion(DB_VERSION)
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    db.getDatabase().enableWriteAheadLogging();
                }
            })
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                }
            });
    public static DbManager getInstance() {
        return x.getDb(daoConfig);
    }
}
