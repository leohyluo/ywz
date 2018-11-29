package com.alpha.his.pojo.dto.input;

import com.alpha.commons.util.XMLUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 挂号信息数据传输类
 */
public class RegisterDTO {
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

    public static List<RegisterDTO> parse(String xmlString) {
        try{
            xmlString = xmlString.replace("soapenv:Envelope", "soapenvEnvelope");
            xmlString = xmlString.replace("soapenv:Body", "soapenvBody");
            xmlString = xmlString.replace("NS1:HIPMessageServerResponse", "NS1HIPMessageServerResponse");
            xmlString = xmlString.replace("NS1:output", "NS1output");
            Document dom = XMLUtils.getXMLByString(xmlString);
            String path = "//soapenv:Envelope/soapenv:Body/NS1:HIPMessageServerResponse/NS1:output/ESBEntry/MsgInfo/Msg";
            path = path.replace(":", "");
            List list = dom.selectNodes(path);
            List<RegisterDTO> resultList = new ArrayList<>();
            for (Object obj : list) {
                Element element = (Element) obj;
                String msgText = element.getStringValue();
                Document msgDom = XMLUtils.getXMLByString(msgText);

                Element msg = msgDom.getRootElement();
                Element body = msg.element("body");

                String patientName = body.element("patientName").getText();
                String pno = body.element("pno").getText();
                String sex = body.element("sex").getText();
                String birthday = body.element("birthday").getText();
                String patientCardNo = body.element("patientCardNo").getText();
                String depName = body.element("depName").getText();
                String outPatientNo = body.element("outPatientNo").getText();
                String doctorName = body.element("doctorName").getText();
                String visitTime = body.element("visitTime").getText();

                RegisterDTO registerDTO = new RegisterDTO();
                registerDTO.setPatientName(patientName);
                registerDTO.setPno(pno);
                registerDTO.setSex(sex);
                registerDTO.setBirthday(birthday);
                registerDTO.setPatientCardNo(patientCardNo);
                registerDTO.setDepName(depName);
                registerDTO.setOutPatientNo(outPatientNo);
                registerDTO.setDoctorName(doctorName);
                registerDTO.setVisitTime(visitTime);
                resultList.add(registerDTO);
            }
            return resultList;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
