package com.alpha.his;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.his.pojo.dto.PushInfoLog;
import com.alpha.redis.RedisMrg;

import java.util.List;

/**
 * Created by MR.Wu on 2018-06-28.
 */
public class RedisTest {
    public static void main(String[] args) {
//        String key = "SYS_CONFIG".concat("_").concat("enable_push");
//        SysConfig sysConfig = (SysConfig) RedisMrg.getKeyEm(key, RedisMrg.DB1);
//        System.out.println(sysConfig);
//
        String keyPre = GlobalConstants.REDIS_KEY_EXIST_PUSHLOG_PRE;
        List<PushInfoLog> pushInfoLogsLast = (List<PushInfoLog>) RedisMrg.getInstance("192.168.29.191", "6380", "redis_ywz").getKeyEm(keyPre, RedisMrg.DB3);
        pushInfoLogsLast.stream().map(e -> e.getCardNo() + " --" + e.getpNo()).forEach(System.out::println);
    }
}
