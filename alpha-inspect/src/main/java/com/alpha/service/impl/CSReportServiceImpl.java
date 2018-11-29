package com.alpha.service.impl;

import com.alpha.commons.core.mapper.CSReportMapper;
import com.alpha.commons.core.pojo.inspcetion.CSReport;
import com.alpha.commons.core.pojo.inspcetion.FSRequest;
import com.alpha.commons.core.util.SoapUtil;
import com.alpha.service.CSReportService;
import com.alpha.utils.InspectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.PatternSyntaxException;

/**
 * Created by edz on 2018/10/31.
 */
@Service
public class CSReportServiceImpl implements CSReportService {


    private static final Logger logger = LoggerFactory.getLogger(CSReportServiceImpl.class);

    private static String wsdl_request="http://172.16.240.124:7803/BS25011/BS25011?wsdl";
    private static String fid_request="BS25011";

    @Autowired
    private CSReportMapper csReportMapper;

    @Override
    public List<CSReport> cs_request(String startTime, String endTime) {
        logger.info("获取超声报告");
        long a=System.currentTimeMillis();
        String requestxml =FSJobServiceImpl.getrequest(wsdl_request, fid_request, startTime, endTime);
        List<String> list = SoapUtil.parseETYYxml(requestxml);
        List<CSReport> datalist=new ArrayList<>();
        String str = CSReport.class.toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return null;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.CS_result);
            objectList.stream().forEach(e -> {
                CSReport temp =(CSReport)e;
                temp.setRecordDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                temp.setPushStatus(0);
                datalist.add(temp);
            });
        }
        if(datalist.size()>0){
            csReportMapper.insertList(datalist);
        }
        logger.info("获取超声报告耗时：{} 毫秒",(System.currentTimeMillis()-a));
        return datalist;
    }

    @Override
    public void push() {
        long a=System.currentTimeMillis();
        CSReport param=new CSReport();
        param.setPushStatus(0);
        param.setPatientType("1");
        Map<Integer,String> map=new HashMap<>();
        List<CSReport> list=csReportMapper.select(param);
        if(null !=list && list.size()>0){
            list.stream().forEach( e -> {
                if(e.getHospitalizedNo() !=null){
                    String cardNo=e.getHospitalizedNo();
                    //根据id 去获取卡号
                    String id=e.getRequestId();
                    String content="检验报告";
                    String title="亲爱的"+e.getName()+", "+e.getStudyPart()+"已经处理完成";
                    String note="点击查看报告";
                    String url="http://h5ppe.zhyaoshi.com/ocr/#/hospitalHistory/1024/8100447421";
                    String resultxml= InspectionUtils.push160(cardNo,content,"",title,note,url);
                    map.put(e.getId(),resultxml);
                }else {
                    map.put(e.getId(),"00");
                }
                long b=System.currentTimeMillis();
                logger.info("发送 {} 条数据共耗时：{} 毫秒",list.size(),(b-a));
            });
            InspectionUtils.recordPush(map,3);
        }
    }
}
