package com.alpha.his.pojo.dto;

/**
 * Created by MR.Wu on 2018-06-27.
 */
public class EnUrlDTO {
    private int id;

    /**
     * 原始URL
     */
    private String url;

    /**
     * 加密后的URL
     */
    private String enUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEnUrl() {
        return enUrl;
    }

    public void setEnUrl(String enUrl) {
        this.enUrl = enUrl;
    }
}
