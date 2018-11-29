package com.alpha.self.diagnosis.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alpha.commons.core.util.StringUtil;
import com.alpha.commons.util.StringUtils;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import com.alpha.self.diagnosis.pojo.vo.DiseaseVo;
import com.alpha.self.diagnosis.service.DiagnosisDiseaseService;
import com.alpha.treatscheme.pojo.vo.TreatSchemeVo;
import com.alpha.treatscheme.service.TreatSchemeService;
import com.alpha.user.service.UserInfoService;

/**
 * Created by xc.xiong on 2017/10/12.
 */
@RestController
@RequestMapping("/treatscheme")
public class TreatSchemeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TreatSchemeController.class);

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private TreatSchemeService treatSchemeService;

    @Resource
    private DiagnosisDiseaseService diagnosisDiseaseService;

    @GetMapping("/diseaseList")
    public ResponseMessage diseaseList(String keyword) {
        String diseaseName = keyword;
        if(StringUtils.isEmpty(diseaseName)) {
        	return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        List<DiseaseVo> diseaseList = diagnosisDiseaseService.findByDiseaseName(diseaseName);
        return WebUtils.buildSuccessResponseMessage(diseaseList);
    }


    /**
     * 治疗方案搜索
     *
     * @param
     * @param @ModelAttribute
     * @return
     */
    @PostMapping(value = "/get")
    public ResponseMessage treatScheme(String diseaseCode) {
        try {
            if (StringUtils.isEmpty(diseaseCode)) {
                return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
            }
            TreatSchemeVo treatSchemeVo = treatSchemeService.getTreatScheme(diseaseCode);
            if (treatSchemeVo != null)
                return WebUtils.buildSuccessResponseMessage(treatSchemeVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseMessage(ResponseStatus.INVALID_VALUE.code(), "未找到相关治疗方案");
    }


}
