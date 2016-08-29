package com.zdr.findnews.entity;

import java.util.List;

/**
 * 新闻头条JSON
 * String httpUrl = "http://v.juhe.cn/toutiao/index?type=top&key=APPKEY";
 * Created by zdr on 16-8-21.
 */
public class TopNewsJSON {

    /**
     * code : 0
     * data : {"channel":"热点","article":[{},{}]}
     */

    private int code;
    /**
     * channel : 热点
     * article : [{},{}]
     */

    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String channel;
        private List<TopNews> article;

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public List<TopNews> getArticle() {
            return article;
        }

        public void setArticle(List<TopNews> article) {
            this.article = article;
        }
    }
}
