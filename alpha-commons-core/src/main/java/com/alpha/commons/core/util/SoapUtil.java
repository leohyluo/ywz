package com.alpha.commons.core.util;


import com.alpha.commons.core.pojo.HospitalizedNoticeNew;
import com.alpha.commons.core.pojo.HospitalizedPatientInfoNew1;
import com.alpha.commons.core.pojo.OutPatientInfo;
import com.alpha.commons.util.XMLUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by HP on 2018/3/19.
 */
public class SoapUtil {

    private static Logger logger = LoggerFactory.getLogger(SoapUtil.class);

        public static  final ConcurrentHashMap map = new ConcurrentHashMap();
        public static  final ConcurrentHashMap mapNotice = new ConcurrentHashMap();
        public static  final ConcurrentHashMap ghmap=new ConcurrentHashMap(); //预约挂号解析
        public static  final ConcurrentHashMap outpatientmap=new ConcurrentHashMap();
        public static final ConcurrentHashMap jy_requst=new ConcurrentHashMap();//检验申请单
        public static final ConcurrentHashMap jy_result=new ConcurrentHashMap();//检验申请单
        public static final ConcurrentHashMap result_vo=new ConcurrentHashMap();//检验明细结果返回
        public static final ConcurrentHashMap xnd_request=new ConcurrentHashMap();//心脑
        public static final ConcurrentHashMap xnd_result=new ConcurrentHashMap();//心脑
        public static final ConcurrentHashMap fs_request=new ConcurrentHashMap();//放射
        public static final ConcurrentHashMap fs_result=new ConcurrentHashMap();//放射
        public static final ConcurrentHashMap CS_result=new ConcurrentHashMap();//放射


         static {
             map.put("PATIENT_ID","patientId");
             map.put("VISIT_ID","visitTimes");
             map.put("DEPT_DISCHARGE_FROM","deptDischargeFrom");
             map.put("ADMISSION_DATE_TIME","admissionDateTime");
             map.put("WARD_DISCHARGE_FROM","wardDischargeFrom");
             map.put("DISCHARGE_DATE_TIME","dischargeDateTime");
             map.put("INP_NO","inpNo");
             map.put("NAME","patientName");
             map.put("AGE","age");
             map.put("BED_NO","bedNo");
             map.put("STATUS","status");
             map.put("INSURANCE_NO","insuranceNo");
             map.put("XZZ","xzz");
             map.put("PLACE_OF_BIRTH","homePlace");
             map.put("MAILING_ADDRESS","mailingAddr");
             map.put("NEXT_OF_KIN_PHONE","contactPhone");
             map.put("ID_NO","patientCertiNo");
             map.put("MARITAL_STATUS","marialStatus");
             map.put("NATION","nation");
             map.put("COUNTRY","nationality");
             map.put("SERVICE_AGENCY","serviceAgency");
             map.put("NATIVE_PLACE","nativePlace");
             map.put("CONSULTING_DOCTOR","consultingDoctor");
             map.put("ADMITTED_BY","admittedBy");
             map.put("RELATIONSHIP","relationship");
             map.put("NEXT_OF_KIN_ADDR","contactAddr");
             map.put("NEXT_OF_KIN","contactName");
             map.put("BED_LABEL","bedNo");
             map.put("NURSING_GRADE","nursingGrade");
             map.put("INSURANCE_TYPE","insuranceType");
             map.put("OCCUPATION","occupation");
             map.put("DIAGNOSIS","diagnosis");
             map.put("IDENTITY0","identityo");
             map.put("DISCHARGE_DISPOSITION","dischargeDisposition");
             map.put("PAT_ADM_CONDITION","patAdmCondition");
             map.put("SEX","sex");
             map.put("MZHM","outPatientNo");
             map.put("DATE_OF_BIRTH","birthday");
             map.put("ZZYS","zzys");
             map.put("CZGH","czgh");
             map.put("RYTJ","rytj");
         }
         static {
             mapNotice.put("MZHM","outPatientNo");
             mapNotice.put("NAME","patientName");
             mapNotice.put("SEX","sex");
             mapNotice.put("BIRTHDAY","birthday");
             mapNotice.put("AGE","age");
             mapNotice.put("CONTACTPHONE","contactPhone");
             mapNotice.put("INDEP","inDep");
             mapNotice.put("INTYPE","inType");
             mapNotice.put("INCASE","inCase");
             mapNotice.put("INCHANNEL","inChannel");
             mapNotice.put("DIAGNOSIS","diagnosis");
             mapNotice.put("ISFECT","isFect");
             mapNotice.put("NOTIFYTIME","notifyTime");
             mapNotice.put("DISDESC","disDesc");
             mapNotice.put("DOCTORNAME","doctorName");

         }
         static {
             ghmap.put("patientName","patientName");
             ghmap.put("pno","pno");
             ghmap.put("pnoNew","yno");
             ghmap.put("sex","sex");
             ghmap.put("birthday","birthday");
             ghmap.put("patientCardNo","patientCardNo");
             ghmap.put("deptName","deptName");
             ghmap.put("outPatientNo","outPatientNo");
             ghmap.put("doctorName","doctorName");
             ghmap.put("visitTime","visitTime");
             ghmap.put("phone","phone");
             ghmap.put("phoneNew","phoneNew");
             ghmap.put("cardNo","cardNo");
             ghmap.put("createTime","createTime");
             ghmap.put("type","type");
             ghmap.put("intervalTime","intervalTime");
         }
         static {
             outpatientmap.put("PATIENT_ID","patientId");
             outpatientmap.put("CARD_NO","cardNo");
             outpatientmap.put("CONTACT_ADDRESS","contactAddress");
             outpatientmap.put("PHONE_NO","phoneNo");
             outpatientmap.put("IDENTITY_NO","identityNo");
             outpatientmap.put("PATIENT_NAME","patientName");
             outpatientmap.put("SEX","sex");
             outpatientmap.put("DOB","birth");
             outpatientmap.put("COMPANY","company");
             outpatientmap.put("FAMILY_ADDRESS","familyAddress");
             outpatientmap.put("PATIENT_TYPE","patientType");
             outpatientmap.put("INSURANCE","insurance");
             outpatientmap.put("REMARK","remark");
             outpatientmap.put("MZHM","outpatientNo");
         }
         static {
             jy_requst.put("REQUEST_NO","requestNo");
             jy_requst.put("PATIENT_ID","patientId");
             jy_requst.put("PATIENT_NAME","patientName");
             jy_requst.put("SEX","sex");
             jy_requst.put("AGE","age");
             jy_requst.put("PATIENT_TYPE","patientType");
             jy_requst.put("DIAGNOSE","diagnose");
             jy_requst.put("SPECIMEN_TYPE","specimenType");
             jy_requst.put("RECEIVE_DATE","receiveDate");
             jy_requst.put("DEPT_REQUEST_NAME","deptRequestName");
             jy_requst.put("DOCTOR_REQUEST_NAME","doctorRequestName");
             jy_requst.put("REPORT_NAME","reportName");
             jy_requst.put("REPORT_ID","reportId");
             jy_requst.put("DEPT_REPORT_NAME","deptReportName");
             jy_requst.put("REPORTER","reporter");
             jy_requst.put("REPORT_DATE","reportDate");
             jy_requst.put("REPORT_STATUS","reportStatus");
         }
         static {
             jy_result.put("REPORT_ID","reportId");
             jy_result.put("ITEM_ID","itemId");
             jy_result.put("ITEM_NAME","itemName");
             jy_result.put("ITEM_RESULT","itemResult");
             jy_result.put("RESULT_ABNORMAL_SIGN","resultAbnormalSign");
             jy_result.put("RESULT_REFERENCE_HIGH","resultReferenceHigh");
             jy_result.put("RESULT_REFERENCE_LOW","resultReferenceLow");
             jy_result.put("RESULT_REFERENCE","resultReference");
             jy_result.put("UNIT","unit");
             jy_result.put("MEASURE_DATE","measureDate");
         }
         static {
             result_vo.put("ITEM_NAME","projectName");
             result_vo.put("ITEM_ID","projectCode");
             result_vo.put("RESULT_REFERENCE","rangeValue");
             result_vo.put("ITEM_RESULT","resultValue");
             result_vo.put("UNIT","unit");
             result_vo.put("RESULT_ABNORMAL_SIGN","tips");
         }
         static {
             xnd_request.put("REQUEST_NO","requestNo");
             xnd_request.put("PATIENT_ID","patientId");
             xnd_request.put("PATIENT_NAME","patientName");
             xnd_request.put("SEX","sex");
             xnd_request.put("BIRTHDAY","birthday");
             xnd_request.put("PATIENT_TYPE","patientType");
             xnd_request.put("RECEIVE_DATE","receiveDate");
             xnd_request.put("DEPT_REQUEST_NAME","deptRequestName");
             xnd_request.put("DOCTOR_REQUEST_NAME","doctorRequestName");
             xnd_request.put("REPORT_NAME","reportName");
             xnd_request.put("REPORT_ID","reportId");
             xnd_request.put("REPORT_DEPT","reportDept");
             xnd_request.put("REPORTER","reporter");
             xnd_request.put("REPORT_DATE","reportDate");
             xnd_request.put("REPORT_STATUS","reportStatus");
         }
         static {
             xnd_result.put("REPORT_ID","reportId");
             xnd_result.put("ITEM_ID","itemId");
             xnd_result.put("ITEM_NAME","itemName");
             xnd_result.put("ITEM_RESULT","itemResult");
             xnd_result.put("RESULT_DESCRIPTION","resultDescription");
             xnd_result.put("MEASURE_DATE","measureDate");
         }
         static {
             fs_request.put("EXAM_SYSTEM_CODE","examSystemCode");
             fs_request.put("HIS_PATIENT_ID","hisPatientId");
             fs_request.put("NAME","name");
             fs_request.put("SEX_NAME","sex");
             fs_request.put("AGE","age");
             fs_request.put("SNO_TYPE","snoType");
             fs_request.put("EXAM_MODALITY","examModality");
             fs_request.put("REG_DATE","regDate");
             fs_request.put("EXAM_DEPT_NAME","examDeptName");
             fs_request.put("EXAM_DOCTOR_SEND_NAME","examDoctorSendName");
             fs_request.put("ITEM_CH_NAME","itemChName");
             fs_request.put("EXAM_SNO","examSno");
         }
         static {
             fs_result.put("EXAM_SNO","examSno");
             fs_result.put("EXAM_PART_CODE","examPartCode");
             fs_result.put("EXAM_METHOD","examMethod");
             fs_result.put("EXAM_RESULT","examResult");
             fs_result.put("EXAM_DESC","examDesc");
             fs_result.put("EXAM_DATE","examDate");
             fs_result.put("EXAM_REPORT_DATE","examReportDate");
             fs_result.put("EXAM_REPORTOR_NAME","examReportorName");
             fs_result.put("EXAM_VERIFY_DOCTOR_NAME","examVerifyDoctorName");
         }
         static {
             CS_result.put("requestid","requestId");
             CS_result.put("HospitalizedNo","hospitalizedNo");
             CS_result.put("name","name");
             CS_result.put("sex_name","sex");
             CS_result.put("StudyAge","studyAge");
             CS_result.put("PATIENT_TYPE","patientType");
             CS_result.put("StudyMethod","studyMethod");
             CS_result.put("StudyDate","studyDate");
             CS_result.put("RequestDept","requestDept");
             CS_result.put("RequestPhysician","requestPhysician");
             CS_result.put("ReportDate","reportDate");
             CS_result.put("reporter","reporter");
             CS_result.put("StudyPart","studyPart");
             CS_result.put("Conclusion","conclusion");
             CS_result.put("Report","report");
         }



    /**
     * 转换 住院信息
     * @param xmlString
     * @return
     */
    public static HospitalizedPatientInfoNew1 parseSoapXml(String xmlString){
        if(org.apache.commons.lang3.StringUtils.isBlank(xmlString) || xmlString.contains("调用错误")){
            return null;
        }
        HospitalizedPatientInfoNew1 hospitalizedPatientInfo=new HospitalizedPatientInfoNew1();
        try {
            Document dom = XMLUtils.getXMLByString(xmlString);
            Node node = dom.selectSingleNode("//Msg");
            String nodetext=node.getText();
            Document dom1 = XMLUtils.getXMLByString(nodetext);
            Element element=dom1.getRootElement();
            Element element1=element.element("body");
            List<Element> list1=element1.elements();
            for (Element element2:list1){
                if(element2.getName().equals("row")){
                   List<Element> list2=element2.elements();
                   for (Element element3:list2){
                       String name=element3.getName();
                       String value=element3.getTextTrim();
                       addParams(name,value,hospitalizedPatientInfo,map);//不管多少次住院只拿其中的一个数据
                   }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return  hospitalizedPatientInfo;
    }

    /**
     * 把属性 Str 赋值到到对象里面去
     * @param str  his 返回的参数
     * @param value  返回的值
     * @param hospitalizedPatientInfo 封装的对象
     */

    public static void addParams(String eleName,String value,Object hospitalizedPatientInfo,Map map){
        try {
            if(!org.apache.commons.lang3.StringUtils.isBlank(value)){
               String name=(String) map.get(eleName);
               if(StringUtils.isBlank(name)){
                   return;
               }
                   Field[] fields=hospitalizedPatientInfo.getClass().getDeclaredFields();
                   for (Field field:fields){
                       field.setAccessible(true);
                       String name1 = field.getName();
                           if(name.equals(name1)){
                               name1 = name1.substring(0, 1).toUpperCase() + name1.substring(1);
                               String type = field.getGenericType().toString();
                               if (type.equals("class java.lang.String")) {
                                   Method m = hospitalizedPatientInfo.getClass().getMethod("set" + name1,String.class);
                                   m.invoke(hospitalizedPatientInfo,value);
                               }
                               if (type.equals("class java.lang.Integer")) {
                                   Method m = hospitalizedPatientInfo.getClass().getMethod("set" + name1,Integer.class);
                                   m.invoke(hospitalizedPatientInfo,Integer.parseInt(value));
                               }
                           }
                   }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把 对象 obj2 里面不为空的字段 填充到obj1 里面去
     * @param obj1
     * @param obj2
     */
    public static void mergeBean(HospitalizedPatientInfoNew1 obj1, HospitalizedPatientInfoNew1 obj2){
        try {
            Field[] field=obj1.getClass().getDeclaredFields();
            for (int i=0;i<field.length;i++){
                String name=field[i].getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String type = field[i].getGenericType().toString();
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = obj2.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(obj2); // 调用getter方法获取属性值
                    if (value != null) {
                        m = obj1.getClass().getMethod("set"+name,String.class);
                        m.invoke(obj1, value);
                    }
                }
                if (type.equals("class java.lang.Integer")) {
                    Method m = obj2.getClass().getMethod("get" + name);
                    Integer value = (Integer) m.invoke(obj2);
                    if (value != null) {
                        m = obj1.getClass().getMethod("set"+name,Integer.class);
                        m.invoke(obj1, value);
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知单信息转换
     * @param xmlString
     * @return
     */
    public static HospitalizedNoticeNew parseNoticeSoapXml(String xmlString){
        if(org.apache.commons.lang3.StringUtils.isBlank(xmlString) || xmlString.contains("调用错误")){
            return null;
        }
        HospitalizedNoticeNew hospitalizedNoticeNew= null;
        try {
            hospitalizedNoticeNew = new HospitalizedNoticeNew();
            Document dom = XMLUtils.getXMLByString(xmlString);
            Node node = dom.selectSingleNode("//Msg");
            String nodetext=node.getText();
            Document dom1 = XMLUtils.getXMLByString(nodetext);
            Element element=dom1.getRootElement();
            Element element1=element.element("body");
            List<Element> list1=element1.elements();
            for (Element element2:list1){
                String name=element2.getName();
                String value=element2.getTextTrim();
                addParams(name,value,hospitalizedNoticeNew,mapNotice);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return  hospitalizedNoticeNew;
    }

   //以下解析儿童医院 挂号信息，门诊信息，住院信息，住院通知信息，
   public static void main(String[] args) {
       StringBuffer xml= new StringBuffer();
       xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
               "\n" +
               "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
               "  <soapenv:Body>\n" +
               "    <NS1:HIPMessageServerResponse xmlns:NS1=\"http://esb.ewell.cc\">\n" +
               "      <NS1:output>\n" +
               "        <ESBEntry>\n" +
               "          <MessageHeader>\n" +
               "            <MsgDate>2018-06-04T12:29:36.912</MsgDate>\n" +
               "            <TargetSysCode>S23</TargetSysCode>\n" +
               "            <SourceSysCode>S01</SourceSysCode>\n" +
               "          </MessageHeader>\n" +
               "          <MsgInfo>\n" +
               "            <Msg><![CDATA[<msg><head><msgId>BS10008</msgId><version>2</version><msgName>门诊病人信息</msgName><sourceSysCode>S01</sourceSysCode><targetSysCode>S23</targetSysCode><createTime>20180604122936</createTime></head><body><row action=\"select\"><PATIENT_ID>9447492</PATIENT_ID><CARD_NO>8100353898</CARD_NO><CONTACT_ADDRESS>/</CONTACT_ADDRESS><PHONE_NO>18665841430</PHONE_NO><IDENTITY_NO>/</IDENTITY_NO><PATIENT_NAME>毛明胜</PATIENT_NAME><SEX>1</SEX><DOB>2017-01-12 00:00:00</DOB><COMPANY>/</COMPANY><FAMILY_ADDRESS>广东省深圳市</FAMILY_ADDRESS><PATIENT_TYPE>1</PATIENT_TYPE><INSURANCE>现金</INSURANCE><REMARK></REMARK><MZHM>17082553030</MZHM></row></body></msg>]]></Msg>\n" +
               "            <MsgCount>1</MsgCount>\n" +
               "            <CurrentNum>1</CurrentNum>\n" +
               "          </MsgInfo>\n" +
               "          <RetInfo>\n" +
               "            <RetCode>1</RetCode>\n" +
               "            <RetCon>查询成功</RetCon>\n" +
               "          </RetInfo>\n" +
               "        </ESBEntry>\n" +
               "      </NS1:output>\n" +
               "    </NS1:HIPMessageServerResponse>\n" +
               "  </soapenv:Body>\n" +
               "</soapenv:Envelope>");
      String string=xml.toString();
      List<String> list =parseETYYxml(string);
      OutPatientInfo a=new OutPatientInfo();
       String str=a.getClass().toString();
       str=str.replace("class ","");
       str=str.replace(" ","");
       if(null ==list || list.size()<1){
           System.out.println("没有数据");
       }else {
           List<Object> objectList=string2obj(list,str,outpatientmap);
           System.out.println(new Gson().toJson((OutPatientInfo)(objectList.get(0))));
       }
   }

    /**
     * 解析儿童医院互联互通协议主体信息
     * 返回list  可能有多条信息，如挂号，住院，通知单
     * @param xml
     * @return
     */
    public static List<String> parseETYYxml(String xml){
        List<String> list=new ArrayList<>();
        if(StringUtils.isBlank(xml)){
            return list;
        }
        try {
            Document document = XMLUtils.getXMLByString(xml);
            Element element=document.getRootElement();
            List<Element> elementList=element.elements();
            if(elementList.size()> 0){
                elementToString(elementList,list);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
            logger.info("解析异常："+e.toString());
        }
        if(list.size()>0){
            logger.info("解析到xml包含 "+list.size()+" 条信息");
            return  list;
        }
        return null;
    }

    private static void elementToString(List<Element> elementList, List<String> list) {
        for (Element element:elementList){
            String name=element.getName();
            String value=element.getTextTrim();
            if(element.getParent().getName().equals("MsgInfo")){
                if(name.equals("Msg") && !StringUtils.isBlank(value)){
                    list.add(value);
                }
            }
            List<Element> eleList=element.elements();
            if(eleList.size()>0){
                elementToString(eleList,list);
            }
        }
    }

    /**
     * 根据主体 xml 数据转换成 对象
     * @param list
     * @param objname
     * @param map  对象属性和返回值得映射
     * @return
     */
    public static List<Object> string2obj(List<String> list,String classname,Map map){
        List<Object> listobj=new ArrayList<>();
        if(list.size()>0){
            for(String str:list){
                str="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+str;
                Object obj=str2obj(str,classname,map);
                if(null != obj){
                    listobj.add(obj);
                }
            }
        }
        if(listobj.size()>0){
            return  listobj;
        }
        return null;
    }


    private static Object str2obj(String str, String classname, Map map) {
            Object object= null;
        try {
            Class cla=Class.forName(classname);
            object=cla.newInstance();
            Document document = XMLUtils.getXMLByString(str);
            Element element=document.getRootElement();
            List<Element> elementList=element.elements();
            if(elementList.size()> 0){
                element2obj(elementList,object,map);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return object;
    }

    private static void element2obj(List<Element> elementList, Object obj, Map map) {
         if(elementList.size()>0){
             for (Element element:elementList){
                  String name = element.getName();
                  String value = element.getTextTrim();
                  if(!StringUtils.isBlank(value)){
                      addParams(name,value,obj,map);
                  }
                  List<Element> elementList1=element.elements();
                  if(elementList1.size()>0){
                      element2obj(elementList1,obj,map);
                  }
             }
         }
    }
}

