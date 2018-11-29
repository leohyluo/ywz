package com.alpha.user.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.user.dao.DiagnosisMedicalTemplateDao;
import com.alpha.user.pojo.DiagnosisMedicalTemplate;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/9/5.
 * 建议检查
 */
@Repository
public class DiagnosisMedicalTemplateDaoImpl extends BaseDao<DiagnosisMedicalTemplate, Long> implements DiagnosisMedicalTemplateDao {

    private static final String STATEMENT_HEAD = "com.alpha.user.pojo.DiagnosisMedicalTemplate";

    /**
     * 查询病历模板
     *
     * @param templateCode
     * @return
     */
    public DiagnosisMedicalTemplate getDiagnosisMedicalTemplate(String templateCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("templateCode", templateCode);
        DataRecord data = super.selectForDataRecord(STATEMENT_HEAD + ".getByTemplateCode", params);
        DiagnosisMedicalTemplate diagnosisDisease = (DiagnosisMedicalTemplate) JavaBeanMap.convertMap2JavaBean(data, DiagnosisMedicalTemplate.class);
        return diagnosisDisease;
    }

    @Autowired
    public DiagnosisMedicalTemplateDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<DiagnosisMedicalTemplate> getClz() {
        return DiagnosisMedicalTemplate.class;
    }


}
