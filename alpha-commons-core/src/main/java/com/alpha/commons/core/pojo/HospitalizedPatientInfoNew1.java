package com.alpha.commons.core.pojo;




import javax.persistence.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * Created by HP on 2018/3/27.
 */
@Entity
@Table(name = "hospitalized_patient_info_new")
public class HospitalizedPatientInfoNew1 implements Serializable{

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "patient_name")
    private String patientName; //患者姓名
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "age")
    private String age;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "nation")
    private Integer nation;
    @Column(name = "nationality")
    private Integer nationality;
    @Column(name = "out_patient_no")
    private String outPatientNo; //门诊号
    @Column(name = "contact_phone")
    private String contactPhone;
    @Column(name = "contact_name")
    private String contactName;
    @Column(name = "contact_addr")
    private String contactAddr;//联系地址
    @Column(name = "contact_idcard")
    private String contactIdCard;
    @Column(name = "relationship")
    private Integer relationship;
    @Column(name = "patient_certiNo")
    private String patientCertiNo;//患者证件号
    @Column(name = "homeplace")
    private String homePlace;//出生地
    @Column(name = "signUrl")
    private String signUrl; //签名url 地址
    @Column(name = "ishospitalized")
    private Integer isHospital; //是否住过本院
    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    @Column(name = "mailing_address")
    private String  mailingAddress;//户口地址
    @Column(name = "bed_no")
    private String bedNo;//床号；
    @Column(name = "pat_adm_condition")
    private String patAdmCondition;//入院病情；
    @Column(name = "admitted_by")
    private String admittedBy;//办理入院者工号
    @Column(name = "consulting_doctor")
    private String consultingDoctor;//门诊医师工号；
    @Column(name = "occupation")
    private String occupation;//职业职称；
    @Column(name = "discharge_disposition")
    private String dischargeDisposition;//出院情况代码
    @Column(name = "ward_discharge_from")
    private String wardDischargeFrom;//出院病区代码
    @Column(name = "dept_discharge_from")
    private String deptDischargeFrom;//出院科室代码
    @Column(name = "discharge_date_time")
    private String dischargeDateTime;//出院日期
    @Column(name = "admission_date_time")
    private String admissionDateTime;//入院日期
    @Column(name = "insurance")
    private String insurance;//医保类型名称
    @Column(name = "insurance_no")
    private String insuranceNo;//医疗保险号
    @Column(name = "nursing_grade")
    private Integer nursingGrade;//护理等级代码 ；
    @Column(name = "visit_times")
    private Integer visitTimes;//住院次数
    @Column(name = "diagnosis")
    private String diagnosis;//门诊诊断名称
    @Column(name = "status")
    private String status;//his 返回的
    @Column(name = "inp_no")
    private String inpNo;// 住院号
    @Column(name = "patient_id")
    private String patientId;//本次住院的唯一标识
    @Column(name = "service_agency")
    private String serviceAgency;//工作单位名称

    @Column(name = "native_place")
    private Integer nativePlace;//his 返回的籍贯代码
    @Column(name = "marital_status")
    private Integer maritalStatus;//his 返回的婚姻状态
    @Column(name = "xzz")
    private String xzz;//现在住址
    @Column(name = "insurance_type")
    private Integer insuranceType;//医疗类型；现金 少儿医保 。。。
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "update_time")
    private String updateTime;
    @Column(name = "czgh")
    private String czgh;
    @Column(name = "rytj")
    private String rytj;
    @Column(name = "zzys")
    private String zzys;
    @Column(name = "patienttype")
    private Integer patientType;
    @Column(name = "noticeId")
    private String noticeId;
    @Transient
    private String homePlaceName;
    @Transient
    private String nationalityName;
    @Transient
    private String nativePlaceName;

    public String getHomePlaceName() {
        return homePlaceName;
    }

    public void setHomePlaceName(String homePlaceName) {
        this.homePlaceName = homePlaceName;
    }

    public String getNationalityName() {
        return nationalityName;
    }

    public void setNationalityName(String nationalityName) {
        this.nationalityName = nationalityName;
    }

    public String getNativePlaceName() {
        return nativePlaceName;
    }

    public void setNativePlaceName(String nativePlaceName) {
        this.nativePlaceName = nativePlaceName;
    }

    public Integer getNativePlace() {
        return nativePlace;
    }
    public void setNativePlace(Integer nativePlace) {
        this.nativePlace = nativePlace;
    }
    public Integer getPatientType() {
        return patientType;
    }
    public void setPatientType(Integer patientType) {
        this.patientType = patientType;
    }
    public String getNoticeId() {
        return noticeId;
    }
    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
    public String getCzgh() {
        return czgh;
    }
    public void setCzgh(String czgh) {
        this.czgh = czgh;
    }

    public String getRytj() {
        return rytj;
    }

    public void setRytj(String rytj) {
        this.rytj = rytj;
    }

    public String getZzys() {
        return zzys;
    }

    public void setZzys(String zzys) {
        this.zzys = zzys;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public Integer getNationality() {
        return nationality;
    }

    public void setNationality(Integer nationality) {
        this.nationality = nationality;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public Integer getIsHospital() {
        return isHospital;
    }

    public void setIsHospital(Integer isHospital) {
        this.isHospital = isHospital;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

    public String getOutPatientNo() {
        return outPatientNo;
    }

    public void setOutPatientNo(String outPatientNo) {
        this.outPatientNo = outPatientNo;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactAddr() {
        return contactAddr;
    }

    public void setContactAddr(String contactAddr) {
        this.contactAddr = contactAddr;
    }

    public String getContactIdCard() {
        return contactIdCard;
    }

    public void setContactIdCard(String contactIdCard) {
        this.contactIdCard = contactIdCard;
    }

    public Integer getRelationship() {
        return relationship;
    }

    public void setRelationship(Integer relationship) {
        this.relationship = relationship;
    }

    public String getPatientCertiNo() {
        return patientCertiNo;
    }

    public void setPatientCertiNo(String patientCertiNo) {
        this.patientCertiNo = patientCertiNo;
    }

    public String getHomePlace() {
        return homePlace;
    }

    public void setHomePlace(String homePlace) {
        this.homePlace = homePlace;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getPatAdmCondition() {
        return patAdmCondition;
    }

    public void setPatAdmCondition(String patAdmCondition) {
        this.patAdmCondition = patAdmCondition;
    }

    public String getAdmittedBy() {
        return admittedBy;
    }

    public void setAdmittedBy(String admittedBy) {
        this.admittedBy = admittedBy;
    }

    public String getConsultingDoctor() {
        return consultingDoctor;
    }

    public void setConsultingDoctor(String consultingDoctor) {
        this.consultingDoctor = consultingDoctor;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDischargeDisposition() {
        return dischargeDisposition;
    }

    public void setDischargeDisposition(String dischargeDisposition) {
        this.dischargeDisposition = dischargeDisposition;
    }

    public String getWardDischargeFrom() {
        return wardDischargeFrom;
    }

    public void setWardDischargeFrom(String wardDischargeFrom) {
        this.wardDischargeFrom = wardDischargeFrom;
    }

    public String getDeptDischargeFrom() {
        return deptDischargeFrom;
    }

    public void setDeptDischargeFrom(String deptDischargeFrom) {
        this.deptDischargeFrom = deptDischargeFrom;
    }

    public String getDischargeDateTime() {
        return dischargeDateTime;
    }

    public void setDischargeDateTime(String dischargeDateTime) {
        this.dischargeDateTime = dischargeDateTime;
    }

    public String getAdmissionDateTime() {
        return admissionDateTime;
    }

    public void setAdmissionDateTime(String admissionDateTime) {
        this.admissionDateTime = admissionDateTime;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurence2) {
        this.insurance = insurence2;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public Integer getNursingGrade() {
        return nursingGrade;
    }

    public void setNursingGrade(Integer nursingGrade) {
        this.nursingGrade = nursingGrade;
    }

    public Integer getVisitTimes() {
        return visitTimes;
    }

    public void setVisitTimes(Integer visitTimes) {
        this.visitTimes = visitTimes;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInpNo() {
        return inpNo;
    }

    public void setInpNo(String inpNo) {
        this.inpNo = inpNo;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getServiceAgency() {
        return serviceAgency;
    }

    public void setServiceAgency(String serviceAgency) {
        this.serviceAgency = serviceAgency;
    }

    public String getXzz() {
        return xzz;
    }

    public void setXzz(String xzz) {
        this.xzz = xzz;
    }

    public Integer getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(Integer insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }
    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public static void main(String[] args) {
        HospitalizedPatientInfoNew1 hospitalizedPatientInfoNew1=new HospitalizedPatientInfoNew1();
        hospitalizedPatientInfoNew1.setId(1);
        StringWriter sw = new StringWriter();
        try {
            // 利用jdk中自带的转换类实现
            JAXBContext context = JAXBContext.newInstance(hospitalizedPatientInfoNew1.getClass());

            Marshaller marshaller = context.createMarshaller();

            // 格式化xml输出的格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            // 将对象转换成输出流形式的xml
            marshaller.marshal(hospitalizedPatientInfoNew1, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println(sw.toString());
    }
}
