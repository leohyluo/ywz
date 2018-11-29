package com.alpha.his.controller;

import com.alpha.commons.config.pojo.FTPInfo;
import com.alpha.commons.core.annotation.JSON;
import com.alpha.commons.core.annotation.JSONS;
import com.alpha.commons.core.annotation.Repeat;
import com.alpha.commons.core.pojo.HospitalizedNoticeNew;
import com.alpha.commons.core.pojo.HospitalizedPatientInfoNew1;
import com.alpha.commons.core.util.ImageUtil;
import com.alpha.commons.enums.ChildrenType;
import com.alpha.commons.enums.SysEnv;
import com.alpha.commons.util.BeanCopierUtil;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.StringUtils;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import com.alpha.his.service.etyy.HospitalizedCommonIllChildService;
import com.alpha.his.service.etyy.HospitalizedNewIllChildService;
import com.alpha.his.service.etyy.HospitalizedNoticeService;
import com.alpha.his.service.etyy.HospitalizedPatientInfoService;
import com.alpha.his.service.etyy.HospitalizedService;
import com.alpha.server.rpc.his.pojo.HospitalizedCommonIllChild;
import com.alpha.server.rpc.his.pojo.HospitalizedNewIllChild;

import com.alpha.server.rpc.his.vo.HospitalizedPatientInfoVo;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 住院模块
 */
@RestController
@RequestMapping("/hospitalized")
public class HospitalizedController {

    @Resource
    private HospitalizedNoticeService hospitalizedNoticeService;
    @Resource
    private HospitalizedPatientInfoService hospitalizedPatientInfoService;
    @Resource
    private HospitalizedCommonIllChildService hospitalizedCommonIllChildService;
    @Resource
    private HospitalizedNewIllChildService hospitalizedNewIllChildService;
    @Resource
    HospitalizedService hospitalizedService;
    @Value("${imgUpload.imgRoot}")
    private String imgRoot;
    @Resource
    FTPInfo ftpInfo;
    @Value("${address}")
    private String address;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 查询入院基本信息
     *
     * @param patientInfoVo
     * @return
     */
    @PostMapping("/patient/query")
    @JSONS({
            @JSON(type = HospitalizedPatientInfoNew1.class, exclude = "signUrl")
    })
    public ResponseMessage queryPatientInfo(HospitalizedPatientInfoVo patientInfoVo, PageInfo<HospitalizedPatientInfoVo> pageInfoVo) {
        String patientInfoFinish = "";              //入院信息采集是否已完成
        String hosNo = "";  //住院号
        String childrenType = "";                    //新生儿、普通患儿
        String illHistoryFinish = "";               //病史采集是否已完成
        ResponseStatus responseStatus = ResponseStatus.SUCCESS;

        //门诊号和此次住院的唯一标识(noticeId)通过二维码传过来
        String outPatientNo = patientInfoVo.getOutPatientNo();
        String noticeId = patientInfoVo.getNoticeId();
        if(StringUtils.isEmpty(outPatientNo, noticeId)) {
            logger.warn("门诊号和通知单编号不允许为空");
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        //从本地获取住院通知书，本地没有则从儿童医院获取住
        HospitalizedNoticeNew hospitalizedNotice = getHospitalizedNotice(outPatientNo, noticeId, responseStatus);
        if(responseStatus != ResponseStatus.SUCCESS) {
            return WebUtils.buildResponseMessage(responseStatus);
        }
        //从本地库里获取已签名认证的入院信息采集
        HospitalizedPatientInfoNew1 localPatientInfo = hospitalizedService.getByNoticeIdAndStatus(noticeId, null);
        //如果本地的入院信息采集已签名确认，则跳到下一环节，否则调用医院接口查看是否有历史住院信息
        if(localPatientInfo != null) {
            hosNo = localPatientInfo.getInpNo();
            logger.info("根据通知单编码{}在本地找到患者的入院信息采集，其住院号为{}", noticeId, hosNo);
            //如果本地住院号为空则调用医院接口获取住院号
            if(StringUtils.isEmpty(hosNo)) {
                HospitalizedPatientInfoNew1 hisHospitalizedPatientInfo = null;
                if(SysEnv.isCompanyEnv(address)) {
                    logger.info("本地住院号为空，开始模拟调用医院接口获取住院号，门诊号为{}", outPatientNo);
                    hisHospitalizedPatientInfo = hospitalizedService.mockPatientHospitalizedInfoNew(outPatientNo);
                } else if (SysEnv.isHisEnv(address)) {
                    logger.info("本地住院号为空，开始调用医院接口获取住院号，门诊号为{}", outPatientNo);
                    hisHospitalizedPatientInfo = hospitalizedService.getPatientHospitalizedInfoNew(outPatientNo);
                }
                if(hisHospitalizedPatientInfo != null && StringUtils.isNotEmpty(hisHospitalizedPatientInfo.getInpNo())) {
                    hosNo = hisHospitalizedPatientInfo.getInpNo();
                    logger.info("医院返回的住院号为{}", hosNo);
                    localPatientInfo.setInpNo(hosNo);
                    hospitalizedService.updateHospitalizedPatientInfo(localPatientInfo);
                    logger.info("将住院号{}更新到本地成功", hosNo);
                } else {
                    logger.info("门诊号{}对应的住院号为空,无法进行病史采集", outPatientNo);
                }
            }
            if(StringUtils.isNotEmpty(localPatientInfo.getSignUrl())) {
                patientInfoFinish = "1";
            } else{
                //如未签名则调用医院接口获取患者信息
                patientInfoFinish = "0";
                return buildHospitalizedProgress(patientInfoFinish, hosNo, childrenType, illHistoryFinish, localPatientInfo, null, null);
            }
        } else {
            //调用HIS接口获取患者以前的住院信息，如以前有住院信息则将其显示在页面上
            logger.info("根据通知单编码{}在本地找不到患者的入院信息采集，开始调用医院接口获取患者的信息，其门诊号为{}", noticeId, outPatientNo);
            patientInfoFinish = "0";
            HospitalizedPatientInfoNew1 hisHospitalizedPatientInfo = null;
            if(SysEnv.isCompanyEnv(address)) {
                hisHospitalizedPatientInfo = hospitalizedService.mockPatientHospitalizedInfoNew(outPatientNo);
            } else if(SysEnv.isHisEnv(address)) {
                hisHospitalizedPatientInfo = hospitalizedService.getPatientHospitalizedInfoNew(outPatientNo);
            }
            if(hisHospitalizedPatientInfo != null) {
                hosNo = hisHospitalizedPatientInfo.getInpNo();
                logger.info("根据门诊号{}在医院查到相关的患者信息，住院号为{}", outPatientNo, hosNo);
                if(StringUtils.isEmpty(hisHospitalizedPatientInfo.getNoticeId())) {
                    hisHospitalizedPatientInfo.setNoticeId(noticeId);
                    //将住院通知单的信息填充到患者信息
                    fetchNoticeInfoIntoPatientInfo(hospitalizedNotice, hisHospitalizedPatientInfo);
                    //将患者信息与通知单绑定
                    hospitalizedService.updateHospitalizedPatientInfo(hisHospitalizedPatientInfo);
                    logger.info("将医院返回的患者信息保存到本地，并与住院通知单{}绑定", noticeId);
                }
                return buildHospitalizedProgress(patientInfoFinish, hosNo, childrenType, illHistoryFinish, hisHospitalizedPatientInfo, null, null);
            } else {
                //如果患者信息为空，则将住院通知单的信息（出生日期、性别等）显示
                hisHospitalizedPatientInfo = new HospitalizedPatientInfoNew1();
                if(hospitalizedNotice != null) {
                    hisHospitalizedPatientInfo.setNoticeId(hospitalizedNotice.getNoticeId());
                    hisHospitalizedPatientInfo.setOutPatientNo(hospitalizedNotice.getOutPatientNo());
                    hisHospitalizedPatientInfo.setPatientName(hospitalizedNotice.getPatientName());
                    hisHospitalizedPatientInfo.setBirthday(hospitalizedNotice.getBirthday());
                    hisHospitalizedPatientInfo.setSex(hospitalizedNotice.getSex());
                    hisHospitalizedPatientInfo.setAge(hospitalizedNotice.getAge());
                }
                return buildHospitalizedProgress(patientInfoFinish, hosNo, childrenType, illHistoryFinish, hisHospitalizedPatientInfo, null, null);
            }

        }

        //如果入院信息采集完成，则调医院接口获取住院号，如果有住院号则跳转到病历采集页面，否则提示拿到住院号才能开始病史采集
        if(StringUtils.isNotEmpty(hosNo)) {
            //判断患者是新生儿还是普通患儿
            String birthStr = hospitalizedNotice.getBirthday();
            logger.info("住院通知单上的患儿出生日期为{}", birthStr);
            if(StringUtils.isEmpty(birthStr)) {
                return WebUtils.buildResponseMessage(ResponseStatus.INVALID_BIRTH);
            }
            Date birth = null;
            try {
                birth = DateUtils.string2Date(birthStr);
            } catch (ParseException e) {
                logger.error("无效的出生日期", e);
                return WebUtils.buildResponseMessage(ResponseStatus.INVALID_BIRTH);
            }
            long diffDays = DateUtils.getDiffDays(birth);
            childrenType = diffDays < 28 ? ChildrenType.NEWER.getValue() : ChildrenType.COMMON.getValue();
            //判断病史采集是否已完成
            if(childrenType.equals(ChildrenType.NEWER.getValue())) {
                HospitalizedNewIllChild hospitalizedNewIllChild = hospitalizedNewIllChildService.getByNoticeId(noticeId);
                illHistoryFinish = hospitalizedNewIllChild == null ? "0" : "1";
                return buildHospitalizedProgress(patientInfoFinish, hosNo, childrenType, illHistoryFinish, localPatientInfo, hospitalizedNewIllChild, null);
            } else if(childrenType.equals(ChildrenType.COMMON.getValue())) {
                HospitalizedCommonIllChild hospitalizedCommonIllChild = hospitalizedCommonIllChildService.getByNoticeId(noticeId);
                illHistoryFinish = hospitalizedCommonIllChild == null ? "0" : "1";
                return buildHospitalizedProgress(patientInfoFinish, hosNo, childrenType, illHistoryFinish, localPatientInfo, null, hospitalizedCommonIllChild);
            }
        }
        return buildHospitalizedProgress(patientInfoFinish, hosNo, childrenType, illHistoryFinish, localPatientInfo, null, null);
    }

    /**
     * 保存入院信息采集（基本信息、家庭信息、电子签名）
     * @param patientInfoVo
     * @return
     */
    @Repeat
    @PostMapping("/patient/save")
    public ResponseMessage savePatientInfo(HttpServletRequest request,@RequestBody @Validated({HospitalizedPatientInfoVo.SaveGroup.class}) HospitalizedPatientInfoVo patientInfoVo, BindingResult result) {
        if (result.hasErrors()) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        String noticeId = patientInfoVo.getNoticeId();
//        HospitalizedNoticeNew localNotice = hospitalizedService.getHospitalizedNoticeFromLocal(noticeId);
//        if(localNotice == null) {
//            logger.error("住院通知书{}不存在", noticeId);
//            return WebUtils.buildResponseMessage(ResponseStatus.NOTICE_NOTFOUND);
//        }

        //上传签名至FTP服务器
        /*String signUrl = patientInfoVo.getSignUrl();
        if(StringUtils.isNotEmpty(patientInfoVo.getSignUrl())){
            String imgPath = ImageUtil.generateImage(patientInfoVo.getSignUrl(), imgRoot, ftpInfo);
            if (StringUtils.isEmpty(imgPath)) {
                return WebUtils.buildResponseMessage(ResponseStatus.IMG_NOTFOUND);
            }
            patientInfoVo.setSignUrl(imgPath);
        }*/
        //新增或修改基本信息、家庭信息
        //HospitalizedPatientInfoNew patientInfoNew = hospitalizedPatientInfoService.getByNoticeId(noticeId);
        HospitalizedPatientInfoNew1 patientInfoNew = null;
        if(patientInfoVo.getId() == null) {
            patientInfoNew = BeanCopierUtil.uniformCopy(patientInfoVo, HospitalizedPatientInfoNew1::new);
            patientInfoNew.setCreateTime(DateUtils.date2String(new Date(), DateUtils.DATE_TIME_FORMAT));
            patientInfoVo.setUpdateTime(new Date());
//            fetchNoticeInfoIntoPatientInfo(localNotice, patientInfoNew);
            hospitalizedService.createHospitalizedPatientInfo(patientInfoNew);
        } else {
            patientInfoNew = hospitalizedService.getHospitalizedPatientInfoById(patientInfoVo.getId().intValue());
            //noticeId不一样则新增一条数据
            if(StringUtils.isNotEmpty(patientInfoNew.getNoticeId()) && !patientInfoNew.getNoticeId().equals(noticeId)) {
                HospitalizedPatientInfoNew1 newPatientInfo = BeanCopierUtil.uniformCopy(patientInfoNew, HospitalizedPatientInfoNew1::new);
                newPatientInfo.setId(null);
                newPatientInfo.setNoticeId(noticeId);
                newPatientInfo.setCreateTime(DateUtils.date2String(new Date(), DateUtils.DATE_TIME_FORMAT));
                newPatientInfo.setUpdateTime(DateUtils.date2String(new Date(), DateUtils.DATE_TIME_FORMAT));
                hospitalizedService.createHospitalizedPatientInfo(newPatientInfo);
            } else {
                BeanCopierUtil.copyPropertiesWithoutNull(patientInfoVo, patientInfoNew);
                patientInfoNew.setUpdateTime(DateUtils.date2String(new Date(), DateUtils.DATE_TIME_FORMAT));
                hospitalizedService.updateHospitalizedPatientInfo(patientInfoNew);
            }
        }
        return WebUtils.buildSuccessResponseMessage(patientInfoNew.getId());
    }

    /**
     * 通过线下扫码进入身份较验
     * @param hosNo 住院号
     * @param birth 出生日期
     * @return
     */
    @PostMapping("/patient/verify")
    public ResponseMessage patientVerify(String hosNo, String birth) {
        if(StringUtils.isEmpty(hosNo, birth)) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        //通过住院号从本地查找最近一次入院采集的信息（由于调不通医院接口先查本地）
        HospitalizedPatientInfoNew1 patientInfo = hospitalizedService.getHospitalizedPatientInfoByHosNo(hosNo);
        if(patientInfo == null) {
            if(SysEnv.isCompanyEnv(address)) {
                logger.info("根据住院号{}无法在本地找到患者入院采集信息，开始模块调用医院接口获取患者信息", hosNo);
                patientInfo = hospitalizedService.mockPatientHospitalizedInfoNew("1308155064");
            } else if(SysEnv.isHisEnv(address)){
                logger.info("根据住院号{}无法在本地找到患者入院采集信息，开始调用医院接口获取患者信息", hosNo);
                patientInfo = hospitalizedService.getHospitalizedbyInpNo(hosNo); //调his接口
            }
            if(patientInfo == null) {
                logger.warn("根据住院号{}无法在医院找到患者信息，无法进行身份验证", hosNo);
                return WebUtils.buildSuccessResponseMessage(ResponseStatus.PATIENT_INFO_NOTFOUND);
            }
        }
        String patientBirthStr = patientInfo.getBirthday();
        if(StringUtils.isEmpty(patientBirthStr)) {
            logger.warn("患者出生日期为空");
            return WebUtils.buildResponseMessage(ResponseStatus.INVALID_BIRTH);
        }
        Date inputBirth = null;
        try {
            Date patientBirth = DateUtils.string2Date(patientBirthStr);
            inputBirth = DateUtils.string2Date(birth);
            LocalDate localDate1 = ZonedDateTime.ofInstant(patientBirth.toInstant(), ZoneId.systemDefault()).toLocalDate();
            LocalDate localDate2 = ZonedDateTime.ofInstant(inputBirth.toInstant(), ZoneId.systemDefault()).toLocalDate();
            if(localDate1.isEqual(localDate2) == false) {
                return WebUtils.buildResponseMessage(ResponseStatus.BIRTH_NOT_MATCH);
            }
        } catch (ParseException e) {
            logger.error("日期转换异常", e);
            return WebUtils.buildResponseMessage(ResponseStatus.INVALID_BIRTH);
        }
        String outPatientNo = patientInfo.getOutPatientNo();
        //查找最近一次病历采集返回页面
        long diffDays = DateUtils.getDiffDays(inputBirth);
        String childrenType = diffDays < 28 ? ChildrenType.NEWER.getValue() : ChildrenType.COMMON.getValue();
        if(childrenType.equals(ChildrenType.NEWER.getValue())) {
            HospitalizedNewIllChild hospitalizedNewIllChild = hospitalizedNewIllChildService.getLastByOutPatientNo(outPatientNo);
            return buildHospitalizedProgress("1", hosNo, childrenType, "0", patientInfo, hospitalizedNewIllChild, null);
        } else {
            HospitalizedCommonIllChild hospitalizedCommonIllChild = hospitalizedCommonIllChildService.getLastByOutPatientNo(outPatientNo);
            return buildHospitalizedProgress("1", hosNo, childrenType, "0", patientInfo, null, hospitalizedCommonIllChild);
        }
    }

    /**
     * 增加普通儿童病史信息
     *
     * @param commonIllChild
     * @param result
     * @return
     */
    @Repeat
    @PostMapping("/commomIllChild/save")
    public ResponseMessage saveCommomIllChild(HttpServletRequest request,@RequestBody @Validated({HospitalizedCommonIllChild.SaveGroup.class}) HospitalizedCommonIllChild commonIllChild, BindingResult result){
        if (result.hasErrors()) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        String noticeId = commonIllChild.getNoticeId();
        //在病区扫码采集病史时没有noticeId
        if(StringUtils.isEmpty(noticeId)) {
            Long id = commonIllChild.getId();
            if(id == null) {
                commonIllChild.setCreateTime(new Date());
                commonIllChild.setUpdateTime(new Date());
                hospitalizedCommonIllChildService.save(commonIllChild);
            } else {
                commonIllChild.setUpdateTime(new Date());
                HospitalizedCommonIllChild param = new HospitalizedCommonIllChild();
                param.setId(id);
                HospitalizedCommonIllChild localCommonIllChild = hospitalizedCommonIllChildService.queryById(param);
                if(localCommonIllChild == null) {
                    logger.error("普通患儿{}在本地不存在，无法保存病史", id);
                    return WebUtils.buildResponseMessage(ResponseStatus.INVALID_VALUE);
                }
                BeanCopierUtil.copyPropertiesWithoutNull(commonIllChild, localCommonIllChild);
                hospitalizedCommonIllChildService.modify(localCommonIllChild);
            }
            logger.info("在病区扫码采集病史完成");
        } else {
            //公众号消息采集病史
            HospitalizedNoticeNew localNotice = hospitalizedService.getHospitalizedNoticeFromLocal(noticeId);
            if(localNotice == null) {
                logger.error("住院通知书{}不存在", noticeId);
                return WebUtils.buildResponseMessage(ResponseStatus.NOTICE_NOTFOUND);
            }
            HospitalizedCommonIllChild hospitalizedCommonIllChild = hospitalizedCommonIllChildService.getByNoticeId(noticeId);
            if (null != hospitalizedCommonIllChild) {
                commonIllChild.setId(hospitalizedCommonIllChild.getId());
                commonIllChild.setUpdateTime(new Date());
                Long sqlResult = hospitalizedCommonIllChildService.modify(commonIllChild);
                if (sqlResult < 0) {
                    return WebUtils.buildResponseMessage(ResponseStatus.FAIL);
                }
            } else {
                commonIllChild.setCreateTime(new Date());
                commonIllChild.setUpdateTime(new Date());
                Long sqlResult = hospitalizedCommonIllChildService.save(commonIllChild);
                if (sqlResult < 0) {
                    return WebUtils.buildResponseMessage(ResponseStatus.FAIL);
                }
            }
        }
        return WebUtils.buildSuccessResponseMessage();
    }

    /**
     * 增加新生儿病史信息
     *
     * @param newIllChild
     * @param result
     * @return
     */
    @Repeat
    @PostMapping("/newIllChild/save")
    public ResponseMessage saveNewIllChild(HttpServletRequest request,@RequestBody @Validated({HospitalizedNewIllChild.SaveGroup.class}) HospitalizedNewIllChild newIllChild, BindingResult result) {
        if (result.hasErrors()) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        String noticeId = newIllChild.getNoticeId();
        //在病区扫码采集病史时没有noticeId
        if(StringUtils.isEmpty(noticeId)) {
            Long id = newIllChild.getId();
            if(id == null) {
                newIllChild.setCreateTime(new Date());
                newIllChild.setUpdateTime(new Date());
                hospitalizedNewIllChildService.save(newIllChild);
            } else {
                newIllChild.setUpdateTime(new Date());
                HospitalizedNewIllChild param = new HospitalizedNewIllChild();
                param.setId(id);
                HospitalizedNewIllChild localNewerIllChild = hospitalizedNewIllChildService.queryById(param);
                if(localNewerIllChild == null) {
                    logger.error("新生儿{}在本地不存在，无法保存新生儿的病史", id);
                    return WebUtils.buildResponseMessage(ResponseStatus.INVALID_VALUE);
                }
                BeanCopierUtil.copyPropertiesWithoutNull(newIllChild, localNewerIllChild);
                hospitalizedNewIllChildService.modify(localNewerIllChild);
            }
            logger.info("在病区扫码采集病史完成");
        } else {
            HospitalizedNoticeNew localNotice = hospitalizedService.getHospitalizedNoticeFromLocal(noticeId);
            if(localNotice == null) {
                logger.error("住院通知书{}不存在", noticeId);
                return WebUtils.buildResponseMessage(ResponseStatus.NOTICE_NOTFOUND);
            }
            HospitalizedNewIllChild hospitalizedNewIllChild = hospitalizedNewIllChildService.getByNoticeId(noticeId);
            if (null != hospitalizedNewIllChild) {
                newIllChild.setId(hospitalizedNewIllChild.getId());
                newIllChild.setUpdateTime(new Date());
                Long sqlResult = hospitalizedNewIllChildService.modify(newIllChild);
                if (sqlResult < 0) {
                    return WebUtils.buildResponseMessage(ResponseStatus.FAIL);
                }
            } else {
                newIllChild.setCreateTime(new Date());
                newIllChild.setUpdateTime(new Date());
                Long sqlResult = hospitalizedNewIllChildService.save(newIllChild);
                if (sqlResult < 0) {
                    return WebUtils.buildResponseMessage(ResponseStatus.FAIL);
                }
            }
        }
        return WebUtils.buildSuccessResponseMessage();
    }

    /**
     * 从本地或医院获取住院通知书
     * @param outPatientNo
     * @param noticeId
     * @return
     */
    private HospitalizedNoticeNew getHospitalizedNotice(String outPatientNo, String noticeId, ResponseStatus responseStatus) {
        //从本地获取住院通知书，本地没有则从儿童医院获取住
        HospitalizedNoticeNew hospitalizedNotice = hospitalizedService.getHospitalizedNoticeFromLocal(noticeId);
        if(hospitalizedNotice == null) {
            if(SysEnv.isCompanyEnv(address)) {
                logger.info("本地住院通知单{}为空，开始模拟调用医院接口获取住院通知单", noticeId);
                hospitalizedNotice = hospitalizedService.mockHospitalizedNotice(outPatientNo);
            } else if (SysEnv.isHisEnv(address)) {
                logger.info("本地住院通知单{}为空，开始调用医院接口获取住院通知单", noticeId);
                hospitalizedNotice = hospitalizedService.getHospitalizedNotice(noticeId,outPatientNo);
            }
        }
        if(hospitalizedNotice == null) {
            logger.warn("门诊号{}在医院无住院通知书", outPatientNo);
            responseStatus = ResponseStatus.NOTICE_NOTFOUND;
            return null;
        } else {
            if(StringUtils.isEmpty(hospitalizedNotice.getNoticeId())) {
                hospitalizedNotice.setNoticeId(noticeId);
                hospitalizedNotice.setUpdateTime(DateUtils.date2String(new Date(), DateUtils.DATE_TIME_FORMAT));
                hospitalizedService.updateHospitalizedNotice(hospitalizedNotice);
                logger.info("更新入院通知书的通知单编码为{}", noticeId);
            } else if(!hospitalizedNotice.getNoticeId().equals(noticeId)) {
                logger.error("通知单的编码{}与公众号的通知单编码{}不一致", hospitalizedNotice.getNoticeId(), noticeId);
                responseStatus = ResponseStatus.NOTICE_ID_ERROR;
            }
        }
        return hospitalizedNotice;
    }

    /**
     *
     * @param patientInfoFinish 入院信息采集是否已完成
     * @param hosNo 住院号
     * @param childrenType 新生儿、普通患儿
     * @param illHistoryFinish 病史采集是否已完成
     * @param hospitalizedPatientInfoNew1 医院传过来的住院信息
     * @return
     */
    private ResponseMessage buildHospitalizedProgress(String patientInfoFinish, String hosNo, String childrenType, String illHistoryFinish, HospitalizedPatientInfoNew1 hospitalizedPatientInfoNew1,
                                                      HospitalizedNewIllChild newIllChild, HospitalizedCommonIllChild commonIllChild) {
        Map<String, Object> result = new HashMap<>();
        result.put("patientInfoFinish", patientInfoFinish);
        result.put("hosNo", hosNo);
        result.put("illHistoryFinish", illHistoryFinish);
        result.put("childrenType", childrenType);
        result.put("hospitalizedPatientInfo", hospitalizedPatientInfoNew1);
        result.put("newIllChild", newIllChild);
        result.put("commonIllChild", commonIllChild);
        return WebUtils.buildSuccessResponseMessage(result);
    }

    private void fetchNoticeInfoIntoPatientInfo(HospitalizedNoticeNew hospitalizedNotice, HospitalizedPatientInfoNew1 patientInfo) {
        if(StringUtils.isEmpty(patientInfo.getPatientName()))
            patientInfo.setPatientName(hospitalizedNotice.getPatientName());
        if(patientInfo.getSex() == null)
            patientInfo.setSex(hospitalizedNotice.getSex());
        if(StringUtils.isEmpty(patientInfo.getBirthday()))
            patientInfo.setBirthday(hospitalizedNotice.getBirthday());
    }
}