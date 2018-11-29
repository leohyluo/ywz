package com.alpha.push.handler;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.enums.RegisterType;
import com.alpha.commons.util.StringUtils;
import com.alpha.push.service.AppointmentService;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * Created by MR.Wu on 2018-07-18.
 * 消费者  事件处理器 消息预处理转化为消息发送事件
 */
public class MessageProccessHandler implements EventHandler<HisRegisterYygh>, WorkHandler<HisRegisterYygh> {

    private AppointmentService appointmentService;
//    @Value("${push.appointment.success}")
//    private String msgOfNoticeSuccess = "patientName家长，您已预约成功。为了医生更准确地诊断孩子的病情，避免漏诊，请务必先回答几个关于孩子的问题。";

    public MessageProccessHandler(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public void onEvent(HisRegisterYygh hisRegisterYygh, long l, boolean b) throws Exception {
        this.onEvent(hisRegisterYygh);
    }

    /**
     * 供外调用（其它服务调用 control）
     * @param e
     * @throws Exception
     */
    @Override
    public void onEvent(HisRegisterYygh e) throws Exception {
        //业务逻辑处理
        if (e.getType() == Integer.parseInt(RegisterType.LIVE.getValue()) || e.getType() == Integer.parseInt(RegisterType.GET_FOR_APPOINTMENT.getValue())) {
            appointmentService.noticeLivePatient(e);
        } else if (e.getType() == Integer.parseInt(RegisterType.APPOINTMENT.getValue())) {
            /*String msgOfNoticeSuccess = "patientName家长，您已预约成功。为了医生更准确地诊断孩子的病情，避免漏诊，请务必先回答几个关于孩子的问题。";
            msgOfNoticeSuccess = StringUtils.replaceWithPatientNameOrDoctorName(msgOfNoticeSuccess, e.getPatientName(), e.getDoctorName());
            e.setDesc(msgOfNoticeSuccess);
            e.setFooter(GlobalConstants.WX_FOOTER1);*/
            appointmentService.noticeAppointmentPatient(e);
        }
    }
}
