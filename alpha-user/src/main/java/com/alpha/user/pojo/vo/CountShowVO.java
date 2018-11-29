package com.alpha.user.pojo.vo;

/**
 * Created by HP on 2018/5/15.
 * 综合统计信息
 */
public class CountShowVO<T> {

    private String date;
    private T list;

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
