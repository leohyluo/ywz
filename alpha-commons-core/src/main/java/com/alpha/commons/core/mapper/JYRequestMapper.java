package com.alpha.commons.core.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.commons.core.pojo.inspcetion.JYRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by edz on 2018/10/29.
 */
@Mapper
public interface JYRequestMapper extends MyMapper<JYRequest> {

    List<JYRequest> selectByParam(@Param("param") Map<String, String> param);

    void updateByMap(@Param("param")Map<String, Integer> param);
}
