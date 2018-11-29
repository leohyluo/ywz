package com.alpha.commons.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class SMSMessageComponent {

    /**
     * 日志对象，用于输出日志
     */
    private static final Logger logger = LoggerFactory.getLogger(SMSMessageComponent.class);

    private static SMSMessageComponent instance;

    private SMSMessageComponent() {
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new SMSMessageComponent();
            init();
        }
    }

    public static SMSMessageComponent getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    private static String softwareSerialNo;
    private static String key;

    private static SMSmessageClient msgClient = null;

    /*public static void init() {
        ResourceBundle bundle = PropertyResourceBundle.getBundle("other/smsconfig");
        softwareSerialNo = bundle.getString("profileCode");
        key = bundle.getString("apikey");
        try {
            msgClient = new SMSmessageClient(softwareSerialNo, key);
        } catch (Exception e) {
            logger.error("短信接口初始化失败", e);
            throw new RuntimeException("短信接口初始化失败", e);
        }
    }*/
    
    public static void init() {
//      ResourceBundle bundle = PropertyResourceBundle.getBundle("other/smsconfig");
//      softwareSerialNo = bundle.getString("profileCode");
//      key = bundle.getString("apikey");
      try {
          msgClient = new SMSmessageClient("pjN4ZsJR-8YCm-v06K-h3KH", "3fd4f3730239256c4d4e6b4292a29799");
      } catch (Exception e) {
          logger.error("短信接口初始化失败", e);
          throw new RuntimeException("短信接口初始化失败", e);
      }
  }

    /**
     * 发送短信
     *
     * @param mobile     单个手机号码
     * @param smsContent 短信内容
     * @return
     */
    public synchronized int sendMessage(String mobile, String smsContent) {
        int code = msgClient.sendSMS(mobile, smsContent);
        logger.debug("短信发送状态：" + getResult(code));
        return code;
    }

    public static String getResult(Number number) {
        int code = number.intValue();
        switch (code) {
            case 1000:
                return "成功";
            default:
                return "未知错误";
        }
    }
}

