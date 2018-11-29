package com.alpha.push.timer;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.util.StringUtils;
import com.alpha.push.service.AppointmentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 预问诊消息通知定时器
 * 就诊前一天晚上9点
 * 	    未完成预问诊 -> 推送预问诊链接
 * 	    已完成预问诊 -> do nothing
 * 就诊当天7点半
 * 	    未取号->未完成预问诊->推送预问诊链接
 * 	    已取号
 */
@RestController
@RequestMapping("/notice")
public class PreDiagnosisNoticeTimerTest {



    @Value("${push.appointment.dayBefore}")
    private String msgOfDayBefore;
    @Value("${push.appointment.today}")
    private String msgOfToday;

    @Resource
    private HisRegisterYyghMapper hisRegisterYyghMapper;
    @Resource
    private AppointmentService appointmentService;

    /**
     * 就诊前一天给未完成预问诊的患者推送预问诊消息
     */
    @GetMapping("/test1")
    public void noticeAppointmentAtDayBefore() {
        //就诊前一天的预约号
        List<HisRegisterYygh> hisRegisterYyghList = hisRegisterYyghMapper.getAppointmentUserForTomorrow();
        for (HisRegisterYygh hisRegisterYygh : hisRegisterYyghList) {
            String noticeMsg = StringUtils.replaceWithPatientNameOrDoctorName(msgOfDayBefore, hisRegisterYygh.getPatientName(), hisRegisterYygh.getDoctorName());
            hisRegisterYygh.setDesc(noticeMsg);
            hisRegisterYygh.setFooter(GlobalConstants.WX_FOOTER2);
            appointmentService.noticeAppointmentPatient(hisRegisterYygh);
        }
    }

    /**
     * 就诊当天给未完成预问诊的患者推送预问诊消息
     */
    @GetMapping("/test2")
    public void noticeAppointmentAtToday () {
        //由于数据太多关联查询效率太低，所以分步处理，第一步先找出就诊当天的门诊号，第二步通过门诊号找当天未被取号的预约记录
        List<HisRegisterYygh> hisRegisterYyghList = hisRegisterYyghMapper.getAppointmentUserForToday();
        Set<String> outPatientNoSet = hisRegisterYyghList.stream().map(HisRegisterYygh::getOutPatientNo).collect(Collectors.toSet());
        for(String outPatientNo : outPatientNoSet) {
            HisRegisterYygh param = new HisRegisterYygh();
            param.setOutPatientNo(outPatientNo);
            //每个患者的当天预约记录（不包括已取号）
            List<HisRegisterYygh> userAppointmentList = hisRegisterYyghMapper.getAppointmentUserWithoutRegister(param);
            for (HisRegisterYygh hisRegisterYygh : userAppointmentList) {
                String noticeMsg = StringUtils.replaceWithPatientNameOrDoctorName(msgOfToday, hisRegisterYygh.getPatientName(), hisRegisterYygh.getDoctorName());
                hisRegisterYygh.setDesc(noticeMsg);
                hisRegisterYygh.setFooter(GlobalConstants.WX_FOOTER3);
                appointmentService.noticeAppointmentPatient(hisRegisterYygh);
            }
        }
    }
}
