package com.enetic.push.bean;

import com.google.gson.annotations.Expose;

/**
 * Created by json on 2016/5/5.
 */
public class SeacherBean {
    private int id;
    @Expose
    private String content;
    @Expose
    private int type; //用于标识，标识数据属于哪个页面的搜索。比如：首页的搜索为4，代理的搜索为6.
    @Expose
    private long createTime = System.currentTimeMillis();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
