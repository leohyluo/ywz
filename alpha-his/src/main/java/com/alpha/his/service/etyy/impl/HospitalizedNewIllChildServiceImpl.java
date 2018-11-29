package com.alpha.his.service.etyy.impl;

import com.alpha.commons.core.service.impl.BaseServiceImpl;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.his.dao.HospitalizedNewIllChildDao;
import com.alpha.his.service.etyy.HospitalizedNewIllChildService;
import com.alpha.server.rpc.his.pojo.HospitalizedNewIllChild;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalizedNewIllChildServiceImpl extends BaseServiceImpl<HospitalizedNewIllChild, HospitalizedNewIllChildDao> implements HospitalizedNewIllChildService {

    @Override
    public HospitalizedNewIllChild getByNoticeId(String noticeId) {
        HospitalizedNewIllChild param = new HospitalizedNewIllChild();
        param.setNoticeId(noticeId);
        return super.queryById(param);
    }

    @Override
    public HospitalizedNewIllChild getLastByOutPatientNo(String outPatientNo) {
        HospitalizedNewIllChild param = new HospitalizedNewIllChild();
        param.setOutPatientNo(outPatientNo);
        param.setOrderByClause("update_time desc");
        List<HospitalizedNewIllChild> list = super.query(param);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }
}
