package cloud.cn.androidlib.entity;

import org.xutils.db.annotation.Column;

/**
 * Created by Cloud on 2016/5/13.
 */
public class BaseEntity {
    @Column(name = "id", isId = true)
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
