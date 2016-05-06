package cloud.cn.applicationtest.entity;

/**
 * Created by john on 2016/5/6.
 */
public class DialogMessageEvent extends MessageEvent{
    public static int TYPE_WRONG_PASS = 1;

    public DialogMessageEvent() {}

    public DialogMessageEvent(int type, String message) {
        super(type, message);
    }
}
