package cloud.cn.applicationtest.entity;

/**
 * Created by Cloud on 2016/5/25.
 */
public class TabMessageEvent extends MessageEvent{
    public final static int TYPE_CHANGE_TAB = 0;

    public TabMessageEvent(int type, int what) {
        super(type, null, what);
    }

    public TabMessageEvent() {}
}
