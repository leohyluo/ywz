package com.alpha.push.service.impl;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.enums.RegisterType;
import com.alpha.commons.util.BeanCopierUtil;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.push.domain.*;
import com.alpha.push.mapper.UserBasicRecordMapper;
import com.alpha.push.service.AppointmentService;
import com.alpha.push.service.NotifyPushService;
import com.alpha.push.service.PushMessageService;
import com.alpha.push.service.RegisterService;
import com.alpha.redis.RedisMrg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private RestTemplate restTemplate;
    @Value("${alpha.diagnosis.url}")
    private String diagnosis_server_url;
    @Value("${alpha.h5.wecharUrl}")
    private String alpha_h5_url;
    @Value("${hospital.code}")
    private String hospitalCode;
    @Resource
    private PushMessageService pushMessageService;
    @Resource
    private RegisterService registerService;
    @Resource
    private HisRegisterYyghMapper hisRegisterYyghMapper;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    @Value("${push.afterDesc}")
    private String afterDesc;

    @Value("${push.getNoDesc}")
    private String getNoDesc;

    @Value("${push.comfireDesc}")
    private String comfireDesc;

    @Value("${push.appointment.registed}")
    private String msgOfRegistedWithoutFinish;

    @Autowired
    private NotifyPushService notifyPushService;

    @Resource
    private UserBasicRecordMapper userBasicRecordMapper;

    @Override
    public String getH5Url() {
        return alpha_h5_url;
    }

    @Override
    public String getHospitalCode() {
        return hospitalCode;
    }

    /**
     * 推送现场挂号与线上预约后到现场取号的用户群体
     *
     * @param livePatient
     */
    @Override
    @Transactional
    public MessageEvent noticeLivePatient(HisRegisterYygh livePatient) {
        //如现场挂号的患者没有完成过预问诊，则向他推送一条预问诊消息
        UserBasicRecord ubr = null;
        PushInfo pushInfo = null;
        if (livePatient.getType() == Integer.parseInt(RegisterType.LIVE.getValue())) {
            boolean over = getCompletedDiagnosisRecord(livePatient);
            if (!over) {
                logger.info("现场挂号患者{}-{},没有完成过预问诊，开始向他(她)推送预问诊连接", livePatient.getOutPatientNo(), livePatient.getOutPatientNo());
                livePatient.setDesc(livePatient.getPatientName() + getNoDesc);
                livePatient.setFooter(GlobalConstants.WX_FOOTER0);
                pushInfo = getLiveTemplate(livePatient);
            } else {
                //异常用数据
                updateHisRegisterStatus(livePatient, -1);
            }
        } else if (livePatient.getType() == Integer.parseInt(RegisterType.GET_FOR_APPOINTMENT.getValue())) {
            //是否在取号之前完成过预问诊，如未完成预问诊则向他(她)推送预问诊连接，如已完成预问诊则判断时间跟完成预问诊时间是否为同一天，如果不是同一天则推送病情核对连接
            HisRegisterYygh tempPatient = BeanCopierUtil.uniformCopy(livePatient, HisRegisterYygh::new);
            tempPatient.setType(Integer.parseInt(RegisterType.APPOINTMENT.getValue()));
            boolean over = getCompletedDiagnosisRecord(tempPatient);
            //来现场取号时未完成过预问诊
            if (!over) {
                //String noticeMsg = StringUtils.replaceWithPatientNameOrDoctorName(msgOfRegistedWithoutFinish, livePatient.getPatientName(), livePatient.getDoctorName());
                logger.info("患者{}-{}已来现场取号,没有完成过预问诊，开始向他(她)推送预问诊连接", livePatient.getOutPatientNo(), livePatient.getOutPatientNo());
                //家长，您好！您还未完成预问诊，请立刻点击“详情”，完成预问诊。
                livePatient.setDesc(livePatient.getPatientName() + getNoDesc);
                livePatient.setFooter(GlobalConstants.WX_FOOTER2);
                pushInfo = getLiveTemplate(livePatient);
            } else { //预约阶段已完成过预问诊
                //String noticeMsg = StringUtils.replaceWithPatientNameOrDoctorName(comfireDesc, livePatient.getPatientName(), livePatient.getDoctorName());
                HisRegisterRecord hisRegisterRecord = registerService.getRegisterRecord(livePatient.getOutPatientNo(), livePatient.getYno());
                if (hisRegisterRecord != null) {
                    hisRegisterRecord.setFetchComplete(livePatient.getPno());
                    hisRegisterRecord.setUpdateTime(new Date());
                    registerService.updateHisRegisterRecord(hisRegisterRecord);
                }
                //获取填写完后的记录
                ubr = userBasicRecordMapper.getForLiveObject(tempPatient.getYno());
                LocalDate finishDate = DateUtils.dateToLocalDate(ubr.getCreateTime());
                LocalDate today = DateUtils.dateToLocalDate(new Date());
                //如不是今天完成预问诊，则推送病情核对连接
                if (!finishDate.isEqual(today)) {
                    /*livePatient.setDesc(noticeMsg);
                    livePatient.setFooter(GlobalConstants.WX_FOOTER4);*/
                    livePatient.setDesc(comfireDesc);
                    pushInfo = getConfirmTemplate(livePatient, ubr);
                } else {
                    //如果在预约阶段已完成过预问诊，则标识为不需要推送消息
                    updateHisRegisterStatus(livePatient, -1);
                }
            }
        }
        //推送消息
        if (pushInfo != null) {
            MessageEvent messageEvent = getEvent(pushInfo, livePatient);
            //push(pushInfo, livePatient);
            //发送事件
            notifyPushService.sendMessage(messageEvent);
        }
        return null;
    }

    private void push(PushInfo pushInfo, HisRegisterYygh appointmentPatient) {
        PushResp pushResp = pushMessageService.pushWeiXinMsg(pushInfo);
        if (pushResp != null && pushResp.getResult() == 1) {
            //消息推送成功后更新状态
            logger.info("给患者{}-{}推送消息成功", appointmentPatient.getPatientName(), appointmentPatient.getOutPatientNo());
            //消息推送成功后更新状态
            updateHisRegisterStatus(appointmentPatient, 1);
        }
    }

    /**
     *
     * @param appointmentPatient 预约对象
     * @return
     */
    @Override
    public MessageEvent noticeAppointmentPatient(HisRegisterYygh appointmentPatient) {
        //是否已完成过预问诊
//        UserBasicRecord ubr = getCompletedDiagnosisRecord(appointmentPatient);
        PushInfo pushInfo = null;
        boolean over = getCompletedDiagnosisRecord(appointmentPatient);
        if (!over) {
            //未能完成问诊
            //家长，为了医生更准确地诊断孩子的病情，避免漏诊，就诊前请务必回答几个关于孩子的问题。

            //2018-11-09产品要求改
            /*if (StringUtils.isEmpty(appointmentPatient.getDesc())) {
                appointmentPatient.setDesc(appointmentPatient.getPatientName() + afterDesc);
            }*/
            appointmentPatient.setDesc(appointmentPatient.getPatientName() + afterDesc);
            pushInfo = getAppointmentTemplate(appointmentPatient);
        } else {
            updateHisRegisterStatus(appointmentPatient, -1);
        }
        //推送消息
        if (pushInfo != null) {
            MessageEvent messageEvent = getEvent(pushInfo, appointmentPatient);
            //push(pushInfo, appointmentPatient);
            //发送事件
            notifyPushService.sendMessage(messageEvent);
        }
        return null;
    }

    private MessageEvent getEvent(PushInfo pushInfo, HisRegisterYygh hisRegisterYygh) {
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setPushInfo(pushInfo);
        messageEvent.setHisRegisterYygh(hisRegisterYygh);
        return messageEvent;
    }

    /*
    * 是否完成过预问诊
    * @param registerInfo 预约/挂号信息
    * @return
   */
    protected boolean getCompletedDiagnosisRecord(HisRegisterYygh registerInfo) {
        Integer registerType = registerInfo.getType();
        if (registerType == null) {
            logger.error("数据发现异常，预约表中的type为空");
            return true;
        }
        String pno = registerType == RegisterType.LIVE.ordinal() ? registerInfo.getPno() : registerInfo.getYno();
        String key = GlobalConstants.REDIS_KEY_WENZHEN_OVER_FLAG_ + pno;
        Object obj = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(key, RedisMrg.DB4);

        //数据库查询
        if (null == obj) {
            int userBasicRecordCount = 0;
            if (registerType.toString().equals(RegisterType.LIVE.getValue()) || registerType.toString().equals(RegisterType.GET_FOR_APPOINTMENT.getValue())) {
                userBasicRecordCount = userBasicRecordMapper.getForLive(pno);
            } else if (registerType.toString().equals(RegisterType.APPOINTMENT.getValue())) {
                userBasicRecordCount = userBasicRecordMapper.getForAppointment(pno);
            }

            if (userBasicRecordCount > 0)
                return true;
            return false;
        }
        return null == obj ? false : true;
    }


    /*
     * 是否完成过预问诊
     * @param registerInfo 预约/挂号信息
     * @return
    */
//    protected UserBasicRecord getCompletedDiagnosisRecord(HisRegisterYygh registerInfo) {
//        Integer registerType = registerInfo.getType();
//        if (registerType == null) {
//            logger.error("数据发现异常，预约表中的type为空");
//            return null;
//        }
//        String type = String.valueOf(registerType);
//        String pno = registerInfo.getType() == 1 ? registerInfo.getPno() : registerInfo.getYno();
//        //远程调用
//        String uri = diagnosis_server_url.concat("/rpc/basicRecord");
//        JSONObject json = new JSONObject();
//        json.put("pno", pno);
//        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
//        requestEntity.add("allParam", json.toString());
//
//        if (type.equals(RegisterType.LIVE.getValue())) {
//            uri = uri.concat("/live");
//        } else if (type.equals(RegisterType.APPOINTMENT.getValue())) {
//            uri = uri.concat("/appointment");
//        } else if (type.equals(RegisterType.GET_FOR_APPOINTMENT.getValue())) {
//            uri = uri.concat("/live");
//        }
//        UserBasicRecord userBasicRecord = restTemplate.postForObject(uri, requestEntity, UserBasicRecord.class);
//        return userBasicRecord;
//    }

    private void updateHisRegisterStatus(HisRegisterYygh appointmentPatient, Integer status) {
        //更新状态
        appointmentPatient.setStatus(status);
        hisRegisterYyghMapper.updateByPrimaryKey(appointmentPatient);
    }

    /**
     * 是否为当天预约
     * @return
     */
    private Boolean appointmentAtToday(HisRegisterYygh hisRegisterYygh) {
        HisRegisterYygh param = new HisRegisterYygh();
        param.setOutPatientNo(hisRegisterYygh.getOutPatientNo());
        param.setYno(hisRegisterYygh.getYno());
        param.setType(Integer.parseInt(RegisterType.APPOINTMENT.getValue()));
        List<HisRegisterYygh> appointmentList = hisRegisterYyghMapper.select(param);
        if(CollectionUtils.isEmpty(appointmentList)) {
            return false;
        }
        HisRegisterYygh hry = appointmentList.get(0);
        String createTimeStr = hry.getCreateTime();
        if(StringUtils.isEmpty(createTimeStr)) {
            return false;
        }
        LocalDate appointmentDate = DateUtils.string2LocalDate(createTimeStr);
        LocalDate today = LocalDate.now();
        return appointmentDate.isEqual(today);
    }
}
