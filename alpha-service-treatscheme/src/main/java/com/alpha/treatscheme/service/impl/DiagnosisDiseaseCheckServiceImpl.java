package com.alpha.treatscheme.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alpha.treatscheme.dao.DiagnosisDiseaseCheckDao;
import com.alpha.treatscheme.pojo.DiagnosisDiseaseCheck;
import com.alpha.treatscheme.service.DiagnosisDiseaseCheckService;

@Service
public class DiagnosisDiseaseCheckServiceImpl implements DiagnosisDiseaseCheckService {
	
	@Resource
	private DiagnosisDiseaseCheckDao diagnosisDiseaseCheckDao;

	@Override
	public List<DiagnosisDiseaseCheck> listDiagnosisDiseaseCheck(String diseaseCode) {
		return diagnosisDiseaseCheckDao.listDiagnosisDiseaseCheck(diseaseCode);
	}

   

}
