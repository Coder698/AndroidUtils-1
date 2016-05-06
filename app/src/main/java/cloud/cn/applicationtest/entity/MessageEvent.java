package cloud.cn.applicationtest.entity;

/**
 * Created by john on 2016/5/6.
 */
public class MessageEvent {
    private int type;
    private String message;

    public MessageEvent() {

    }

    public MessageEvent(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
