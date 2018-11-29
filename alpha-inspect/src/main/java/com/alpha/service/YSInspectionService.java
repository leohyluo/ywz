package com.alpha.service;

import com.alpha.pojo.ao.YSInspectAO;
import com.alpha.pojo.vo.CSreportVO;
import com.alpha.pojo.vo.FSrequestVO;
import com.alpha.pojo.vo.XNDrequestVO;
import com.alpha.pojo.vo.YSInspcetVO;

import java.util.List;

/**
 * Created by Administrator on 2018/8/16.
 */
public interface YSInspectionService {

    List<YSInspcetVO> detailInfo(YSInspectAO ysInspectAO);

    List<XNDrequestVO> XNDrequest(YSInspectAO ysInspectAO);

    List<FSrequestVO> FSrequest(YSInspectAO ysInspectAO);

    List<CSreportVO> CSrequest(YSInspectAO ysInspectAO);
}
