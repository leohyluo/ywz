package com.alpha.commons.core.service.impl;

import com.alpha.commons.core.mapper.SysSequenceMapper;
import com.alpha.commons.core.pojo.SysSequence;
import com.alpha.commons.core.service.SysSequenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by xc.xiong on 2017/9/5.
 */
@Service
public class SysSequenceServiceImpl implements SysSequenceService {

    private static final Logger logger = LoggerFactory.getLogger(SysSequenceServiceImpl.class);
    private static Object syncLock = new Object();

    @Autowired
    private SysSequenceMapper sysSequenceMapper;

    /**
     * 获取序号
     *
     * @param sequenceKey
     * @return
     */
    @Transactional
    public Long getNextSequence(String sequenceKey) {
        synchronized (syncLock) {
            for (; ; ) {
                Long value = getSequence(sequenceKey);
                if (value > 0)
                    return value;
                logger.error("获取序列号失败：{}", sequenceKey);
                LockSupport.parkNanos(1000);
            }
        }
    }

    /**
     * 获取序号，并在这个基础上加N返回给客户端
     *
     * @param sequenceKey
     * @return
     */
    public Long getSequence(String sequenceKey) {
        SysSequence sequence = sysSequenceMapper.getSequence(sequenceKey);
        if (sequence == null) {
            logger.error("无法找到此序号:{}", sequenceKey);
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();

        Long oldCurrentValue = sequence.getCurrentValue();
        Long newCurrentValue = sequence.getCurrentValue() + sequence.getIncrement().longValue();

        map.put("oldCurrentValue", oldCurrentValue);
        map.put("newCurrentValue", newCurrentValue);
        map.put("sequenceKey", sequenceKey);
        //logger.info("old：{}, new：{}, seq：{}",oldCurrentValue, newCurrentValue,sequenceKey);
        int count = sysSequenceMapper.updateCurrentValue(map);
        if (count == 1)
            return newCurrentValue;
        return null;
    }

    public static void main(String[] args) {
        Long a = 1L;
        Integer b = 1;
        Long c = a + b;
        System.out.println(c);
    }

}
