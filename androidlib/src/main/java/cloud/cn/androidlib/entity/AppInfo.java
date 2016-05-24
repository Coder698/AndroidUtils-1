package cloud.cn.androidlib.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Cloud on 2016/5/16.
 */
public class AppInfo {
    private Drawable icon;
    private String name;
    private String packname;
    private String md5Sign; //md5加密过的签名
    private boolean inRom; //true代码装在内部存储中,false表示装在外部存储中
    private boolean userApp;

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

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }

    public boolean isInRom() {
        return inRom;
    }

    public void setInRom(boolean inRom) {
        this.inRom = inRom;
    }

    public boolean isUserApp() {
        return userApp;
    }

    public void setUserApp(boolean userApp) {
        this.userApp = userApp;
    }

    public String getMd5Sign() {
        return md5Sign;
    }

    public void setMd5Sign(String md5Sign) {
        this.md5Sign = md5Sign;
    }
}