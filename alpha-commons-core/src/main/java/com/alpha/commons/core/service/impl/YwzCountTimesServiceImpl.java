package com.alpha.commons.core.service.impl;

import com.alpha.commons.core.mapper.YwzCountTimesMapper;
import com.alpha.commons.core.pojo.YwzCountTimes;
import com.alpha.commons.core.service.YwzCountTimesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2018/5/9.
 */
@Service
public class YwzCountTimesServiceImpl implements YwzCountTimesService {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd ");
    private static final Logger logger = LoggerFactory.getLogger(YwzCountTimesServiceImpl.class);

    @Resource
    private YwzCountTimesMapper ywzCountTimesMapper;


    @Override
    public List<YwzCountTimes> gettimes(String startTime, String endTime, String depName) {

        return ywzCountTimesMapper.gettimes(startTime, endTime, depName);
    }

    @Override
    public List<YwzCountTimes> getimporttimes(String startTime, String endTime) {
        return ywzCountTimesMapper.getimporttimes(startTime, endTime);
    }

    @Override
    public List<YwzCountTimes> getedittimes(String startTime, String endTime) {
        return ywzCountTimesMapper.getedittimes(startTime, endTime);
    }

    @Override
    public List<YwzCountTimes> doctorpatienttimes(String startTime, String endTime) {
        return ywzCountTimesMapper.doctorpatienttimes(startTime, endTime);
    }

    @Override
    public List<YwzCountTimes> getDoctorimporttimes(String startTime, String endTime) {
        return ywzCountTimesMapper.getDoctorimporttimes(startTime, endTime);
    }

    @Override
    public void addTimes(YwzCountTimes ywzCountTimes, Integer type) {
        ywzCountTimes.setCreateTime(sdf1.format(new Date()));
        String doctorName = null;
        if (ywzCountTimes.getType() == 5) {
            doctorName = (null == ywzCountTimes.getDoctorName() ? null : ywzCountTimes.getDoctorName());
            ywzCountTimes.setDoctorName(null);
        }
        YwzCountTimes ywzCountTimes1 = sel(ywzCountTimes);
        if (null == ywzCountTimes1) {
            ywzCountTimes.setCreateTime(sdf.format(new Date()));
            ywzCountTimes.setDoctorName(doctorName);
            ywzCountTimesMapper.insert(ywzCountTimes);
        } else {
            if (ywzCountTimes.getType() == 5) {
                ywzCountTimes1.setDoctorName(doctorName);
                ywzCountTimesMapper.updateByPrimaryKeySelective(ywzCountTimes1);
            }
        }
    }

    @Override
    public List<YwzCountTimes> userAndimport(String startTime, String endTime, String depName) {
        return ywzCountTimesMapper.userAndimport(startTime, endTime, depName);
    }

    @Override
    public List<YwzCountTimes> doctorShowTimes(String startTime, String endTime, String depName) {
        return ywzCountTimesMapper.doctorShowTimes(startTime, endTime, depName);
    }

    @Override
    public List<YwzCountTimes> editTimes(String startTime, String endTime) {
        return ywzCountTimesMapper.editTimes(startTime, endTime);
    }

    @Override
    public List<YwzCountTimes> importTimes(String startTime, String endTime, String depName) {
        return ywzCountTimesMapper.importTimes(startTime, endTime, depName);
    }

    @Override
    public List<YwzCountTimes> nodoctortimes(String startTime, String endTime) {
        return ywzCountTimesMapper.nodoctortimes(startTime, endTime);
    }

    @Override
    public void addTimes(YwzCountTimes ywzCountTimes) {

        if (ywzCountTimes.getType() != 1) {
            ywzCountTimes.setCreateTime(sdf1.format(new Date()));
        }

        String doctorName = null;
        String diseaseId = null;
        if (ywzCountTimes.getType() == 6) {
            doctorName = ywzCountTimes.getDoctorName() == null ? null : ywzCountTimes.getDoctorName();
            ywzCountTimes.setDoctorName(null);
        }
        if (ywzCountTimes.getType() == 5) {
            diseaseId = ywzCountTimes.getDiseaseId() == null ? null : ywzCountTimes.getDiseaseId();
            doctorName = ywzCountTimes.getDoctorName() == null ? null : ywzCountTimes.getDoctorName();
            ywzCountTimes.setDoctorName(null);
            ywzCountTimes.setDiseaseId(null);
        }
        if (ywzCountTimes.getType() == 7) {
            diseaseId = ywzCountTimes.getDiseaseId() == null ? null : ywzCountTimes.getDiseaseId();
        }
        if (ywzCountTimes.getType() == 4) {
            diseaseId = ywzCountTimes.getDiseaseId() == null ? null : ywzCountTimes.getDiseaseId();
        }

        //员工统计 type=10
        YwzCountTimes ywzCountTimes1 = sel(ywzCountTimes);
        if (null == ywzCountTimes1 || ywzCountTimes.getType() == 10) {
            ywzCountTimes.setCreateTime(sdf.format(new Date()));
            ywzCountTimes.setDoctorName(doctorName);
            ywzCountTimes.setDiseaseId(diseaseId);
            ywzCountTimes.setTimes(1);
            ywzCountTimesMapper.insert(ywzCountTimes);
        } else {
            if (ywzCountTimes.getType() == 5) {
                logger.info("获取url 存在医生了，次数为空 ");
                ywzCountTimes1.setDoctorName(doctorName);
                ywzCountTimes.setDiseaseId(diseaseId);
                ywzCountTimes1.setTimes(1);
                ywzCountTimesMapper.updateByPrimaryKeySelective(ywzCountTimes1);
            }
        }
    }

    @Override
    public YwzCountTimes sel(YwzCountTimes ywzCountTimes) {
        return ywzCountTimesMapper.sel(ywzCountTimes);
    }


}
