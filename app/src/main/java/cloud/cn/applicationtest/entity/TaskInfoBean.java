package cloud.cn.applicationtest.entity;

import cloud.cn.androidlib.entity.TaskInfo;

/**
 * Created by Cloud on 2016/5/20.
 */
public class TaskInfoBean extends TaskInfo{
    private boolean checked;

    public TaskInfoBean() {}
    public TaskInfoBean(TaskInfo taskInfo) {
        setUsertask(taskInfo.isUsertask());
        setPackname(taskInfo.getPackname());
        setIcon(taskInfo.getIcon());
        setMemBytes(taskInfo.getMemBytes());
        setName(taskInfo.getName());
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
