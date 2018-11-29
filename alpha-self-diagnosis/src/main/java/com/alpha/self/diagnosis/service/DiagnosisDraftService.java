package com.alpha.self.diagnosis.service;

import com.alpha.commons.enums.AppType;
import com.alpha.commons.exception.ServiceException;
import com.alpha.self.diagnosis.pojo.vo.BasicQuestionVo;

import java.util.List;

/**
 * 预问诊草稿业务接口
 */
public interface DiagnosisDraftService {

    /**
     * 获取问诊草稿
     * @param pno
     * @return
     */
    List<BasicQuestionVo> getDraft(String pno, AppType appType) throws ServiceException;

    /**
     * 病情确认列表
     * @param diagnosisId
     * @return
     */
    List<BasicQuestionVo> confirm(Long diagnosisId, AppType appType) throws ServiceException;
}
