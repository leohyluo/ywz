package com.alpha.his.service.etyy.impl;

import com.alpha.commons.core.mapper.ProvinceMapper;
import com.alpha.commons.core.pojo.RegisterDTO;
import com.alpha.commons.core.pojo.YwzCountTimes;
import com.alpha.commons.core.service.YwzCountTimesService;
import com.alpha.commons.core.util.SoapUtil;
import com.alpha.his.dao.HisDiagnosisRecordDao;
import com.alpha.his.dao.HospitalInfoDao;
import com.alpha.his.mapper.BirthPlaceMapper;
import com.alpha.his.mapper.CountryMapper;
import com.alpha.his.mapper.NativePlaceMapper;
import com.alpha.his.pojo.dao.BirthPlace;
import com.alpha.his.pojo.dao.Country;
import com.alpha.his.pojo.dao.NativePlace;
import com.alpha.his.service.etyy.HospitalService;
import com.alpha.his.service.etyy.HospitalizedService;
import com.alpha.commons.core.util.StaticHttpclientCall;
import com.alpha.server.rpc.diagnosis.pojo.HospitalInfo;
import com.alpha.server.rpc.his.pojo.HisDiagnosisRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HospitalServiceImpl implements HospitalService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private HisDiagnosisRecordDao hisDiagnosisRecordDao;
	@Resource
	private HospitalInfoDao hospitalInfoDao;

	@Resource
	private HospitalizedService hospitalizedService;

	@Value("${hisSevice.registrationWSDL}")
	private String registrationWSDL;

	@Value("${hisSevice.registrationFID}")
	private String registrationFID;

    @Autowired
	YwzCountTimesService ywzCountTimesService;

	@Autowired
	private BirthPlaceMapper birthPlaceMapper;
	@Autowired
	private CountryMapper countryMapper;
	@Autowired
	private NativePlaceMapper nativePlaceMapper;
	@Resource
	private ProvinceMapper provinceMapper;

	@Override
	public void saveHisDiagnosisRecord(HisDiagnosisRecord hisDiagnosisRecord) {
		HisDiagnosisRecord dbHisDiagnosisRecord = hisDiagnosisRecordDao.getByDiagnosisId(hisDiagnosisRecord.getDiagnosisId());
		/*String diseaseInfo = null;
		if(CollectionUtils.isNotEmpty(hisDiagnosisRecord.getDiseases())) {
			diseaseInfo = JSON.toJSONString(hisDiagnosisRecord.getDiseases());
		}*/
		String diseaseInfo = hisDiagnosisRecord.getDiseaseInfo();
		YwzCountTimes ywzCountTimes =new YwzCountTimes();
		if(dbHisDiagnosisRecord != null) {
			dbHisDiagnosisRecord.setUpdateTime(new Date());
			ywzCountTimes.setType(7);
			ywzCountTimes.setDiseaseId(String.valueOf(hisDiagnosisRecord.getDiagnosisId()));
			if(StringUtils.isNotEmpty(hisDiagnosisRecord.getMainSymptomName())) {
				dbHisDiagnosisRecord.setMainSymptomName(hisDiagnosisRecord.getMainSymptomName());
				ywzCountTimes.setDescri("71");
			}
			if(StringUtils.isNotEmpty(hisDiagnosisRecord.getPresentIllnessHistory())) {
				dbHisDiagnosisRecord.setPresentIllnessHistory(hisDiagnosisRecord.getPresentIllnessHistory());
				ywzCountTimes.setDescri("72");
			}
			if(StringUtils.isNotEmpty(hisDiagnosisRecord.getPastMedicalHistory())) {
				dbHisDiagnosisRecord.setPastMedicalHistory(hisDiagnosisRecord.getPastMedicalHistory());
				ywzCountTimes.setDescri("73");
			}
			if(StringUtils.isNotEmpty(diseaseInfo)) {
				dbHisDiagnosisRecord.setDiseaseInfo(diseaseInfo);
				ywzCountTimes.setDescri("74");
			}
			hisDiagnosisRecordDao.update(dbHisDiagnosisRecord);
			ywzCountTimesService.addTimes(ywzCountTimes);
		} else {
			hisDiagnosisRecord.setCreateTime(new Date());
			hisDiagnosisRecord.setUpdateTime(new Date());
			hisDiagnosisRecord.setDiseaseInfo(diseaseInfo);
			hisDiagnosisRecordDao.insert(hisDiagnosisRecord);
		}
	}

	@Override
	public HospitalInfo getHospitalInfo(String hospitalCode) {
		return hospitalInfoDao.getByHospitalCode(hospitalCode);
	}

	@Override
	public HisDiagnosisRecord getByDiagnosisId(Long diagnosisId) {
		return hisDiagnosisRecordDao.getByDiagnosisId(diagnosisId);
	}

	@Override
	public List<RegisterDTO>   registrationInfo(String outPatientNo, String visitTime) {
		List<RegisterDTO> registerList=new ArrayList<>();
		String result= null;
		try {
			result = StaticHttpclientCall.registrationInfo(registrationWSDL,registrationFID,outPatientNo,visitTime);
			List<String> stringList= SoapUtil.parseETYYxml(result);
			if (null == stringList || stringList.size()<1){
				String outpatient =hospitalizedService.outPatientNum(outPatientNo);
				result = StaticHttpclientCall.registrationInfo(registrationWSDL,registrationFID,outpatient,visitTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.toString());
		}
		if(null == result){
			return registerList;
		}
		registerList = RegisterDTO.parse(result);
		return registerList;
	}

	@Override
	public Map<String,Object> HotAdress(String keyword, Integer type) {
		keyword=keyword.toUpperCase().trim();
		Map<String,Object> result=new HashMap<>();
		if(1==type){
			List<BirthPlace> birthPlaceList=birthPlaceMapper.selectAll();
			Map<String,List<BirthPlace>> data=birthPlaceList.stream().collect(Collectors.groupingBy(BirthPlace::getFirst));
			Collections.sort(birthPlaceList, Comparator.comparing(BirthPlace::getTimes).reversed());
			result.put("data",data);
			result.put("hot",birthPlaceList.subList(0,10));
			if(!StringUtils.isBlank(keyword)) {
				List<BirthPlace> keylist = birthPlaceMapper.selectKeyword(keyword);
				if(null !=keylist && keylist.size()>0){
					result.put("data",keylist);
				}else {
					result.put("data",null);
				}

			}

		}else if (2==type){
			List<NativePlace> birthPlaceList=nativePlaceMapper.selectAll();
			Map<String,List<NativePlace>> data=birthPlaceList.stream().collect(Collectors.groupingBy(NativePlace::getFirst));
			Collections.sort(birthPlaceList, Comparator.comparing(NativePlace::getTimes).reversed());
			result.put("data",data);
			result.put("hot",birthPlaceList.subList(0,10));
			if(!StringUtils.isBlank(keyword)) {
				List<NativePlace> keylist = nativePlaceMapper.selectKeyword(keyword);

				if(null !=keylist && keylist.size()>0){
					result.put("data",keylist);
				}else {
					result.put("data",null);
				}
			}

		}else {
			List<Country> birthPlaceList=countryMapper.selectAll();
			Map<String,List<Country>> data=birthPlaceList.stream().collect(Collectors.groupingBy(Country::getFirst));
			Collections.sort(birthPlaceList, Comparator.comparing(Country::getTimes).reversed());
			result.put("data",data);
			result.put("hot",birthPlaceList.subList(0,10));
			if(!StringUtils.isBlank(keyword)) {
				List<Country> keylist = countryMapper.selectKeyword(keyword);
				if(null !=keylist && keylist.size()>0){
					result.put("data",keylist);
				}else {
					result.put("data",null);
				}
			}
		}
		return result;
	}
}
