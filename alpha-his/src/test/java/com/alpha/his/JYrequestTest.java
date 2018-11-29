package com.alpha.his;



import com.alpha.commons.core.pojo.inspcetion.JYRequest;
import com.alpha.commons.core.util.SoapUtil;
import com.alpha.commons.core.util.StaticHttpclientCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by edz on 2018/10/29.
 */
public class JYrequestTest {


    private static final Logger logger = LoggerFactory.getLogger(JYrequestTest.class);

    private static String wsdl_jy_request="http://172.16.240.124:7803/BS20008/BS20008?wsdl";
    private static String fid_jy_request="BS20008";

    private static String wsdl_jy_result="http://172.16.240.124:7803/BS20009/BS20009?wsdl";
    private static String fid_jy_result="BS20009";

    private static String wsdl_xnd_request="http://172.16.240.124:7803/BS25007/BS25007?wsdl";
    private static String fid_xnd_request="BS25007";

    private static String wsdl_xnd_result="http://172.16.240.124:7803/BS25008/BS25008?wsdl";
    private static String fid_xnd_result="BS25008";

    private static String wsdl_fs_request="http://172.16.240.124:7803/BS25009/BS25009?wsdl";
    private static String fid_fs_request="BS25009";

    private static String wsdl_fs_result="http://172.16.240.124:7803/BS25010/BS25010?wsdl";
    private static String fid_fs_result="BS25010";


    public static void main(String[] args) {

//检验申请单
//        String requestxml = getrequest(wsdl_jy_request, fid_jy_request, "2018-10-29 12:00:00", "2018-10-29 12:10:00");
        //检验结果
//         String requestxml   = getresult(wsdl_jy_result,fid_jy_result,"20181029NUF01075","2018-10-29 09:30:00");
        //心脑电
//         String requestxml   =getrequest(wsdl_xnd_request,fid_xnd_request,"2018-10-23 10:00:00","2018-10-29 13:30:00");
//
//         String requestxml   =getresult(wsdl_xnd_result,fid_xnd_result,"465344","2018-10-29 09:30:00");
//       //放射
//         String requestxml   =getrequest(wsdl_fs_request,fid_fs_request,"2018-10-29 10:00:00","2018-10-29 13:30:00");
//
         String requestxml   =getresult(wsdl_fs_result,fid_fs_result,"2018-11-05 08:00:00","2018-11-06 09:30:00");

        List<String> list = SoapUtil.parseETYYxml(requestxml);


        List<JYRequest> datalist=new ArrayList<>();
        System.out.println(list.size());
        String str = JYRequest.class.toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.jy_requst);
            objectList.stream().forEach(e -> {
                JYRequest temp =(JYRequest)e;
                temp.setRecordDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
                datalist.add(temp);
            });
        }
        System.out.println(datalist.size());
    }


    public static String getrequest(String wsdl,String fid,String startTime,String endTime){
        String result= null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append(getFid(fid));
            stringBuffer.append(" and REPORT_DATE between to_date('"+startTime);
            stringBuffer.append("','yyyy-mm-dd hh24:mi:ss') and to_date('").append(endTime).append("'," +
                    "'yyyy-mm-dd hh24:mi:ss')");
//            stringBuffer.append(" and SNO ='385824'");
//            stringBuffer.append("</Msg></MsgInfo></ESBEntry>");
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
            stringBuffer.append(getFid(fid));
//            stringBuffer.append(" and  EXAM_SNO ='");
//            stringBuffer.append(startTime+"'");
//            stringBuffer.append(" and EXAM_REPORT_DATE between to_date('"+startTime);
//            stringBuffer.append("','yyyy-mm-dd hh24:mi:ss') and to_date('").append(endTime).append("'," +
//                    "'yyyy-mm-dd hh24:mi:ss')");
            stringBuffer.append(" and EXAM_REPORT_DATE &gt;= '"+startTime);
            stringBuffer.append("' and EXAM_REPORT_DATE &lt;= '").append(endTime).append("'");
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



    public static String getFid(String fid){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("<ESBEntry><MessageHeader><Fid>").append(fid);
        stringBuffer.append("</Fid><SourceSysCode>S23</SourceSysCode><TargetSysCode>S01</TargetSysCode><MsgDate></MsgDate></MessageHeader><MsgInfo><Msg>");
        return stringBuffer.toString();
    }

}
