package com.alpha.service;


import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.fegin.SchedualServiceDataAdvance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by HP on 2018/6/19.
 * 定时拉取预约挂号的信息推送预问诊url  定时获取住院通知单信息
 */
@Component
public class QuartzService {

    private static Logger logger = LoggerFactory.getLogger(QuartzService.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
    private static String wsdl = "http://172.16.240.124:7811/BS10020?wsdl";
    private static String fid = "BS10020";
    private static String noticewsdl = "http://172.16.240.124:7811/BS10023?wsdl";
    private static String noticefid = "BS10023";

    @Autowired
    private HisRegisterYyghMapper hisRegisterYyghMapper;

    @Value("${hospital.code}")
    private  String code;

    @Autowired
    private SchedualServiceDataAdvance schedualServiceDataAdvance;


    //定时获取预约，现在挂号的挂号信息

//    @Scheduled(cron = "0/10 * * * * ?")
    public void getregister() {
        logger.info("开始拉取挂号信息");
        Calendar calendar = Calendar.getInstance();
        long a = System.currentTimeMillis();
        String endtime = sdf.format(calendar.getTime());
        calendar.add(Calendar.MINUTE, -1);
        String starttime = sdf.format(calendar.getTime());
        calendar.add(Calendar.MINUTE,6);
        endtime=sdf.format(calendar.getTime());
       List<HisRegisterYygh> list= QuartzDataServiceImlFactory.createService(code).hisRegisterYyghInfo(starttime,endtime);
        if(null != list && list.size()>0){
           hisRegisterYyghMapper.insertBatch(list);
       }

        logger.info("结束拉取挂号信息：" + starttime + "->" + endtime + "耗时：" + (System.currentTimeMillis() - a) + "毫秒");
    }

    // 定时新下的住院通知单信息
//    @Scheduled(cron = "0/20 * * * * ?")
    public void noticeData() {
        logger.info("开始处理 住院通知单信息");
        Calendar calendar = Calendar.getInstance();
        long a = System.currentTimeMillis();
        String endtime = sdf.format(calendar.getTime());
        calendar.add(Calendar.MINUTE, -30);
        String starttime = sdf.format(calendar.getTime());
        QuartzDataServiceImlFactory.createService(code).NoticeData(starttime,endtime);
        logger.info("获取住院通知单：" + starttime + "->" + endtime + "耗时：" + (System.currentTimeMillis() - a) + "毫秒");
    }


    @Scheduled(cron = "0/15 * * * * ?")
    public void push(){
        List<HisRegisterYygh> registerList = hisRegisterYyghMapper.listNeedPushRecord();
        logger.info("定时推送消息，共{}条消息", registerList.size());
        if(null !=registerList && registerList.size()>0){
            registerList.stream().forEach(e -> {
                schedualServiceDataAdvance.invokeDataAdvanceService(e);
            });
        }
    }

}
