package cloud.cn.applicationtest.entity;

import java.util.List;

/**
 * Created by Cloud on 2016/5/24.
 */
public class NewsMenuData {
    private long id;
    private String title;
    private int type;
    private String url;
    private List<NewsMenuData> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<NewsMenuData> getChildren() {
        return children;
    }

    public void setChildren(List<NewsMenuData> children) {
        this.children = children;
    }
}
