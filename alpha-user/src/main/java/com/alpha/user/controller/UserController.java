package com.alpha.user.controller;

import com.alibaba.fastjson.JSON;
import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.mapper.OpenDepartmentMapper;
import com.alpha.commons.core.pojo.DepartMent;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.core.pojo.YwzCountTimes;
import com.alpha.commons.core.service.YwzCountTimesService;
import com.alpha.commons.core.util.DeptUtils;
import com.alpha.commons.enums.AppType;
import com.alpha.commons.enums.SysEnv;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import com.alpha.his.service.etyy.HospitalizedService;
import com.alpha.his.service.etyy.RegisterService;
import com.alpha.redis.RedisMrg;
import com.alpha.server.rpc.user.pojo.HisRegisterInfo;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.server.rpc.user.pojo.UserMember;
import com.alpha.user.pojo.vo.*;
import com.alpha.user.service.UserInfoService;
import com.alpha.user.service.UserMemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
public class UserController {

    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserMemberService userMemberService;
    @Resource
    private HospitalizedService hospitalizedService;
    @Resource
    private OpenDepartmentMapper openDepartmentMapper;
    @Resource
    private RegisterService registerService;
    @Autowired
    YwzCountTimesService ywzCountTimesService;
    @Resource
    private HisRegisterYyghMapper hisRegisterYyghMapper;

    @Value("${address}")
    private String address;

    @Value("${versionFlag}")
    private String versionFlag;

    @Value("${redis.ip}")
    private String redisIp;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.pwd}")
    private String redisPwd;

    @Value("${hospital.code}")
    private String defaultHospitalCode;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 问诊前授权信息，目前没有做限制，只要有userinfo.externalUserId 都能授权成功
     *
     * @param userInfo
     * @param inType
     * @param isself   是否本人问诊
     * @return 返回问诊信息，用户编号，用户对象
     */
    @PostMapping("/auth")
    public ResponseMessage authorization(String userInfo, Integer inType, Integer isself) {

//		Map<String, String[]> map = request.getParameterMap();
//		logger.info("为用户授权，获取用户信息，渠道编号: {} ", JSON.toJSONString(map));
        logger.info("为用户授权，获取用户信息，渠道编号: {} ,{}", inType, userInfo);
        try {
            if (inType == null || inType == 0 || userInfo == null) {
                return new ResponseMessage(ResponseStatus.INVALID_VALUE.code(), "授权信息不完整,服务器已拒绝");
            }
            ObjectMapper mapper = new ObjectMapper();
            Gson gson = new Gson();
//			UserInfo user = mapper.readValue(userInfo, UserInfo.class);
            UserInfo user = gson.fromJson(userInfo, UserInfo.class);
            if (StringUtils.isEmpty(user.getExternalUserId())) {
                return new ResponseMessage(ResponseStatus.INVALID_VALUE.code(), "授权信息不完整,服务器已拒绝");
            }
            //获取用户信息
            //如果存在用户信息，返回用户信息
            //如果不存在，生成一个临时的用户，返回UserId
            user = userInfoService.createOrUpdateUserInfo(user, inType);
//			logger.info("用户信息,{},{}",inType, JSON.toJSON(user));
            return new ResponseMessage(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseMessage(ResponseStatus.EXCEPTION);
        }
    }

    /**
     * 获取用户/用户成员列表
     *
     * @return
     */
    @PostMapping("/list")
    public ResponseMessage list(String externalUserId, int inType) {
        if (StringUtils.isEmpty(externalUserId)) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        List<HisUserInfoVo> userList = new ArrayList<>();
        //UserInfo userInfo = userInfoService.queryByExternalUserId(externalUserId, inType);
        UserInfo userInfo = userInfoService.getSelfUserInfoByExternalUserId(externalUserId);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setUserName("自己");
            userInfo.setExternalUserId(externalUserId);
            userInfo = userInfoService.create(userInfo);
            HisUserInfoVo hisUserInfoVo = new HisUserInfoVo(userInfo, null);
            hisUserInfoVo.setSelf("Y");
            userList.add(hisUserInfoVo);

            return WebUtils.buildSuccessResponseMessage(userList);
        }
        userList = userInfoService.list(userInfo.getUserId());
        return WebUtils.buildSuccessResponseMessage(userList);
    }

    /**
     * 调用HIS接口查询用户挂号信息是否存在
     *
     * @return
     */

    @PostMapping("/registerInfo/query/{outPatientNo}")
    public ResponseMessage queryRegisterInfoFromHis(@PathVariable String outPatientNo) {
        if (com.alpha.commons.util.StringUtils.isEmpty(outPatientNo)) {
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        logger.info("env address is {}", address);
        UserInfo userInfo = null;
        if (address.equals(SysEnv.HIS_ONLINE.getValue()) || address.equals(SysEnv.HIS_TEST.getValue())) {
            userInfo = userInfoService.getUserFromLocalOrPullFromHis(outPatientNo);
        } else { //其他的都不是连儿童医院地址，直接查本地库
            userInfo = userInfoService.queryUserInfoFromLocal(outPatientNo);
            if (userInfo == null) {
                userInfo = userInfoService.createUserInfoByHisRegisterHryy(outPatientNo);
            }
        }
        if (userInfo == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.OUT_PATIENT_NO_NOT_FOUND);
        }
        /*if(appType == AppType.WOMAN) {
            if(userInfo.getGender() == null || userInfo.getGender() != 1) {
                return WebUtils.buildResponseMessage(ResponseStatus.GENDER_NOT_MATCH);
            }
        }*/
        String birth = "";
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId", userInfo.getUserId());
        resultMap.put("idcard", userInfo.getOutPatientNo());

        if (userInfo.getBirth() != null) {
            List<Integer> dayRangeList = new ArrayList<>();
            birth = DateUtils.getBirthDayRange(userInfo.getBirth(), dayRangeList);
        }
        resultMap.put("birth", birth);

        YwzCountTimes ywzCountTimes = new YwzCountTimes();
        ywzCountTimes.setType(3);
        ywzCountTimes.setDescri(outPatientNo);
        ywzCountTimesService.addTimes(ywzCountTimes);
        return WebUtils.buildSuccessResponseMessage(resultMap);
    }

    /**
     * 调用第三方接口查询患者信息
     *
     * @param
     * @return
     */
    @PostMapping("/query")
    public ResponseMessage query(HisUserInfoVo vo) {
        String hospitalCode = vo.getHospitalCode();
        String outPatientNo = vo.getIdcard();
        Long userId = vo.getUserId();
        String birth = vo.getBirth();
        if (StringUtils.isEmpty(hospitalCode)) {
            hospitalCode = defaultHospitalCode;
        }
        logger.info("idcard is {}, userId is {}, birth is {}", outPatientNo, userId, birth);
        UserInfo userInfo = userInfoService.queryByUserId(userId);
        if (userInfo == null) {
            return WebUtils.buildResponseMessage(ResponseStatus.USER_NOT_FOUND);
        }
        //公司准生产环境不验证生日
        if (SysEnv.COM_PPE.getValue().equals(address) && StringUtils.isNotEmpty(birth)) {
            try {
                if (!SysEnv.F_BJ_FK.getValue().equals(versionFlag)) {
                    Date birthDay = DateUtils.string2Date(birth);
                    float age = DateUtils.getAge(birthDay);
                    //只有儿科不能超过18岁
                    if (age > 18) {
                        LocalDate maxAge = LocalDate.now().plusYears(-18);
                        birth = maxAge.toString();
                    }
                }
                userInfo.setBirth(DateUtils.string2Date(birth));
                userInfoService.updateUserInfo(userInfo);
            } catch (ParseException e) {
                e.printStackTrace();
                return WebUtils.buildResponseMessage(ResponseStatus.INVALID_BIRTH);
            }
        } else {
            Set<String> userBirthSet = userInfoService.listUserBirthDay(outPatientNo);
            if (CollectionUtils.isNotEmpty(userBirthSet)) {
                if (StringUtils.isNotEmpty(birth) && !userBirthSet.contains(birth)) {
                    logger.warn("出生日期验证不通过");
                    return WebUtils.buildResponseMessage(ResponseStatus.BIRTH_NOT_MATCH);
                }
                if(StringUtils.isNotEmpty(vo.getSystemType()) && vo.getSystemType().equals(AppType.CHILD.getValue())) {
                    Date birthDay = null;
                    try {
                        birthDay = DateUtils.string2Date(birth);
                        float age = DateUtils.getAge(birthDay);
                        if (age > 18) {
                            return WebUtils.buildResponseMessage(ResponseStatus.AGE_NOT_MATCH);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return WebUtils.buildResponseMessage(ResponseStatus.INVALID_BIRTH);
                    }
                }
            }
        }
        HisUserInfoVo hisUserInfo = userInfoService.listRegisterInfoFromLocalAndHis(hospitalCode, vo.getIdcard(), vo.getUserId());
        //过滤科室
        filer(hisUserInfo);
        return WebUtils.buildSuccessResponseMessage(hisUserInfo);
    }

    /**
     * 保存用户信息
     *
     * @param
     * @return
     */
    @PostMapping("/save")
    public ResponseMessage saveUserInfo(String allParam) {
        UserInfoRequestVo userVo = JSON.parseObject(allParam, UserInfoRequestVo.class);
        MemberInfoVo memberInfo = userVo.getMemberInfo();

        //为他人问诊
        if (memberInfo != null) {
            Long userId = memberInfo.getUserId();
            String memberName = memberInfo.getMemberName();
            if (userId == null || StringUtils.isEmpty(memberName)) {
                return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
            }
            //判断用户成员是否超过5个
            List<UserMember> memberList = userMemberService.listByUserId(userId);
            if (CollectionUtils.isNotEmpty(memberList) && memberList.size() >= 5) {
                return WebUtils.buildResponseMessage(ResponseStatus.USER_MEMBER_FULL);
            }
            //判断用户成员是否已存在
            memberList = userMemberService.listByUserIdAndMemberName(userId, memberName);
            if (CollectionUtils.isNotEmpty(memberList)) {
                return WebUtils.buildResponseMessage(ResponseStatus.USER_EXISTED);
            }
            //创建用户成员
            HisUserInfoVo hisUserInfo = userInfoService.saveUserMember(userId, memberName);
            return WebUtils.buildSuccessResponseMessage(hisUserInfo);
        } else {
            SaveUserInfoVo userInfo = userVo.getUserInfo();
            OtherHospitalInfo hospitalInfo = userVo.getOtherHospitalInfo();
            PresentIllnessVo presentIllness = userVo.getPresentIllness();

            userInfoService.save(userVo.getDiagnosisId(), userInfo, presentIllness, hospitalInfo, userVo.getInType());

            /*ThreadPoolScheduler.addTask(()->{
                userInfoService.save(userVo.getDiagnosisId(), userInfo, presentIllness, hospitalInfo, userVo.getInType());
            });*/
            return WebUtils.buildSuccessResponseMessage();
        }
    }

    /**
     * 扫码获取门诊号   预问诊
     *
     * @param cardNo
     */
    @GetMapping("/outPatientNum")
    public ResponseMessage outPatientNum(String cardNo) {
        logger.info("查询卡号为{}的门诊号" + cardNo);

        if (org.apache.commons.lang3.StringUtils.isBlank(cardNo)) {
            return WebUtils.buildResponseMessage(ResponseStatus.INVALID_VALUE);
        }
        String outpatientno = null;
        try {
            outpatientno = hospitalizedService.outPatientNum(cardNo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询扫码卡号的门诊号异常");
        }
        return WebUtils.buildSuccessResponseMessage(outpatientno);

    }

    private void filer(HisUserInfoVo hisUserInfo) {

        List<String> listpartment = DeptUtils.getDepartment();
        List<HisRegisterInfo> list = hisUserInfo.getHisDepartmentList();
        List<HisRegisterInfo> listnew = new ArrayList<>();
        if (list.size() > 0) {
            String filterDepartment = "";
            for (int i = 0; i < list.size(); i++) {
                if (checkPartment(list.get(i).getDepartment(), listpartment)) {
                    listnew.add(list.get(i));
                } else {
                    filterDepartment += "【" + list.get(i).getDepartment() + "】 ";
                }
            }
            hisUserInfo.setFilterDepartment(filterDepartment);
            hisUserInfo.setHisDepartmentList(listnew);
        }
    }

    public boolean checkPartment(String partment, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            if (partment.contains(list.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 远程增加开放科室
     * @param partment
     * @return
     */
    @PostMapping("/addPartment")
    public ResponseMessage addPartment(String partment){
        if(!org.apache.commons.lang3.StringUtils.isBlank(partment)){
            logger.info("开放科室：》》"+partment);
            List<String> listpartment=new ArrayList<>();
            listpartment.add(partment);
            RedisMrg.getInstance(redisIp, redisPort, redisPwd).setSetString(listpartment, "partmentlist", 13);
            DepartMent departMent=new DepartMent();
            departMent.setName(partment);
            openDepartmentMapper.insert(departMent);
           return WebUtils.buildSuccessResponseMessage();
        }
        return WebUtils.buildResponseMessage(ResponseStatus.INVALID_VALUE);
    }

    @GetMapping("/allPartment")
    public ResponseMessage allPartment(){
            List<DepartMent> list=openDepartmentMapper.selectAll();
            logger.info("所有科室："+list.toString());
           return WebUtils.buildSuccessResponseMessage(list);
    }

    @PostMapping("/delPartment")
    public ResponseMessage delPartment(String partment){
        logger.info("删除科室：》》"+partment);
        DepartMent departMent=new DepartMent();
        departMent.setName(partment);
        openDepartmentMapper.delete(departMent);
        RedisMrg.getInstance(redisIp, redisPort, redisPwd).delSetValue("partmentlist",partment, 13);
        return WebUtils.buildSuccessResponseMessage();

    }

    /**
     * 获取当天预问诊测试号
     */
    @GetMapping("/getTestAccount")
    public ResponseMessage getTestAccount(){
        String  datenow = sdf.format(new Date());
        List<HisRegisterYygh> list = hisRegisterYyghMapper.getTestAccount(datenow);
        return WebUtils.buildSuccessResponseMessage(list);
    }

}
