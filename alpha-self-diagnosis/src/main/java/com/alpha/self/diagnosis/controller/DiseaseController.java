package com.alpha.self.diagnosis.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.WebUtils;
import com.alpha.self.diagnosis.pojo.vo.TreatAdviceVo;
import com.alpha.self.diagnosis.service.DiagnosisDiseaseService;
import com.alpha.treatscheme.pojo.DiagnosisDiseaseCheck;
import com.alpha.treatscheme.pojo.DiagnosisDiseaseDept;
import com.alpha.treatscheme.pojo.vo.DiagnosisDiseaseCheckVo;
import com.alpha.treatscheme.service.DiagnosisDiseaseCheckService;
import com.alpha.treatscheme.service.DiagnosisDiseaseDeptService;

@RestController
@RequestMapping("/disease")
public class DiseaseController {
	
	@Resource
	private DiagnosisDiseaseService diseaseService;
	@Resource
	private DiagnosisDiseaseCheckService diagnosisDiseaseCheckService;
	@Resource
	private DiagnosisDiseaseDeptService diagnosisDiseaseDeptService;

	/**
	 * 查询疾病诊疗意见
	 * @return
	 */
	@PostMapping("/treatmentadvice/query/{diseaseCode}")
	public ResponseMessage queryTreatmentAdvice(@PathVariable String diseaseCode) {
		TreatAdviceVo treatAdvice = diseaseService.queryTreatAdvice(diseaseCode);
		return WebUtils.buildSuccessResponseMessage(treatAdvice);
	}
	
	/**
	 * 查询疾病推荐科室
	 * @param diseaseCode
	 * @return
	 */
	@PostMapping("/dept/query/{diseaseCode}")
	public ResponseMessage queryDepartment(@PathVariable String diseaseCode) {
		List<DiagnosisDiseaseDept> deptList = diagnosisDiseaseDeptService.listDiagnosisDiseaseDept(diseaseCode);
		return WebUtils.buildSuccessResponseMessage(deptList);
	}
	
	/**
	 * 查询疾病建议检查
	 * @param diseaseCode
	 * @return
	 */
	@PostMapping("/check/query/{diseaseCode}")
	public ResponseMessage queryDiseaseCheck(@PathVariable String diseaseCode) {
		List<DiagnosisDiseaseCheck> checkList = diagnosisDiseaseCheckService.listDiagnosisDiseaseCheck(diseaseCode);
		List<DiagnosisDiseaseCheckVo> voList = checkList.stream().map(DiagnosisDiseaseCheckVo::new).collect(Collectors.toList());
		return WebUtils.buildSuccessResponseMessage(voList);
	}
}
