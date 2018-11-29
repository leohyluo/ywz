package com.alpha.user.dao;

import com.alpha.user.pojo.DiagnosisMedicalTemplate;

/**
 * Created by xc.xiong on 2017/10/12.
 */
public interface DiagnosisMedicalTemplateDao {

    /**
     * 查询病历模板
     *
     * @param templateCode
     * @return
     */
    DiagnosisMedicalTemplate getDiagnosisMedicalTemplate(String templateCode);

}
