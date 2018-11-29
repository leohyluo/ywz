package com.alpha.push.service.impl;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.enums.RegisterType;
import com.alpha.commons.pojo.SysConfig;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.MD5Util;
import com.alpha.commons.util.MapUtil;
import com.alpha.commons.util.XMLUtils;
import com.alpha.push.domain.PushInfo;
import com.alpha.push.domain.PushInfoLog;
import com.alpha.push.domain.PushResp;
import com.alpha.push.domain.PushinfoZd;
import com.alpha.push.excepton.RetryException;
import com.alpha.push.mapper.PushLogMapper;
import com.alpha.push.mapper.PushZdMapper;
import com.alpha.push.service.NotifyOService;
import com.alpha.push.service.PushMessageService;
import com.alpha.push.service.UrlEncryptService;
import com.alpha.redis.ObjectsTranscoder;
import com.alpha.redis.RedisMrg;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by MR.Wu on 2018-06-25.
 */
@org.springframework.stereotype.Service
public class PushMessageServiceImpl implements PushMessageService {


    /**
     * 微信（160）推送地址
     */
    @Value("${weix.pushUrl}")
    private String pushUrl = "http://121.15.136.85:17001/hdepc/services/hisWebService?wsdl";

    /**
     * 问诊域名信息
     */
    @Value("${alpha.h5.linkUrl}")
    private String domainUrl;

    @Value("${notice.linkUrl}")
    private String noticeUrl;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    @Value("${hospital.code}")
    private  String code;

    @Resource
    private PushLogMapper pushLogMapper;

    @Resource
    private PushZdMapper pushZdMapper;

    private static Logger logger = LoggerFactory.getLogger(PushMessageServiceImpl.class);

    @Autowired
    private UrlEncryptService urlEncryptService;

    @Autowired
    private NotifyOService notifyoService;

    @Resource
    private HisRegisterYyghMapper hisRegisterYyghMapper;

    static class P implements Runnable {
        private CountDownLatch latch = null;

        PushMessageServiceImpl pushMessageService = new PushMessageServiceImpl();
        PushInfo pushInfo;

        public P(PushInfo pushInfo, CountDownLatch latch) {
            this.pushInfo = pushInfo;
            this.latch = latch;
        }

        @Override
        public void run() {
            long s = System.currentTimeMillis();
            pushMessageService.cl1(pushInfo);
            long c = (System.currentTimeMillis() - s);
            System.out.println(Thread.currentThread().getName() + "单个时间：" + c);
            latch.countDown();
        }
    }

    public static void main(String[] args) throws DocumentException, InterruptedException {
        PushInfo pushInfo = new PushInfo();
        pushInfo.setTitle("为了让医生提前了解您宝宝的病情，请就诊前完成预问诊！");
        pushInfo.setDep("内科综合(孙)");
        pushInfo.setDepDocter("孙先军");
        pushInfo.setCardNo("8100742443");
        pushInfo.setPhone("15012600526");
        pushInfo.setWatchingTime("2018-06-27");
        pushInfo.setUserName("菲菲123");
        pushInfo.setUrl("http://192.168.100.10:8080/ywz_.html#/link/9ad8c35d22b330a98fffec3a4d710df63308");

        int counter = 1;
        CountDownLatch latch = new CountDownLatch(counter);
        ExecutorService executor = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int i = 0; i < counter; i++) {
            executor.submit(new P(pushInfo, latch));
        }
        latch.await();
        System.out.println("总时间：" + (System.currentTimeMillis() - start));
        executor.shutdown();

        new PushMessageServiceImpl().pushWeiXinMsg(pushInfo);

    }

    /**
     * 获取处理结果
     *
     * @param result
     * @return
     */
    public static Map<String, Object> getRsult(String result) {
        Map<String, Object> map = null;
        try {
            Document doc = XMLUtils.getXMLByString(result);
            // 得到database节点
            Element database = (Element) doc
                    .selectSingleNode("//head");
            List<Element> list = database.elements();
//            list.stream().map(e -> e.getName() + e.getText()).forEach(System.out::println);
            map = list.stream().collect(Collectors.toMap(Element::getName, Element::getText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 推送消息结构体
     *
     * @param pushInfo
     * @return
     */
    public  String getPushMsg(PushInfo pushInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            String hospcode="138";
            if(code.equals("A001")){
                hospcode="111";
            }else if (code.equals("A002")){
//                hospcode="138";
            }

            String curTime = String.valueOf(System.currentTimeMillis());
            stringBuffer.append("<request><head><key>hisRemind_common_message</key>")
                    .append("<hospcode>").append(hospcode).append("</hospcode>")
                    .append("<token></token>").append("<time>")
                    .append(curTime).append("</time></head><body>")
                    .append("<cardNo>").append(pushInfo.getCardNo()).append("</cardNo>")
                    .append("<phone>").append(pushInfo.getPhone()).append("</phone>")
                    .append("<title>").append(pushInfo.getTitle()).append("</title>").
                    append("<content>预问诊须知</content>").
                    append("<note>");
            if ("内科专家综合副高".equals(pushInfo.getDep())) {
                stringBuffer.append("就诊科室：小儿内科\n就诊医生：").append(pushInfo.getDep());
            }else if ("普通儿科".equals(pushInfo.getDep())) {
                stringBuffer.append("就诊科室：小儿内科\n就诊医生：").append(pushInfo.getDep());
            } else {
                stringBuffer.append("就诊科室：").append(pushInfo.getDep()).append("\n就诊医生：").append(pushInfo.getDepDocter());
            }

            stringBuffer.append("\n就诊时间：").append(DateUtils.date2String(DateUtils.string2Date(pushInfo.getWatchingTime()), "yyyy-MM-dd")).
                    append("\n就诊人：").append(pushInfo.getUserName()).
                    append("\n\n\n点击此处开始预问诊 ﹥﹥﹥").
//                    append("\n\n\n").append(pushInfo.getFooter()).
                    append("</note>").append("<url><![CDATA[").append(pushInfo.getUrl()).append("]]></url></body></request>");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }


    /**
     * axis推送
     *
     * @param pushInfo
     * @return
     */
    public PushResp cl1(PushInfo pushInfo) {
        logger.debug("开始推送，类型为：{}", pushInfo.getType());
        try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            if(code.equals("A001")){
                call.setTargetEndpointAddress(pushUrl);
            }else if (code.equals("A002")){
//                pushUrl="http://192.168.101.171:9180/hdepc/services/hisWebService?wsdl";
                call.setTargetEndpointAddress(pushUrl);
            }
            call.setTargetEndpointAddress(pushUrl);
            call.setOperationName(new QName("http://service.bd.com/", "requestWS"));
            call.setUseSOAPAction(true);
            call.addParameter("request", XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);//接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型

            String s = getPushMsg(pushInfo);
            logger.info("push msg：{}", s);
            String result = (String) call.invoke(new Object[]{s});
            //给方法传递参数，并且调用方法
            Map<String, Object> map = getRsult(result);
            PushResp resp = (PushResp) MapUtil.mapToObject(map, PushResp.class);
            logger.debug(resp.toString());
            return resp;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    /**
     * 重试两次，每次间隔了15秒
     * @param pushInfo
     */
    @Override
    @Retryable(value= {RetryException.class},maxAttempts = 2,backoff = @Backoff(delay = 15000l,multiplier = 1))
    public void retryPush(PushInfo pushInfo) throws RetryException {
        PushResp pushResp = pushWeiXinMsg(pushInfo);
        if (pushResp != null && pushResp.getResult() == 1) {
            //消息推送成功后更新状态
            logger.info("{重试}{} 给患者{}-{}推送消息成功", Thread.currentThread().getName(), pushInfo.getUserName(), pushInfo.getCardNo());

            //这里只更新缓存中的内容，数据落地由定时器完成
            String tempKey = GlobalConstants.REDIS_KEY_PNO_FLAG_ + pushInfo.getPno();
            RedisMrg.getInstance(redisIp, redisPort, redisPwd).updateKey(tempKey, GlobalConstants.HIS_STATUS_SUCCESS, RedisMrg.DB6);
        } else {
            //继续重试
            logger.info("{重试}重试结果 {} ", pushInfo.getPhone());
            throw new RetryException(pushInfo, pushInfo.getPno());
        }
    }

    /**
     * 重试失败调用此方法 RetryException 对应重试次数达到后处理结果
     */
    @Recover
    public void recover(RetryException e){
        String tempKey = GlobalConstants.REDIS_KEY_PNO_FLAG_ + e.getMessage();
        RedisMrg.getInstance(redisIp, redisPort, redisPwd).updateKey(tempKey, GlobalConstants.HIS_STATUS_FAIL, RedisMrg.DB6);
        logger.info("重试失败,结束.....{}", e.getMessage());
    }

    @Override
    public PushResp pushWeiXinMsg(PushInfo pushInfo) {
        PushResp pushResp = null;
        try {
            //URL加密
            String url = pushInfo.getUrl();
            //http://192.168.100.10:8080/ywz_.html#link/@
            logger.info("pushWeiXinMsg.pushInfo=" + pushInfo);
            //modify by lhy,type=3的也加密
            /*if (pushInfo.getType() != RegisterType.GET_FOR_APPOINTMENT) {
                pushInfo.setUrl(domainUrl.replace("@", urlEncryptService.getEncryptUrl(url)));
            }*/
            pushInfo.setUrl(domainUrl.replace("@", urlEncryptService.getEncryptUrl(url)));

            //开关控制 1:推送指定号源  2:推送全部号源
            String key = GlobalConstants.REDIS_KEY_SYS_CONFIG.concat("_").concat("push_branch");
            SysConfig sysConfig = (SysConfig) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKeyEm(key, RedisMrg.DB1);
            if ("1".equals(sysConfig.getConfigValue())) {
                //获取指定号源
                List<PushinfoZd> pushinfoZdList = pushZdMapper.getInfos();
                Set<String> setPhone = pushinfoZdList.stream().map(PushinfoZd::getPhone).collect(Collectors.toSet());
                List<String> list=new ArrayList<>(setPhone);
                pushInfo.setPhone(list.get( (int) (Math.random() * list.size())));//随机从数据库的电话推送
                if (setPhone.contains(pushInfo.getPhone())) {
                    //推送
                    pushResp = cl1(pushInfo);
                } else {
                    logger.info("不在指定列表中...{} -- {}", pushInfo.getCardNo(), pushInfo.getPhone());
                    return null;
                }
            } else if ("2".equals(sysConfig.getConfigValue())) {
                //推送所有用户
                pushResp = cl1(pushInfo);
            }

            //记录推送日志
            rec(pushInfo, pushResp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pushResp;
    }

    private List<PushInfoLog> getLastInfoFromRedis() {
        String keyPre = GlobalConstants.REDIS_KEY_EXIST_PUSHLOG_PRE;
        List<PushInfoLog> pushInfoLogsLast = (List<PushInfoLog>) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKeyEm(keyPre, RedisMrg.DB3);
        return pushInfoLogsLast;
    }

    private void rec(PushInfo pushInfo, PushResp pushResp) {
        String keyPre = GlobalConstants.REDIS_KEY_EXIST_PUSHLOG_PRE;
        logger.info("给卡号：{},手机号：{}推送消息状态为：{}", pushInfo.getCardNo(), pushInfo.getPhone(), pushResp.getDesc());

        PushInfoLog pushInfoLog = new PushInfoLog();
        pushInfoLog.setCardNo(pushInfo.getCardNo());
        pushInfoLog.setpNo(pushInfo.getPno());
        pushInfoLog.setResult(pushResp.getResult());
        pushInfoLog.setDesc(pushResp.getDesc());
        StringBuffer sb = new StringBuffer();
        String hashCode = MD5Util.MD5(sb.append(pushInfo.getCardNo()).append("-").append(pushInfo.getPno()).append("-").append(pushResp.getResult()).append("-").append(pushResp.getDesc()).toString());
        pushInfoLog.setHashCode(hashCode);

        //判断是否存在记录
        String key = GlobalConstants.REDIS_KEY_EXIST_PUSHLOG + hashCode;
        Object obj = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(key, RedisMrg.DB3);
        List<PushInfoLog> pushInfoLogs = new ArrayList<>();
        if (null == obj) {
            //可插入
            List<PushInfoLog> pushInfoLogsLast = getLastInfoFromRedis();
            if (null != pushInfoLogsLast) {
                pushInfoLogsLast.add(pushInfoLog);
                RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKey(keyPre.getBytes(), ObjectsTranscoder.serialize(pushInfoLogsLast), RedisMrg.DB3);
                logger.info("上次不为空--------------");
            } else {
                pushInfoLogs.add(pushInfoLog);
                RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKey(keyPre.getBytes(), ObjectsTranscoder.serialize(pushInfoLogs), RedisMrg.DB3);
                logger.info("##########单个添加到redis");
            }
            //过期值 7天  60*24*7=10080
            RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKeyAndExpire(key, "1", 10080, RedisMrg.DB3);
        } else {
            logger.info("redis 存在");
        }

        //是否达到插入条件  在规定时间内进行批量插入
        Object conditionFlag = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(GlobalConstants.REDIS_KEY_WEIXIN_LOG_FLAG, RedisMrg.DB3);
        List<PushInfoLog> pushInfoLogsLast = getLastInfoFromRedis();
        if (null == conditionFlag && null != pushInfoLogsLast) {
            pushLogMapper.batch(pushInfoLogsLast);
            RedisMrg.getInstance(redisIp, redisPort, redisPwd).delKey(keyPre.getBytes(), RedisMrg.DB3);
            logger.info("批量插入完成.........");
            RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKeyAndExpire(GlobalConstants.REDIS_KEY_WEIXIN_LOG_FLAG, "1", 120, RedisMrg.DB3);
        }
    }

    /**
     * 推送消息
     * @param hisRegisterYygh
     */
    @Override
    public void pushMessage(HisRegisterYygh hisRegisterYygh) {
        long start = System.currentTimeMillis();
        String key = GlobalConstants.REDIS_KEY_SYS_CONFIG.concat("_").concat("enable_push");
        SysConfig sysConfig = (SysConfig) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKeyEm(key, RedisMrg.DB1);
        if (sysConfig != null) {
            Object flag = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(GlobalConstants.REDIS_KEY_SHECHED_FLAG_, RedisMrg.DB2);
            if ("1".equals(sysConfig.getConfigValue()) && null == flag) {
                //定时任务启动中标识
                RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKey(GlobalConstants.REDIS_KEY_SHECHED_FLAG_, "1", RedisMrg.DB2);


                List<HisRegisterYygh> registerList = new ArrayList<>();
                registerList.add(hisRegisterYygh);

                hisRegisterYygh.setStatus(GlobalConstants.HIS_STATUS_WAIT);
                //暂时存入缓存中，由定时器进行数据库落地
                String tempKey = GlobalConstants.REDIS_KEY_PNO_FLAG_ + hisRegisterYygh.getPno();
                RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKey(tempKey, hisRegisterYygh.getStatus(), RedisMrg.DB6);

                //发送事件
                notifyoService.sendMessage(hisRegisterYygh);

                //将数据库状态改为 -2 以免下次取到脏数据
                if (null != registerList && !registerList.isEmpty())
                    hisRegisterYyghMapper.batchUpdateStatus(registerList);

                //结束定时任务
                RedisMrg.getInstance(redisIp, redisPort, redisPwd).delKey(GlobalConstants.REDIS_KEY_SHECHED_FLAG_, RedisMrg.DB2);
            } else {
                logger.warn("推送开关处于关闭状态");
            }
        } else {
            logger.error("没有推送的配置，请检查数据库和redis");
        }
        logger.info("总时间：" + (System.currentTimeMillis() - start));
    }
}
