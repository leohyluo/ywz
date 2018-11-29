package com.alpha.treatscheme.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.treatscheme.dao.DiagnosisDiseaseCheckDao;
import com.alpha.treatscheme.pojo.DiagnosisDiseaseCheck;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/9/5.
 * 建议检查
 */
@Repository
public class DiagnosisDiseaseCheckDaoImpl extends BaseDao<DiagnosisDiseaseCheck, Long> implements DiagnosisDiseaseCheckDao {

    private static final String STATEMENT_HEAD = "com.alpha.treatscheme.pojo.DiagnosisDiseaseCheck";

    /**
     * 查询建议检查
     *
     * @param diseaseCode
     * @return
     */
    public List<DiagnosisDiseaseCheck> listDiagnosisDiseaseCheck(String diseaseCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("diseaseCode", diseaseCode);
        List<DataRecord> datas = super.selectForList(STATEMENT_HEAD + ".queryByDiseaseCode", params);
        List<DiagnosisDiseaseCheck> list = JavaBeanMap.convertListToJavaBean(datas, DiagnosisDiseaseCheck.class);
        return list;
    }

    @Autowired
    public DiagnosisDiseaseCheckDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<DiagnosisDiseaseCheck> getClz() {
        return DiagnosisDiseaseCheck.class;
    }


}
