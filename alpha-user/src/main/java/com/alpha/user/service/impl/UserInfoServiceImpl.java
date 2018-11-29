package com.alpha.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.core.pojo.RegisterDTO;
import com.alpha.commons.core.service.SysSequenceService;
import com.alpha.commons.enums.*;
import com.alpha.commons.util.BeanCopierUtil;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.his.dao.HisRegisterRecordDao;
import com.alpha.his.mapper.HospitalDeptMapper;
import com.alpha.his.service.etyy.HospitalService;
import com.alpha.his.service.etyy.RegisterService;
import com.alpha.server.rpc.diagnosis.pojo.HospitalDept;
import com.alpha.server.rpc.his.pojo.HisRegisterRecord;
import com.alpha.server.rpc.user.pojo.HisRegisterInfo;
import com.alpha.server.rpc.user.pojo.UserBasicRecord;
import com.alpha.server.rpc.user.pojo.UserInfo;
import com.alpha.server.rpc.user.pojo.UserMember;
import com.alpha.user.dao.DiagnosisMedicalTemplateDao;
import com.alpha.user.dao.UserBasicRecordDao;
import com.alpha.user.dao.UserInfoDao;
import com.alpha.user.pojo.DiagnosisMedicalTemplate;
import com.alpha.user.pojo.vo.*;
import com.alpha.user.service.MedicalRecordService;
import com.alpha.user.service.UserBasicRecordService;
import com.alpha.user.service.UserInfoService;
import com.alpha.user.service.UserMemberService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private SysSequenceService sysSequenceService;
	@Resource
	private UserBasicRecordDao userBasicRecordDao;

	@Resource
	private UserBasicRecordService userBasicRecordService;

	@Resource
	private MedicalRecordService medicalRecordService;

	@Resource
	private DiagnosisMedicalTemplateDao diagnosisMedicalTemplateDao;

	@Resource
	private UserMemberService userMemberService;

	@Resource
	private HospitalService hospitalService;

	@Resource
	private RegisterService registerService;

	@Resource
	private HisRegisterYyghMapper hisRegisterYyghMapper;
	@Resource
	private HisRegisterRecordDao hisRegisterRecordDao;
	@Resource
	private HospitalDeptMapper hospitalDeptMapper;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public UserInfo create(UserInfo userInfo) {
		Long userId = sysSequenceService.getNextSequence("user_seq").longValue();
		userInfo.setUserId(userId);
		//userInfo.setExternalUserId(String.valueOf(userId));
		userInfo.setCreateTime(new Date());
		userInfo.setLastUpdateTime(new Date());
		userInfoDao.insert(userInfo);
		return userInfo;
	}

	/**
	 * 根据第三方用户编号，渠道编号获取用户信息
	 * 如果没有用户信息，则创建一个新的用户
	 *
	 * @param userInfo
	 * @param inType
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public UserInfo createOrUpdateUserInfo(UserInfo userInfo, int inType) {
		//UserInfo user = userInfoDao.getUserInfoByExternalUserId(userInfo.getExternalUserId(), inType);
		//UserInfo user = userInfoDao.getUserInfoByExternalUserId(userInfo.getExternalUserId());
		List<UserInfo> userList = this.listByExternalUserId(userInfo.getExternalUserId());

		//if (user == null || user.getUserId() == 0) {
		if (CollectionUtils.isEmpty(userList)) {
			userInfo.setUserId(sysSequenceService.getNextSequence("user_seq").longValue());
			userInfo.setInType(inType);
			userInfo.setCreateTime(new Date());
			userInfo.setLastUpdateTime(new Date());
			userInfoDao.insert(userInfo);
			return userInfo;
		}
		return updateUserInfo(userInfo);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public UserInfo updateUserInfo(UserInfo userInfo) {
		UserInfo user = userInfoDao.queryByUserId(userInfo.getUserId());
		user.setLastUpdateTime(new Date());
		if (StringUtils.isNotEmpty(userInfo.getUserName()))
			user.setUserName(userInfo.getUserName());
		if (userInfo.getBirth() != null)
			user.setBirth(userInfo.getBirth());

		if (userInfo.getGender() != null)
			user.setGender(userInfo.getGender());

		if (StringUtils.isNotEmpty(userInfo.getIdcard()))
			user.setIdcard(userInfo.getIdcard());

		if (StringUtils.isNotEmpty(userInfo.getPhoneNumber()))
			user.setPhoneNumber(userInfo.getPhoneNumber());

		if (StringUtils.isNotEmpty(userInfo.getLiverFuncText()))
			user.setLiverFuncText(userInfo.getLiverFuncText());

		if (userInfo.getLiverFunc() != null)
			user.setLiverFunc(userInfo.getLiverFunc());

		if (StringUtils.isNotEmpty(userInfo.getRenalFuncText()))
			user.setRenalFuncText(userInfo.getRenalFuncText());

		if (userInfo.getRenalFunc() != null)
			user.setRenalFunc(userInfo.getRenalFunc());

		//if (StringUtils.isNotEmpty(userInfo.getWeight()))
			user.setWeight(userInfo.getWeight());

		//if (StringUtils.isNotEmpty(userInfo.getHeight()))
			user.setHeight(userInfo.getHeight());

		if (StringUtils.isNotEmpty(userInfo.getSpecialPeriod()))
			user.setSpecialPeriod(userInfo.getSpecialPeriod());

		if (StringUtils.isNotEmpty(userInfo.getFertilityType()))
			user.setFertilityType(userInfo.getFertilityType());

		if (StringUtils.isNotEmpty(userInfo.getGestationalAge()))
			user.setGestationalAge(userInfo.getGestationalAge());

		if (StringUtils.isNotEmpty(userInfo.getFeedType()))
			user.setFeedType(userInfo.getFeedType());

		//if (StringUtils.isNotEmpty(userInfo.getMenstrualPeriod()))
		user.setMenstrualPeriod(userInfo.getMenstrualPeriod());

		//if (StringUtils.isNotEmpty(userInfo.getPastmedicalHistoryCode()))
		user.setPastmedicalHistoryCode(userInfo.getPastmedicalHistoryCode());

		//if (StringUtils.isNotEmpty(userInfo.getPastmedicalHistoryText()))
		user.setPastmedicalHistoryText(userInfo.getPastmedicalHistoryText());

		if (StringUtils.isNotEmpty(userInfo.getAllergicHistoryCode()))
			user.setAllergicHistoryCode(userInfo.getAllergicHistoryCode());

		if (StringUtils.isNotEmpty(userInfo.getAllergicHistoryText()))
			user.setAllergicHistoryText(userInfo.getAllergicHistoryText());

		if (StringUtils.isNotEmpty(userInfo.getOperationCode()))
			user.setOperationCode(userInfo.getOperationCode());

		if (StringUtils.isNotEmpty(userInfo.getOperationText()))
			user.setOperationText(userInfo.getOperationText());

		if (StringUtils.isNotEmpty(userInfo.getVaccinationHistoryCode()))
			user.setVaccinationHistoryCode(userInfo.getVaccinationHistoryCode());

		userInfoDao.updateEntity(user);
		return user;
	}

	@Override
	public UserInfo queryByUserId(Long userId) {
		return userInfoDao.queryByUserId(userId);
	}

	@Override
	public UserInfo getByOutPatientNo(String outPatientNo) {
		return userInfoDao.getByOutPatientNo(outPatientNo);
	}

	@Override
	public List<UserInfo> listByExternalUserId(String externalUserId) {
		return userInfoDao.listByExternalUserId(externalUserId);
	}

	@Override
	public UserInfo getSelfUserInfoByExternalUserId(String externalUserId) {
		List<UserInfo> userList = this.listByExternalUserId(externalUserId);
		if(CollectionUtils.isEmpty(userList)) {
			return null;
		}
		if(userList.size() == 1) {
			return userList.get(0);
		} else {
			for(UserInfo user : userList) {
				List<UserMember> memberList = userMemberService.listByUserId(user.getUserId());
				if(CollectionUtils.isNotEmpty(memberList)) {
					return user;
				}
			}
			logger.warn("根据externalUserId找到多个用户，但无法得知主用户");
			return null;
		}
		/*userList = userList.stream().filter(e->GlobalConstants.SELF.equals(e.getUserName())).collect(Collectors.toList());
		if(CollectionUtils.isNotEmpty(userList)) {
			return userList.get(0);
		} else {
			return null;
		}*/
	}

	/**
	 * 保存用户信息
	 * @param diagnosisId	诊断ID
	 * @param userInfo		用户基础信息
	 * @param presentIllness 现病史信息
	 * @param hospitalInfo	其它医院就诊信息
	 * @param inType
	 * @return
	 */
	@Override
	public UserInfo save(Long diagnosisId, SaveUserInfoVo userInfo, PresentIllnessVo presentIllness, OtherHospitalInfo hospitalInfo, int inType) {
		//更新用户信息
		if (userInfo != null) {
			String hisRegisterNo = userInfo.getHisRegisterNo();
			//既往史
			List<DiseaseHistoryVo> pastmedicalHistoryList = userInfo.getPastmedicalHistory();
			//妇科版月经史
			MenstruationVo menstruationVo = userInfo.getMenstruation();
			//妇科版婚育史
			MarryVo marryVo = userInfo.getMarry();

			String pastMedicalHistoryCodes = "";
			String pastMedicalHistoryNames = "";
			if (CollectionUtils.isNotEmpty(pastmedicalHistoryList)) {
				pastMedicalHistoryCodes = pastmedicalHistoryList.stream().map(DiseaseHistoryVo::getAnswerValue).collect(Collectors.joining(","));
				pastMedicalHistoryNames = pastmedicalHistoryList.stream().map(DiseaseHistoryVo::getAnswerTitle).collect(Collectors.joining(","));

				userInfo.setPastmedicalHistoryCode(pastMedicalHistoryCodes);
				userInfo.setPastmedicalHistoryText(pastMedicalHistoryNames);
			}
			//过敏史
			String allergicHistoryCodes = "";
			String allergicHistoryNames = "";
			List<DiseaseHistoryVo> allergicHistoryList = userInfo.getAllergicHistory();
			if (CollectionUtils.isNotEmpty(allergicHistoryList)) {
				allergicHistoryCodes = allergicHistoryList.stream().map(DiseaseHistoryVo::getAnswerValue).collect(Collectors.joining(","));
				allergicHistoryNames = allergicHistoryList.stream().map(DiseaseHistoryVo::getAnswerTitle).collect(Collectors.joining(","));
				userInfo.setAllergicHistoryCode(allergicHistoryCodes);
				userInfo.setAllergicHistoryText(allergicHistoryNames);
			}
			UserInfo ui = new UserInfo();
			BeanCopierUtil.copy(userInfo, ui);
			updateUserInfo(ui);

			if(diagnosisId != null) {
				UserBasicRecord record = userBasicRecordService.findByDiagnosisId(diagnosisId);
				record.setPastmedicalHistoryCode(pastMedicalHistoryCodes);
				record.setPastmedicalHistoryText(pastMedicalHistoryNames);
				record.setAllergicHistoryCode(allergicHistoryCodes);
				record.setAllergicHistoryText(allergicHistoryNames);
				record.setMenstrualPeriod(userInfo.getMenstrualPeriod());

				String specialPeriod = StringUtils.isNotEmpty(userInfo.getSpecialPeriod()) ? WomenSpecialPeriod.getText(Integer.parseInt(userInfo.getSpecialPeriod())) : "";
				record.setSpecialPeriod(specialPeriod);

				String fertilityType = StringUtils.isNotEmpty(userInfo.getFertilityType()) ? FertilityType.getText(Integer.parseInt(userInfo.getFertilityType())) : "";
				record.setFertilityType(fertilityType);

				String gestationalAge = StringUtils.isNotEmpty(userInfo.getGestationalAge()) ? GestationalAge.getText(Integer.parseInt(userInfo.getGestationalAge())) : "";
				record.setGestationalAge(gestationalAge);

				String feedType = StringUtils.isNotEmpty(userInfo.getFeedType()) ? FeedType.getText(Integer.parseInt(userInfo.getFeedType())) : "";
				record.setFeedType(feedType);

				String vaccinationHistoryText = StringUtils.isNotEmpty(userInfo.getVaccinationHistoryCode()) ? VaccinationHistory.getText(Integer.parseInt(userInfo.getVaccinationHistoryCode())) : "";
				record.setVaccinationHistoryText(vaccinationHistoryText);

				record.setUpdateTime(new Date());
				userBasicRecordService.updateUserBasicRecord(record);
			}

			if (menstruationVo != null ||  marryVo != null) {
				UserBasicRecord record = userBasicRecordService.findByDiagnosisId(diagnosisId);
				if(menstruationVo != null) {
					BeanCopierUtil.copy(menstruationVo, record);
				}
				if(marryVo != null) {
					BeanCopierUtil.copy(marryVo, record);
				}
				record.setUpdateTime(new Date());
				userBasicRecordService.updateUserBasicRecord(record);
			}
		}
		//更新现病史
		if(presentIllness != null) {
			if(diagnosisId != null) {
				updateUserBasicRecord(diagnosisId, presentIllness);
			}
		}
		//更新到其它医院的就诊记录
		if (hospitalInfo != null) {
			if(diagnosisId != null) {
				updateUserBasicRecord(diagnosisId, hospitalInfo);
			}
		}

		return null;
	}

	@Override
	public UserInfo queryByUserIdAndUserName(Long userId, String userName) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("userName", userName);
		List<UserInfo> list = userInfoDao.query(param);
		UserInfo userInfo = null;
		if (CollectionUtils.isNotEmpty(list)) {
			userInfo = list.get(0);
		}
		return userInfo;
	}


	//v1.1.0方法

	/**
	 * 由于从1.0.0开启项目所有地方都围绕着userId,对接后才知道userId没有用，后期优化把user_info表删掉
	 * @param outPatientNo
	 * @return
	 */
	@Override
	public UserInfo getUserFromLocalOrPullFromHis(String outPatientNo) {
		UserInfo userInfo = this.queryUserInfoFromLocal(outPatientNo);
		if (userInfo != null) {
			return userInfo;
		}
		//从预约表创建用户
		userInfo = createUserInfoByHisRegisterHryy(outPatientNo);
		//调用医院接口创建用户
		if(userInfo == null) {
			//医院当天的挂号记录
			String visitTime = DateUtils.date2String(new Date(), DateUtils.DATE_FORMAT);
			List<RegisterDTO> hisRegistList = registerService.getUserRegisterInfo(outPatientNo,visitTime);
			if(CollectionUtils.isEmpty(hisRegistList)) {
				return null;
			}
			userInfo = this.parseFromRegisterInfo(hisRegistList);
			if (StringUtils.isEmpty(userInfo.getOutPatientNo())) {
				userInfo.setOutPatientNo(outPatientNo);
			}
			userInfo = create(userInfo);
		}
		return userInfo;
	}

	@Override
	public UserInfo queryUserInfoFromLocal(String outPatientNo) {
		return userInfoDao.getByOutPatientNo(outPatientNo);
	}

	@Override
	public HisUserInfoVo listRegisterInfoFromLocalAndHis(String hospitalCode, String outPatientNo, Long userId) {
		UserInfo userInfo = queryByUserId(userId);
		List<HisRegisterRecord> newHisDiagnosisRecordList = null;
		//根据门诊号从预约临时表取出今天及以后的预约/挂号记录存到真实挂号表
		HisRegisterYygh param = new HisRegisterYygh();
		param.setOutPatientNo(outPatientNo);
		List<HisRegisterYygh> hisRegisterYyghList = hisRegisterYyghMapper.listPatientRegisters(param);
		if(CollectionUtils.isNotEmpty(hisRegisterYyghList)) {
			logger.info("患者{}共有{}条记录", outPatientNo, hisRegisterYyghList.size());
			newHisDiagnosisRecordList = new ArrayList<>();
			for(HisRegisterYygh hry : hisRegisterYyghList) {
				String pno = hry.getType() == Integer.parseInt(RegisterType.APPOINTMENT.getValue()) ? hry.getYno() : hry.getPno();
				HisRegisterRecord hisRegisterRecord = registerService.getRegisterRecord(hry.getOutPatientNo(), pno);
				if (hisRegisterRecord == null) {
					logger.info("挂号表不存在门诊号={}，挂号码={}的记录", outPatientNo, pno);
					newHisDiagnosisRecordList.add(new HisRegisterRecord(hry));
				} else {
				    if (hry.getVisitTime() != hisRegisterRecord.getVisitTime()) {
                        hisRegisterRecord.setVisitTime(hry.getVisitTime());
                        hisRegisterRecordDao.update(hisRegisterRecord);
                    }
                }
			}
			if(CollectionUtils.isNotEmpty(newHisDiagnosisRecordList)) {
				logger.info("从预约表同步{}条记录到挂号表", newHisDiagnosisRecordList.size());
				registerService.saveHisRegisterRecord(newHisDiagnosisRecordList);
				logger.info("从预约表同步记录到挂号表成功");
			}
		} else {
			//调用his接口将患者当天的挂号记录存储到本地
			/*String visitTime = DateUtils.date2String(new Date(), DateUtils.DATE_FORMAT);
			List<RegisterDTO> hisRegistList = registerService.getUserRegisterInfo(outPatientNo,visitTime);*/
			logger.info("患者{}在预约表没找到挂号记录，开始调用HIS接口获取挂号信息", outPatientNo);
			List<RegisterDTO> hisRegistList = null;
			if(CollectionUtils.isNotEmpty(hisRegistList)) {
				logger.info("HIS接口返回{}条挂号记录", hisRegistList.size());
				List<HisRegisterRecord> hisRegisterRecordList = registerService.registerDTO2HisRegisterRecord(hisRegistList);
				//根据门诊号、挂号码是否存在，不存在则新增一条挂号记录
				newHisDiagnosisRecordList = new ArrayList<>();
				for (HisRegisterRecord hrr : hisRegisterRecordList) {
					HisRegisterRecord hisRegisterRecord = registerService.getRegisterRecord(hrr.getOutPatientNo(), hrr.getPno());
					if (hisRegisterRecord == null) {
						logger.info("挂号表不存在门诊号={}，挂号码={}的记录", outPatientNo, hrr.getPno());
						newHisDiagnosisRecordList.add(hrr);
					}
				}
				if(CollectionUtils.isNotEmpty(newHisDiagnosisRecordList)) {
					logger.info("将HIS返回的{}条挂号记录保存到挂号表", newHisDiagnosisRecordList.size());
					registerService.saveHisRegisterRecord(newHisDiagnosisRecordList);
					logger.info("挂号记录保存成功");
				}
			}
		}

		//从真实挂号表取出当天的预约记录
		List<HisRegisterRecord> todayAppointmentList = hisRegisterRecordDao.listTodayAppointment(outPatientNo);

		//判断当天的预约号是否已取号
		if (CollectionUtils.isNotEmpty(todayAppointmentList)) {
			for(HisRegisterRecord item : todayAppointmentList) {
				if(StringUtils.isEmpty(item.getFetchComplete()) || "0".equals(item.getFetchComplete())) {
					HisRegisterYygh params = new HisRegisterYygh();
					params.setOutPatientNo(outPatientNo);
					params.setYno(item.getPno());
					List<HisRegisterYygh> exHisRegisterYyghList = hisRegisterYyghMapper.listAppointmentWhichRegistered(params);
					if(CollectionUtils.isNotEmpty(exHisRegisterYyghList)) {
						item.setUpdateTime(new Date());
						//将预约记录状态更新为已取号
						item.setFetchComplete(exHisRegisterYyghList.get(0).getPno());
						hisRegisterRecordDao.update(item);
					}
				}
			}
		}
		//取出当天之后(包括当天)的预约及挂号记录
		List<HisRegisterRecord> registerRecordList = registerService.listByOutPatientNoAndVisitTime(outPatientNo, null);
		Predicate<HisRegisterRecord> predicate = e->{
			if(e.getType() != null && e.getType() == 2) {
				if(StringUtils.isNotEmpty(e.getFetchComplete()) && !"0".equals(e.getFetchComplete())) {
					return false;
				}
			}
			return true;
		};
		registerRecordList = registerRecordList.stream().filter(predicate).sorted(Comparator.comparing(HisRegisterRecord::getVisitTime)).collect(Collectors.toList());
		//医院对应的科室
		HospitalDept hospitalDeptParam = new HospitalDept();
		hospitalDeptParam.setHospitalCode(hospitalCode);
		List<HospitalDept> HospitalDeptList = hospitalDeptMapper.select(hospitalDeptParam);
		Map<String, HospitalDept> deptNameMap = HospitalDeptList.stream().collect(Collectors.toMap(HospitalDept::getDeptName, Function.identity()));
		logger.info("hospital {} has {} deptment", hospitalCode, HospitalDeptList.size());

		List<HisRegisterInfo> hisRegisterInfoListList = new ArrayList<>();
		for(HisRegisterRecord hrr : registerRecordList) {
			UserBasicRecord record = null;
			//拼装就诊科室编码
			String deptName = hrr.getDeptName();
			String appType = null;
			HospitalDept dept = null;
			if (StringUtils.isNotEmpty(deptName)) {
				for (String hospitalDeptName : deptNameMap.keySet()) {
					if (deptName.contains(hospitalDeptName)) {
						dept = deptNameMap.get(hospitalDeptName);
						appType = dept.getDeptCode();
					} else {
						logger.error("根据医院编码{}找不到科室{}", hospitalCode, deptName);
					}
				}
			}
			if (dept == null) {
				continue;
			}

			if (dept.getGender() != null) {
				if (userInfo.getGender() != dept.getGender()) {
					logger.error(deptName + "与患者性别不匹配");
					continue;
				}
			}

			//是否已完成预问诊
			HisRegisterInfo hri = new HisRegisterInfo(hrr);
			hri.setDeptCode(appType);
			if(hrr.getType() != null && hrr.getType() == Integer.parseInt(RegisterType.GET_FOR_APPOINTMENT.getValue())) {
				List<UserBasicRecord> userBasicRecordList = userBasicRecordDao.listFinishByAppointmentOrOffLine(hrr.getPno());
				record = CollectionUtils.isNotEmpty(userBasicRecordList) ? userBasicRecordList.get(0) : null;
			} else {
				record = userBasicRecordDao.listFinishByPno(hri.getHisRegisterNo(), null);
			}
			if(record != null) {
				hri.setDiagnosisId(String.valueOf(record.getDiagnosisId()));
				//hri.setStatus(DiagnosisStatus.PRE_DIAGNOSIS_FINISH.getValue());
				hri.setStatus(record.getStatus());
			}
			hisRegisterInfoListList.add(hri);
		}
		//组装返回结果
		HisUserInfoVo hisUserInfo = new HisUserInfoVo(userInfo, hisRegisterInfoListList);

		List<HisRegisterInfo> deptList = hisUserInfo.getHisDepartmentList();
		if(CollectionUtils.isNotEmpty(deptList)) {
			deptList.stream().filter(e->StringUtils.isNotEmpty(e.getCureTime())).peek(e->{
				String cureTimeStr = e.getCureTime();
				if(StringUtils.isNotEmpty(cureTimeStr)) {
					try {
						Date cureTime = DateUtils.string2Date(cureTimeStr);
						String cureDate = DateUtils.date2String(cureTime, DateUtils.DATE_FORMAT);
						e.setCureTime(cureDate);
					} catch (ParseException ex) {
						ex.printStackTrace();
					}
				}
			}).collect(Collectors.toList());
		}
		return hisUserInfo;
	}

	@Override
	public List<UserInfo> query(Map<String, Object> map) {
		return userInfoDao.query(map);
	}

	@Override
	public List<HisUserInfoVo> list(Long userId) {
		List<HisUserInfoVo> userList = new ArrayList<>();

		HisUserInfoVo self = this.listRegisterInfoFromLocalAndHis(null,null, userId);
		self.setSelf("Y");
		userList.add(self);
		List<UserMember> userMemberList = userMemberService.listByUserId(userId);

		for(UserMember userMember : userMemberList) {
			HisUserInfoVo memberInfo = this.listRegisterInfoFromLocalAndHis(null,null, userMember.getMemberId());
			userList.add(memberInfo);
		}
		return userList;
	}

	@Override
	@Transactional
	public HisUserInfoVo saveUserMember(Long userId, String memberName) {
		UserInfo self = userInfoDao.queryByUserId(userId);

		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(memberName);
		userInfo.setExternalUserId(self.getExternalUserId());
		userInfo.setInType(InType.ALPHA.getValue());
		userInfo = this.create(userInfo);
		Long memberId = userInfo.getUserId();

		UserMember userMember = new UserMember();
		userMember.setUserId(userId);
		userMember.setMemberId(memberId);
		userMember.setMemberName(memberName);
		userMemberService.create(userMember);

		HisUserInfoVo hisUserInfo = this.listRegisterInfoFromLocalAndHis(null,null, memberId);
		return hisUserInfo;
	}


	@Override
	public UserInfo getByPhoneNumber(String phoneNumber) {
		return userInfoDao.getByPhoneNumber(phoneNumber);
	}


	@Override
	public UserInfo follow(UserInfo userInfo, String wecharId) {
		userInfo.setExternalUserId(wecharId);
		userInfo.setInType(InType.WECHAR.getValue());
		userInfo.setLastUpdateTime(new Date());
		userInfoDao.update(userInfo);
		return userInfo;
	}

	@Override
	public List<UserInfo> listUserMemberInfo(Long userId) {
		return userInfoDao.listUserMemberInfo(userId);
	}


	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void updateUserBasicRecord(Long diagnosisId, OtherHospitalInfo hospitalInfo) {
		//将其它医院的就诊信息数据同步至UserBasicRecord
		UserBasicRecord record = userBasicRecordService.findByDiagnosisId(diagnosisId);
		if (record == null) {
			logger.warn("can not found UserBasicRecord by diagnosisId {}", diagnosisId);
			return;
		}

		BeanCopierUtil.copy(hospitalInfo, record);
		List<DrugVo> useDrugList = hospitalInfo.getOtherHospitalUseDrugList();
		String otherHospitalDrugList = "[]";
		if(CollectionUtils.isNotEmpty(useDrugList)) {
			otherHospitalDrugList = JSON.toJSONString(useDrugList);
			record.setOtherHospitalDrugList(otherHospitalDrugList);
		}
		//填充模板
		String templateId = userBasicRecordService.findTemplateId(diagnosisId);
		if (StringUtils.isNotEmpty(templateId)) {
			DiagnosisMedicalTemplate diagnosisMedicalTemplate = diagnosisMedicalTemplateDao.getDiagnosisMedicalTemplate(templateId);
			String presentIllnessHistoryHospital = medicalRecordService.getPresentIllnessHistoryHospital(diagnosisMedicalTemplate, record);
			record.setPresentIllnessHistoryHospital(presentIllnessHistoryHospital);
		}
		userBasicRecordService.updateUserBasicRecord(record);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void updateUserBasicRecord(Long diagnosisId, PresentIllnessVo presentIllnessVo) {
		//将现病史同步至UserBasicRecord
		UserBasicRecord record = userBasicRecordService.findByDiagnosisId(diagnosisId);
		if (record == null) {
			logger.warn("can not found UserBasicRecord by diagnosisId {}", diagnosisId);
			return;
		}

		BeanCopierUtil.copy(presentIllnessVo, record);
		userBasicRecordService.updateUserBasicRecord(record);
	}

	@Override
	public void save(UserInfo userInfo) {
		userInfoDao.update(userInfo);
	}

	@Override
	public List<UserInfo> listByUserId(List<Long> userIdList) {
		return userInfoDao.listByUserId(userIdList);
	}

	private UserInfo parseFromRegisterInfo(List<RegisterDTO> list) {
		String hospitalCode = "";
		RegisterDTO registerDTO = list.get(0);
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(registerDTO.getPatientName());
		try {
			userInfo.setBirth(DateUtils.string2Date(registerDTO.getBirthday()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int gender = registerDTO.getSex().equals("男") ? 2 : 1;
		userInfo.setGender(gender);
		userInfo.setIdcard(registerDTO.getPatientCardNo());
		userInfo.setHospitalCode(hospitalCode);
		userInfo.setOutPatientNo(registerDTO.getOutPatientNo());

		JSONArray jarr = new JSONArray();
		for(RegisterDTO item : list) {
			JSONObject itemJson = new JSONObject();
			itemJson.put("hospitalCode", hospitalCode);
			itemJson.put("hisRegisterNo", item.getPno());
			itemJson.put("department", item.getDepName());
			itemJson.put("doctorName", item.getDoctorName());
			itemJson.put("cureTime", item.getVisitTime());
			jarr.add(itemJson);
		}
		String departmentInfo = jarr.toJSONString();
		userInfo.setDepartmentList(departmentInfo);
		userInfo.setInType(InType.HIS.getValue());
		return userInfo;
	}

	@Override
	@Transactional
	public Long saveByBatch(List<UserInfo> userInfoList,List<HisRegisterRecord> hisRegisterRecordList) {
		return userInfoDao.insertByBatch(userInfoList)+registerService.saveByBatch(hisRegisterRecordList);
	}

	/**
	 * 从预约表生成用户
	 * @param outPatientNo
	 * @return
	 */
	@Override
	public UserInfo createUserInfoByHisRegisterHryy(String outPatientNo) {
		UserInfo userInfo = null;
		//从预约表创建用户
		HisRegisterYygh params = new HisRegisterYygh();
		params.setOutPatientNo(outPatientNo);
		List<HisRegisterYygh> hisRegisterYyghList = hisRegisterYyghMapper.getOneByOutPatient(params);
		if (CollectionUtils.isNotEmpty(hisRegisterYyghList)) {
			HisRegisterYygh item = hisRegisterYyghList.get(0);
			userInfo = new UserInfo();
			userInfo.setUserName(item.getPatientName());
			try {
				userInfo.setBirth(DateUtils.string2Date(item.getBirthday()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int gender = item.getSex().equals("男") ? 2 : 1;
			userInfo.setGender(gender);
			userInfo.setIdcard(item.getPatientCardNo());
			userInfo.setOutPatientNo(item.getOutPatientNo());
			userInfo.setInType(InType.HIS.getValue());
			userInfo = create(userInfo);
		}
		return userInfo;
	}

	@Override
	public Set<String> listUserBirthDay(String outPatientNo) {
		//页面输入的有可能是门诊号或卡号
		HisRegisterYygh param = new HisRegisterYygh();
		param.setOutPatientNo(outPatientNo);
		List<HisRegisterYygh> hisRegisterYyghList = hisRegisterYyghMapper.select(param);
		if (CollectionUtils.isEmpty(hisRegisterYyghList)) {
			param = new HisRegisterYygh();
			param.setCardNo(outPatientNo);
			hisRegisterYyghList = hisRegisterYyghMapper.select(param);
		}
		Set<String> userBirthSet = hisRegisterYyghList.stream().filter(e -> StringUtils.isNotEmpty(e.getBirthday())).map(HisRegisterYygh::getBirthday).map(DateUtils::stringToDate)
			.map(e -> DateUtils.date2String(e, DateUtils.DATE_FORMAT)).collect(Collectors.toSet());
		return userBirthSet;
	}
}
