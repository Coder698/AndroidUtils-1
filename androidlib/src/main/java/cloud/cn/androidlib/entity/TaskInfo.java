package cloud.cn.androidlib.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Cloud on 2016/5/20.
 */
public class TaskInfo {
    private Drawable icon;
    private String name;
    private long memBytes;
    private boolean usertask;
    private String packname;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMemBytes() {
        return memBytes;
    }

    public void setMemBytes(long memBytes) {
        this.memBytes = memBytes;
    }

    public boolean isUsertask() {
        return usertask;
    }

    public void setUsertask(boolean usertask) {
        this.usertask = usertask;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }
}
