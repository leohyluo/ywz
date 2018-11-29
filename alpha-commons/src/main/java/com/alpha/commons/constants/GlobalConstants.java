package com.alpha.commons.constants;

public class GlobalConstants {
    //主症状部位关键字
    public static final String MAINSYMPTOM_KEYWORDS = "便,口,头,眼,耳,胸,腰,腹,阴道,鼻";
    //既往史阀值
    public static final Integer PAST_MEDICAL_HISTORY_THRESHOLD = 10000;
    //过敏史阀值
    public static final Integer ALLERGIC_HISTORY_THRESHOLD = 10000;
    //热门疾病阀值
    public static final Integer HOT_DISEASE_THRESHOLD = 1000;
    //热门药品阀值
    public static final Integer HOT_DRUG_THRESHOLD = 1000;
    //热门疾病疾显示数量
    public static final Integer HOT_DISEASE_COUNT = 30;
    //热门疾病疾显示数量
    public static final Integer HOT_DRUG_COUNT = 30;
    //问题称谓占位符
    public static final String USER_NAME = "{userName}";
    //问题称谓占位符
    public static final String USER_NAME1 = "{userName1}";
    //家长名字占位符
    public static final String PATIENT_NAME = "patientName";
    //医生姓名占位符
    public static final String DOCTOR_NAME1 = "doctorName";
    //<=14岁统一称呼为宝宝
    public static final String USER_NAME_BABY = "宝宝";
    public static final String USER_NAME1_BABY = "小朋友";
    //问题称谓占位符
    public static final String DOCTOR_NAME = "{doctorName}";
    //>14岁统一称呼为您
    public static final String USER_NAME_YOU = "您";
    public static final String USER_NAME1_YOU = "同学";
    public static final String UNKNOWN_ANSWER = "不清楚";
    public static final String TEMPERATURE_UNKNOWN = "未测量";
    public static final String NONE = "以上都没有";
    //自己
    public static final String SELF = "自己";
    public static final String KEY_CONC_SYMP_NAME = "关键伴随症状";

    public static final String IMAGE_BASE_URL = "images";
    public static final String SZ_CHILD_HOSPITAL_CODE = "A002";
    public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    //伴随症状redis-key
    public static final String REDIS_KEY_MAIN_COCN_PREFIX = "MAIN_COCN_SYMP";
    //系统配置redis-key
    public static final String REDIS_KEY_SYS_CONFIG = "SYS_CONFIG";
    //日志记录标识
    public static final String REDIS_KEY_EXIST_PUSHLOG = "RPUSH_";
    public static final String REDIS_KEY_PRE_QUESTION = "PRE_QUESTION_";
    public static final String REDIS_KEY_PRE_QUESTION_ANSWER = "PRE_QUESTION_ANSWER_";
    //批量插入条件值
    public static final int BATCH_COUNT = 100;
    public static final String REDIS_KEY_EXIST_PUSHLOG_PRE = "PUSHLIST_";
    public static final String REDIS_KEY_WEIXIN_LOG_FLAG = "REDIS_KEY_WEIXIN_LOG_FLAG";
    public static final String REDIS_KEY_EXIST_PUSHLOG_FLAG = "P_FLAG_";

    public static final String REDIS_KEY_SHECHED_FLAG_ = "SHECHED_FLAG_";

    public static final String REDIS_KEY_WENZHEN_OVER_FLAG_ = "SHECHED_WZ_OVER_";

    //问诊完成过期时间 默认为七天 60*60*24*7
    public static final int REDIS_KEY_WENZHEN_OVER_TIME_ = 60 * 60 * 24 * 7;

    //公众号URL推送地址
    public static final String REDIS_KEY_ENURL_FLAG_ = "SHECHED_WZ_ENRUL_";

    //Pno 标识
    public static final String REDIS_KEY_PNO_FLAG_ = "SHECHED_WZ_PNO_";

    //挂号表推送状态  -2:推送中
    public static final Integer HIS_STATUS_WAIT = -2;

    //挂号表推送状态  1:推送成功
    public static final Integer HIS_STATUS_SUCCESS = 1;

    //挂号表推送状态  2:推送失败
    public static final Integer HIS_STATUS_FAIL = 2;

    //微信推关失败标识
    public static final String REDIS_KEY_PNO_PUSH_FAIL = "PUSH_FAIL_WX_";

    public static final String WX_FOOTER0 = "开始提供病情﹥﹥﹥";
    public static final String WX_FOOTER1 = "开始回答﹥﹥﹥";
    public static final String WX_FOOTER2 = "开始提供病情﹥﹥﹥";
    public static final String WX_FOOTER3 = "开始提供病情﹥﹥﹥";
    public static final String WX_FOOTER4 = "开始更新病情﹥﹥﹥";
}
