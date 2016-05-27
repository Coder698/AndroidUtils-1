package cloud.cn.applicationtest.entity;

/**
 * Created by john on 2016/5/6.
 */
public class MessageEvent {
    private int type; //事件类型
    private String message; //事件消息
    private int what; //事件额外数据

    public MessageEvent() {

    }

    public MessageEvent(int type) {
        this(type, null);
    }

    public MessageEvent(int type, String message) {
        this(type, message, 0);
    }

    public MessageEvent(int type, String message, int what) {
        this.type = type;
        this.message = message;
        this.what = what;
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

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }
}
