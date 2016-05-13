package cloud.cn.applicationtest.engine;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import cloud.cn.androidlib.entity.BaseEntity;

/**
 * Created by Cloud on 2016/5/13.
 */
@Table(name="blacklist")
public class Blacklist extends BaseEntity{
    public static int TYPE_INTERCEPTER_PHONE = 0;
    public static int TYPE_INTERCEPTER_SMS = 1;
    public static int TYPE_INTERCEPTER_ALL = 2;

    @Column(name="phoneNum")
    private String phoneNum;
    @Column(name="type")
    private int type;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
