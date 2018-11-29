package com.alpha.commons.core.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.commons.core.pojo.DepartMent;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by HP on 2018/4/28.
 * 开发特定的科室进行预问诊
 */
@Mapper
public interface OpenDepartmentMapper extends MyMapper<DepartMent>{

}
