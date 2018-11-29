package com.alpha.user.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.user.pojo.UserInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/9/5.
 */
@Component
public interface UserInfoDao extends IBaseDao<UserInfo, Long> {

    /**
     * 查询用户信息
     *
     * @param externalUserId
     * @param inType
     * @return
     */
    //UserInfo getUserInfoByExternalUserId(String externalUserId, int inType);

    /**
     * 查询用户信息
     *
     * @param externalUserId
     * @param inType
     * @return
     */
    List<UserInfo> listByExternalUserId(String externalUserId);

    UserInfo queryByUserId(Long userId);

    List<UserInfo> query(Map<String, Object> map);

    /**
     * 根据手机号码查询用户信息
     *
     * @param phoneNumber
     * @return
     */
    UserInfo getByPhoneNumber(String phoneNumber);

    /**
     * 根据用户id查询下属成员的信息
     *
     * @param userId
     * @return
     */
    List<UserInfo> listUserMemberInfo(Long userId);

    /**
     * 根据用户id查询
     *
     * @param userId
     * @return
     */
    List<UserInfo> listByUserId(List<Long> userId);

    /**
     * 根据门诊号获取用户信息
     *
     * @param outPatientNo
     * @return
     */
    UserInfo getByOutPatientNo(String outPatientNo);

    Long insertByBatch(List<UserInfo> t);
}
