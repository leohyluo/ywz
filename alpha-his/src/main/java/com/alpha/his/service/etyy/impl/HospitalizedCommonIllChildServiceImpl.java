package com.alpha.his.service.etyy.impl;

import com.alpha.commons.core.service.impl.BaseServiceImpl;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.his.dao.HospitalizedCommonIllChildDao;
import com.alpha.his.service.etyy.HospitalizedCommonIllChildService;
import com.alpha.server.rpc.his.pojo.HospitalizedCommonIllChild;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalizedCommonIllChildServiceImpl extends BaseServiceImpl<HospitalizedCommonIllChild, HospitalizedCommonIllChildDao> implements HospitalizedCommonIllChildService {

    @Override
    public HospitalizedCommonIllChild getByNoticeId(String noticeId) {
        HospitalizedCommonIllChild param = new HospitalizedCommonIllChild();
        param.setNoticeId(noticeId);
        return super.queryById(param);
    }

    @Override
    public HospitalizedCommonIllChild getLastByOutPatientNo(String outPatientNo) {
        HospitalizedCommonIllChild param = new HospitalizedCommonIllChild();
        param.setOutPatientNo(outPatientNo);
        param.setOrderByClause("update_time desc");
        List<HospitalizedCommonIllChild> list = super.query(param);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }
}
