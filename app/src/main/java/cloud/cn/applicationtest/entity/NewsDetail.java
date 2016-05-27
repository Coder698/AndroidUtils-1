package cloud.cn.applicationtest.entity;

import java.util.List;

/**
 * Created by Cloud on 2016/5/26.
 */
public class NewsDetail {
    private String countcommenturl;
    private String more;
    private String title;
    private List<News> news;
    private List<Topic> topic;
    private List<News> topnews;

    public String getCountcommenturl() {
        return countcommenturl;
    }

    public void setCountcommenturl(String countcommenturl) {
        this.countcommenturl = countcommenturl;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Topic> getTopic() {
        return topic;
    }

    public void setTopic(List<Topic> topic) {
        this.topic = topic;
    }

    public List<News> getTopnews() {
        return topnews;
    }

    public void setTopnews(List<News> topnews) {
        this.topnews = topnews;
    }

    public class News {
        private long id;
        private boolean comment;
        private String topimage;
        private String commentlist;
        private String commenturl;
        private String listimage;
        private String pubdate;
        private String title;
        private String type;
        private String url;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public boolean isComment() {
            return comment;
        }

        public void setComment(boolean comment) {
            this.comment = comment;
        }

        public String getCommentlist() {
            return commentlist;
        }

        public void setCommentlist(String commentlist) {
            this.commentlist = commentlist;
        }

        public String getCommenturl() {
            return commenturl;
        }

        public void setCommenturl(String commenturl) {
            this.commenturl = commenturl;
        }

        public String getListimage() {
            return listimage;
        }

        public void setListimage(String listimage) {
            this.listimage = listimage;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTopimage() {
            return topimage;
        }

        public void setTopimage(String topimage) {
            this.topimage = topimage;
        }
    }
    public class Topic {
        private long id;
        private String description;
        private String listimage;
        private int sort;
        private String title;
        private String url;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getListimage() {
            return listimage;
        }

        public void setListimage(String listimage) {
            this.listimage = listimage;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
