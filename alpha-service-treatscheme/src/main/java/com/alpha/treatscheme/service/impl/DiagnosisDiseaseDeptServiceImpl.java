package com.alpha.treatscheme.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alpha.treatscheme.dao.DiagnosisDiseaseDeptDao;
import com.alpha.treatscheme.pojo.DiagnosisDiseaseDept;
import com.alpha.treatscheme.service.DiagnosisDiseaseDeptService;

@Service
public class DiagnosisDiseaseDeptServiceImpl implements DiagnosisDiseaseDeptService {

	@Resource
	private DiagnosisDiseaseDeptDao diagnosisDiseaseDeptDao;
	
	@Override
	public List<DiagnosisDiseaseDept> listDiagnosisDiseaseDept(String diseaseCode) {
		return diagnosisDiseaseDeptDao.listDiagnosisDiseaseDept(diseaseCode);
	}
	
	
}
