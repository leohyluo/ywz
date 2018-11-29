package com.alpha.user.mapper;

import com.alpha.server.rpc.user.pojo.UserMember;

import java.util.List;

public interface UserMemberMapper {

    List<UserMember> find(UserMember userMember);
}
