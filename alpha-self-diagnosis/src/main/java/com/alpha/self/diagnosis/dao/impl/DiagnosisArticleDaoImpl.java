package com.alpha.self.diagnosis.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alpha.commons.core.dao.impl.BaseDao;
import com.alpha.commons.core.sql.dto.DataRecord;
import com.alpha.commons.core.util.JavaBeanMap;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.self.diagnosis.dao.DiagnosisArticleDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisArticle;

@Repository
public class DiagnosisArticleDaoImpl extends BaseDao<DiagnosisArticle, Long> implements DiagnosisArticleDao {

	private final String NAME_SPACE = "com.alpha.server.rpc.diagnosis.pojo.DiagnosisArticle";

	@SuppressWarnings("unchecked")
	@Override
	public List<DiagnosisArticle> listTop5ByMainSympCode(String mainSympCode) {
		String statement = NAME_SPACE.concat(".queryArticleByMainCode");
		Map<String, Object> params = new HashMap<>();
        params.put("mainSympCode", mainSympCode);
        List<DataRecord> datas = super.selectForList(statement, params);
        List<DiagnosisArticle> dmcs = JavaBeanMap.convertListToJavaBean(datas, DiagnosisArticle.class);
        return dmcs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DiagnosisArticle getByArticleCode(String articleCode) {
		String statement = NAME_SPACE.concat(".queryByArticleCode");
		Map<String, Object> params = new HashMap<>();
		params.put("articleCode", articleCode);
		List<DataRecord> datas = super.selectForList(statement, params);
        List<DiagnosisArticle> dmcs = JavaBeanMap.convertListToJavaBean(datas, DiagnosisArticle.class);
        DiagnosisArticle article = null;
        if(CollectionUtils.isNotEmpty(dmcs)) {
        	article = dmcs.get(0);
        }
        return article;
	}
	
	@Autowired
	public DiagnosisArticleDaoImpl(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	@Override
	public Class<DiagnosisArticle> getClz() {
		return DiagnosisArticle.class;
	}
}
