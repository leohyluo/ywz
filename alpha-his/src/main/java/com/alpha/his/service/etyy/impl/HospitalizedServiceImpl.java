package com.alpha.his.service.etyy.impl;

import com.alpha.commons.core.mapper.HospitalizedNoticeNewMapper;
import com.alpha.commons.core.mapper.HospitalizedPatientInfoNew1Mapper;
import com.alpha.commons.core.pojo.HospitalizedNoticeNew;
import com.alpha.commons.core.pojo.HospitalizedPatientInfoNew1;
import com.alpha.commons.core.pojo.OutPatientInfo;
import com.alpha.commons.core.util.SoapUtil;
import com.alpha.commons.util.CollectionUtils;
import com.alpha.commons.util.DateUtils;
import com.alpha.his.dao.HospitalizedCommonIllChildDao;
import com.alpha.his.dao.HospitalizedNewIllChildDao;
import com.alpha.his.dao.HospitalizedNoticeDao;
import com.alpha.his.dao.HospitalizedPatientInfoDao;
import com.alpha.his.pojo.dto.UserHospitalized;
import com.alpha.his.service.etyy.HospitalizedService;
import com.alpha.his.service.etyy.OutptientInfroService;
import com.alpha.commons.core.util.StaticHttpclientCall;
import com.alpha.server.rpc.his.pojo.HospitalizedCommonIllChild;
import com.alpha.server.rpc.his.pojo.HospitalizedNewIllChild;
import com.alpha.server.rpc.his.pojo.HospitalizedNotice;
import com.alpha.server.rpc.his.pojo.HospitalizedPatientInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class HospitalizedServiceImpl implements HospitalizedService {
	private static final Logger logger = LoggerFactory.getLogger(HospitalizedServiceImpl.class);

	private static final  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final  SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
	@Resource
	private HospitalizedPatientInfoDao hospitalizedPatientInfoDao;
	@Resource
	private HospitalizedNoticeDao hospitalizedNoticeDao;
	@Resource
	private HospitalizedCommonIllChildDao hospitalizedCommonIllChildDao;
	@Resource
	private HospitalizedNewIllChildDao hospitalizedNewIllChildDao;
	@Resource
	private  HospitalizedPatientInfoNew1Mapper hospitalizedPatientInfoNew1Mapper;
	@Resource
	private HospitalizedNoticeNewMapper hospitalizedNoticeNewMapper;

	@Autowired
	OutptientInfroService outptientInfroService;

	@Value("${hisSevice.outpatientWSDL}")
	private String outpatientWSDL;
	@Value("${hisSevice.outpatientFID}")
	private String outpatientFID;
	@Value("${hisSevice.hospitalizedWSDL}")
	private String hospitalizedWSDL;
	@Value("${hisSevice.hospitalizedFID}")
	private String hospitalizedFID;
	@Value("${hisSevice.hositalizedNoticeWDSL}")
	private String hositalizedNoticeWDSL;
	@Value("${hisSevice.hositalizedNoticeFID}")
	private String hositalizedNoticeFID;


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

	/**
	 * 返回最近一次住院的信息，如没有住院过，就填充部分门诊基本信息，（包含字典）
	 * @param outPatientNo   门诊号码
	 * @return
	 */
	@Override
	public HospitalizedPatientInfoNew1 getPatientHospitalizedInfoNew(String outPatientNo) {
		if(StringUtils.isBlank(outPatientNo)){
			return null;
		}

		String resultXml= StaticHttpclientCall.hospitalizedInfo(hospitalizedWSDL,hospitalizedFID, outPatientNo);
		if(StringUtils.isBlank(resultXml)){
			return null;
		}
		List<String> list = SoapUtil.parseETYYxml(resultXml);
		HospitalizedPatientInfoNew1 hospitalizedPatientInfoNew1=new HospitalizedPatientInfoNew1();
		String str=hospitalizedPatientInfoNew1.getClass().toString();
		str=str.replace("class ","");
		str=str.replace(" ","");
        if(null == list || list.size()<1){//没有住过院
			List<OutPatientInfo> li=outptientInfroService.getOutpatientInfoByMZHM(null,null,outPatientNo,null);
			if(null == li || li.size()<1){
				return null;
			}
			OutPatientInfo outPatientInfo=li.get(0); //门诊卡信息
			hospitalizedPatientInfoNew1.setPatientName(outPatientInfo.getPatientName());
			hospitalizedPatientInfoNew1.setBirthday(outPatientInfo.getBirth());
			hospitalizedPatientInfoNew1.setContactPhone(outPatientInfo.getPhoneNo());
			hospitalizedPatientInfoNew1.setPatientType(outPatientInfo.getPatientType());
			hospitalizedPatientInfoNew1.setMailingAddress(outPatientInfo.getFamilyAddress());
			hospitalizedPatientInfoNew1.setPatientCertiNo(outPatientInfo.getIdentityNo());
			String age= null;
			try {
				age = DateUtils.getAgeText(new SimpleDateFormat("yyyy-MM-dd").parse(outPatientInfo.getBirth()), true);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			hospitalizedPatientInfoNew1.setAge(age);

			if(null != outPatientInfo.getContactAddress()) {
				hospitalizedPatientInfoNew1.setContactAddr(outPatientInfo.getContactAddress());
			}
        	return hospitalizedPatientInfoNew1;
		}else {//住过院返回最近一次住院信息回去
			List<Object> objectList=SoapUtil.string2obj(list,str,SoapUtil.map);
			List<HospitalizedPatientInfoNew1> hospitalizedPatientInfoNew1s=new ArrayList<>();
			List<HospitalizedPatientInfoNew1> hos=new ArrayList<>();
			Comparator<HospitalizedPatientInfoNew1> comparator = (h1, h2) -> h2.getVisitTimes().compareTo
					(h1.getVisitTimes());
			objectList.stream().forEach( e -> {
				HospitalizedPatientInfoNew1 hospitalizedPatientInfoNew11=(HospitalizedPatientInfoNew1)e;
				HospitalizedPatientInfoNew1 param=new HospitalizedPatientInfoNew1();
				param.setPatientId(hospitalizedPatientInfoNew11.getPatientId());
				param.setPatientName(hospitalizedPatientInfoNew11.getPatientName());
				HospitalizedPatientInfoNew1 rest=hospitalizedPatientInfoNew1Mapper.selectOne(param);
				hos.add(hospitalizedPatientInfoNew11);
				if(null == rest){
					hospitalizedPatientInfoNew1s.add(hospitalizedPatientInfoNew11);
				}
			});
			hos.sort(comparator);
			if(hospitalizedPatientInfoNew1s.size()>1){
				hospitalizedPatientInfoNew1Mapper.insertList(hospitalizedPatientInfoNew1s);
			}
			hospitalizedPatientInfoNew1=hos.get(0);
			hospitalizedPatientInfoNew1.setPatientType(hospitalizedPatientInfoNew1.getInsuranceType());
			String age= null;
			try {
				age = DateUtils.getAgeText(new SimpleDateFormat("yyyy-MM-dd").parse(hospitalizedPatientInfoNew1.getBirthday()), true);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			hospitalizedPatientInfoNew1.setAge(age);
			return hospitalizedPatientInfoNew1;
		}
	}

	@Override
	public HospitalizedPatientInfoNew1 mockPatientHospitalizedInfoNew(String outPatientNo) {
		HospitalizedPatientInfoNew1 param = new HospitalizedPatientInfoNew1();
		param.setOutPatientNo(outPatientNo);
		List<HospitalizedPatientInfoNew1> list = hospitalizedPatientInfoNew1Mapper.select(param);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	/**
	 * 住院通知实时去his 获取最新的一条
	 * 保存最新的入院通知书，以前的将不会增加
	 * @param outPatientNo
	 * @return
	 */
	@Override
	public HospitalizedNoticeNew getHospitalizedNotice(String noticeId,String outPatientNo) {
		try {
			HospitalizedNoticeNew param=new HospitalizedNoticeNew();
			param.setNoticeId(noticeId);
			HospitalizedNoticeNew result=hospitalizedNoticeNewMapper.selectOne(param);
			if(null != result){
				return result;
			}
			if(StringUtils.isBlank(outPatientNo)){
				return null;
			}
			String resultXml= StaticHttpclientCall.hospitalizedNotice(hositalizedNoticeWDSL,hositalizedNoticeFID, outPatientNo);
			if(StringUtils.isBlank(resultXml)){
				return null;
			}
			List<String> list = SoapUtil.parseETYYxml(resultXml);
			String str=param.getClass().toString();
			str=str.replace("class ","");
			str=str.replace(" ","");
			if(null ==list || list.size()<1){
				return null;
			}else {
				List<Object> objectList=SoapUtil.string2obj(list,str,SoapUtil.mapNotice);
				List<HospitalizedNoticeNew> hospitalizedNoticeNews=new ArrayList<>();
				objectList.stream().forEach(e -> {
					HospitalizedNoticeNew hospitalizedNoticeNew1=(HospitalizedNoticeNew)e;
						hospitalizedNoticeNew1.setCreateTime(sdf.format(new Date()));
					try {
						String noticeid=sdf1.format(sdf.parse(hospitalizedNoticeNew1.getNotifyTime()));
						hospitalizedNoticeNew1.setNoticeId(hospitalizedNoticeNew1.getOutPatientNo()+noticeid);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					hospitalizedNoticeNew1.setCreateTime(sdf.format(new Date()));
						hospitalizedNoticeNews.add(hospitalizedNoticeNew1);
				});
				if(hospitalizedNoticeNews.size()>0){
					hospitalizedNoticeNewMapper.insertBatch(hospitalizedNoticeNews);
				}
				result=hospitalizedNoticeNewMapper.selectOne(param);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public HospitalizedNoticeNew mockHospitalizedNotice(String outPatientNo) {
		HospitalizedNoticeNew param = new HospitalizedNoticeNew();
		param.setOutPatientNo(outPatientNo);
		List<HospitalizedNoticeNew> list = hospitalizedNoticeNewMapper.select(param);
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}

	@Override
	public String outPatientNum(String cardNo) {
		String cardNum="";
		if(StringUtils.isBlank(cardNo)){
			return null;
		}
		try {
			String resultXml=StaticHttpclientCall.outPatientNo(outpatientWSDL,outpatientFID,cardNo);
			if(null == resultXml){
				return null;
			}
			List<String> list = SoapUtil.parseETYYxml(resultXml);
			if(null == list || list.size()<1 ){
				return null;
			}
			OutPatientInfo outPatientInfo=new OutPatientInfo();
			String str=outPatientInfo.getClass().toString();
			str=str.replace("class ","");
			str=str.replace(" ","");
			List<Object> objectList=SoapUtil.string2obj(list,str,SoapUtil.outpatientmap);
			outPatientInfo=(OutPatientInfo)objectList.get(0);
			cardNum = outPatientInfo.getOutpatientNo();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("根据卡号获取门诊号异常；"+e.toString());
		}
		return cardNum;
	}

	@Override
	public HospitalizedPatientInfoNew1 getByNoticeIdAndStatus(String noticeId, Integer status) {
		HospitalizedPatientInfoNew1 param = new HospitalizedPatientInfoNew1();
		param.setNoticeId(noticeId);
		List<HospitalizedPatientInfoNew1> list = hospitalizedPatientInfoNew1Mapper.select(param);
		if(CollectionUtils.isNotEmpty(list) && list.size() > 1) {
			logger.error("数据发现异常，住院通知单{}存在多个患者信息", noticeId);
			return null;
		}
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
		//return hospitalizedPatientInfoNew1Mapper.getByNoticeIdAndStatus(noticeId, status);
	}

	@Override
	public HospitalizedPatientInfoNew1 getHospitalizedPatientInfoById(Integer id) {
		return hospitalizedPatientInfoNew1Mapper.selectByPrimaryKey(id);
	}

	@Override
	public HospitalizedPatientInfoNew1 getHospitalizedPatientInfoByHosNo(String hosNo) {
		HospitalizedPatientInfoNew1 param = new HospitalizedPatientInfoNew1();
		param.setInpNo(hosNo);
		List<HospitalizedPatientInfoNew1> list = hospitalizedPatientInfoNew1Mapper.select(param);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public void updateHospitalizedPatientInfo(HospitalizedPatientInfoNew1 hospitalizedPatientInfo) {
		hospitalizedPatientInfoNew1Mapper.updateByPrimaryKey(hospitalizedPatientInfo);
	}

	@Override
	public void updateHospitalizedNotice(HospitalizedNoticeNew hospitalizedNotice) {
		hospitalizedNoticeNewMapper.updateByPrimaryKey(hospitalizedNotice);
	}

	@Override
	public void createHospitalizedPatientInfo(HospitalizedPatientInfoNew1 hospitalizedPatientInfo) {
		hospitalizedPatientInfoNew1Mapper.insert(hospitalizedPatientInfo);
	}
	@Override
	public HospitalizedNoticeNew getHospitalizedNoticeFromLocal(String noticeId) {
		HospitalizedNoticeNew param = new HospitalizedNoticeNew();
		param.setNoticeId(noticeId);
		List<HospitalizedNoticeNew> list = hospitalizedNoticeNewMapper.select(param);
		if(CollectionUtils.isNotEmpty(list) && list.size() > 1) {
			logger.error("本地存在多条相同的住院通知书，其编码为{}", noticeId);
			return null;
		}
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}


	@Override
	public HospitalizedPatientInfoNew1 getHospitalizedbyInpNo(String inpNo) {
		if(StringUtils.isBlank(inpNo)){
			return null;
		}
		String resultXml= StaticHttpclientCall.hospitalizedInfobyinpno(hospitalizedWSDL,hospitalizedFID, inpNo);
		if(StringUtils.isBlank(resultXml)){
			return null;
		}
		List<String> list = SoapUtil.parseETYYxml(resultXml);
		HospitalizedPatientInfoNew1 hospitalizedPatientInfoNew1=new HospitalizedPatientInfoNew1();
		String str=hospitalizedPatientInfoNew1.getClass().toString();
		str=str.replace("class ","");
		str=str.replace(" ","");
		if(null == list || list.size()<1){
			return null;
		}else {
			List<Object> objectList=SoapUtil.string2obj(list,str,SoapUtil.map);
			List<HospitalizedPatientInfoNew1> hospitalizedPatientInfoNew1s=new ArrayList<>();
			Comparator<HospitalizedPatientInfoNew1> comparator = (h1, h2) -> h2.getVisitTimes().compareTo
					(h1.getVisitTimes());
			objectList.stream().forEach( e -> {
				hospitalizedPatientInfoNew1s.add((HospitalizedPatientInfoNew1)e);
			});
			hospitalizedPatientInfoNew1s.sort(comparator);
			return hospitalizedPatientInfoNew1s.get(0);
		}
	}

}
