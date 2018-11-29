package com.alpha.his.service.etyy.impl;

import com.alpha.commons.core.service.impl.BaseServiceImpl;
import com.alpha.his.dao.UserCardInfoDao;
import com.alpha.his.service.etyy.UserCardInfoService;
import com.alpha.server.rpc.his.pojo.UserCard;
import org.springframework.stereotype.Service;

@Service
public class UserCardInfoServiceImpl extends BaseServiceImpl<UserCard, UserCardInfoDao> implements UserCardInfoService {

}
