package com.alpha.service.impl;

import com.alpha.commons.core.mapper.JYRequestMapper;
import com.alpha.commons.core.mapper.JYResultMapper;
import com.alpha.commons.core.pojo.inspcetion.JYRequest;
import com.alpha.commons.core.pojo.inspcetion.JYResult;
import com.alpha.commons.core.util.SoapUtil;
import com.alpha.commons.core.util.StaticHttpclientCall;
import com.alpha.pojo.vo.InspectionDetailVO;
import com.alpha.service.JYJobService;
import com.alpha.utils.InspectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.alpha.utils.InspectionUtils.push160;

/**
 * Created by edz on 2018/10/29.
 */
@Service
public class JYJobServiceImpl implements JYJobService {

    private static final Logger logger = LoggerFactory.getLogger(JYJobServiceImpl.class);

    private static String wsdl_request="http://172.16.240.124:7803/BS20008/BS20008?wsdl";
    private static String fid_request="BS20008";

    private static String wsdl_result="http://172.16.240.124:7803/BS20009/BS20009?wsdl";
    private static String fid_result="BS20009";


    @Resource
    private JYRequestMapper jyRequestMapper;
    @Resource
    private JYResultMapper jyResultMapper;

    @Override
    public List<JYRequest> jy_request(String startTime, String endTime) {
        logger.info("获取检验申请单");
        long a=System.currentTimeMillis();
        String requestxml = getrequest(wsdl_request, fid_request, startTime, endTime);
        List<String> list = SoapUtil.parseETYYxml(requestxml);
        List<JYRequest> datalist=new ArrayList<>();
        String str = JYRequest.class.toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return null;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.jy_requst);
            objectList.stream().forEach(e -> {
                JYRequest temp =(JYRequest)e;
                temp.setRecordDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                temp.setPushStatus(0);
                datalist.add(temp);
            });
        }
        if(datalist.size()>0){
            jyRequestMapper.insertList(datalist);
        }
        logger.info("获取检验申请单耗时：{} 毫秒",(System.currentTimeMillis()-a));
        return datalist;
    }

    public static void main(String[] args) {
        new JYJobServiceImpl().detailByReportId("20181029LSX00703");
    }
    @Override
    public List<JYResult> jy_result(String startTime, String endTime) {
        logger.info("获取检验结果");
        long a=System.currentTimeMillis();
        String requestxml   =getresult(wsdl_result,fid_result,startTime,endTime);
        List<String> list = SoapUtil.parseETYYxml(requestxml);
        List<JYResult> datalist=new ArrayList<>();
        String str = JYResult.class.toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return null;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.jy_result);
            objectList.stream().forEach(e -> {
                JYResult temp =(JYResult)e;
                temp.setRecordDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                datalist.add(temp);
            });
        }
        if(datalist.size()>0){
            jyResultMapper.insertList(datalist);
        }
        logger.info("获取检验结果耗时：{} 毫秒",(System.currentTimeMillis()-a));
        return datalist;
    }

    @Override
    public List<JYResult> jy_resultByReportId(String reportId) {
        long startTime=System.currentTimeMillis();
        String requestxml   =getresultByReportId(wsdl_result,fid_result,reportId);
        List<String> list = SoapUtil.parseETYYxml(requestxml);
        List<JYResult> datalist=new ArrayList<>();
        String str = JYResult.class.toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return null;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.jy_result);
            objectList.stream().forEach(e -> {
                JYResult temp =(JYResult)e;
                temp.setRecordDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                datalist.add(temp);
            });
        }
        logger.info("耗时：{}",System.currentTimeMillis()-startTime);
        return datalist;
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
            logger.info("检验申请单参数是：{}",input);
            result = StaticHttpclientCall.servicetest(wsdl,input);
            logger.info("检验申请单结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("检验申请单信息异常："+e.toString());
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
            logger.info("检验结果信息参数是：{}",input);
            result = StaticHttpclientCall.servicetest(wsdl,input);
            logger.info("检验结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求检验结果信息异常："+e.toString());
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
            logger.info("检验结果信息参数是：{}",input);
            result = StaticHttpclientCall.servicetest(wsdl,input);
            logger.info("检验结果是：{}",result);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("请求检验结果信息异常："+e.toString());
        }
        return result;
    }

    @Override
    public List<InspectionDetailVO> detailByReportId(String reportNo) {
        List<InspectionDetailVO> vos=new ArrayList<>();
        String resu=getresultByReportId(wsdl_result,fid_result,reportNo);
        List<String> list = SoapUtil.parseETYYxml(resu);
        String str = InspectionDetailVO.class.toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return null;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.result_vo);
            objectList.stream().forEach(e -> {
                InspectionDetailVO temp =(InspectionDetailVO)e;
                vos.add(temp);
            });
        }
        return vos;
    }

    @Override
    public void push() {
        long a=System.currentTimeMillis();
        JYRequest param=new JYRequest();
        param.setPushStatus(0);
        param.setPatientType("1");
        Map<Integer,String> map=new HashMap<>();
        List<JYRequest> list=jyRequestMapper.select(param);
        if(null !=list && list.size()>0){
            list.stream().forEach( e -> {
                if(e.getPatientId() !=null){
                    //获取卡号
//                    String cardNo=e.getPatientId();
                    String cardNo="8100422627";
                    String content="检验报告";
                    String title="亲爱的"+e.getPatientName()+", "+e.getReportName()+"已经处理完成";
                    String note="点击查看报告";
                    String url="www.baidu.com";
                    String resultxml= push160(cardNo,content,"",title,note,url);
                    map.put(e.getId(),resultxml);
                }else {
                    map.put(e.getId(),"00");
                }
                long b=System.currentTimeMillis();
                logger.info("发送 {} 条数据共耗时：{} 毫秒",list.size(),(b-a));
            });
            InspectionUtils.recordPush(map,1);
        }
    }

}
