package com.alpha.commons.core.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.commons.core.pojo.UserCardInfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/16.
 */
public interface UserCardInfoMapper extends MyMapper<UserCardInfo>{

    Integer updateUserCardInfo(HashMap map);

    UserCardInfo selectUserCardInfo(Map map);

    Integer saveUserCardInfo(UserCardInfo userCardInfo);

    UserCardInfo orderOrPhone(@Param("order") Integer order, @Param("phone") String phone);
}
