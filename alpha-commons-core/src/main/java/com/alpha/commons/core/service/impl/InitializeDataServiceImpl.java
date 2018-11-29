package com.alpha.commons.core.service.impl;

import com.alpha.commons.core.mapper.*;
import com.alpha.commons.core.pojo.InitializeDataVo;
import com.alpha.commons.core.service.InitializeDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by HP on 2018/3/22.
 */
@Service
public class InitializeDataServiceImpl implements InitializeDataService {

    private static final Logger logger = LoggerFactory.getLogger(InitializeDataServiceImpl.class);

    @Autowired
    NationMapper nationMapper;
    @Autowired
    NationalityMapper nationalityMapper;
    @Autowired
    PayTypeMapper payTypeMapper;
    @Autowired
    PatientIdCardTypeMapper patientIdCardTypeMapper;
    @Autowired
    GuardianIdCardTypeMapper guardianIdCardTypeMapper;
    @Autowired
    RelationshipTypeMapper relationshipTypeMapper;



    @Override
    public InitializeDataVo innitializeData() {

        InitializeDataVo initializeDataVo=new InitializeDataVo();

        try {
            logger.info("初始化数据：");

            initializeDataVo.setGuardianIdCardTypes(guardianIdCardTypeMapper.list());
            initializeDataVo.setNationalities(nationalityMapper.list());
            initializeDataVo.setNations(nationMapper.list());
            initializeDataVo.setPayTypes(payTypeMapper.list());
            initializeDataVo.setPatientIdCardTypes(patientIdCardTypeMapper.list());
            initializeDataVo.setRelationshipTypes(relationshipTypeMapper.selectAll());


        } catch (Exception e) {
            e.printStackTrace();
            logger.info("初始化数据异常");
        }
        return initializeDataVo;
    }
}
