package com.alpha.commons.core.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.commons.core.pojo.inspcetion.FSRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by edz on 2018/10/31.
 */
public interface FSRequestMapper extends MyMapper<FSRequest> {
    List<FSRequest> selectByParam(@Param("param") Map<String, String> param);

    void updateByMap(Map<String, Integer> param);
}
