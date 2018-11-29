package com.alpha.push.service.impl;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.util.RandomUtils;
import com.alpha.push.domain.EnUrlDTO;
import com.alpha.push.mapper.EnUrlMapper;
import com.alpha.push.service.UrlEncryptService;
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
     * <p>
     * 先入redis.然后由定时器落地到数据库中...
     *
     * @param oriUrl
     * @return
     */
    @Override
    public String getEncryptUrl(String oriUrl) {
        //只取问号后的
        oriUrl = oriUrl.substring(oriUrl.indexOf("?") + 1, oriUrl.length());
        String str = "";
        str = RandomUtils.buildRandom();

        //放入缓存中
        EnUrlDTO enUrlDTO = new EnUrlDTO();
        enUrlDTO.setUrl(oriUrl);
        enUrlDTO.setEnUrl(str);
        String key = str;
        String value = oriUrl;
        RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKey(GlobalConstants.REDIS_KEY_ENURL_FLAG_ + key, value, RedisMrg.DB5);

        return str;
    }

}
