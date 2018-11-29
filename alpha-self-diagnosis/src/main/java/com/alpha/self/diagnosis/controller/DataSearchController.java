package com.alpha.self.diagnosis.controller;

import com.alpha.commons.util.StringUtils;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import com.alpha.self.diagnosis.pojo.vo.IAnswerVo;
import com.alpha.self.diagnosis.service.DataSearchService;
import com.alpha.self.diagnosis.service.SymptomAccompanyService;
import com.alpha.self.diagnosis.utils.ServiceUtil;
import com.alpha.server.rpc.diagnosis.pojo.DiagnosisMainSymptoms;
import com.alpha.server.rpc.diagnosis.pojo.SearchKeys;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.user.dao.UserInfoDao;
import com.alpha.user.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by xc.xiong on 2017/9/5.
 * 数据查询
 */
@RestController
@RequestMapping("/data/search")
public class DataSearchController {

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private DataSearchService dataSearchService;
    @Resource
    private UserInfoDao userInfoDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSearchController.class);


    /**
     * 既往史搜索
     *
     * @param
     * @return
     */
    @GetMapping("/pastmedicalHistory")
    public ResponseMessage pastmedicalHistorySearch(Long userId, String keyword) {
        keyword = ServiceUtil.stringFilter(keyword);
        if (StringUtils.isEmpty(keyword))
            return new ResponseMessage();
        UserInfo userInfo = userInfoDao.queryByUserId(Long.valueOf(userId));
        List<IAnswerVo> answerList = dataSearchService.searchPastMedicalHistory(userInfo, keyword);
        return WebUtils.buildSuccessResponseMessage(answerList);
    }

    /**
     * 过敏史搜索
     *
     * @param
     * @return
     */
    @PostMapping("/allergicHistory")
    public ResponseMessage allergicHistorySearch(Long userId, String keyword) {
        keyword = ServiceUtil.stringFilter(keyword);
        if (StringUtils.isEmpty(keyword))
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        UserInfo userInfo = userInfoDao.queryByUserId(Long.valueOf(userId));
        List<IAnswerVo> answerList = dataSearchService.searchAllergyHistory(userInfo, keyword);
        return WebUtils.buildSuccessResponseMessage(answerList);
    }

    /**
     * 伴随症状搜索
     *
     * @param
     * @param @ModelAttribute
     * @return
     */
    @PostMapping(value = "/concsymp")
    public ResponseMessage diagnosisStart(Long userId, Long diagnosisId, String sympCode, String keyword) {
        if (userId == null || StringUtils.isEmpty(keyword)) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        keyword = ServiceUtil.stringFilter(keyword);
        if (StringUtils.isEmpty(keyword)) {
            return new ResponseMessage();
        }
        UserInfo userInfo = userInfoService.queryByUserId(userId);
        if (userInfo == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.USER_NOT_FOUND);
        }
        LinkedHashSet<IAnswerVo> answerVos = dataSearchService.searchCocnSymptom(diagnosisId, sympCode, userInfo, keyword);
        return WebUtils.buildSuccessResponseMessage(answerVos);
    }

    @PostMapping("/skey")
    public ResponseMessage keys(String cardNo, String pNo, String keys) {
        if (StringUtils.isEmpty(keys) || keys == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        SearchKeys searchKeys = new SearchKeys();
        searchKeys.setCardNo(cardNo);
        searchKeys.setpNo(pNo);
        searchKeys.setKeys(keys);
        DiagnosisMainSymptoms mainSymptoms = dataSearchService.searchMainSymptom(searchKeys);
        if (mainSymptoms != null) {
            return WebUtils.buildSuccessResponseMessage(mainSymptoms);
        } else {
            return WebUtils.buildResponseMessage(ResponseStatus.NOT_FOUND);
        }
    }
}
