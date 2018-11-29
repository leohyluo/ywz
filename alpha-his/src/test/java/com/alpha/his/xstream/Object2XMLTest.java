package com.alpha.his.xstream;

import com.alpha.commons.util.XMLUtils;
import com.alpha.commons.util.XStreamUtils;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Object2XMLTest {

    @Test
    public void test1() {
        String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body><NS1:HIPMessageServerResponse xmlns:NS1=\"http://esb.ewell.cc\"><NS1:output><ESBEntry><MessageHeader><Fid>BS10015</Fid><SourceSysCode>S23</SourceSysCode><TargetSysCode>S01</TargetSysCode><MsgDate>2018-03-20T12:36:30.601695</MsgDate></MessageHeader><MsgInfo><Msg><![CDATA[<msg><body><patientName>周子熙</patientName><pno>15343206</pno><sex>男</sex><birthday>2011-12-22 00:00:00</birthday><patientCardNo>440304201112221831</patientCardNo><depName></depName><outPatientNo>150531107</outPatientNo><doctorName></doctorName><visitTime>2014-08-13 19:27:32</visitTime></body></msg>]]></Msg><Msg><![CDATA[<msg><body><patientName>周子熙</patientName><pno>15353143</pno><sex>男</sex><birthday>2011-12-22 00:00:00</birthday><patientCardNo>440304201112221831</patientCardNo><depName></depName><outPatientNo>150531107</outPatientNo><doctorName></doctorName><visitTime>2014-08-15 19:38:17</visitTime></body></msg>]]></Msg><Msg><![CDATA[<msg><body><patientName>周子熙</patientName><pno>15354259</pno><sex>男</sex><birthday>2011-12-22 00:00:00</birthday><patientCardNo>440304201112221831</patientCardNo><depName></depName><outPatientNo>150531107</outPatientNo><doctorName></doctorName><visitTime>2014-08-16 08:03:20</visitTime></body></msg>]]></Msg><Msg><![CDATA[<msg><body><patientName>周子熙</patientName><pno>15572868</pno><sex>男</sex><birthday>2011-12-22 00:00:00</birthday><patientCardNo>440304201112221831</patientCardNo><depName></depName><outPatientNo>150531107</outPatientNo><doctorName></doctorName><visitTime>2014-09-30 17:29:20</visitTime></body></msg>]]></Msg><Msg><![CDATA[<msg><body><patientName>周子熙</patientName><pno>16686521</pno><sex>男</sex><birthday>2011-12-22 00:00:00</birthday><patientCardNo>440304201112221831</patientCardNo><depName></depName><outPatientNo>150531107</outPatientNo><doctorName></doctorName><visitTime>2015-04-25 20:14:42</visitTime></body></msg>]]></Msg><MsgCount>1</MsgCount><CurrentNum>1</CurrentNum></MsgInfo><RetInfo><RetCode>1</RetCode><RetCon>查询成功</RetCon></RetInfo></ESBEntry></NS1:output></NS1:HIPMessageServerResponse></soapenv:Body></soapenv:Envelope>";
//        String str = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenvEnvelope xmlnssoapenv=\"http//schemas.xmlsoap.org/soap/envelope/\"><soapenvBody><NS1HIPMessageServerResponse xmlnsNS1=\"http//esb.ewell.cc\"><NS1output><ESBEntry><MessageHeader><Fid>BS10015</Fid><SourceSysCode>S23</SourceSysCode><TargetSysCode>S01</TargetSysCode><MsgDate>2018-03-20T123630.601695</MsgDate></MessageHeader><MsgInfo><Msg><![CDATA[<msg><body><patientName>周子熙</patientName><pno>15343206</pno><sex>男</sex><birthday>2011-12-22 000000</birthday><patientCardNo>440304201112221831</patientCardNo><depName></depName><outPatientNo>150531107</outPatientNo><doctorName></doctorName><visitTime>2014-08-13 192732</visitTime></body></msg>]]></Msg><Msg><![CDATA[<msg><body><patientName>周子熙</patientName><pno>15353143</pno><sex>男</sex><birthday>2011-12-22 000000</birthday><patientCardNo>440304201112221831</patientCardNo><depName></depName><outPatientNo>150531107</outPatientNo><doctorName></doctorName><visitTime>2014-08-15 193817</visitTime></body></msg>]]></Msg><Msg><![CDATA[<msg><body><patientName>周子熙</patientName><pno>15354259</pno><sex>男</sex><birthday>2011-12-22 000000</birthday><patientCardNo>440304201112221831</patientCardNo><depName></depName><outPatientNo>150531107</outPatientNo><doctorName></doctorName><visitTime>2014-08-16 080320</visitTime></body></msg>]]></Msg><Msg><![CDATA[<msg><body><patientName>周子熙</patientName><pno>15572868</pno><sex>男</sex><birthday>2011-12-22 000000</birthday><patientCardNo>440304201112221831</patientCardNo><depName></depName><outPatientNo>150531107</outPatientNo><doctorName></doctorName><visitTime>2014-09-30 172920</visitTime></body></msg>]]></Msg><Msg><![CDATA[<msg><body><patientName>周子熙</patientName><pno>16686521</pno><sex>男</sex><birthday>2011-12-22 000000</birthday><patientCardNo>440304201112221831</patientCardNo><depName></depName><outPatientNo>150531107</outPatientNo><doctorName></doctorName><visitTime>2015-04-25 201442</visitTime></body></msg>]]></Msg><MsgCount>1</MsgCount><CurrentNum>1</CurrentNum></MsgInfo><RetInfo><RetCode>1</RetCode><RetCon>查询成功</RetCon></RetInfo></ESBEntry></NS1output></NS1HIPMessageServerResponse></soapenvBody></soapenvEnvelope>";
        try {
            str = str.replace(":", "");
            Document dom = XMLUtils.getXMLByString(str);

//            Element root = dom.getRootElement();
//            root.addNamespace("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
//            root.addNamespace("NS1", "http://schemas.xmlsoap.org/soap/envelope/soapenv");

            String path = "//soapenv:Envelope/soapenv:Body/NS1:HIPMessageServerResponse/NS1:output/ESBEntry/MsgInfo/Msg";
//            String path = "//soapenvEnvelope/soapenvBody/NS1HIPMessageServerResponse/NS1output/ESBEntry/MsgInfo/Msg";
//            String path = "//Envelope/Body/HIPMessageServerResponse/output/ESBEntry/MsgInfo/Msg";

            path = path.replace(":", "");
            List list = dom.selectNodes(path);
            for(Object obj : list) {
                Element element = (Element) obj;
                String msgText = element.getStringValue();
                Document msgDom = XMLUtils.getXMLByString(msgText);

                Element msg = msgDom.getRootElement();
                Element body = msg.element("body");

                String patientName = body.element("patientName").getText();
                System.out.println(patientName);
            }
            System.out.println(list.size());
            /*Element root = dom.getRootElement();
            Element soapenv_body = root.element("soapenvBody");
            Element ns1_serverResponse = soapenv_body.element("NS1:HIPMessageServerResponse");
            Element ns1_output = ns1_serverResponse.element("NS1:output");
            Element esbEntry = ns1_output.element("ESBEntry");
            Element msgInfo = esbEntry.element("ESBEntry");
            List<Element> msgList = XMLUtils.getChildElements(msgInfo);*/
            System.out.println(1);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


}


/*class MsgInfo {
    List<Msg> msgList;

    public List<Msg> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<Msg> msgList) {
        this.msgList = msgList;
    }
}

class Msg {
    Body body;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}

class Body {
    PatientInfo patientInfo;

    public PatientInfo getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(PatientInfo patientInfo) {
        this.patientInfo = patientInfo;
    }
}*/

class PatientInfo {
    private String patientName;
    private String pno;
    private String sex;
    private String birthday;
    private String patientCardNo;
    private String depName;
    private String outPatientNo;
    private String doctorName;
    private String visitTime;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPatientCardNo() {
        return patientCardNo;
    }

    public void setPatientCardNo(String patientCardNo) {
        this.patientCardNo = patientCardNo;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getOutPatientNo() {
        return outPatientNo;
    }

    public void setOutPatientNo(String outPatientNo) {
        this.outPatientNo = outPatientNo;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }
}
