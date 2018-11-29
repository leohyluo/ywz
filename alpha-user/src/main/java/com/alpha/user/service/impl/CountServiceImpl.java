package com.alpha.user.service.impl;

import com.alpha.commons.core.mapper.YwzCountTimesMapper;
import com.alpha.commons.core.pojo.YwzCountTimes;
import com.alpha.commons.core.service.YwzCountTimesService;
import com.alpha.user.pojo.vo.*;
import com.alpha.user.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by HP on 2018/5/19.
 */
@Service
public class CountServiceImpl implements CountService {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

    @Autowired
    YwzCountTimesService ywzCountTimesService;

    @Resource
    private YwzCountTimesMapper ywzCountTimesMapper;


    @Override
    public ResultVo ywztimesnew(String startTime, String endTime) {
        ResultVo resultVo = new ResultVo();

        //综合统计
        List<CountShowVO> list = new ArrayList<>();
        //医生人数统计
        List<CountShowVO> list1 = new ArrayList<>();
        //导入详情统计
        List<CountShowVO> list2 = new ArrayList<>();
        //编辑详情统计
        List<CountShowVO> list3 = new ArrayList<>();

//获取时间段有哪些日期
        List<String> listdate = countDate(startTime, endTime);

        if (null != listdate && listdate.size() > 0) {
            for (String s : listdate) {
                //一天
                CountShowVO countShowVO = new CountShowVO();
                countShowVO.setDate(s);

                CountShowVO docot = new CountShowVO();
                docot.setDate(s);

                CountShowVO imp = new CountShowVO();
                imp.setDate(s);

                CountShowVO edi = new CountShowVO();
                edi.setDate(s);

                List<CountTimesVo> countTimesVoList = new ArrayList<>();
                List<DocDetailVo> docDetailVos = new ArrayList<>();
                List<ImportAndEditDetailVo> importAndEditDetailVos = new ArrayList<>();
                List<ImportAndEditDetailVo> importAndEditDetailVos1 = new ArrayList<>();

//             上午
                String AMstart = s.concat(" 00:00:00");
                String AMend = s.concat(" 12:00:00");
                CountTimesVo am = new CountTimesVo();
                am.setFlag("AM");
                DocDetailVo docDetailVoAM = new DocDetailVo();
                docDetailVoAM.setFlag("AM");
                ImportAndEditDetailVo importAndEditDetailVoAM = new ImportAndEditDetailVo();
                importAndEditDetailVoAM.setFlage("AM");
                ImportAndEditDetailVo importAndEditDetailVo1AM = new ImportAndEditDetailVo();
                importAndEditDetailVo1AM.setFlage("AM");
                selTimesnew(AMstart, AMend, am, docDetailVoAM, importAndEditDetailVoAM, importAndEditDetailVo1AM);


//             下午
                String PMstart = s.concat(" 12:00:00");
                String PMend = s.concat(" 23:59:59");
                CountTimesVo pm = new CountTimesVo();
                pm.setFlag("PM");
                DocDetailVo docDetailVoPM = new DocDetailVo();
                docDetailVoPM.setFlag("PM");
                ImportAndEditDetailVo importAndEditDetailVoPM = new ImportAndEditDetailVo();
                importAndEditDetailVoPM.setFlage("PM");
                ImportAndEditDetailVo importAndEditDetailVo1PM = new ImportAndEditDetailVo();
                importAndEditDetailVo1PM.setFlage("PM");
                selTimesnew(PMstart, PMend, pm, docDetailVoPM, importAndEditDetailVoPM, importAndEditDetailVo1PM);


//             总计
                CountTimesVo count = new CountTimesVo();
                count.setFlag("总计");
                DocDetailVo docDetailVocount = new DocDetailVo();
                docDetailVocount.setFlag("总计");
                ImportAndEditDetailVo importAndEditDetailVocount = new ImportAndEditDetailVo();
                importAndEditDetailVocount.setFlage("总计");
                ImportAndEditDetailVo importAndEditDetailVo1count = new ImportAndEditDetailVo();
                importAndEditDetailVo1count.setFlage("总计");

                //上下午统计
                add(count, am, pm);
                add1(docDetailVocount, docDetailVoAM, docDetailVoPM);
                add2(importAndEditDetailVocount, importAndEditDetailVoAM, importAndEditDetailVoPM);
                add2(importAndEditDetailVo1count, importAndEditDetailVo1PM, importAndEditDetailVo1AM);

                countTimesVoList.add(am);
                countTimesVoList.add(pm);
                countTimesVoList.add(count);
                countShowVO.setList(countTimesVoList);
                list.add(countShowVO);

                docDetailVos.add(docDetailVoAM);
                docDetailVos.add(docDetailVoPM);
                docDetailVos.add(docDetailVocount);
                docot.setList(docDetailVos);
                list1.add(docot);

                importAndEditDetailVos.add(importAndEditDetailVoAM);
                importAndEditDetailVos.add(importAndEditDetailVoPM);
                importAndEditDetailVos.add(importAndEditDetailVocount);
                imp.setList(importAndEditDetailVos);
                list2.add(imp);

                importAndEditDetailVos1.add(importAndEditDetailVo1AM);
                importAndEditDetailVos1.add(importAndEditDetailVo1PM);
                importAndEditDetailVos1.add(importAndEditDetailVo1count);
                edi.setList(importAndEditDetailVos1);
                list3.add(edi);

            }
        }

        resultVo.setAllresult(list);
        resultVo.setDocDetail(list1);
        resultVo.setImportDetail(list2);
        resultVo.setEditDetail(list3);

        return resultVo;
    }


    @Override
    public void selTimesnew(String startTime, String endTime, CountTimesVo countTimesVo, DocDetailVo docDetailVo, ImportAndEditDetailVo importAndEditDetailVo, ImportAndEditDetailVo importAndEditDetailVo1) {

        //1开卡，2扫码，3使用，4提交，5展现
        List<YwzCountTimes> typelist = ywzCountTimesService.gettimes(startTime, endTime, null);
        Map<Integer, Integer> mapTypeList = typelist.stream().collect(Collectors.toMap(YwzCountTimes::getType,
                YwzCountTimes::getTimes));
        countTimesVo.setCardTimes(mapTypeList.get(1) == null ? 0 : mapTypeList.get(1));
        countTimesVo.setScanTimes(mapTypeList.get(2) == null ? 0 : mapTypeList.get(2));
        countTimesVo.setUseYwzTimes(mapTypeList.get(3) == null ? 0 : mapTypeList.get(3));
        countTimesVo.setSubmitYwzTimes(mapTypeList.get(4) == null ? 0 : mapTypeList.get(4));
        countTimesVo.setECHopenTiems(mapTypeList.get(5) == null ? 0 : mapTypeList.get(5));
        //医生使用数
        List<YwzCountTimes> userAndimport = ywzCountTimesService.userAndimport(startTime, endTime, null);
        countTimesVo.setDoctorNum((null == userAndimport || userAndimport.size() < 1) ? 0 : userAndimport.size());
        //导入数
        countTimesVo.setECHimportTimes((null == userAndimport || userAndimport.size() < 1) ? 0 : (userAndimport.stream()
                .mapToInt(YwzCountTimes::getTimes)).sum());

        //具体医生展现次数
        List<YwzCountTimes> doctorShowTimes = ywzCountTimesService.doctorShowTimes(startTime, endTime, null);
        for (YwzCountTimes doctorShowTime : doctorShowTimes) {
            if (null == doctorShowTime.getDoctorName()) {
                doctorShowTime.setDoctorName("未知姓名");
            }
        }
        for (YwzCountTimes doctorShowTime : userAndimport) {
            if (null == doctorShowTime.getDoctorName()) {
                doctorShowTime.setDoctorName("未知姓名");
            }
        }
        Map<String, Integer> improt = userAndimport.stream().collect(Collectors.toMap(YwzCountTimes::getDoctorName,
                YwzCountTimes::getTimes));
        Map<String, Integer> show = doctorShowTimes.stream().collect(Collectors.toMap(YwzCountTimes::getDoctorName,
                YwzCountTimes::getTimes));
        List<DoctorTimes> doctorTimes = new ArrayList<>();
        for (String key : show.keySet()) {
            DoctorTimes doctorTime = new DoctorTimes();
            if (null == key) {
                doctorTime.setDocName("未知姓名");
            } else {
                doctorTime.setDocName(key);
            }
            doctorTime.setTimes(improt.get(key) == null ? 0 : improt.get(key));
            doctorTime.setShowTimes(show.get(key) == null ? 0 : show.get(key));
            doctorTimes.add(doctorTime);
        }
        Comparator<DoctorTimes> comparator = (h1, h2) -> h1.getTimes().compareTo(h2.getTimes());
        doctorTimes.sort(comparator.reversed());
        docDetailVo.setDoctorTimes(doctorTimes);
        //模块导入统计
        List<YwzCountTimes> importTimes = ywzCountTimesService.importTimes(startTime, endTime, null);
        Map<String, Integer> improtMap = importTimes.stream().collect(Collectors.toMap(YwzCountTimes::getDescri,
                YwzCountTimes::getTimes));
        importAndEditDetailVo.setMainSymptomName(improtMap.get("61") == null ? 0 : improtMap.get("61"));
        importAndEditDetailVo.setPresentIllnessHistory(improtMap.get("62") == null ? 0 : improtMap.get("62"));
        importAndEditDetailVo.setPastmedicalHistory(improtMap.get("63") == null ? 0 : improtMap.get("63"));
        importAndEditDetailVo.setDiseases(improtMap.get("64") == null ? 0 : improtMap.get("64"));

        //模块编辑统计
        List<YwzCountTimes> editTimes = ywzCountTimesService.editTimes(startTime, endTime);
        Map<String, Integer> editMap = editTimes.stream().collect(Collectors.toMap(YwzCountTimes::getDescri,
                YwzCountTimes::getTimes));
        importAndEditDetailVo1.setMainSymptomName(editMap.get("71") == null ? 0 : editMap.get("71"));
        importAndEditDetailVo1.setPresentIllnessHistory(editMap.get("72") == null ? 0 : editMap.get("72"));
        importAndEditDetailVo1.setPastmedicalHistory(editMap.get("73") == null ? 0 : editMap.get("73"));
        importAndEditDetailVo1.setDiseases(editMap.get("74") == null ? 0 : editMap.get("74"));

        //给综合统计模块增加一些新的数据
        //1.预约推送量   增加科室分组
        Integer pushAppointmentTimes = ywzCountTimesMapper.pushAppointmentTimes(startTime, endTime, null);
        countTimesVo.setPushAppointmentTimes(pushAppointmentTimes);
        //2.预约推送成功量 增加科室分组
        Integer pushAppointmentSuccessTimes = ywzCountTimesMapper.pushAppointmentSuccessTimes(startTime, endTime, null);
        countTimesVo.setPushAppointmentSuccessTimes(pushAppointmentSuccessTimes);
        //3.现场取号量推送量 增加科室分组
        Integer pushLiveTimes = ywzCountTimesMapper.pushLiveTimes(startTime, endTime, null);
        countTimesVo.setPushLiveTimes(pushLiveTimes);
        //4.取号推送成功量 增加科室分组
        Integer pushLiveSuccessTimes = ywzCountTimesMapper.pushLiveSuccessTimes(startTime, endTime, null);
        countTimesVo.setPushLiveSuccessTimes(pushLiveSuccessTimes);
        //6.扫码量
        //7.扫码完成量
        //根据channel =1 and status =10 统计
        Integer scanSuccessTimes1 = ywzCountTimesMapper.scanSuccessTimes1(startTime, endTime);
        //统计channel 字段上线之前的
        Integer scanSuccessTimes2 = ywzCountTimesMapper.scanSuccessTimes2(startTime, endTime);

        countTimesVo.setScanSuccessTimes(scanSuccessTimes1 + scanSuccessTimes2);
//        //8.初诊量
        Integer firstVisitTimes = ywzCountTimesMapper.firstVisitTimes(startTime, endTime, null);
        Integer firstVisitTimes1 = ywzCountTimesMapper.firstVisitTimes1(startTime, endTime, null);
       // Integer firstVisitTimes2 = ywzCountTimesMapper.firstVisitTimes2(startTime, endTime, null);

        countTimesVo.setFirstVisitTimes(firstVisitTimes + firstVisitTimes1);
        //10.复诊量
        Integer secondVisitTimes = ywzCountTimesMapper.secondVisitTimes(startTime, endTime);
        countTimesVo.setSecondVisitTimes(secondVisitTimes);
        //5.预问诊完成量
        //+复诊的即可
        countTimesVo.setWzSuccessTimes(countTimesVo.getFirstVisitTimes());
        //9.初诊未覆盖量=进入系统量-复诊-初诊量
        Integer firstNoCoverTimes = ywzCountTimesMapper.firstNoCoverTimes(startTime, endTime);
        countTimesVo.setFirstVisitNoCoverTimes(firstNoCoverTimes);
        //推送成功点击
        List<Map<String, String>> clik = ywzCountTimesMapper.getClik(startTime, endTime, null);
        Integer a = 0;
        Integer b = 0;
        if (clik != null && clik.size() > 0) {
            for (int i = 0; i < clik.size(); i++) {
                Map map = clik.get(i);
                Integer type = (Integer) map.get("type");
                Integer times = Integer.parseInt(map.get("times").toString());
                if (type == 1 || type == 3) {
                    a = a + times;
                } else {
                    b = b + times;
                }
            }
        }
        countTimesVo.setPushAppointmentClickTimes(b);
        countTimesVo.setPushLiveClickTimes(a);
        //点击提交
        List<Map<String, String>> submit = ywzCountTimesMapper.getSubmit(startTime, endTime, null);
        Integer c = 0;
        Integer d = 0;
        if (clik != null && submit.size() > 0) {
            for (int i = 0; i < submit.size(); i++) {
                Map map = submit.get(i);
                Integer type = (Integer) map.get("type");
                Integer times = Integer.parseInt(map.get("times").toString());
                if (type == 1 || type == 3) {
                    c = c + times;
                } else {
                    d = d + times;
                }
            }
        }
        countTimesVo.setPushAppointmentSubmitTimes(d);
        countTimesVo.setPushLiveSubmitTimes(c);
    }


    @Override
    public void selTimes(String startTime, String endTime, CountTimesVo countTimesVo, DocDetailVo docDetailVo, ImportAndEditDetailVo importAndEditDetailVo, ImportAndEditDetailVo importAndEditDetailVo1) {


        List<YwzCountTimes> typelist = ywzCountTimesService.gettimes(startTime, endTime, null);

        List<YwzCountTimes> patientlist = ywzCountTimesService.doctorpatienttimes(startTime, endTime);

        List<YwzCountTimes> nodoctorlist = ywzCountTimesService.nodoctortimes(startTime, endTime);

        List<YwzCountTimes> editlist = ywzCountTimesService.getedittimes(startTime, endTime);

        List<YwzCountTimes> importlist = ywzCountTimesService.getimporttimes(startTime, endTime);


        //医生导入人次统计
        List<DoctorTimes> list = new ArrayList<>();
        if (null != patientlist && patientlist.size() > 0) {
            patientlist.stream().forEach(e -> {
                DoctorTimes doctorTimes = new DoctorTimes();
                doctorTimes.setDocName(e.getDoctorName());
                doctorTimes.setTimes(e.getTimes());
                list.add(doctorTimes);
            });
        }

        if (null != nodoctorlist && nodoctorlist.size() > 0) {
            DoctorTimes doctorTimes = new DoctorTimes();
            doctorTimes.setTimes(nodoctorlist.size());
            doctorTimes.setDocName("未知医生名字");
            list.add(doctorTimes);
        }
        docDetailVo.setDoctorTimes(list);

        Map editlistmap = new HashMap();
        if (null != editlist && editlist.size() > 0) {
            editlistmap = editlist.stream().collect(Collectors.toMap(YwzCountTimes::getDescri, Function.identity()));
        }

        Map importlistmap = new HashMap();
        if (null != importlist && importlist.size() > 0) {
            importlistmap = importlist.stream().collect(Collectors.toMap(YwzCountTimes::getDescri, Function.identity()));
        }

        //编辑详情

        if (null != editlistmap.get("71")) {
            YwzCountTimes ywzCountTimes = (YwzCountTimes) (editlistmap.get("71"));
            importAndEditDetailVo1.setMainSymptomName(ywzCountTimes.getTimes());
        } else {
            importAndEditDetailVo1.setMainSymptomName(0);
        }
        if (null != editlistmap.get("72")) {
            YwzCountTimes ywzCountTimes = (YwzCountTimes) (editlistmap.get("72"));
            importAndEditDetailVo1.setPresentIllnessHistory(ywzCountTimes.getTimes());
        } else {
            importAndEditDetailVo1.setPresentIllnessHistory(0);
        }
        if (null != editlistmap.get("73")) {
            YwzCountTimes ywzCountTimes = (YwzCountTimes) (editlistmap.get("73"));
            importAndEditDetailVo1.setPastmedicalHistory(ywzCountTimes.getTimes());
        } else {
            importAndEditDetailVo1.setPastmedicalHistory(0);
        }
        if (null != editlistmap.get("74")) {
            YwzCountTimes ywzCountTimes = (YwzCountTimes) (editlistmap.get("74"));
            importAndEditDetailVo1.setDiseases(ywzCountTimes.getTimes());
        } else {
            importAndEditDetailVo1.setDiseases(0);
        }

        //导入详情

        if (null != importlistmap.get("61")) {
            YwzCountTimes ywzCountTimes = (YwzCountTimes) (importlistmap.get("61"));
            importAndEditDetailVo.setMainSymptomName(ywzCountTimes.getTimes());
        } else {
            importAndEditDetailVo.setMainSymptomName(0);
        }
        if (null != importlistmap.get("62")) {
            YwzCountTimes ywzCountTimes = (YwzCountTimes) (importlistmap.get("62"));
            importAndEditDetailVo.setPresentIllnessHistory(ywzCountTimes.getTimes());
        } else {
            importAndEditDetailVo.setPresentIllnessHistory(0);
        }
        if (null != importlistmap.get("63")) {
            YwzCountTimes ywzCountTimes = (YwzCountTimes) (importlistmap.get("63"));
            importAndEditDetailVo.setPastmedicalHistory(ywzCountTimes.getTimes());
        } else {
            importAndEditDetailVo.setPastmedicalHistory(0);
        }
        if (null != importlistmap.get("64")) {
            YwzCountTimes ywzCountTimes = (YwzCountTimes) (importlistmap.get("64"));
            importAndEditDetailVo.setDiseases(ywzCountTimes.getTimes());
        } else {
            importAndEditDetailVo.setDiseases(0);
        }

//综合统计
        if (null != typelist && typelist.size() > 0) {
            typelist.stream().forEach(e -> {
                if (e.getType().equals(1)) {
                    countTimesVo.setCardTimes(e.getTimes());
                }
                if (e.getType().equals(2)) {
                    countTimesVo.setScanTimes(e.getTimes());
                }
                if (e.getType().equals(3)) {
                    countTimesVo.setUseYwzTimes(e.getTimes());
                }
                if (e.getType().equals(4)) {
                    countTimesVo.setSubmitYwzTimes(e.getTimes());
                }
                if (e.getType().equals(5)) {
                    countTimesVo.setECHopenTiems(e.getTimes());
                }
            });
            if (null == countTimesVo.getCardTimes())
                countTimesVo.setCardTimes(0);
            if (null == countTimesVo.getScanTimes())
                countTimesVo.setScanTimes(0);
            if (null == countTimesVo.getSubmitYwzTimes())
                countTimesVo.setSubmitYwzTimes(0);
            if (null == countTimesVo.getUseYwzTimes())
                countTimesVo.setUseYwzTimes(0);
            if (null == countTimesVo.getECHopenTiems())
                countTimesVo.setECHopenTiems(0);
        } else {
            countTimesVo.setSubmitYwzTimes(0);
            countTimesVo.setUseYwzTimes(0);
            countTimesVo.setCardTimes(0);
            countTimesVo.setScanTimes(0);
            countTimesVo.setECHopenTiems(0);
        }

// 被导入人数  +  医生数
        int a = (null == patientlist || patientlist.size() < 1) ? 0 : patientlist.size();
        int d = (null == nodoctorlist || nodoctorlist.size() < 1) ? 0 : nodoctorlist.size();
        int b = 0;
        if (d > 0) {
            b = 1;
        }
        countTimesVo.setDoctorNum(a + b);
        int c = 0;
        if (null != patientlist && patientlist.size() > 0) {
            for (YwzCountTimes ywzCountTimes : patientlist) {
                c += ywzCountTimes.getTimes();
            }
        }
        countTimesVo.setECHimportTimes(d + c);
    }


    @Override
    public void add(CountTimesVo count, CountTimesVo am, CountTimesVo pm) {
        count.setECHimportTimes(am.getECHimportTimes() + pm.getECHimportTimes());
        count.setECHopenTiems(am.getECHopenTiems() + pm.getECHopenTiems());
        count.setDoctorNum(am.getDoctorNum() + pm.getDoctorNum());
        count.setScanTimes(am.getScanTimes() + pm.getScanTimes());
        count.setCardTimes(am.getCardTimes() + pm.getCardTimes());
        count.setUseYwzTimes(am.getUseYwzTimes() + pm.getUseYwzTimes());
        count.setSubmitYwzTimes(am.getSubmitYwzTimes() + pm.getSubmitYwzTimes());

        count.setFirstVisitNoCoverTimes(am.getFirstVisitNoCoverTimes() + pm.getFirstVisitNoCoverTimes());
        count.setWzSuccessTimes(am.getWzSuccessTimes() + pm.getWzSuccessTimes());
        count.setFirstVisitTimes(am.getFirstVisitTimes() + pm.getFirstVisitTimes());
        count.setScanSuccessTimes(am.getScanSuccessTimes() + pm.getScanSuccessTimes());
        count.setPushLiveSuccessTimes(am.getPushLiveSuccessTimes() + pm.getPushLiveSuccessTimes());
        count.setPushLiveTimes(am.getPushLiveTimes() + pm.getPushLiveTimes());
        count.setPushAppointmentSuccessTimes(am.getPushAppointmentSuccessTimes() + pm.getPushAppointmentSuccessTimes());
        count.setPushAppointmentTimes(am.getPushAppointmentTimes() + pm.getPushAppointmentTimes());
        count.setSecondVisitTimes(am.getSecondVisitTimes() + pm.getSecondVisitTimes());

        count.setPushAppointmentSubmitTimes(am.getPushAppointmentSubmitTimes() + pm.getPushAppointmentSubmitTimes());
        count.setPushLiveSubmitTimes(am.getPushLiveSubmitTimes() + pm.getPushLiveSubmitTimes());
        count.setPushAppointmentClickTimes(am.getPushAppointmentClickTimes() + pm.getPushAppointmentClickTimes());
        count.setPushLiveClickTimes(am.getPushLiveClickTimes() + pm.getPushLiveClickTimes());
    }

    @Override
    public void add2(ImportAndEditDetailVo importAndEditDetailVocount, ImportAndEditDetailVo importAndEditDetailVoAM, ImportAndEditDetailVo importAndEditDetailVoPM) {
        importAndEditDetailVocount.setPresentIllnessHistory(importAndEditDetailVoAM.getPresentIllnessHistory()
                + importAndEditDetailVoPM.getPresentIllnessHistory());
        importAndEditDetailVocount.setPastmedicalHistory(importAndEditDetailVoAM.getPastmedicalHistory()
                + importAndEditDetailVoPM.getPastmedicalHistory());
        importAndEditDetailVocount.setMainSymptomName(importAndEditDetailVoAM.getMainSymptomName()
                + importAndEditDetailVoPM.getMainSymptomName());
        importAndEditDetailVocount.setDiseases(importAndEditDetailVoAM.getDiseases()
                + importAndEditDetailVoPM.getDiseases());
    }


    @Override
    public void add1(DocDetailVo docDetailVocount, DocDetailVo docDetailVoAM, DocDetailVo docDetailVoPM) {
        List<DoctorTimes> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();

        List<DoctorTimes> listam = docDetailVoAM.getDoctorTimes();

        List<DoctorTimes> listpm = docDetailVoPM.getDoctorTimes();

        if (null != listam && listam.size() > 0) {
            listam.stream().forEach(e -> {
                String name = e.getDocName();
                if (map.containsKey(name)) {
                    DoctorTimes doctorTimes = (DoctorTimes) map.get(name);
                    DoctorTimes tme = new DoctorTimes();
                    tme.setDocName(name);
                    tme.setTimes(doctorTimes.getTimes() + e.getTimes());
                    tme.setShowTimes(doctorTimes.getShowTimes() + e.getShowTimes());
                    map.put(name, tme);
                } else {
                    map.put(name, e);
                }
            });
        }
        if (null != listpm && listpm.size() > 0) {
            listpm.stream().forEach(e -> {
                String name = e.getDocName();
                if (map.containsKey(name)) {
                    DoctorTimes doctorTimes = (DoctorTimes) map.get(name);
                    DoctorTimes tex = new DoctorTimes();
                    tex.setDocName(name);
                    tex.setTimes(doctorTimes.getTimes() + e.getTimes());
                    tex.setShowTimes(doctorTimes.getShowTimes() + e.getShowTimes());
                    map.put(name, tex);
                } else {
                    map.put(name, e);
                }
            });
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            DoctorTimes value = (DoctorTimes) entry.getValue();
            list.add(value);
        }
        Comparator<DoctorTimes> comparator = (h1, h2) -> h1.getTimes().compareTo(h2.getTimes());
        list.sort(comparator.reversed());
        docDetailVocount.setDoctorTimes(list);
    }

    @Override
    public List<String> countDate(String startTime, String endTime) {
        List<String> list = new ArrayList<>();
        if (startTime.equals(endTime)) {
            list.add(startTime);
            return list;
        }
        list.add(startTime);
        try {
            Date start = sdf.parse(startTime);
            Date end = sdf.parse(endTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            boolean bContinue = true;
            while (bContinue) {
                cal.add(Calendar.DAY_OF_MONTH, 1);
                if (end.after(cal.getTime())) {
                    list.add(sdf.format(new Date(cal.getTimeInMillis())));
                } else {
                    break;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        list.add(endTime);
        return list;
    }

    /**
     * 按科室统计
     *
     * @param startTime
     * @param endTime
     * @param depName
     * @return
     */
    @Override
    public ResultVo ywztimesnew(String startTime, String endTime, String depName) {

        ResultVo resultVo = new ResultVo();

        //综合统计
        List<CountShowVO> list = new ArrayList<>();
        //医生人数统计
        List<CountShowVO> list1 = new ArrayList<>();
        //导入详情统计
        List<CountShowVO> list2 = new ArrayList<>();
        //编辑详情统计
        List<CountShowVO> list3 = new ArrayList<>();

        //获取时间段有哪些日期
        List<String> listdate = countDate(startTime, endTime);

        if (null != listdate && listdate.size() > 0) {
            for (String s : listdate) {
                //一天
                CountShowVO countShowVO = new CountShowVO();
                countShowVO.setDate(s);

                CountShowVO docot = new CountShowVO();
                docot.setDate(s);

                CountShowVO imp = new CountShowVO();
                imp.setDate(s);

                CountShowVO edi = new CountShowVO();
                edi.setDate(s);

                List<CountTimesVo> countTimesVoList = new ArrayList<>();
                List<DocDetailVo> docDetailVos = new ArrayList<>();
                List<ImportAndEditDetailVo> importAndEditDetailVos = new ArrayList<>();
                List<ImportAndEditDetailVo> importAndEditDetailVos1 = new ArrayList<>();

//             上午
                String AMstart = s.concat(" 00:00:00");
                String AMend = s.concat(" 12:00:00");
                CountTimesVo am = new CountTimesVo();
                am.setFlag("AM");
                DocDetailVo docDetailVoAM = new DocDetailVo();
                docDetailVoAM.setFlag("AM");
                ImportAndEditDetailVo importAndEditDetailVoAM = new ImportAndEditDetailVo();
                importAndEditDetailVoAM.setFlage("AM");
                ImportAndEditDetailVo importAndEditDetailVo1AM = new ImportAndEditDetailVo();
                importAndEditDetailVo1AM.setFlage("AM");
                selTimesnew(AMstart, AMend, am, docDetailVoAM, importAndEditDetailVoAM, importAndEditDetailVo1AM, depName);


//             下午
                String PMstart = s.concat(" 12:00:00");
                String PMend = s.concat(" 23:59:59");
                CountTimesVo pm = new CountTimesVo();
                pm.setFlag("PM");
                DocDetailVo docDetailVoPM = new DocDetailVo();
                docDetailVoPM.setFlag("PM");
                ImportAndEditDetailVo importAndEditDetailVoPM = new ImportAndEditDetailVo();
                importAndEditDetailVoPM.setFlage("PM");
                ImportAndEditDetailVo importAndEditDetailVo1PM = new ImportAndEditDetailVo();
                importAndEditDetailVo1PM.setFlage("PM");
                selTimesnew(PMstart, PMend, pm, docDetailVoPM, importAndEditDetailVoPM, importAndEditDetailVo1PM, depName);


//             总计
                CountTimesVo count = new CountTimesVo();
                count.setFlag("总计");
                DocDetailVo docDetailVocount = new DocDetailVo();
                docDetailVocount.setFlag("总计");
                ImportAndEditDetailVo importAndEditDetailVocount = new ImportAndEditDetailVo();
                importAndEditDetailVocount.setFlage("总计");
                ImportAndEditDetailVo importAndEditDetailVo1count = new ImportAndEditDetailVo();
                importAndEditDetailVo1count.setFlage("总计");

                //上下午统计
                add(count, am, pm);
                add1(docDetailVocount, docDetailVoAM, docDetailVoPM);
                add2(importAndEditDetailVocount, importAndEditDetailVoAM, importAndEditDetailVoPM);
                add2(importAndEditDetailVo1count, importAndEditDetailVo1PM, importAndEditDetailVo1AM);

                countTimesVoList.add(am);
                countTimesVoList.add(pm);
                countTimesVoList.add(count);
                countShowVO.setList(countTimesVoList);
                list.add(countShowVO);

                docDetailVos.add(docDetailVoAM);
                docDetailVos.add(docDetailVoPM);
                docDetailVos.add(docDetailVocount);
                docot.setList(docDetailVos);
                list1.add(docot);

                importAndEditDetailVos.add(importAndEditDetailVoAM);
                importAndEditDetailVos.add(importAndEditDetailVoPM);
                importAndEditDetailVos.add(importAndEditDetailVocount);
                imp.setList(importAndEditDetailVos);
                list2.add(imp);

                importAndEditDetailVos1.add(importAndEditDetailVo1AM);
                importAndEditDetailVos1.add(importAndEditDetailVo1PM);
                importAndEditDetailVos1.add(importAndEditDetailVo1count);
                edi.setList(importAndEditDetailVos1);
                list3.add(edi);

            }
        }

        resultVo.setAllresult(list);
        resultVo.setDocDetail(list1);
        resultVo.setImportDetail(list2);
        resultVo.setEditDetail(list3);

        return resultVo;
    }

    /**
     * 按部门统计
     *
     * @param startTime
     * @param endTime
     * @param countTimesVo
     * @param docDetailVo
     * @param importAndEditDetailVo
     * @param importAndEditDetailVo1
     * @param depName                呼吸内科
     *                               消化内科
     *                               内科综合
     *                               内科
     */
    private void selTimesnew(String startTime, String endTime, CountTimesVo countTimesVo, DocDetailVo docDetailVo,
                             ImportAndEditDetailVo importAndEditDetailVo, ImportAndEditDetailVo importAndEditDetailVo1, String depName) {


        //1开卡，2扫码，3使用，4提交，5展现
        List<YwzCountTimes> typelist = ywzCountTimesService.gettimes(startTime, endTime, depName);
        Map<Integer, Integer> mapTypeList = typelist.stream().collect(Collectors.toMap(YwzCountTimes::getType,
                YwzCountTimes::getTimes));
        countTimesVo.setCardTimes(mapTypeList.get(1) == null ? 0 : mapTypeList.get(1));
        countTimesVo.setScanTimes(mapTypeList.get(2) == null ? 0 : mapTypeList.get(2));
        countTimesVo.setUseYwzTimes(mapTypeList.get(3) == null ? 0 : mapTypeList.get(3));
        countTimesVo.setSubmitYwzTimes(mapTypeList.get(4) == null ? 0 : mapTypeList.get(4));
        countTimesVo.setECHopenTiems(mapTypeList.get(5) == null ? 0 : mapTypeList.get(5));


        //医生使用数
        List<YwzCountTimes> userAndimport = ywzCountTimesService.userAndimport(startTime, endTime, depName);
        countTimesVo.setDoctorNum((null == userAndimport || userAndimport.size() < 1) ? 0 : userAndimport.size());

        //导入数
        countTimesVo.setECHimportTimes((null == userAndimport || userAndimport.size() < 1) ? 0 : (userAndimport.stream()
                .mapToInt(YwzCountTimes::getTimes)).sum());

        //具体医生展现次数
        List<YwzCountTimes> doctorShowTimes = ywzCountTimesService.doctorShowTimes(startTime, endTime, depName);
        for (YwzCountTimes doctorShowTime : doctorShowTimes) {
            if (null == doctorShowTime.getDoctorName()) {
                doctorShowTime.setDoctorName("未知姓名");
            }
        }
        for (YwzCountTimes doctorShowTime : userAndimport) {
            if (null == doctorShowTime.getDoctorName()) {
                doctorShowTime.setDoctorName("未知姓名");
            }
        }
        Map<String, Integer> improt = userAndimport.stream().collect(Collectors.toMap(YwzCountTimes::getDoctorName,
                YwzCountTimes::getTimes));
        Map<String, Integer> show = doctorShowTimes.stream().collect(Collectors.toMap(YwzCountTimes::getDoctorName,
                YwzCountTimes::getTimes));
        List<DoctorTimes> doctorTimes = new ArrayList<>();
        for (String key : show.keySet()) {
            DoctorTimes doctorTime = new DoctorTimes();
            if (null == key) {
                doctorTime.setDocName("未知姓名");
            } else {
                doctorTime.setDocName(key);
            }
            doctorTime.setTimes(improt.get(key) == null ? 0 : improt.get(key));
            doctorTime.setShowTimes(show.get(key) == null ? 0 : show.get(key));
            doctorTimes.add(doctorTime);
        }
        Comparator<DoctorTimes> comparator = (h1, h2) -> h1.getTimes().compareTo(h2.getTimes());
        doctorTimes.sort(comparator.reversed());
        docDetailVo.setDoctorTimes(doctorTimes);

        //模块导入统计
        List<YwzCountTimes> importTimes = ywzCountTimesService.importTimes(startTime, endTime, depName);
        Map<String, Integer> improtMap = importTimes.stream().collect(Collectors.toMap(YwzCountTimes::getDescri,
                YwzCountTimes::getTimes));
        importAndEditDetailVo.setMainSymptomName(improtMap.get("61") == null ? 0 : improtMap.get("61"));
        importAndEditDetailVo.setPresentIllnessHistory(improtMap.get("62") == null ? 0 : improtMap.get("62"));
        importAndEditDetailVo.setPastmedicalHistory(improtMap.get("63") == null ? 0 : improtMap.get("63"));
        importAndEditDetailVo.setDiseases(improtMap.get("64") == null ? 0 : improtMap.get("64"));


        //模块编辑统计
//        List<YwzCountTimes> editTimes = ywzCountTimesService.editTimes(startTime, endTime);
//        Map<String, Integer> editMap = editTimes.stream().collect(Collectors.toMap(YwzCountTimes::getDescri,
//                YwzCountTimes::getTimes));
//        importAndEditDetailVo1.setMainSymptomName(editMap.get("71") == null ? 0 : editMap.get("71"));
//        importAndEditDetailVo1.setPresentIllnessHistory(editMap.get("72") == null ? 0 : editMap.get("72"));
//        importAndEditDetailVo1.setPastmedicalHistory(editMap.get("73") == null ? 0 : editMap.get("73"));
//        importAndEditDetailVo1.setDiseases(editMap.get("74") == null ? 0 : editMap.get("74"));

        importAndEditDetailVo1.setMainSymptomName(0);
        importAndEditDetailVo1.setPresentIllnessHistory(0);
        importAndEditDetailVo1.setPastmedicalHistory(0);
        importAndEditDetailVo1.setDiseases(0);


        //给综合统计模块增加一些新的数据
        //1.预约推送量   增加科室分组
        Integer pushAppointmentTimes = ywzCountTimesMapper.pushAppointmentTimes(startTime, endTime, depName);
        countTimesVo.setPushAppointmentTimes(pushAppointmentTimes);
        //2.预约推送成功量 增加科室分组
        Integer pushAppointmentSuccessTimes = ywzCountTimesMapper.pushAppointmentSuccessTimes(startTime, endTime, depName);
        countTimesVo.setPushAppointmentSuccessTimes(pushAppointmentSuccessTimes);
        //3.现场取号量推送量 增加科室分组
        Integer pushLiveTimes = ywzCountTimesMapper.pushLiveTimes(startTime, endTime, depName);
        countTimesVo.setPushLiveTimes(pushLiveTimes);
        //4.取号推送成功量 增加科室分组
        Integer pushLiveSuccessTimes = ywzCountTimesMapper.pushLiveSuccessTimes(startTime, endTime, depName);
        countTimesVo.setPushLiveSuccessTimes(pushLiveSuccessTimes);

        /**
         * 扫码目前只能显示综合科的
         */
        if(!depName.equals("none")){
            countTimesVo.setScanSuccessTimes(0);
        }else {
            //6.扫码量
            //7.扫码完成量
            //根据channel =1 and status =10 统计
            //Integer scanSuccessTimes1 = ywzCountTimesMapper.scanSuccessTimes1(startTime, endTime);
            //统计channel 字段上线之前的
            Integer scanSuccessTimes2 = ywzCountTimesMapper.scanSuccessTimes2(startTime, endTime);
            countTimesVo.setScanSuccessTimes(scanSuccessTimes2);
        }

//        //8.初诊量 就诊用户完成量
        Integer firstVisitTimes = ywzCountTimesMapper.firstVisitTimes(startTime, endTime, depName);
        Integer firstVisitTimes1 = ywzCountTimesMapper.firstVisitTimes1(startTime, endTime, depName);
        //Integer firstVisitTimes2 = ywzCountTimesMapper.firstVisitTimes2(startTime, endTime, depName);
        countTimesVo.setFirstVisitTimes(firstVisitTimes + firstVisitTimes1);

        //10.复诊量
        Integer secondVisitTimes = ywzCountTimesMapper.secondVisitTimes(startTime, endTime);
        countTimesVo.setSecondVisitTimes(secondVisitTimes);
        //5.预问诊完成量
        //+复诊的即可
        countTimesVo.setWzSuccessTimes(countTimesVo.getFirstVisitTimes());
        //9.初诊未覆盖量=进入系统量-复诊-初诊量
        Integer firstNoCoverTimes = ywzCountTimesMapper.firstNoCoverTimes(startTime, endTime);
        countTimesVo.setFirstVisitNoCoverTimes(firstNoCoverTimes);
        //推送成功点击
        List<Map<String, String>> clik = ywzCountTimesMapper.getClik(startTime, endTime, depName);
        Integer a = 0;
        Integer b = 0;
        if (clik != null && clik.size() > 0) {
            for (int i = 0; i < clik.size(); i++) {
                Map map = clik.get(i);
                Integer type = (Integer) map.get("type");
                Integer times = Integer.parseInt(map.get("times").toString());
                if (type == 1 || type == 3) {
                    a = a + times;
                } else {
                    b = b + times;
                }
            }
        }
        countTimesVo.setPushAppointmentClickTimes(b);
        countTimesVo.setPushLiveClickTimes(a);
        //点击提交
        List<Map<String, String>> submit = ywzCountTimesMapper.getSubmit(startTime, endTime, depName);
        Integer c = 0;
        Integer d = 0;
        if (clik != null && submit.size() > 0) {
            for (int i = 0; i < submit.size(); i++) {
                Map map = submit.get(i);
                Integer type = (Integer) map.get("type");
                Integer times = Integer.parseInt(map.get("times").toString());
                if (type == 1 || type == 3) {
                    c = c + times;
                } else {
                    d = d + times;
                }
            }
        }
        countTimesVo.setPushAppointmentSubmitTimes(d);
        countTimesVo.setPushLiveSubmitTimes(c);
    }
}
