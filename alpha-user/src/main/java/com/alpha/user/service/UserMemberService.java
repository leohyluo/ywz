package com.alpha.user.service;

import java.util.List;

import com.alpha.server.rpc.user.pojo.UserMember;

public interface UserMemberService {
    
    void create(UserMember userMember);
    
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
    
    /**
     * 根据用户id删除
     * @param memberId
     */
    void deleteByUserId(Long userId);
}
