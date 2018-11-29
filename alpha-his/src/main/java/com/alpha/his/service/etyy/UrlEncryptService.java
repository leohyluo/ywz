package com.alpha.his.service.etyy;

import com.alpha.commons.web.ResponseMessage;

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

    /**
     * 获取解密后的URL
     * @param enUrl
     * @return
     */
    ResponseMessage getDecryptUrl(String enUrl);
}
