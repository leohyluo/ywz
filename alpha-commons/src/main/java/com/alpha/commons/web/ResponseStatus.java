package com.alpha.commons.web;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leohyluo 服务器响应请求状态码。
 */
public enum ResponseStatus {
    /**
     * 成功，服务器处理完成 20000, "success"
     */
//	SUCCESS(200, "操作成功"),
    SUCCESS(1000, "操作成功"),
    FAIL(1001,"操作失败"),
    NULLDATA(1002,"无数据"),

    MEDICAL_ALREADY_IMPORT(101, "病历已导入过"),

    /**
     * 缺少必要参数 30001, "Required parameter missing"
     */
    REQUIRED_PARAMETER_MISSING(201, "缺少必要参数或参数为空"),
    /**
     * 用户不存在 30002, "User not found"
     */
    USER_NOT_FOUND(202, "用户不存在"),
    /**
     * 无效的签名
     */
    INVALID_SIGN(203, "签名验证不通过"),
    /**
     * 用户密码不匹配 30003, "Incorrect password"
     */
    USER_PASSWORD_INCORRECT(204, "密码错误"),
    /**
     * 缺少必要参数 30001, "Required parameter missing"
     */
    MOBILE_ALREADY_EXISTS(205, "手机号码已被注册"),

    NICKNAME_ALREADY_EXISTS(206, "昵称已存在"),

    UPLOAD_FAIL(207, "头像上传失败"),
    /**
     * 用户密码不匹配 30003, "Incorrect password"
     */
    OLD_PASSWORD_INCORRECT(208, "原密码不正确,请重新输入"),
    /**
     * token格式错误
     */
    ACCESS_TOKEN_INCORRECT(209, "token有误"),
    /**
     * token不存在
     */
    ACCESS_TOKEN_NOT_EXISTS(210, "您的账号已在其他设备登录"),

    /**
     * 无就诊记录
     */
    BASIC_RECORD_NOTFOUND(211, "无就诊记录"),
    /**
     * 座席信息不存在
     */
    PATIENT_INFO_NOTFOUND(212, "患者基本信息不存在"),
    /**
     * 身份证号码/就诊号不存在
     */
    IDCARD_NOTFOUND(213, "号码不存在"),

    /**
     * 手机号码格式不正确
     */
    PHONE_TYPE_NOTRIGHT(214, "手机号码格式不正确"),

    /**
     * 手机号码格式不正确
     */
    NAME_TOO_LONG(215, "姓名过长"),

    /**
     * 身份较验不通过
     */
    BIRTH_NOT_MATCH(216, "身份较验不通过"),

    /**
     * 不存在挂号信息
     */
    REGISTER_INFO_NOTFOUND(217, "不存在挂号信息"),

    /**
     * 数据重复
     */
    DATA_DUPLICATION(218, "数据重复"),
    /**
     * 入院通知不存在
     */
    NOTICE_NOTFOUND(219, "入院通知书不存在"),
    /**
     * 出生日期不存在
     */
    BIRTHDAY_NOTFOUND(220, "出生日期不存在"),
    /**
     * 图片上传失败
     */
    IMG_NOTFOUND(221, "图片上传失败！"),
    /**
     * 入院信息不存在
     */
    PATIENTINFO_NOTFOUND(222, "入院信息不存在！"),
    /**
     * 出生日期格式不正确
     */
    INVALID_BIRTH(223, "出生日期格式不正确！"),
    /**
     * 入院通知书编码与公众号传过来的通知书编码不一致
     */
    NOTICE_ID_ERROR(224, "入院通知书编码与公众号传过来的通知书编码不一致！"),
    /**
     * 就诊时间为空或不正确
     */
    INVALID_VISIT_TIME(225, "不确切的就诊时间！"),
    /**
     * 就诊时间已过
     */
    VISIT_TIME_EXPIRED(226, "就诊时间已过"),
    /**
     * 病历模板不存在
     */
    TEMPLATE_NOT_FOUND(227, "病历模板不存在"),
    /**
     * 个人病历不存在
     */
    MEDICAL_TEMPLATE_NOT_FOUND(228, "个人病历不存在"),
    /**
     * 门诊号不存在
     */
    OUT_PATIENT_NO_NOT_FOUND(229, "门诊号不存在"),
    /**
     * 妇科只对女性开放
     */
    GENDER_NOT_MATCH(230, "妇科只对女性开放"),
    /**
     * 儿科只针对18岁以下人群开放
     */
    AGE_NOT_MATCH(231, "儿科只针对18岁以下人群开放"),
    /**
     * 预问诊结束
     */
    DIAGNOSIS_COMPLETED(300, "预问诊结束"),
    /**
     * 预问诊基础问题不存在
     */
    BASIC_QUESTION_NOT_FOUND(301, "问题不存在"),
    /**
     * 没有诊断结果
     */
    DIAGNOSIS_OUTCOME_NOTFOOUND(302, "没有诊断结果"),
    /**
     * 疾病没有与疾病系统关联
     */
    DISEASE_SYSTEM_EMPTY(303, "疾病没有与疾病系统关联"),
    /**
     * 疾病系统未找到
     */
    DISEASE_SYSTEM_NOT_FOUND(304, "疾病系统未找到"),
    /**
     * 服务器异常 44444, "Exception"
     */
    EXCEPTION(500, "服务器异常"),
    /**
     * 服务器无记录 20002, "Not logged or not connected"
     */
    NO_SESSION(20002, "用户未登录"),
    /**
     * 已存在 44403, "Existed"
     */
    EXISTED(44403, "Existed"),
    /**
     * 未找到数据 44404, "Not found"
     */
    NOT_FOUND(44404, "Not found"),

    /**
     * 服务器地址未找到 44445, "Address not found"
     */
    SERVER_NOT_FOUND(44445, "Address not found"),

    /**
     * 不支持的操作 44531, "Unsupport operation"
     */
    UNSUPPORT_OPERATION(44531, "Unsupport operation"),


    /**
     * 无权限操作 30004, "No permission"
     */
    NO_PERMISSION(30004, "No permission"),
    /**
     * 用户已登录 30005, "User Logged in"
     */
    USER_LOGINED(30005, "User Logged in"),

    /**
     * 不匹配的参数值 30006, "Invalid value"
     */
    INVALID_VALUE(30006, "请求参数不正确"),

    /**
     * 用户已存在 30022, "Existing user name"
     */
    USER_EXISTED(30022, "用户已存在"),
    
    USER_MEMBER_FULL(30023, "用户成员已达到5个"),

    HOSPITAL_NOT_FOUND(30024, "医院不存在"),

    DEPT_IS_NULL(30025, "挂号科室为空"),

    DEPT_NOT_OPEN(30026, "挂号科暂未开放"),

    NULL_DIAGNOSIS(20011, "没有找到对应的诊断结果");




    private final int code;
    private final String message;

    private ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static ResponseStatus findByCode(int code) {
        for (ResponseStatus rs : values()) {
            if (rs.code == code) {
                return rs;
            }
        }
        throw new IllegalArgumentException("Cannot create enum from " + code + " code!");
    }

    public static Map<Integer, String> all2Map() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (ResponseStatus t : ResponseStatus.values()) {
            map.put(t.code, t.message);
        }
        return map;
    }
}
