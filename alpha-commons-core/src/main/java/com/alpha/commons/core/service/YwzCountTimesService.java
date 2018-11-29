package com.alpha.commons.core.service;

import com.alpha.commons.core.pojo.YwzCountTimes;

import java.util.List;

/**
 * Created by HP on 2018/5/9.
 */

public interface YwzCountTimesService {

    YwzCountTimes sel(YwzCountTimes ywzCountTimes);

    void addTimes(YwzCountTimes ywzCountTimes);

    void addTimes(YwzCountTimes ywzCountTimes,Integer type);

    List<YwzCountTimes> gettimes(String startTime,  String endTime, String depName);

    List<YwzCountTimes> getimporttimes(String startTime,String endTime);

    List<YwzCountTimes> getedittimes(String startTime, String endTime);

    List<YwzCountTimes> getDoctorimporttimes(String startTime, String endTime);

    List<YwzCountTimes> doctorpatienttimes(String startTime, String endTime);

    List<YwzCountTimes> nodoctortimes(String startTime, String endTime);

    List<YwzCountTimes> userAndimport(String startTime, String endTime, String depName);

    List<YwzCountTimes> doctorShowTimes(String startTime, String endTime, String depName);

    List<YwzCountTimes> importTimes(String startTime, String endTime, String depName);

    List<YwzCountTimes> editTimes(String startTime, String endTime);
}
