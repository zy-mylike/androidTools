package com.enetic.push.bean;

public class SortModel {

    private Object obj;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
