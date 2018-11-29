package com.alpha.his.service.etyy.impl;

import com.alpha.commons.core.service.impl.BaseServiceImpl;
import com.alpha.his.dao.HospitalizedNoticeDao;
import com.alpha.his.service.etyy.HospitalizedNoticeService;
import com.alpha.server.rpc.his.pojo.HospitalizedNotice;
import org.springframework.stereotype.Service;

@Service
public class HospitalizedNoticeServiceImpl extends BaseServiceImpl<HospitalizedNotice, HospitalizedNoticeDao> implements HospitalizedNoticeService {
    @Override
    public HospitalizedNotice getByNoticeId(String noticeId) {
        HospitalizedNotice param = new HospitalizedNotice();
        param.setNoticeId(noticeId);
        return super.queryById(param);
    }
}
