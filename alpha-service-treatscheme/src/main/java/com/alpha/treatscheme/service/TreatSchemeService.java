package com.alpha.treatscheme.service;

import java.util.List;

import com.alpha.treatscheme.pojo.vo.TreatSchemeVo;

/**
 * Created by xc.xiong on 2017/10/12.
 */
public interface TreatSchemeService {

    /**
     * 获取治疗方案
     *
     * @param diseaseCode
     */
    TreatSchemeVo getTreatScheme(String diseaseCode);

}
