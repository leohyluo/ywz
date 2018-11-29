package com.alpha.self.diagnosis.dao;

import java.util.List;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.diagnosis.pojo.SyDiagnosisAnswer;

public interface SyDiagnosisAnswerDao extends IBaseDao<SyDiagnosisAnswer, Long> {

	/**
	 * 根据答案大类编码找小类答案或同义词等
	 * @param connCode
	 * @param wordsProp
	 * @return
	 */
	List<SyDiagnosisAnswer> listSyDiagnosisAnswer(String connCode, String wordsProp);
	
	/**
	 * 根据答案小类编码找所属大类
	 * @param connCode
	 * @param wordsProp
	 * @return
	 */
	List<SyDiagnosisAnswer> listSyDiagnosisAnswer(List<String> answerCodeList, String wordsProp);
}
