package com.alpha.commons.core.util;


import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.commons.core.mapper.OpenDepartmentMapper;
import com.alpha.commons.core.pojo.DepartMent;
import com.alpha.redis.RedisMrg;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

/**
 * Created by HP on 2018/10/12.
 */
public class DeptUtils {

    private static Logger logger = LoggerFactory.getLogger(DeptUtils.class);

    @Value("${redis.ip}")
    private static String redisIp;

    @Value("${redis.port}")
    private static String redisPort;

    @Value("${redis.pwd}")
    private static String redisPwd;

    public static List<String> getDepartment(){
        OpenDepartmentMapper openDepartmentMapper= SpringContextHolder.getBean(OpenDepartmentMapper.class);
        //redis 取开放的科室
        List<String> listpartment = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getSetString("partmentlist", 13);
        logger.info("当前开放的科室有：{}", new Gson().toJson(listpartment));
        if (null == listpartment || listpartment.size() < 1) {
            logger.info("当前redis 没有加载开发的科室，从数据库加载。。。。");
            List<DepartMent> list = openDepartmentMapper.selectAll();
            if (list.size() > 0) {
                list.forEach(a -> {
                    listpartment.add(a.getName());
                });
                RedisMrg.getInstance(redisIp, redisPort, redisPwd).setSetString(listpartment, "partmentlist", 13);
            }
        }
        return listpartment;
    }



}
