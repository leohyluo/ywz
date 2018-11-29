package com.alpha.pojo.vo;



import com.alpha.commons.core.pojo.inspcetion.JYRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/16.
 *
 */
public class YSInspcetVO {

    private String medicalCardNo;
    private String name;
    private Integer sex;
    private String age;
    private String inspectionProjectName;
    private Integer inspectionProjectCode;
    private String applicationDepartment;
    private String doctor;
    private String diagnosis;
    private String reportingTime;
    private String reportNo;
    private Integer type;
    private Integer status;
    private String position;
    private String canSee;
    private String diagnosticOpinion;
    private List<String> pictures;
    private List<InspectionDetailVO> details;
    private String hospital;

    private String jc_type; //检验 心脑电 放射  超声

    public String getJc_type() {
        return jc_type;
    }

    public void setJc_type(String jc_type) {
        this.jc_type = jc_type;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public List<InspectionDetailVO> getDetails() {
        return details;
    }

    public void setDetails(List<InspectionDetailVO> details) {
        this.details = details;
    }


    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getMedicalCardNo() {
        return medicalCardNo;
    }

    public void setMedicalCardNo(String medicalCardNo) {
        this.medicalCardNo = medicalCardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getInspectionProjectName() {
        return inspectionProjectName;
    }

    public void setInspectionProjectName(String inspectionProjectName) {
        this.inspectionProjectName = inspectionProjectName;
    }

    public Integer getInspectionProjectCode() {
        return inspectionProjectCode;
    }

    public void setInspectionProjectCode(Integer inspectionProjectCode) {
        this.inspectionProjectCode = inspectionProjectCode;
    }

    public String getApplicationDepartment() {
        return applicationDepartment;
    }

    public void setApplicationDepartment(String applicationDepartment) {
        this.applicationDepartment = applicationDepartment;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getReportingTime() {
        return reportingTime;
    }

    public void setReportingTime(String reportingTime) {
        this.reportingTime = reportingTime;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCanSee() {
        return canSee;
    }

    public void setCanSee(String canSee) {
        this.canSee = canSee;
    }

    public String getDiagnosticOpinion() {
        return diagnosticOpinion;
    }

    public void setDiagnosticOpinion(String diagnosticOpinion) {
        this.diagnosticOpinion = diagnosticOpinion;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public static YSInspcetVO parse(JYRequest jyRequest){
        YSInspcetVO vo=new YSInspcetVO();
        vo.setMedicalCardNo(jyRequest.getPatientId()==null?null:jyRequest.getPatientId());
        vo.setAge(jyRequest.getAge()==null?null:jyRequest.getAge());
        vo.setSex(jyRequest.getSex()=="男"?1:2);
        vo.setInspectionProjectName(jyRequest.getReportName());
        vo.setName(jyRequest.getPatientName());
        vo.setApplicationDepartment(jyRequest.getDeptReportName());
        vo.setDoctor(jyRequest.getReporter());
        vo.setDiagnosis(jyRequest.getDiagnose());
        vo.setReportingTime(jyRequest.getReportDate());
        vo.setReportNo(jyRequest.getReportId());
        vo.setType(1);
        vo.setStatus(2);
        vo.setPosition(jyRequest.getSpecimenType());
        vo.setJc_type("检验");
        vo.setHospital("深圳市儿童医院");
        return vo;
    }

    public static List<YSInspcetVO> parseXND(List<XNDrequestVO> xnd){
        List<YSInspcetVO> li=new ArrayList<>();
        xnd.stream().forEach(e -> {
            YSInspcetVO vo=new YSInspcetVO();
            vo.setName(e.getPatientName());
            vo.setSex(e.getSex()=="男"?1:2);
            vo.setDoctor(e.getReporter());
            vo.setReportNo(e.getReportId());
            vo.setReportingTime(e.getReportDate());
            vo.setApplicationDepartment(e.getDeptRequestName());
            vo.setInspectionProjectName(e.getReportName());
            vo.setDiagnosticOpinion(e.getDetail().get(0).getItemResult());
            vo.setCanSee(e.getDetail().get(0).getItemResult()==null?null:e.getDetail().get(0).getItemResult());
            vo.setType(2);
            vo.setStatus(2);
            vo.setJc_type("心脑电");
            vo.setHospital("深圳市儿童医院");
            li.add(vo);
        });

        return li;
    }

    public static List<YSInspcetVO> parseFS(List<FSrequestVO> fs){

        List<YSInspcetVO> li=new ArrayList<>();
        fs.stream().forEach(e -> {
            YSInspcetVO vo=new YSInspcetVO();
            vo.setName(e.getName());
            vo.setSex(e.getSex()=="男"?1:2);
            vo.setApplicationDepartment(e.getExamDeptName());
            vo.setInspectionProjectName(e.getItemChName());
            vo.setReportNo(e.getExamSno());
            vo.setDoctor(e.getDetail().get(0).getExamVerifyDoctorName());
            vo.setReportingTime(e.getDetail().get(0).getExamReportDate());
            vo.setPosition(e.getDetail().get(0).getExamMethod());
            vo.setDiagnosticOpinion(e.getDetail().get(0).getExamResult());
            vo.setCanSee(e.getDetail().get(0).getExamDesc());
            vo.setType(2);
            vo.setStatus(2);
            vo.setJc_type("放射");
            vo.setHospital("深圳市儿童医院");
            li.add(vo);

        });
        return li;
    }

    public static List<YSInspcetVO> parseCS(List<CSreportVO> cs){

        List<YSInspcetVO> li=new ArrayList<>();
        cs.stream().forEach(e -> {
            YSInspcetVO vo=new YSInspcetVO();
            vo.setReportNo(e.getRequestId());
            vo.setSex(e.getSex()=="男"?1:2);
            vo.setPosition(e.getStudyPart());
            vo.setDoctor(e.getReporter());
            vo.setDiagnosticOpinion(e.getConclusion());
            vo.setCanSee(e.getReport());
            vo.setReportingTime(e.getReportDate());
            vo.setJc_type("放射");
            vo.setType(2);
            vo.setStatus(2);
            vo.setHospital("深圳市儿童医院");
            li.add(vo);
        });
        return li;
    }

}
