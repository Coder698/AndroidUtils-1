package cloud.cn.applicationtest.entity;

import java.util.List;


/**
 * Created by Cloud on 2016/6/29.
 */
public class HomeInfo {
    private String[] picture;
    private List<AppInfo> list;

    public String[] getPicture() {
        return picture;
    }

    public void setPicture(String[] picture) {
        this.picture = picture;
    }

    public List<AppInfo> getList() {
        return list;
    }

    public void setList(List<AppInfo> list) {
        this.list = list;
    }
}
