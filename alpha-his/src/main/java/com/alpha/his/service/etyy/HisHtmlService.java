package com.alpha.his.service.etyy;

import com.alpha.his.pojo.dto.HisHtmlDTO;

import java.util.List;

/**
 * Created by HP on 2018/5/29.
 * 获取嘉和电子病历 html文本
 */
public interface HisHtmlService {
     List<HisHtmlDTO> getHtml(String doctorName,String visitTime);
}
