package com.alpha.commons.core.mapper;

import com.alpha.commons.MyMapper;
import com.alpha.commons.core.pojo.YwzCountTimes;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2018/5/9.
 */
public interface YwzCountTimesMapper extends MyMapper<YwzCountTimes> {

    YwzCountTimes sel(YwzCountTimes ywzCountTimes);

    List<YwzCountTimes> gettimes(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);

    List<YwzCountTimes> getimporttimes(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<YwzCountTimes> getedittimes(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<YwzCountTimes> getDoctorimporttimes(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<YwzCountTimes> doctorpatienttimes(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<YwzCountTimes> nodoctortimes(@Param("startTime") String startTime, @Param("endTime") String endTime);


    List<YwzCountTimes> editTimes(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<YwzCountTimes> importTimes(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);

    List<YwzCountTimes> doctorShowTimes(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);

    List<YwzCountTimes> userAndimport(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);

    /**
     * 预约推送量
     * @param startTime
     * @param endTime
     * @param depName
     * @return
     */
    Integer pushAppointmentTimes(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);

    /**
     * 预约推送成功量
     * @param startTime
     * @param endTime
     * @param depName
     * @return
     */
    Integer pushAppointmentSuccessTimes(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);

    /**
     * 现场取号量推送量
     * @param startTime
     * @param endTime
     * @return
     */
    Integer pushLiveTimes(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);

    /**
     * 取号推送成功量
     * @param startTime
     * @param endTime
     * @param depName
     * @return
     */
    Integer pushLiveSuccessTimes(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);

    Integer scanSuccessTimes1(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Integer scanSuccessTimes2(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Integer firstVisitTimes(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);

    Integer firstVisitTimes1(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);

    Integer firstVisitTimes2(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);

    Integer secondVisitTimes(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Integer firstNoCoverTimes(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<Map<String, String>> getClik(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);

    List<Map<String, String>> getSubmit(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("depName") String depName);
}
