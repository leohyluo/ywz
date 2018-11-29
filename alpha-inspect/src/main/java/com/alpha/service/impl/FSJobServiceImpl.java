package com.alpha.service.impl;

import com.alpha.commons.core.mapper.FSRequestMapper;
import com.alpha.commons.core.mapper.FSResultMapper;
import com.alpha.commons.core.pojo.inspcetion.FSRequest;
import com.alpha.commons.core.pojo.inspcetion.FSResult;
import com.alpha.commons.core.util.SoapUtil;
import com.alpha.commons.core.util.StaticHttpclientCall;
import com.alpha.service.FSJobService;
import com.alpha.utils.InspectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by edz on 2018/10/31.
 */
@Service
public class FSJobServiceImpl implements FSJobService {

    private static final Logger logger = LoggerFactory.getLogger(FSJobServiceImpl.class);

    private static String wsdl_request="http://172.16.240.124:7803/BS25009/BS25009?wsdl";
    private static String fid_request="BS25009";

    private static String wsdl_result="http://172.16.240.124:7803/BS25010/BS25010?wsdl";
    private static String fid_result="BS25010";



    @Resource
    private FSRequestMapper fsRequestMapper;
    @Resource
    private FSResultMapper fsResultMapper;

    @Override
    public List<FSRequest> fs_request(String startTime, String endTime) {
        logger.info("获取放射申请单");
        long a=System.currentTimeMillis();
        String requestxml = getrequest(wsdl_request, fid_request, startTime, endTime);
        List<String> list = SoapUtil.parseETYYxml(requestxml);
        List<FSRequest> datalist=new ArrayList<>();
        String str = FSRequest.class.toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return null;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.fs_request);
            objectList.stream().forEach(e -> {
                FSRequest temp =(FSRequest)e;
                temp.setRecordDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                temp.setPushStatus(0);
                datalist.add(temp);
            });
        }
        if(datalist.size()>0){
            fsRequestMapper.insertList(datalist);
        }
        logger.info("获取放射申请单 耗时：{} 毫秒",(System.currentTimeMillis()-a));
        return datalist;
    }

    @Override
    public List<FSResult> fs_result(String startTime, String endTime) {
        logger.info("获取放射结果");
        long a=System.currentTimeMillis();
        String requestxml   =get_result(wsdl_result,fid_result,startTime,endTime);
        List<String> list = SoapUtil.parseETYYxml(requestxml);
        List<FSResult> datalist=new ArrayList<>();
        String str = FSResult.class.toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return null;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.fs_result);
            objectList.stream().forEach(e -> {
                FSResult temp =(FSResult)e;
                temp.setRecordDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                datalist.add(temp);
            });
        }
        if(datalist.size()>0){
            fsResultMapper.insertList(datalist);
        }
        logger.info("获取放射结果耗时：{} 毫秒",(System.currentTimeMillis()-a));
        return datalist;
    }

    @Override
    public List<FSResult> fs_resultByReportId(String reportId) {
        String requestxml   =getbySNO(wsdl_result,fid_result,reportId);
        List<String> list = SoapUtil.parseETYYxml(requestxml);
        List<FSResult> datalist=new ArrayList<>();
        String str = FSResult.class.toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return null;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.fs_result);
            objectList.stream().forEach(e -> {
                FSResult temp =(FSResult)e;
                temp.setRecordDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                datalist.add(temp);
            });
        }
        return datalist;
    }

    @Override
    public void push() {
        long a=System.currentTimeMillis();
        FSRequest param=new FSRequest();
        param.setPushStatus(0);
        Map<Integer,String> map=new HashMap<>();
        List<FSRequest> list=fsRequestMapper.select(param);
        if(null !=list && list.size()>0){
            list.stream().forEach( e -> {
                if(e.getExamSno() !=null){
                    String id=e.getHisPatientId();
                    //根据id 去获取卡号
                    String cardNo=e.getExamSno();
                    String content="检验报告";
                    String title="亲爱的"+e.getName()+", "+e.getItemChName()+"已经处理完成";
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


    public static String getrequest(String wsdl,String fid,String startTime,String endTime){
        String result= null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(StaticHttpclientCall.getFid(fid));
            stringBuffer.append(" and reg_date &gt;= '"+startTime);
            stringBuffer.append("' and reg_date &lt;= '").append(endTime).append("'");
            stringBuffer.append(" and PATIENT_TYPE ='1'");
            stringBuffer.append("</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求参数是：{}",input);
            result = StaticHttpclientCall.servicetest(wsdl,input);
            logger.info("请求结果申请单结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("申请单信息异常："+e.toString());
        }
        return result;
    }


    public static String getbySNO(String wsdl,String fid,String examSNO){
        String result= null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(StaticHttpclientCall.getFid(fid));
            stringBuffer.append("  and EXAM_SNO ='").append(examSNO);
            stringBuffer.append("'</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求参数：{}",input);
            result = StaticHttpclientCall.servicetest(wsdl,input);
            logger.info("请求结果：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求异常："+e.toString());
        }
        return result;
    }


    public static String get_result(String wsdl,String fid,String startTime,String endTime){
        String result= null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(StaticHttpclientCall.getFid(fid));
            stringBuffer.append(" and EXAM_REPORT_DATE &gt;= '"+startTime);
            stringBuffer.append("' and EXAM_REPORT_DATE &lt;= '").append(endTime).append("'");
            stringBuffer.append("</Msg></MsgInfo></ESBEntry>");
            String input=stringBuffer.toString();
            logger.info("请求参数是：{}",input);
            result = StaticHttpclientCall.servicetest(wsdl,input);
            logger.info("请求结果申请单结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("申请单信息异常："+e.toString());
        }
        return result;
    }


}
