package com.alpha.user.controller;

import com.alibaba.fastjson.JSON;
import com.alpha.commons.core.mapper.UserCardInfoMapper;
import com.alpha.commons.core.pojo.InitializeDataVo;
import com.alpha.commons.core.pojo.UserCardInfo;
import com.alpha.commons.core.pojo.YwzCountTimes;
import com.alpha.commons.core.pojo.ao.UserCardInfoAO;
import com.alpha.commons.core.service.InitializeDataService;
import com.alpha.commons.core.service.YwzCountTimesService;
import com.alpha.commons.util.BeanUtils;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import com.alpha.his.utils.PhoneUtils;
import com.alpha.redis.RedisMrg;
import com.alpha.user.service.OpenCardService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 开卡流程
 * Created by Administrator on 2018/3/14.
 */
@RestController
@RequestMapping("user")
public class OpenCardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenCardController.class);

    @Autowired
    OpenCardService openCardService;
    @Autowired
    InitializeDataService initializeDataService;

    @Autowired
    UserCardInfoMapper userCardInfoMapper;

    @Autowired
    private YwzCountTimesService ywzCountTimesService;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    /**
     * 保存开卡资料
     *
     * @return
     */
    @PostMapping("/opencard")
    public ResponseMessage openCard(String allParam) {
        LOGGER.info("患者：{} 开卡,开卡信息是：{}", allParam);
        try {
            UserCardInfoAO userCardInfoAO = JSON.parseObject(allParam, UserCardInfoAO.class);
            if (null != userCardInfoAO) {
                userCardInfoAO.setHospitalCode("A002");
                if (BeanUtils.checkFieldValueNull(userCardInfoAO)) {
                    return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
                }
                if (!PhoneUtils.isMobile(userCardInfoAO.getContactPhone())) {
                    return WebUtils.buildResponseMessage(ResponseStatus.PHONE_TYPE_NOTRIGHT);
                }
                if (StringUtils.isNotEmpty(userCardInfoAO.getPatientName()) && userCardInfoAO.getPatientName().length() > 25) {
                    return WebUtils.buildResponseMessage(ResponseStatus.NAME_TOO_LONG);
                }
                String patientCardId = userCardInfoAO.getPatientCertiNo();
                UserCardInfo userCardInfo1 = new UserCardInfo();
                userCardInfo1.setPatientName(userCardInfoAO.getPatientName());
                if (StringUtils.isBlank(patientCardId)) {
                    patientCardId = userCardInfoAO.getContactIdCard();
                    userCardInfo1.setContactIdCard(patientCardId);
                }
                userCardInfo1.setPatientCertiNo(patientCardId);
                userCardInfo1.setPatientName(userCardInfoAO.getPatientName());
                UserCardInfo userCardInfo = userCardInfoMapper.selectOne(userCardInfo1);
                if (null != userCardInfo) {
                    LOGGER.info("该用户已经开过开了，");
                    return WebUtils.buildResponseMessage(ResponseStatus.USER_EXISTED);
                }
                if (StringUtils.isBlank(userCardInfoAO.getSchool())) {
                    userCardInfoAO.setSchool("未上学");
                }
                Integer num = openCardService.openCard(userCardInfoAO);
                if (num > 0) {
                    LOGGER.info("开卡成功");
                    Map map1 = new HashMap();
                    map1.put("idCard", patientCardId);
                    map1.put("id", num);
                    YwzCountTimes ywzCountTimes = new YwzCountTimes();
                    ywzCountTimes.setType(1);
                    ywzCountTimesService.addTimes(ywzCountTimes);
                    return WebUtils.buildSuccessResponseMessage(map1);
                }
            } else {
                return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("保存开卡信息异常：{}", e.toString());
        }
        return WebUtils.buildResponseMessage(ResponseStatus.FAIL);
    }

    /**
     * 病人证件类型，国家，民族，监护人证件类型，医疗费用类型，字典
     *
     * @return
     */
    @GetMapping("/initialize")
    public ResponseMessage initializeData() {
        InitializeDataVo initializeDataVo = null;

        try {
            String str = RedisMrg.getInstance(redisIp, redisPort, redisPwd).getKey2("dictionary", RedisMrg.DB1);
            if (null != str) {
                initializeDataVo = JSON.parseObject(str, InitializeDataVo.class);
            }
            if (initializeDataVo == null) {
                LOGGER.info("从redis 没有获取到字典");
                initializeDataVo = initializeDataService.innitializeData();
                if (null != initializeDataVo) {
                    String data = JSON.toJSONString(initializeDataVo);
                    RedisMrg.getInstance(redisIp, redisPort, redisPwd).setKeyAndExpire("dictionary", data, 600, RedisMrg.DB1);
                    return WebUtils.buildSuccessResponseMessage(initializeDataVo);
                }
            } else {
                LOGGER.info("从redis中获取到字典");
                return WebUtils.buildSuccessResponseMessage(initializeDataVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("获取字典异常：{}", e.toString());
        }

        return WebUtils.buildResponseMessage(ResponseStatus.FAIL);
    }

    /**
     * 返回 Base64 图片
     *
     * @param idCard
     * @return
     */
    @GetMapping("/imagenew")
    public ResponseMessage imageNew(String idCard, String id) {
        if (StringUtils.isBlank(idCard) || StringUtils.isBlank(id)) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        String num = openCardService.imageNew(idCard, id);
        if (StringUtils.isBlank(num)) {
            return WebUtils.buildResponseMessage(ResponseStatus.USER_NOT_FOUND);
        }
        return WebUtils.buildSuccessResponseMessage(num);
    }

    /**
     * /user/scantimes
     *
     * @param deviceId 手机设备标识
     * @param type 手机设备标识 8:160 2:线下扫码
     * @param memberId 员工工号
     * @return
     */
    @GetMapping("scantimes")
    public ResponseMessage scantimes(String deviceId, Integer type, String memberId) {
        if (StringUtils.isBlank(deviceId)) {
            LOGGER.info("扫码参数有误");
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        YwzCountTimes ywzCountTimes = new YwzCountTimes();
        ywzCountTimes.setType(type);
        ywzCountTimes.setDeviceId(deviceId);
        ywzCountTimes.setMemberId(memberId);
        ywzCountTimesService.addTimes(ywzCountTimes);
        return WebUtils.buildSuccessResponseMessage();
    }
}
