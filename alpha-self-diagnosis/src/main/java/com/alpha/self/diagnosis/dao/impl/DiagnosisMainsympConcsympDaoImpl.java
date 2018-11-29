package com.alpha.self.diagnosis.dao.impl;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.self.diagnosis.dao.DiagnosisMainsympConcsympDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp;
import com.alpha.server.rpc.diagnosis.pojo.vo.MedicineQuestionVo;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/9/5.
 */
@Repository
public class DiagnosisMainsympConcsympDaoImpl extends BaseDao<DiagnosisMainsympConcsymp, Long> implements DiagnosisMainsympConcsympDao {


    @Autowired
    public DiagnosisMainsympConcsympDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<DiagnosisMainsympConcsymp> getClz() {
        return DiagnosisMainsympConcsymp.class;
    }

    @Override
    public List<DiagnosisMainsympConcsymp> listCommonSymp(String mainSympCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp.queryCommonSymp", params);
        List<DiagnosisMainsympConcsymp> dmcs = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympConcsymp.class);
        return dmcs;
    }

    /**
     * 查询主症状下，疾病的伴随症状
     *
     * @param mainSympCode
     * @return
     */
    @Override
    public List<DiagnosisMainsympConcsymp> listDiagnosisMainsympConcsymp(String mainSympCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp.queryDiagnosisMainsympConcsymp", params);
        List<DiagnosisMainsympConcsymp> dmcs = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympConcsymp.class);
        return dmcs;
    }

    @Override
    public List<DiagnosisMainsympConcsymp> listCommonDiagnosisMainsympConcsymp(String mainSympCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp.queryCommonDiagnosisMainsympConcsymp", params);
        List<DiagnosisMainsympConcsymp> dmcs = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympConcsymp.class);
        return dmcs;
    }

    /**
     * 查询主症状的常见伴随症状
     *
     * @param mainSympCode
     * @return
     */
    public List<DiagnosisMainsympConcsymp> listConcsymp(String mainSympCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp.queryCommonSymptom", params);
        List<DiagnosisMainsympConcsymp> dmcs = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympConcsymp.class);
        return dmcs;
    }

    /**
     * 查询伴随症状
     *
     * @param mainSympCode
     * @param concSympCodes
     * @return
     */
    @Override
    public List<DiagnosisMainsympConcsymp> listDiagnosisMainsympConcsymp(String mainSympCode, Collection concSympCodes) {
        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        params.put("concSympCodes", concSympCodes);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp.queryByconcSympCodes", params);
        List<DiagnosisMainsympConcsymp> dmcs = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympConcsymp.class);
        return dmcs;
    }

    @Override
    public List<DiagnosisMainsympConcsymp> listByConcSympCodes(Collection concSympCodes) {
        Map<String, Object> params = new HashMap<>();
        params.put("concSympCodes", concSympCodes);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp.queryByconcSympCodesWithoutMainSymp", params);
        List<DiagnosisMainsympConcsymp> dmcs = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympConcsymp.class);
        return dmcs;
    }

    @Override
    public List<DiagnosisMainsympConcsymp> listByConcSympNames(String mainSympCode, Collection concSympNames) {
        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        params.put("concSympNames", concSympNames);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp.queryByconcSympName", params);
        List<DiagnosisMainsympConcsymp> dmcs = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympConcsymp.class);
        return dmcs;
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<MedicineQuestionVo> listDiagnosisMainsympConcsymp(String mainSympCode, List<String> concSympCodes) {
		Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        params.put("concSympCodes", concSympCodes);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp.queryConcSympByCodes", params);
        List<MedicineQuestionVo> dmcs = JavaBeanMap.convertListToJavaBean(datas, MedicineQuestionVo.class);
        return dmcs;
	}

    @Override
    public DiagnosisMainsympConcsymp getMaxWeightConcSymp(String mainSympCode) {
        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp.getMaxWeightConcSympByMainCode", params);
        List<DiagnosisMainsympConcsymp> dmcs = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympConcsymp.class);
        DiagnosisMainsympConcsymp result = null;
        if(CollectionUtils.isNotEmpty(dmcs)) {
            result = dmcs.get(0);
        }
        return result;
    }

    @Override
    public List<DiagnosisMainsympConcsymp> listByMainSymptomAndconcSympCodes(String mainSympCode, List<String> concSympCodes) {
        Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        params.put("concSympCodes", concSympCodes);
        List<DataRecord> datas = super.selectForList("com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainsympConcsymp.queryByMainSymptomAndconcSympCodes", params);
        List<DiagnosisMainsympConcsymp> dmcs = JavaBeanMap.convertListToJavaBean(datas, DiagnosisMainsympConcsymp.class);
        return dmcs;
    }


}
