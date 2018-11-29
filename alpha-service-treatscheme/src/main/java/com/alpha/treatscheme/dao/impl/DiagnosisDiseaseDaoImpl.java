package com.alpha.treatscheme.dao.impl;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.pojo.DiagnosisDisease;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.redis.RedisMrg;
import com.alpha.treatscheme.dao.DiagnosisDiseaseDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xc.xiong on 2017/9/5.
 */
@Repository
public class DiagnosisDiseaseDaoImpl extends BaseDao<DiagnosisDisease, Long> implements DiagnosisDiseaseDao {

	@Value("${redis.ip}")
	private String redisIp;

	@Value("${redis.port}")
	private String redisPort;

	@Value("${redis.pwd}")
	private String redisPwd;

    @Autowired
    public DiagnosisDiseaseDaoImpl(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }

    @Override
    public Class<DiagnosisDisease> getClz() {
        return DiagnosisDisease.class;
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 查询所有的疾病
     *
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<DiagnosisDisease> listDiagnosisDisease(Map<String, Object> params) {

        List<DataRecord> datas = super.selectForList("com.alpha.commons.core.pojo.DiagnosisDisease.queryDiagnosisDisease", params);
        List<DiagnosisDisease> dds = JavaBeanMap.convertListToJavaBean(datas, DiagnosisDisease.class);
        return dds;
    }

    /**
     * 查询疾病
     *
     * @param diseaseCode
     * @return
     */
    public DiagnosisDisease getDiagnosisDisease(String diseaseCode) {
		DiagnosisDisease disease = null;
		try {
			disease = (DiagnosisDisease) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKeyEm(diseaseCode, RedisMrg.DB1);
		} catch (Exception e) {
			logger.warn("从redis获取疾病出现异常");
		}
		if(disease == null) {
			Map<String, Object> params = new HashMap<>();
			params.put("diseaseCode", diseaseCode);
			DataRecord data = super.selectForDataRecord("com.alpha.commons.core.pojo.DiagnosisDisease.getDiagnosisDisease", params);
			disease = (DiagnosisDisease) JavaBeanMap.convertMap2JavaBean(data, DiagnosisDisease.class);
		}
        return disease;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisDisease> listUserHotDisease() {
		Map<String, Object> params = new HashMap<>();
		params.put("threshold", GlobalConstants.HOT_DISEASE_THRESHOLD);
		params.put("size", GlobalConstants.HOT_DISEASE_COUNT);
		List<DataRecord> datas = super.selectForList("com.alpha.commons.core.pojo.DiagnosisDisease.getUserHotDisease", params);
	    List<DiagnosisDisease> dds = JavaBeanMap.convertListToJavaBean(datas, DiagnosisDisease.class);
	    return dds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisDisease> listAnyDiseaseOrderByDefaultOrder(int size) {
		Map<String, Object> params = new HashMap<>();
		params.put("size", size);
		List<DataRecord> datas = super.selectForList("com.alpha.commons.core.pojo.DiagnosisDisease.getDefaultHotDisease", params);
	    List<DiagnosisDisease> dds = JavaBeanMap.convertListToJavaBean(datas, DiagnosisDisease.class);
	    return dds;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisDisease> listByDiseaseName(String diseaseName) {
		Map<String, Object> params = new HashMap<>();
		params.put("diseaseName", diseaseName);
		List<DataRecord> datas = super.selectForList("com.alpha.commons.core.pojo.DiagnosisDisease.getByDiseaseName", params);
	    List<DiagnosisDisease> dds = JavaBeanMap.convertListToJavaBean(datas, DiagnosisDisease.class);
	    return dds;
	}


}
