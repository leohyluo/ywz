package com.alpha.self.diagnosis.controller;

import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.WebUtils;
import com.alpha.self.diagnosis.service.InitDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MR.Wu on 2018-04-09.
 */
@RestController
@RequestMapping("/data/cache")
public class DataCacheController {

    @Autowired
    private InitDataService initDataService;

    /**
     * 缓存疾病信息
     * @return
     */
    @GetMapping("/dd")
    public ResponseMessage dd() {
        initDataService.getAllDiagnosisDisease();
        return WebUtils.buildSuccessResponseMessage();
    }

    /**
     * 缓存伴随症状
     * @return
     */
    @GetMapping("/initConcSymptom")
    public ResponseMessage initConcSymptom() {
        initDataService.getConcSymptom();
        return WebUtils.buildSuccessResponseMessage();
    }

    /**
     * 缓存主症状的下问题信息
     * @return
     */
    @GetMapping("/mques")
    public ResponseMessage mques() {
        initDataService.cacheMainsymps();
        return WebUtils.buildSuccessResponseMessage();
    }

    /**
     * 新增预问诊 科室 需要调这个接口，否则三十分钟后才生效
     * @return
     */
    @GetMapping("/cacheOpenDepartment")
    public ResponseMessage cacheOpenDepartment() {
        initDataService.cacheOpenDepartment();
        return WebUtils.buildSuccessResponseMessage();
    }

    @GetMapping("/sysConfig")
    public ResponseMessage cacheSysConfig() {
        initDataService.cacheSysConfig();
        return WebUtils.buildSuccessResponseMessage();
    }

    @GetMapping("/all")
    public ResponseMessage cacheAll() {
        this.dd();
        this.initConcSymptom();
        this.mques();
        this.cacheOpenDepartment();
        this.cacheSysConfig();
        return WebUtils.buildSuccessResponseMessage();
    }

    @GetMapping("/preQuestionAnswer")
    public ResponseMessage cachePreQuestionAnswer() {
        initDataService.cachePreQuestionAnswer();
        return WebUtils.buildSuccessResponseMessage();
    }

}
