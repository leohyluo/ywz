package com.alpha.service;

import com.alpha.commons.core.his.InspectionService;
import com.alpha.commons.core.his.OutPatientService;
import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.mapper.HospitalizedNoticeNewMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.core.pojo.HospitalizedNoticeNew;
import com.alpha.commons.core.pojo.RegisterDTO;
import com.alpha.commons.core.util.DeptUtils;
import com.alpha.commons.core.util.SoapUtil;
import com.alpha.fegin.SchedualServiceDataAdvance;
import com.alpha.util.StaticHttpclientCall;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by edz on 2018/10/18.
 * 儿童医院 定时任务数据
 */
@Service
public class ETYYJOBImpl implements OutPatientService,InspectionService {

    private static Logger logger = LoggerFactory.getLogger(ETYYJOBImpl.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");

    private static String wsdl = "http://172.16.240.124:7811/BS10020?wsdl";
    private static String fid = "BS10020";
    private static String noticewsdl = "http://172.16.240.124:7811/BS10023?wsdl";
    private static String noticefid = "BS10023";

    @Autowired
    private HisRegisterYyghMapper hisRegisterYyghMapper;
    @Autowired
    private HospitalizedNoticeNewMapper hospitalizedNoticeNewMapper;

    @Autowired
    private SchedualServiceDataAdvance schedualServiceDataAdvance;

    @Override
    public List<RegisterDTO> registrationInfo(String outPatientNo, String visitTime) {
        return null;
    }

    @Override
    public Object patientInfo(String cardNo) {
        return null;
    }

    @Override
    public List<HisRegisterYygh> hisRegisterYyghInfo(String startTime, String endTime) {
       logger.info("深圳市儿童医院 ---> 开始请求医慧平台");
        long currentTimeMillis = System.currentTimeMillis();
        String resultXml = StaticHttpclientCall.all(wsdl, fid, startTime, endTime);
        if (StringUtils.isBlank(resultXml)) {
            return null;
        }
        logger.info("请求医慧平台结束，共计耗时：{} 毫秒", (System.currentTimeMillis() - currentTimeMillis));
        //redis 取开放的科室
        List<String> listpartment = DeptUtils.getDepartment();
        logger.info("当前开放的科室有：{}", new Gson().toJson(listpartment));
        List<String> list = SoapUtil.parseETYYxml(resultXml);
        HisRegisterYygh HisRegisterYyg = new HisRegisterYygh();
        String str = HisRegisterYyg.getClass().toString();
        str = str.replace("class ", "");
        str = str.replace(" ", "");
        if (null == list || list.size() < 1) {
            return null;
        } else {
            List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.ghmap);
            logger.info("解析到数据：" + objectList.size() + " 条");
            List<HisRegisterYygh> list1 = new ArrayList<>();
            objectList.stream().forEach(e -> {
                HisRegisterYygh hisRegisterYygh = (HisRegisterYygh) e;
                listpartment.stream().forEach(a -> {
                    if (hisRegisterYygh.getDeptName().contains(a)) {
                        list1.add(hisRegisterYygh);
                    }
                });
                if (list1.size() > 50) {
                    hisRegisterYyghMapper.insertBatch(list1);
                    list1.clear();
                }
            });
            if (list1.size() > 0) {
                hisRegisterYyghMapper.insertBatch(list1);
                list1.clear();
            }
            //推送
            push();
            return null;
        }
    }

    @Override
    public void NoticeData(String startTime, String endTime) {
        logger.info("儿童医院住院通知单信息");
        try {
            logger.info("开始请求医慧平台");
            long currentTimeMillis = System.currentTimeMillis();
            String resultXml = StaticHttpclientCall.hospitalizedNoticeAll(noticewsdl, noticefid, startTime, endTime);
            if (StringUtils.isBlank(resultXml)) {
                return;
            }
            logger.info("请求医慧平台结束，共计耗时：{} 毫秒", (System.currentTimeMillis() - currentTimeMillis));
            List<String> list = SoapUtil.parseETYYxml(resultXml);
            HospitalizedNoticeNew hospitalizedNoticeNew = new HospitalizedNoticeNew();
            String str = hospitalizedNoticeNew.getClass().toString();
            str = str.replace("class ", "");
            str = str.replace(" ", "");
            if (null == list || list.size() < 1) {
                return;
            } else {
                List<Object> objectList = SoapUtil.string2obj(list, str, SoapUtil.mapNotice);
                logger.info("解析到数据：" + objectList.size() + " 条");
                List<HospitalizedNoticeNew> list1 = new ArrayList<>();
                objectList.stream().forEach(e -> {
                    HospitalizedNoticeNew noticeNew = (HospitalizedNoticeNew) e;
                    String noticeId = null;
                    try {
                        noticeId = sdf1.format(sdf.parse(noticeNew.getNotifyTime()));
                        noticeId = noticeNew.getOutPatientNo() + noticeId;
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    noticeNew.setNoticeId(noticeId);
                    noticeNew.setCreateTime(sdf.format(new Date()));
                    list1.add(noticeNew);
                    if (list1.size() > 50) {
                        hospitalizedNoticeNewMapper.insertBatch(list1);
                        list1.clear();
                    }
                });
                if (list1.size() > 0) {
                    hospitalizedNoticeNewMapper.insertBatch(list1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void push(){
        List<HisRegisterYygh> registerList = hisRegisterYyghMapper.listNeedPushRecord();
        if(null !=registerList && registerList.size()>0){
            registerList.stream().forEach(e -> {
                schedualServiceDataAdvance.invokeDataAdvanceService(e);
            });
        }
    }

    @Override
    public void getEMR(String startTime,String endTime) {



    }


    @Override
    public void getInspcetion(String startTime,String endTime) {



    }

}
