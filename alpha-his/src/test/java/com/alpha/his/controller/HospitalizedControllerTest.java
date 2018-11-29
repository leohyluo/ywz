/*
package com.alpha.his.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alpha.server.rpc.his.pojo.HospitalizedCommonIllChild;
import com.alpha.server.rpc.his.pojo.HospitalizedNewIllChild;
import com.alpha.server.rpc.his.pojo.HospitalizedNotice;
import com.alpha.server.rpc.his.pojo.HospitalizedPatientInfo;
import com.alpha.his.pojo.dto.UserHospitalized;
import com.alpha.his.utils.HttpUtils;
import org.junit.Test;

public class HospitalizedControllerTest {

    @Test
    public void testSaveHospitalized() {
        String uri = "http://172.16.58.221:19092/hospitalized/save";
        HospitalizedPatientInfo hospitalizedPatientInfo = new HospitalizedPatientInfo();

        hospitalizedPatientInfo.setPatientName("张三");
        hospitalizedPatientInfo.setSex("1");
        hospitalizedPatientInfo.setBirthday("1999-11-11");
        hospitalizedPatientInfo.setNationality("中国");
        hospitalizedPatientInfo.setNation("汉族");
        hospitalizedPatientInfo.setContactPhone("134366454512");
        hospitalizedPatientInfo.setContactName("张伟");
        hospitalizedPatientInfo.setContactIdcard("44086464578545121");
        hospitalizedPatientInfo.setRelationship("0");
        hospitalizedPatientInfo.setPatientCertitype("0");
        hospitalizedPatientInfo.setPatientCertino("74568468784212142");
        hospitalizedPatientInfo.setSchool("宝安中学");
        hospitalizedPatientInfo.setAddress("宝安区");

        HospitalizedNotice hospitalizedNotice = new HospitalizedNotice();
//        hospitalizedNotice.setHospitalCode("A002");
//        hospitalizedNotice.setOutPatientNo("B001");
//        hospitalizedNotice.setHosno("1001");
        hospitalizedNotice.setPaytype("1");
        hospitalizedNotice.setIndep("骨科");
        hospitalizedNotice.setIntype("1");
        hospitalizedNotice.setIncase("1");
        hospitalizedNotice.setInchannel("1");
        hospitalizedNotice.setDiagnosis("右拇指骨折");
        hospitalizedNotice.setIsfect("1");
        hospitalizedNotice.setNotifytime("2018-01-11");
        hospitalizedNotice.setContactphone("134366454512");
        hospitalizedNotice.setDisdesc("敷石膏");

        HospitalizedCommonIllChild hospitalizedCommonIllChild = new HospitalizedCommonIllChild();
        hospitalizedCommonIllChild.setBedno("013");
        hospitalizedCommonIllChild.setMorbidityTime("3天");
        hospitalizedCommonIllChild.setAft("第一胎");
        hospitalizedCommonIllChild.setAfp("首产");
        hospitalizedCommonIllChild.setIsterm("1");
        hospitalizedCommonIllChild.setAbp("1");
        hospitalizedCommonIllChild.setDcb("1");
        hospitalizedCommonIllChild.setIsdisim("0");
        hospitalizedCommonIllChild.setBornstatus("1");
        hospitalizedCommonIllChild.setBornweight("5.4kg");
        hospitalizedCommonIllChild.setBmilkmonth("8个月");
        hospitalizedCommonIllChild.setFoodtime("5个月");
        hospitalizedCommonIllChild.setUptime("3个月");
        hospitalizedCommonIllChild.setSeattime("6个月");
        hospitalizedCommonIllChild.setStandtime("9个月");
        hospitalizedCommonIllChild.setMovetime("12个月");
        hospitalizedCommonIllChild.setToothtime("12个月");
        hospitalizedCommonIllChild.setLaughtime("3个月");
        hospitalizedCommonIllChild.setLooktime("3个月");
        hospitalizedCommonIllChild.setSpeektime("12个月");
        hospitalizedCommonIllChild.setVaccineinfo("1");
        hospitalizedCommonIllChild.setSincedis("0");
        hospitalizedCommonIllChild.setSincedistime("2018-01-11");
        hospitalizedCommonIllChild.setSincehosname("儿童医院");
        hospitalizedCommonIllChild.setSincehostime("1天");
        hospitalizedCommonIllChild.setIsah("0");
        hospitalizedCommonIllChild.setWhatah("无");
        hospitalizedCommonIllChild.setIsop("无");
        hospitalizedCommonIllChild.setOpname("无");
        hospitalizedCommonIllChild.setFathername("李鹏");
        hospitalizedCommonIllChild.setFatherage("50岁");
        hospitalizedCommonIllChild.setFatherheal("1");
        hospitalizedCommonIllChild.setFatheridno("46546545874545");
        hospitalizedCommonIllChild.setMomname("周厦");
        hospitalizedCommonIllChild.setMomage("45岁");
        hospitalizedCommonIllChild.setMomheal("1");
        hospitalizedCommonIllChild.setMomidno("7987979454532");
        hospitalizedCommonIllChild.setPregnanttimes("1次");
        hospitalizedCommonIllChild.setAbortiontimes("0次");
        hospitalizedCommonIllChild.setSoptimes("0次");
        hospitalizedCommonIllChild.setWchild("1个");
        hospitalizedCommonIllChild.setChildage("10岁");
        hospitalizedCommonIllChild.setChildheal("1");
        hospitalizedCommonIllChild.setInfectiousdis("无");
        hospitalizedCommonIllChild.setDtime("2018-01-11");
        hospitalizedCommonIllChild.setDauthor("周厦");
        hospitalizedCommonIllChild.setDrship("母子");

        HospitalizedNewIllChild hospitalizedNewIllChild = new HospitalizedNewIllChild();
        hospitalizedNewIllChild.setMainsymp("骨折3天");
        hospitalizedNewIllChild.setMeconium("正常");
        hospitalizedNewIllChild.setUcdown("第3天");
        hospitalizedNewIllChild.setNavel("无");
        hospitalizedNewIllChild.setSecretion("无");
        hospitalizedNewIllChild.setNurse("正常");
        hospitalizedNewIllChild.setCayn("正常");
        hospitalizedNewIllChild.setTemperature("38");
        hospitalizedNewIllChild.setIsjaundice("无");
        hospitalizedNewIllChild.setIscyanose("无");
        hospitalizedNewIllChild.setIstwitch("无");
        hospitalizedNewIllChild.setIsdyspnea("无");
        hospitalizedNewIllChild.setStool("正常");
        hospitalizedNewIllChild.setTp("无");
        hospitalizedNewIllChild.setBmilkmonth("35kg");
        hospitalizedNewIllChild.setFoodtime("1");
        hospitalizedNewIllChild.setCayo("1");
        hospitalizedNewIllChild.setSkincolor("黄色");
        hospitalizedNewIllChild.setToe("正常");
        hospitalizedNewIllChild.setAfi("正常");
        hospitalizedNewIllChild.setOnetime("1");
        hospitalizedNewIllChild.setFivetime("5");
        hospitalizedNewIllChild.setIsasphyxia("无");
        hospitalizedNewIllChild.setTimeasphyxia("无");
        hospitalizedNewIllChild.setResultrm("无");
        hospitalizedNewIllChild.setJaundicetime("10天");
        hospitalizedNewIllChild.setJaundiceuptime("3天");
        hospitalizedNewIllChild.setJaundicedtime("13天");
        hospitalizedNewIllChild.setDjaundicedgree("不严重");
        hospitalizedNewIllChild.setOmtime("1");
        hospitalizedNewIllChild.setOmtype("母乳");
        hospitalizedNewIllChild.setNc("1");
        hospitalizedNewIllChild.setIsbcg("1");
        hospitalizedNewIllChild.setIshb("1");
        hospitalizedNewIllChild.setIshbhei("1");
        hospitalizedNewIllChild.setFatherheal("1");
        hospitalizedNewIllChild.setFathername("张明");
        hospitalizedNewIllChild.setFatheridno("456841564545478");
        hospitalizedNewIllChild.setFatherbtype("O型");
        hospitalizedNewIllChild.setMomname("李厦");
        hospitalizedNewIllChild.setMomage("1236457474");
        hospitalizedNewIllChild.setMomheal("正常");
        hospitalizedNewIllChild.setMomidno("76845614561564");
        hospitalizedNewIllChild.setMombtype("O型");
        hospitalizedNewIllChild.setIshealth("正常");
        hospitalizedNewIllChild.setCh("无");
        hospitalizedNewIllChild.setCha("无");
        hospitalizedNewIllChild.setWjt("第一胎");
        hospitalizedNewIllChild.setWjc("首产");
        hospitalizedNewIllChild.setRrweek("第三周");
        hospitalizedNewIllChild.setDcsize("无");
        hospitalizedNewIllChild.setRradd("医院");
        hospitalizedNewIllChild.setBabytype("接生婆");
        hospitalizedNewIllChild.setRrtype("顺产");
        hospitalizedNewIllChild.setAlltime("23小时");
        hospitalizedNewIllChild.setSecondtime("0小时");
        hospitalizedNewIllChild.setAftype("无");
        hospitalizedNewIllChild.setIsafb("无");
        hospitalizedNewIllChild.setIspla("无");
        hospitalizedNewIllChild.setIsuca("无");
        hospitalizedNewIllChild.setRrtimes("1次");
        hospitalizedNewIllChild.setZrltimes("0次");
        hospitalizedNewIllChild.setRgltimes("0次");
        hospitalizedNewIllChild.setWst("0");
        hospitalizedNewIllChild.setWsc("0");
        hospitalizedNewIllChild.setHchild("1个");
        hospitalizedNewIllChild.setChildages("10岁");
        hospitalizedNewIllChild.setChildheal("1");
        hospitalizedNewIllChild.setInfectiousdis("无");
        hospitalizedNewIllChild.setDtime("2018-01-11");
        hospitalizedNewIllChild.setDauthor("周厦");
        hospitalizedNewIllChild.setDrship("母女");

        UserHospitalized userHospitalized = new UserHospitalized();
        userHospitalized.setHospitalCode("A002");
        userHospitalized.setOutPatientNo("B002");
        userHospitalized.setHosno("1002");
        //userHospitalized.setHospitalizedPatientInfo(hospitalizedPatientInfo);
        //userHospitalized.setHospitalizedNotice(hospitalizedNotice);
        userHospitalized.setHospitalizedCommonIllChild(hospitalizedCommonIllChild);
        //userHospitalized.setHospitalizedNewIllChild(hospitalizedNewIllChild);

        String jsonStr = JSON.toJSONString(userHospitalized);
        System.out.println(jsonStr);

        String result = HttpUtils.postWithAllParam(uri, jsonStr);
        System.out.println(result);
    }

    @Test
    public void testGetNoticedInfo() {
        String uri = "http://172.16.58.221:19092/hospitalized/notice/get";
        JSONObject json = new JSONObject();
        json.put("hospitalCode", "A002");
        json.put("hosNo", "1002");
        String result = HttpUtils.postWithAllParam(uri, json.toJSONString());
        System.out.println(result);
    }

    @Test
    public void testGetHospitalized() {
        String uri = "http://172.16.58.221:19092/user/hospitalized/get";
        JSONObject json = new JSONObject();
        json.put("hospitalCode", "A002");
        json.put("hosNo", "1001");
        String result = HttpUtils.postWithAllParam(uri, json.toJSONString());
        System.out.println(result);
    }
}


*/
