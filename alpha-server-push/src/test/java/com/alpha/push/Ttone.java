package com.alpha.push;

/**
 * Created by MR.Wu on 2018-07-19.
 */

import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.push.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Ttone {
//    @Autowired
//    private NotifyService notifyService;

    @Autowired
    private PushMessageService pushMessageService;

    @Test
    public void send(){
//        PushInfo pushInfo = new PushInfo();
//        pushInfo.setTitle("为了让医生提前了解您宝宝的病情，请就诊前完成预问诊！");
//        pushInfo.setDep("内科综合(孙)");
//        pushInfo.setDepDocter("孙先军");
//        pushInfo.setCardNo("");
//        pushInfo.setPhone("13723415915");
//        pushInfo.setWatchingTime("2018-06-27");
//        pushInfo.setUserName("菲菲00000000");
//        pushInfo.setUrl("http://192.168.100.10:8080/ywz_.html#/link/9ad8c35d22b330a98fffec3a4d710df63308");

        HisRegisterYygh hisRegisterYygh = new HisRegisterYygh();
        hisRegisterYygh.setCardNo("");
        hisRegisterYygh.setPhone("15818518021");
        hisRegisterYygh.setDeptName("内科综合");
        hisRegisterYygh.setDoctorName("孙先军");
        hisRegisterYygh.setOutPatientNo("4513664");
        hisRegisterYygh.setPatientName("刘语涵");
        hisRegisterYygh.setSex("女");
        hisRegisterYygh.setBirthday("2014-08-15");
        hisRegisterYygh.setVisitTime("2018-10-27");
        hisRegisterYygh.setPno("302662053");
        hisRegisterYygh.setYno("202662053");
        hisRegisterYygh.setType(3);
        pushMessageService.pushMessage(hisRegisterYygh);

        //pushMessageService.pushWeiXinMsg(pushInfo);
    }

    @Test
    public void TestGetFileList() {
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 1000; i++) {
//            PushInfo pushInfo = new PushInfo();
//            pushInfo.setCardNo("1212121212");
//            notifyService.sendMessage(pushInfo);
//        }
//        System.out.println("总时间：" + ((System.currentTimeMillis()) - start));
    }

}
