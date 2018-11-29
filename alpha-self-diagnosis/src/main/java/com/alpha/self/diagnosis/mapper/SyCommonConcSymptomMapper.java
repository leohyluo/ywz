package com.alpha.self.diagnosis.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.server.rpc.diagnosis.pojo.SyCommonConcSymptom;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SyCommonConcSymptomMapper extends MyMapper<SyCommonConcSymptom> {

    /**
     * 根据常见伴随症状编码查找同义词
     * @param commonSympCodeList
     * @return
     */
    List<SyCommonConcSymptom> listByConnCode(@Param(value = "commonSympCodeList") List<String> commonSympCodeList);
}
