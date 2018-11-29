package com.alpha.commons.core.service;

/**
 * Created by xc.xiong on 2017/9/5.
 */
public interface SysSequenceService {
    /**
     * 获取序号
     *
     * @param sequenceKey
     * @return
     */
    Long getNextSequence(String sequenceKey);
}
