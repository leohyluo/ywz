package com.alpha.user.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.user.pojo.UserMember;

import java.util.List;
import java.util.Map;

public interface UserMemberDao extends IBaseDao<UserMember, Long> {

    List<UserMember> find(Map<String, Object> map);

    /**
     * 查询用户成员列表
     * @param userId
     * @return
     */
    List<UserMember> listByUserId(Long userId);
    
    /**
     * 查询用户成员列表
     * @param userId
     * @return
     */
    List<UserMember> listByUserIdAndMemberName(Long userId, String memberName);
    
}
