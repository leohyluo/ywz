package com.alpha.his.service.etyy.impl;

import com.alpha.commons.aes.AesMrg;
import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.pojo.SysConfig;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.his.mapper.EnUrlMapper;
import com.alpha.his.pojo.dto.EnUrlDTO;
import com.alpha.his.service.etyy.UrlEncryptService;
import com.alpha.redis.RedisMrg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by MR.Wu on 2018-06-27.
 */
@Service
public class UrlEncryptServiceImpl implements UrlEncryptService {

    private static Logger logger = LoggerFactory.getLogger(UrlEncryptServiceImpl.class);

    @Autowired
    private EnUrlMapper enUrlMapper;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    /**
     * 获取加密后的URL
     *
     * @param oriUrl
     * @return
     */
    @Override
    public String getEncryptUrl(String oriUrl) {
        //只取问号后的
//        oriUrl = oriUrl.substring(oriUrl.indexOf("?") + 1, oriUrl.length());
////        String str = MD5Util.MD5(oriUrl);
////        //添加一个随机数，以免数据重复
////        str += RandomUtils.buildRandom(4);
//        String str = "";
//        str = RandomUtils.buildRandom();
//
//        //into db
//        EnUrlDTO enUrlDTO = new EnUrlDTO();
//        enUrlDTO.setUrl(oriUrl);
//        enUrlDTO.setEnUrl(str);
//        enUrlMapper.addData(enUrlDTO);
//        return str;
        return "";
    }

    /**
     * 返回真实的请求参数，但已对期AES加密
     * @param enUrl
     * @return
     */
    @Override
    public ResponseMessage getDecryptUrl(String enUrl) {

        //优选从缓存里取数据
        String keyTo = GlobalConstants.REDIS_KEY_ENURL_FLAG_ + enUrl;
        Object obj = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(keyTo, RedisMrg.DB5);
        String url;
        if(null != obj){
            url = (String) obj;
        }else{
            EnUrlDTO enUrlDTO = enUrlMapper.getData(enUrl);
            url = null == enUrlDTO ? "" : enUrlDTO.getUrl();
        }

        String key = GlobalConstants.REDIS_KEY_SYS_CONFIG.concat("_").concat("aes_key");
        SysConfig sysConfig = (SysConfig) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKeyEm(key, RedisMrg.DB1);
        String tKey = sysConfig.getConfigValue();
        String encryptUrl = AesMrg.getInstance(tKey).encode(url);

        logger.info("加密后：{}, 原串：{}", encryptUrl, url);
        return new ResponseMessage(encryptUrl);
    }
}
