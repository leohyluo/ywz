package com.alpha.self.diagnosis.dao;

import java.util.List;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisArticle;

/**
 * 文章资讯
 * @author Administrator
 *
 */
public interface DiagnosisArticleDao extends IBaseDao<DiagnosisArticle, Long> {

	/**
	 * 根据主症状找出前5编文章
	 * @param mainSympCode
	 * @return
	 */
    List<DiagnosisArticle> listTop5ByMainSympCode(String mainSympCode);
    
    /**
     * 查看文章详情
     * @param articleCode
     * @return
     */
    DiagnosisArticle getByArticleCode(String articleCode);

}
