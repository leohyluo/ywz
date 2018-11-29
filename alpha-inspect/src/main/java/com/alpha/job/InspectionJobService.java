package com.alpha.job;

import com.alpha.service.CSReportService;
import com.alpha.service.FSJobService;
import com.alpha.service.JYJobService;
import com.alpha.service.XNDJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.dc.pr.PRError;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by Administrator on 2018/10/22.
 */
@Component
public class InspectionJobService {

    private static Logger logger = LoggerFactory.getLogger(InspectionJobService.class);
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private JYJobService jyJobService;
    @Resource
    private XNDJobService xndJobService;
    @Resource
    private FSJobService fsJobService;
    @Resource
    private CSReportService csReportService;

      /**
     * 定时获取申请单，
     */
      @Scheduled(cron = "0 0/10 * * * ?")
    private void getRequest(){
        logger.info("开始获取申请单");
        Calendar calendar = Calendar.getInstance();
        long a = System.currentTimeMillis();
        String endtime = sdf.format(calendar.getTime());
        calendar.add(Calendar.MINUTE, -5);
        String starttime = sdf.format(calendar.getTime());
        jyJobService.jy_request(starttime,endtime);
        xndJobService.xnd_request(starttime,endtime);
        fsJobService.fs_request(starttime,endtime);
        csReportService.cs_request(starttime,endtime);
        logger.info("获取{} -》{} 申请单耗时：{} 毫秒",starttime,endtime,(System.currentTimeMillis()-a));
    }

    /**
     * 定时获取检验结果
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    private void getResponse(){
        Calendar calendar = Calendar.getInstance();
        long a = System.currentTimeMillis();
        String endtime = sdf.format(calendar.getTime());
        calendar.add(Calendar.MINUTE, -10);
        String starttime = sdf.format(calendar.getTime());
        jyJobService.jy_result(starttime,endtime);
        xndJobService.xnd_result(starttime,endtime);
        fsJobService.fs_result(starttime,endtime);
        logger.info("获取{} -》{}结果耗时：{} 毫秒",starttime,endtime,(System.currentTimeMillis()-a));
    }

//    @Scheduled(cron = "0 0/10 * * * ?")
    private void push(){
        logger.info("开始推送");
        long a = System.currentTimeMillis();
        jyJobService.push();
        xndJobService.push();
        fsJobService.push();
        csReportService.push();
        logger.info("推送结束耗时：{} 毫秒",(System.currentTimeMillis()-a));
    }


}
