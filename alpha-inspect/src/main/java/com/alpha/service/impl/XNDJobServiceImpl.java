package com.alpha.service.impl;

import com.alpha.commons.core.mapper.XNDRequestMapper;
import com.alpha.commons.core.mapper.XNDResultMapper;
import com.alpha.commons.core.pojo.inspcetion.FSRequest;
import com.alpha.commons.core.pojo.inspcetion.JYRequest;
import com.alpha.commons.core.pojo.inspcetion.XNDRequest;
import com.alpha.commons.core.pojo.inspcetion.XNDResult;
import com.alpha.commons.core.util.SoapUtil;
import com.alpha.commons.core.util.StaticHttpclientCall;
import com.alpha.pojo.vo.InspectionDetailVO;
import com.alpha.service.XNDJobService;
import com.alpha.utils.InspectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by edz on 2018/10/30.
 */
@Service
public class XNDJobServiceImpl implements XNDJobService {

    private static final Logger logger = LoggerFactory.getLogger(XNDJobServiceImpl.class);

    private static String wsdl_xnd_request="http://172.16.240.124:7803/BS25007/BS25007?wsdl";

    private static String fid_xnd_request="BS25007";

    private static String wsdl_xnd_result="http://172.16.240.124:7803/BS25008/BS25008?wsdl";

    private static String fid_xnd_result="BS25008";

    @Resource
    private XNDRequestMapper xndRequestMapper;
    @Resource
    private XNDResultMapper xndResultMapper;


    @Override
    public List<XNDRequest> xnd_request(String startTime, String endTime) {
        logger.info("获取心脑电申请单");
        long a=System.currentTimeMillis();
        String requestxml = getrequest(wsdl_xnd_request, fid_xnd_request, startTime, endTime);
        List<String> list = SoapUtil.parseETYYxml(requestxml);
        List<XNDRequest> datalist=new ArrayList<>();
        String str = XNDRequest.class.toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return null;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.xnd_request);
            objectList.stream().forEach(e -> {
                XNDRequest temp =(XNDRequest)e;
                temp.setRecordDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                temp.setPushStatus(0);
                datalist.add(temp);
            });
        }
        if(datalist.size()>0){
            xndRequestMapper.insertList(datalist);
        }
        logger.info("获取心脑电申请耗时：{} 毫秒",(System.currentTimeMillis()-a));
        return datalist;
    }

    @Override
    public List<XNDResult> xnd_result(String startTime, String endTime) {
        logger.info("获取心脑电结果");
        long a=System.currentTimeMillis();
        String requestxml = getresult(wsdl_xnd_result, fid_xnd_result, startTime, endTime);
        List<String> list = SoapUtil.parseETYYxml(requestxml);
        List<XNDResult> datalist=new ArrayList<>();
        String str = XNDResult.class.toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return null;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.xnd_result);
            objectList.stream().forEach(e -> {
                XNDResult temp =(XNDResult)e;
                temp.setRecordDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                datalist.add(temp);
            });
        }
        if(datalist.size()>0){
            xndResultMapper.insertList(datalist);
        }
        logger.info("获取心脑电结果：{} 毫秒",(System.currentTimeMillis()-a));
        return datalist;
    }

    @Override
    public List<XNDResult> xnd_resultByReportId(String reportId) {
        String requestxml = getresultByReportId(wsdl_xnd_result, fid_xnd_result,reportId);
        List<String> list = SoapUtil.parseETYYxml(requestxml);
        List<XNDResult> datalist=new ArrayList<>();
        String str = XNDResult.class.toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return null;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.xnd_result);
            objectList.stream().forEach(e -> {
                XNDResult temp =(XNDResult)e;
                temp.setRecordDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                datalist.add(temp);
            });
        }

        return datalist;
    }

    public static void main(String[] args) {
        new XNDJobServiceImpl().xnd_resultByReportId("468746");
    }

    @Override
    public void push() {
        long a=System.currentTimeMillis();
        XNDRequest param=new XNDRequest();
        param.setPushStatus(0);
        param.setPatientType("1");
        Map<Integer,String> map=new HashMap<>();
        List<XNDRequest> list=xndRequestMapper.select(param);
        if(null !=list && list.size()>0){
            list.stream().forEach( e -> {
                if(e.getReportId() !=null){
                    String cardNo=e.getReportId();
                    String content="检查报告";
                    String title="亲爱的"+e.getPatientName()+", "+e.getReportName()+"已经处理完成";
                    String note="点击查看报告";
                    String url="www.baidu.com";
                    String resultxml= InspectionUtils.push160(cardNo,content,"",title,note,url);
                    map.put(e.getId(),resultxml);
                }else {
                    map.put(e.getId(),"00");
                }
                long b=System.currentTimeMillis();
                logger.info("发送 {} 条数据共耗时：{} 毫秒",list.size(),(b-a));
            });
            InspectionUtils.recordPush(map,2);
        }
    }


    public static String getrequest(String wsdl,String fid,String startTime,String endTime){
        String result= null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(StaticHttpclientCall.getFid(fid));
            stringBuffer.append(" and REPORT_DATE between to_date('"+startTime);
            stringBuffer.append("','yyyy-mm-dd hh24:mi:ss') and to_date('").append(endTime).append("'," + "'yyyy-mm-dd hh24:mi:ss')");
            stringBuffer.append(" and PATIENT_TYPE ='1'");
            stringBuffer.append("</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("心脑电申请单参数是：{}",input);
            result = StaticHttpclientCall.servicetest(wsdl,input);
            logger.info("心脑电申请单结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("心脑电申请单信息异常："+e.toString());
        }
        return result;
    }


    public static String getresultByReportId(String wsdl,String fid,String reportId){
        String result= null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(StaticHttpclientCall.getFid(fid));
            stringBuffer.append(" and  REPORT_ID ='");
            stringBuffer.append(reportId+"'");
            stringBuffer.append("</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("心脑电结果信息参数是：{}",input);
            result = StaticHttpclientCall.servicetest(wsdl,input);
            logger.info("心脑电结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求心脑电结果信息异常："+e.toString());
        }
        return result;
    }


    public static String getresult(String wsdl,String fid,String startTime,String endTime){
        String result= null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(StaticHttpclientCall.getFid(fid));
            stringBuffer.append(" and MEASURE_DATE between to_date('"+startTime);
            stringBuffer.append("','yyyy-mm-dd hh24:mi:ss') and to_date('").append(endTime).append("'," +
                    "'yyyy-mm-dd hh24:mi:ss')");
            stringBuffer.append("</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("心脑电结果信息参数是：{}",input);
            result = StaticHttpclientCall.servicetest(wsdl,input);
            logger.info("心脑电结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求心脑电结果信息异常："+e.toString());
        }
        return result;
    }
}
