package com.alpha.treatscheme.service;

import java.util.List;

import com.alpha.treatscheme.pojo.DiagnosisDiseaseCheck;

public interface DiagnosisDiseaseCheckService {

	/**
	 * 根据疾病编码查找建议检查
	 * @param diseaseCode
	 * @return
	 */
	List<DiagnosisDiseaseCheck> listDiagnosisDiseaseCheck(String diseaseCode);

}
