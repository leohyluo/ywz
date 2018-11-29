package com.alpha.self.diagnosis.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.self.diagnosis.dao.SyDiagnosisAnswerDao;
import com.alpha.server.rpc.diagnosis.pojo.SyDiagnosisAnswer;

@Repository
public class SyDiagnosisAnswerDaoImpl extends BaseDao<SyDiagnosisAnswer, Long> implements SyDiagnosisAnswerDao {
	
	private static final String NAMESPACE = "com.alpha.server.rpc.diagnosis.pojo.SyDiagnosisAnswer";

	public SyDiagnosisAnswerDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SyDiagnosisAnswer> listSyDiagnosisAnswer(String connCode, String wordsProp) {
		String statement = NAMESPACE.concat(".query");
		Map<String, Object> params = new HashMap<>();
        params.put("connCode", connCode);
        params.put("wordsProp", wordsProp);
        List<DataRecord> datas = super.selectForList(statement, params);
        List<SyDiagnosisAnswer> syAnswersList = JavaBeanMap.convertListToJavaBean(datas, SyDiagnosisAnswer.class);
        return syAnswersList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SyDiagnosisAnswer> listSyDiagnosisAnswer(List<String> answerCodeList, String wordsProp) {
		String statement = NAMESPACE.concat(".query");
		Map<String, Object> params = new HashMap<>();
		params.put("answerCodeList", answerCodeList);
		params.put("wordsProp", wordsProp);
		List<DataRecord> datas = super.selectForList(statement, params);
		List<SyDiagnosisAnswer> syAnswersList = JavaBeanMap.convertListToJavaBean(datas, SyDiagnosisAnswer.class);
		return syAnswersList;
	}
	
	@Override
	public Class<SyDiagnosisAnswer> getClz() {
		return SyDiagnosisAnswer.class;
	}

}
