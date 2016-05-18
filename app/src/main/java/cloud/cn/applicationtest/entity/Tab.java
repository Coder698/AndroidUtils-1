package cloud.cn.applicationtest.entity;

/**
 * Created by Cloud on 2016/5/18.
 */
public class Tab {
    private String message;
    public Tab(String message) {
        this.message = message;
    }

    public Tab() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
