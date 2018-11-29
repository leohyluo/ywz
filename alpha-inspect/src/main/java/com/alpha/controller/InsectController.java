package com.alpha.controller;


import com.alpha.common.ResponseInfo;
import com.alpha.commons.core.pojo.inspcetion.CSReport;
import com.alpha.commons.util.StringUtils;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.pojo.ao.YSInspectAO;
import com.alpha.pojo.vo.CSreportVO;
import com.alpha.pojo.vo.FSrequestVO;
import com.alpha.pojo.vo.XNDrequestVO;
import com.alpha.pojo.vo.YSInspcetVO;
import com.alpha.service.YSInspectionService;
import com.alpha.utils.InspectionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/16.
 */
@RestController
@RequestMapping("ys/inspection/")
public class InsectController {

    private static Logger logger= LoggerFactory.getLogger(InsectController.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private YSInspectionService ysInspectionService;

    @GetMapping("/detailInfo")
    public Object Inspection(YSInspectAO ysInspectAO){
        ResponseInfo<Object> responseInfo=new ResponseInfo<>();
        long a=System.currentTimeMillis();
            List<YSInspcetVO> list=ysInspectionService.detailInfo(ysInspectAO);
            if(null ==list || list.size()<1){
                responseInfo.setCode(ResponseStatus.NULLDATA.code());
                return responseInfo;
            }
            responseInfo.setResultInfo(list);
        logger.info("耗时：{}",System.currentTimeMillis()-a);
        return responseInfo;
    }

    @GetMapping("/userInfo")
    public Object userInfo(String key,String value){
        Map<String,String> param=new HashMap<>();
        param.put(key,value);
        return InspectionUtils.getInfo(param);
    }

}
