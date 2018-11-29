package com.alpha.his.service.etyy.impl;

import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.pojo.HospitalizedNoticeNew;
import com.alpha.commons.util.MapUtil;
import com.alpha.his.mapper.PushLogMapper;
import com.alpha.his.mapper.PushZdMapper;
import com.alpha.his.pojo.dto.PushInfo;
import com.alpha.his.pojo.dto.PushInfoLog;
import com.alpha.his.pojo.dto.PushResp;
import com.alpha.his.service.etyy.PushMessageService;
import com.alpha.his.service.etyy.UrlEncryptService;
import com.alpha.redis.RedisMrg;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by MR.Wu on 2018-06-25.
 */
@org.springframework.stereotype.Service
public class PushMessageServiceImpl implements PushMessageService {

    /**
     * 微信（160）推送地址
     */
    @Value("${weix.pushUrl}")
    private String pushUrl;

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

    @Resource
    private PushLogMapper pushLogMapper;

    @Resource
    private PushZdMapper pushZdMapper;

    private static Logger logger = LoggerFactory.getLogger(PushMessageServiceImpl.class);

    @Autowired
    private UrlEncryptService urlEncryptService;

    public static void main(String[] args) throws DocumentException {
//        PushInfo pushInfo = new PushInfo();
//        pushInfo.setTitle("为了让医生提前了解您宝宝的病情，请就诊前完成预问诊！");
//        pushInfo.setDep("内科综合(孙)");
//        pushInfo.setDepDocter("孙先军");
//        pushInfo.setCardNo("");
//        pushInfo.setPhone("15084806868");
//        pushInfo.setWatchingTime("2018-06-27");
//        pushInfo.setUserName("魏晨浩");
//        pushInfo.setUrl("http://192.168.100.10:8080/ywz_.html#/link/9ad8c35d22b330a98fffec3a4d710df63308");
//        new PushMessageServiceImpl().pushWeiXinMsg(pushInfo);
//        HospitalizedNoticeNew hospitalizedNoticeNew = new HospitalizedNoticeNew();
//        hospitalizedNoticeNew.setOutPatientNo("888888888");
//        hospitalizedNoticeNew.setNoticeId("88888888820180628");
//        hospitalizedNoticeNew.setContactPhone("15999621670");
//        new PushMessageServiceImpl().pushNotice(hospitalizedNoticeNew);
    }

    /**
     * 获取处理结果
     *
     * @param result
     * @return
     */
    public static Map<String, Object> getRsult(String result) {
        Map<String, Object> map = null;
//        try {
//            Document doc = XMLUtils.getXMLByString(result);
//            // 得到database节点
//            Element database = (Element) doc
//                    .selectSingleNode("//head");
//            List<Element> list = database.elements();
////            list.stream().map(e -> e.getName() + e.getText()).forEach(System.out::println);
//            map = list.stream().collect(Collectors.toMap(Element::getName, Element::getText));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return map;
    }

    /**
     * 推送消息结构体
     *
     * @param pushInfo
     * @return
     */
    public static String getPushMsg(PushInfo pushInfo) {
        StringBuffer stringBuffer = new StringBuffer();
//        try {
//            String curTime = String.valueOf(System.currentTimeMillis());
//            stringBuffer.append("<request><head><key>hisRemind_common_message</key><hospcode>111</hospcode><token></token><time>").append(curTime).append("</time></head><body>")
//                    .append("<cardNo>").append(pushInfo.getCardNo()).append("</cardNo>")
//                    .append("<phone>").append(pushInfo.getPhone()).append("</phone>")
//                    .append("<title>").append(pushInfo.getTitle()).append("</title>").
//                    append("<content>预问诊须知</content>").
//                    append("<note>");
//            if ("内科专家综合副高".equals(pushInfo.getDep())) {
//                stringBuffer.append("就诊科室：小儿内科\n就诊医生：").append(pushInfo.getDep());
//            } else {
//                stringBuffer.append("就诊科室：").append(pushInfo.getDep()).append("\n就诊医生：").append(pushInfo.getDepDocter());
//            }
//
//            stringBuffer.append("\n就诊时间：").append(DateUtils.date2String(DateUtils.string2Date(pushInfo.getWatchingTime()), "yyyy-MM-dd")).
//                    append("\n就诊人：").append(pushInfo.getUserName()).
//                    append("\n\n\n点击此处开始预问诊 ﹥﹥﹥").
//                    append("</note>").append("<url><![CDATA[").append(pushInfo.getUrl()).append("]]></url></body></request>");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return stringBuffer.toString();
    }

    /**
     * axis推送
     *
     * @param pushInfo
     * @return
     */
    public PushResp cl1(PushInfo pushInfo) {
        logger.info("开始推送，类型为：{}", pushInfo.getType());
//        try {
//            Service service = new Service();
//            Call call = (Call) service.createCall();
//            call.setTargetEndpointAddress(pushUrl);
//            call.setOperationName(new QName("http://service.bd.com/", "requestWS"));
//            call.setUseSOAPAction(true);
//            call.addParameter("request", XMLType.XSD_STRING,
//                    javax.xml.rpc.ParameterMode.IN);//接口的参数
//            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
//            String s = getPushMsg(pushInfo);
//            logger.info("push msg：{}", s);
//            String result = (String) call.invoke(new Object[]{s});
//            //给方法传递参数，并且调用方法
//            Map<String, Object> map = getRsult(result);
//            PushResp resp = (PushResp) MapUtil.mapToObject(map, PushResp.class);
//            logger.debug(resp.toString());
//            return resp;
//        } catch (Exception e) {
//            logger.error(e.toString());
//        }
        return null;
    }

    @Override
    public PushResp pushWeiXinMsg(PushInfo pushInfo) {
        PushResp pushResp = null;
//        try {
//            //URL加密
//            String url = pushInfo.getUrl();
//            if(pushInfo.getType() != RegisterType.GET_FOR_APPOINTMENT) {
//                pushInfo.setUrl(domainUrl.replace("@", urlEncryptService.getEncryptUrl(url)));
//            }
//
//            //开关控制 1:推送指定号源  2:推送全部号源
//            String key = GlobalConstants.REDIS_KEY_SYS_CONFIG.concat("_").concat("push_branch");
//            SysConfig sysConfig = (SysConfig) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKeyEm(key, RedisMrg.DB1);
//            if ("1".equals(sysConfig.getConfigValue())) {
//                //获取指定号源
//                List<PushinfoZd> pushinfoZdList = pushZdMapper.getInfos();
//                Set<String> set = pushinfoZdList.stream().map(PushinfoZd::getCardNo).collect(Collectors.toSet());
//                Set<String> setPhone = pushinfoZdList.stream().map(PushinfoZd::getPhone).collect(Collectors.toSet());
//                if (set.contains(pushInfo.getCardNo()) || setPhone.contains(pushInfo.getPhone())) {
//                    //推送
//                    pushResp = cl1(pushInfo);
//                } else {
//                    logger.debug("不在指定列表中...{} -- {}", pushInfo.getCardNo(), pushInfo.getPhone());
//                    return null;
//                }
//
//            } else if ("2".equals(sysConfig.getConfigValue())) {
//                //推送所有用户
//                pushResp = cl1(pushInfo);
//            }
//
//            //记录推送日志
//            rec(pushInfo, pushResp);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return pushResp;
    }

    private List<PushInfoLog> getLastInfoFromRedis() {
        String keyPre = GlobalConstants.REDIS_KEY_EXIST_PUSHLOG_PRE;
        List<PushInfoLog> pushInfoLogsLast = (List<PushInfoLog>) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKeyEm(keyPre, RedisMrg.DB3);
        return pushInfoLogsLast;
    }

    private void rec(PushInfo pushInfo, PushResp pushResp) {
//        String keyPre = GlobalConstants.REDIS_KEY_EXIST_PUSHLOG_PRE;
//        logger.info("给卡号：{},手机号：{}推送消息状态为：{}", pushInfo.getCardNo(), pushInfo.getPhone(), pushResp.getDesc());
//
//        PushInfoLog pushInfoLog = new PushInfoLog();
//        pushInfoLog.setCardNo(pushInfo.getCardNo());
//        pushInfoLog.setpNo(pushInfo.getPno());
//        pushInfoLog.setResult(pushResp.getResult());
//        pushInfoLog.setDesc(pushResp.getDesc());
//        StringBuffer sb = new StringBuffer();
//        String hashCode = MD5Util.MD5(sb.append(pushInfo.getCardNo()).append("-").append(pushInfo.getPno()).append("-").append(pushResp.getResult()).append("-").append(pushResp.getDesc()).toString());
//        pushInfoLog.setHashCode(hashCode);
//
//        //判断是否存在记录
//        String key = GlobalConstants.REDIS_KEY_EXIST_PUSHLOG + hashCode;
//        Object obj = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(key, RedisMrg.DB3);
//        List<PushInfoLog> pushInfoLogs = new ArrayList<>();
//        if (null == obj) {
//            //可插入
//            List<PushInfoLog> pushInfoLogsLast = getLastInfoFromRedis();
//            if (null != pushInfoLogsLast) {
//                pushInfoLogsLast.add(pushInfoLog);
//                RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKey(keyPre.getBytes(), ObjectsTranscoder.serialize(pushInfoLogsLast), RedisMrg.DB3);
//                logger.info("上次不为空--------------");
//            } else {
//                pushInfoLogs.add(pushInfoLog);
//                RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKey(keyPre.getBytes(), ObjectsTranscoder.serialize(pushInfoLogs), RedisMrg.DB3);
//                logger.info("##########单个添加到redis");
//            }
//            //过期值 7天  60*24*7=10080
//            RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKeyAndExpire(key, "1", 10080, RedisMrg.DB3);
//        } else {
//            logger.info("redis 存在");
//        }
//
//        //是否达到插入条件  在规定时间内进行批量插入
//        Object conditionFlag = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey(GlobalConstants.REDIS_KEY_WEIXIN_LOG_FLAG, RedisMrg.DB3);
//        List<PushInfoLog> pushInfoLogsLast = getLastInfoFromRedis();
//        if (null == conditionFlag && null != pushInfoLogsLast) {
//            pushLogMapper.batch(pushInfoLogsLast);
//            RedisMrg.getInstance(redisIp, redisPort, redisPwd).delKey(keyPre.getBytes(), RedisMrg.DB3);
//            logger.info("批量插入完成.........");
//            RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKeyAndExpire(GlobalConstants.REDIS_KEY_WEIXIN_LOG_FLAG, "1", 120, RedisMrg.DB3);
//        }
    }

    @Override
    public PushResp pushNotice(HospitalizedNoticeNew hospitalizedNoticeNew) {
        String cardNo = "";
        String title = "入院办理通知:";
        String content = "办理入院信息须知\n您宝宝的门诊号是：" + hospitalizedNoticeNew.getOutPatientNo() + "\n办理住院请填写以下表格";
        String note = "《住院信息登记表》";
        String phone = "";
        Random random = new Random(System.currentTimeMillis());
        //推送控制：redis>get pushnotice =0 推送自己人  1 推送患者
        String pushnotice = (String) RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey("push_notice", 13);
        if (pushnotice.equals("0")) {
            List<String> list = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getSetString("push_notice_phone",
                    13);
            if (null != list && list.size() > 0) {
                phone = list.get(random.nextInt(list.size()));
            } else {
                logger.info("redis 不存在推送住院通知单电话号码");
                List<String> list1 = pushZdMapper.pushNoticePhone();
                if (null == list1 || list1.size() < 1) {
                    logger.info("推送住院通知，配置有异常");
                } else {
                    phone = list1.get(random.nextInt(list1.size()));
                     RedisMrg.getInstance(redisIp, redisPort, redisPwd).setSetString(list1,"push_notice_phone", 13);
                }
            }
        }
        if (pushnotice.equals("1")) {
            phone = hospitalizedNoticeNew.getContactPhone();
        }
        String url = noticeUrl + hospitalizedNoticeNew.getNoticeId() + "/" + hospitalizedNoticeNew.getOutPatientNo();
        String requestData = getPushNotice(cardNo, title, content, note, phone, url);
        logger.info("推送住院通知单参数是：{}", requestData);
        PushResp pushResp = callNotice160(requestData);
        logger.info("推送住院通知单信息：门诊号：{}，电话号码：{}，结果是：{}", hospitalizedNoticeNew.getOutPatientNo(), hospitalizedNoticeNew
                .getContactPhone(), (pushResp.getResult() == 1) ? pushResp.getDesc() : "推送失败，未关注");
        return pushResp;
    }

    public String getPushNotice(String cardNo, String title, String content, String note, String phone, String url) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<request><head><key>hisRemind_common_message</key><hospcode>111</hospcode><token></token><time>20180425201845</time></head><body>");
        stringBuffer.append("<cardNo>" + cardNo + "</cardNo>");
        stringBuffer.append("<title>" + title + "</title>");
        stringBuffer.append("<content>" + content + "</content>");
        stringBuffer.append("<note>" + note + "</note>");
        stringBuffer.append("<phone>" + phone + "</phone>");
        stringBuffer.append("<url>" + url + "</url>");
        stringBuffer.append("</body></request>");
        String soapRequestData = stringBuffer.toString();
        return soapRequestData;
    }

    public PushResp callNotice160(String requesxml) {
        try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(pushUrl);
            call.setOperationName(new QName("http://service.bd.com/", "requestWS"));
            call.setUseSOAPAction(true);
            call.addParameter("request", XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);//接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
            String result = (String) call.invoke(new Object[]{requesxml});
            Map<String, Object> map = getRsult(result);
            PushResp resp = (PushResp) MapUtil.mapToObject(map, PushResp.class);
            return resp;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

}
