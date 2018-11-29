package com.alpha.his.service.etyy.impl;

import com.alpha.commons.core.service.impl.BaseServiceImpl;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.his.dao.HospitalizedCommonIllChildDao;
import com.alpha.his.dao.HospitalizedNewIllChildDao;
import com.alpha.his.dao.HospitalizedNoticeDao;
import com.alpha.his.dao.HospitalizedPatientInfoDao;
import com.alpha.his.pojo.dto.UserHospitalized;
import com.alpha.his.service.etyy.HospitalizedPatientInfoService;
import com.alpha.server.rpc.his.pojo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalizedPatientInfoServiceImpl extends BaseServiceImpl<HospitalizedPatientInfoNew, HospitalizedPatientInfoDao> implements HospitalizedPatientInfoService {

	@Resource
	private HospitalizedPatientInfoDao hospitalizedPatientInfoDao;
	@Resource
	private HospitalizedNoticeDao hospitalizedNoticeDao;
	@Resource
	private HospitalizedCommonIllChildDao hospitalizedCommonIllChildDao;
	@Resource
	private HospitalizedNewIllChildDao hospitalizedNewIllChildDao;

	@Override
	public void saveUserHospitalizedInfo(String hospitalCode, String outPatientNo, String hosNo, HospitalizedPatientInfo hospitalizedPatientInfo, HospitalizedNotice hospitalizedNotice, HospitalizedCommonIllChild hospitalizedCommonIllChild, HospitalizedNewIllChild hospitalizedNewIllChild) {

	}

	@Override
	public Long save(HospitalizedPatientInfoNew hospitalizedPatientInfoNew) {
		return super.save(hospitalizedPatientInfoNew);
	}

//	@Transactional
//	@Override
//	public void saveUserHospitalizedInfo(String hospitalCode, String outPatientNo, String hosNo, HospitalizedPatientInfo hospitalizedPatientInfo, HospitalizedNotice hospitalizedNotice,
//										 HospitalizedCommonIllChild hospitalizedCommonIllChild, HospitalizedNewIllChild hospitalizedNewIllChild) {
//		//根据住院编号查询4张表，如有数据则更新，没有则插入
//		//新生儿和普通患儿只能二选一
//		if(hospitalizedPatientInfo != null) {
//			HospitalizedPatientInfo dbHospitalizedPatientInfo = hospitalizedPatientInfoDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);
//			if(dbHospitalizedPatientInfo != null) {
//				hospitalizedPatientInfo.setId(dbHospitalizedPatientInfo.getId());
//				hospitalizedPatientInfo.setHospitalCode(hospitalCode);
//				hospitalizedPatientInfo.setOutPatientNo(outPatientNo);
//				hospitalizedPatientInfo.setHosno(hosNo);
//				hospitalizedPatientInfo.setCreateTime(dbHospitalizedPatientInfo.getCreateTime());
//				hospitalizedPatientInfo.setUpdateTime(new Date());
//				hospitalizedPatientInfoDao.update(hospitalizedPatientInfo);
//			} else {
//				hospitalizedPatientInfo.setHospitalCode(hospitalCode);
//				hospitalizedPatientInfo.setOutPatientNo(outPatientNo);
//				hospitalizedPatientInfo.setHosno(hosNo);
//				hospitalizedPatientInfo.setCreateTime(new Date());
//				hospitalizedPatientInfo.setUpdateTime(new Date());
//				hospitalizedPatientInfoDao.insert(hospitalizedPatientInfo);
//			}
//		}
//		if(hospitalizedNotice != null) {
//			HospitalizedNotice dbHospitalizedNotice = hospitalizedNoticeDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);
//			if(dbHospitalizedNotice != null) {
//				hospitalizedNotice.setId(dbHospitalizedNotice.getId());
//				hospitalizedNotice.setCreateTime(dbHospitalizedNotice.getCreateTime());
//				hospitalizedNotice.setUpdateTime(new Date());
//				hospitalizedNoticeDao.update(hospitalizedNotice);
//			} else {
//				hospitalizedNotice.setHospitalCode(hospitalCode);
//				hospitalizedNotice.setOutPatientNo(outPatientNo);
//				hospitalizedNotice.setHosno(hosNo);
//				hospitalizedNotice.setCreateTime(new Date());
//				hospitalizedNotice.setUpdateTime(new Date());
//				hospitalizedNoticeDao.insert(hospitalizedNotice);
//			}
//		}
//		if(hospitalizedCommonIllChild != null) {
//			HospitalizedCommonIllChild dbHospitalizedCommonIllChild = hospitalizedCommonIllChildDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);
//			HospitalizedNewIllChild dbHospitalizedNewIllChild = hospitalizedNewIllChildDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);
//			if(hospitalizedCommonIllChild != null) {
//				if(dbHospitalizedCommonIllChild != null) {
//					hospitalizedCommonIllChild.setId(dbHospitalizedCommonIllChild.getId());
//					hospitalizedCommonIllChild.setHospitalCode(hospitalCode);
//					hospitalizedCommonIllChild.setOutPatientNo(outPatientNo);
//					hospitalizedCommonIllChild.setHosno(hosNo);
//					hospitalizedCommonIllChild.setCreateTime(dbHospitalizedCommonIllChild.getCreateTime());
//					hospitalizedCommonIllChild.setUpdateTime(new Date());
//					hospitalizedCommonIllChildDao.update(hospitalizedCommonIllChild);
//				} else {
//					hospitalizedCommonIllChild.setHospitalCode(hospitalCode);
//					hospitalizedCommonIllChild.setOutPatientNo(outPatientNo);
//					hospitalizedCommonIllChild.setHosno(hosNo);
//					hospitalizedCommonIllChild.setCreateTime(new Date());
//					hospitalizedCommonIllChild.setUpdateTime(new Date());
//					hospitalizedCommonIllChildDao.insert(hospitalizedCommonIllChild);
//				}
//				//删除新生儿数据
//				if(dbHospitalizedNewIllChild != null) {
//					hospitalizedNewIllChildDao.delete(dbHospitalizedNewIllChild.getId());
//				}
//			}
//		}
//		if(hospitalizedNewIllChild != null) {
//			HospitalizedCommonIllChild dbHospitalizedCommonIllChild = hospitalizedCommonIllChildDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);
//			HospitalizedNewIllChild dbHospitalizedNewIllChild = hospitalizedNewIllChildDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);
//			if(dbHospitalizedNewIllChild != null) {
//				hospitalizedNewIllChild.setId(dbHospitalizedNewIllChild.getId());
//				hospitalizedNewIllChild.setHospitalCode(hospitalCode);
//				hospitalizedNewIllChild.setOutPatientNo(outPatientNo);
//				hospitalizedNewIllChild.setHosno(hosNo);
//				hospitalizedNewIllChild.setCreateTime(dbHospitalizedNewIllChild.getCreateTime());
//				hospitalizedNewIllChild.setUpdateTime(new Date());
//				hospitalizedNewIllChildDao.update(hospitalizedNewIllChild);
//			} else {
//				hospitalizedNewIllChild.setHospitalCode(hospitalCode);
//				hospitalizedNewIllChild.setOutPatientNo(outPatientNo);
//				hospitalizedNewIllChild.setHosno(hosNo);
//				hospitalizedNewIllChild.setCreateTime(new Date());
//				hospitalizedNewIllChild.setUpdateTime(new Date());
//				hospitalizedNewIllChildDao.insert(hospitalizedNewIllChild);
//			}
//			//删除普通患儿数据
//			if(dbHospitalizedCommonIllChild != null) {
//				hospitalizedCommonIllChildDao.delete(dbHospitalizedCommonIllChild.getId());
//			}
//		}
//	}

	@Override
	public UserHospitalized getUserHospitalizedInfo(String hospitalCode, String hosNo) {
		HospitalizedPatientInfo dbHospitalizedPatientInfo = hospitalizedPatientInfoDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);
		HospitalizedNotice dbHospitalizedNotice = hospitalizedNoticeDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);
		HospitalizedCommonIllChild dbHospitalizedCommonIllChild = hospitalizedCommonIllChildDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);
		HospitalizedNewIllChild dbHospitalizedNewIllChild = hospitalizedNewIllChildDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);

		UserHospitalized userHospitalized = new UserHospitalized();
		userHospitalized.setHospitalizedPatientInfo(dbHospitalizedPatientInfo);
		userHospitalized.setHospitalizedNotice(dbHospitalizedNotice);
		userHospitalized.setHospitalizedCommonIllChild(dbHospitalizedCommonIllChild);
		userHospitalized.setHospitalizedNewIllChild(dbHospitalizedNewIllChild);
		return  userHospitalized;
	}

	@Override
	public List<UserHospitalized> getUserHospitalizedInfoList(String hospitalCode, String outPatientNo) {
		List<HospitalizedNotice> noticeList = hospitalizedNoticeDao.listByHospitalCodeAndOutPatientNo(hospitalCode, outPatientNo);
		List<UserHospitalized> userHospitalizedList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(noticeList)) {
			for(HospitalizedNotice itemNotice : noticeList) {
				UserHospitalized userHospitalized = new UserHospitalized();
				//住院编号
				String hosNo = itemNotice.getHosno();
				HospitalizedPatientInfo dbHospitalizedPatientInfo = hospitalizedPatientInfoDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);
				HospitalizedCommonIllChild dbHospitalizedCommonIllChild = hospitalizedCommonIllChildDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);
				HospitalizedNewIllChild dbHospitalizedNewIllChild = hospitalizedNewIllChildDao.getByHospitalCodeAndHosNo(hospitalCode, hosNo);

				userHospitalized.setHospitalizedNotice(itemNotice);
				userHospitalized.setHospitalizedPatientInfo(dbHospitalizedPatientInfo);
				userHospitalized.setHospitalizedCommonIllChild(dbHospitalizedCommonIllChild);
				userHospitalized.setHospitalizedNewIllChild(dbHospitalizedNewIllChild);
				userHospitalizedList.add(userHospitalized);
			}
		}
		return userHospitalizedList;
	}

    @Transactional
	@Override
	public Long savePatientInfoAndModifyNotice(HospitalizedPatientInfoNew hospitalizedPatientInfo,HospitalizedNotice notice){
		if(notice != null)
        	hospitalizedNoticeDao.updateSelective(notice);
		return hospitalizedPatientInfoDao.insertSelective(hospitalizedPatientInfo);
	}

	@Override
	public Long modifyPatientInfoAndModifyNotice(HospitalizedPatientInfoNew hospitalizedPatientInfo, HospitalizedNotice notice) {
		if(notice != null)
			hospitalizedNoticeDao.updateSelective(notice);
		return hospitalizedPatientInfoDao.updateSelective(hospitalizedPatientInfo);
	}

	@Override
	public HospitalizedPatientInfoNew getByNoticeId(String noticeId) {
		HospitalizedPatientInfoNew param = new HospitalizedPatientInfoNew();
		param.setNoticeId(noticeId);
		return super.queryById(param);
	}
}

