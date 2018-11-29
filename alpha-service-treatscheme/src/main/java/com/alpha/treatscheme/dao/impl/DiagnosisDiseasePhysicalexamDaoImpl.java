package com.alpha.treatscheme.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.treatscheme.dao.DiagnosisDiseasePhysicalexamDao;
import com.alpha.treatscheme.pojo.DiagnosisDiseasePhysicalexam;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/9/5.
 * 查体
 */
@Repository
public class DiagnosisDiseasePhysicalexamDaoImpl extends BaseDao<DiagnosisDiseasePhysicalexam, Long> implements DiagnosisDiseasePhysicalexamDao {

    private static final String STATEMENT_HEAD = "com.alpha.treatscheme.pojo.DiagnosisDiseasePhysicalexam";


    /**
     * 查询建议查体
     *
     * @param diseaseCode
     * @return
     */
    public List<DiagnosisDiseasePhysicalexam> listDiagnosisDiseasePhysicalexam(String diseaseCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("diseaseCode", diseaseCode);
        List<DataRecord> datas = super.selectForList(STATEMENT_HEAD + ".queryByDiseaseCode", params);
        List<DiagnosisDiseasePhysicalexam> list = JavaBeanMap.convertListToJavaBean(datas, DiagnosisDiseasePhysicalexam.class);
        return list;
    }


    @Autowired
    public DiagnosisDiseasePhysicalexamDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<DiagnosisDiseasePhysicalexam> getClz() {
        return DiagnosisDiseasePhysicalexam.class;
    }


}
