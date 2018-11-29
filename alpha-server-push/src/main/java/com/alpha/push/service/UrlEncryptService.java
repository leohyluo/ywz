package com.alpha.push.service;

/**
 * Created by MR.Wu on 2018-06-27.
 * URL加密
 */
public interface UrlEncryptService {

    /**
     * 获取加密后的URL
     * @param oriUrl
     * @return
     */
    String getEncryptUrl(String oriUrl);

}
