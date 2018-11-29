package com.alpha.user.service;

import com.alpha.user.pojo.vo.CountTimesVo;
import com.alpha.user.pojo.vo.DocDetailVo;
import com.alpha.user.pojo.vo.ImportAndEditDetailVo;
import com.alpha.user.pojo.vo.ResultVo;

import java.util.List;

/**
 * Created by HP on 2018/5/19.
 */
public interface CountService {

    ResultVo ywztimesnew(String startTime, String endTime);

    void selTimes(String startTime, String endTime, CountTimesVo countTimesVo, DocDetailVo docDetailVo,
                  ImportAndEditDetailVo importAndEditDetailVo, ImportAndEditDetailVo importAndEditDetailVo1);

    void selTimesnew(String startTime, String endTime, CountTimesVo countTimesVo, DocDetailVo docDetailVo,
                  ImportAndEditDetailVo importAndEditDetailVo, ImportAndEditDetailVo importAndEditDetailVo1);

    void add(CountTimesVo count,CountTimesVo am,CountTimesVo pm);

    void add2(ImportAndEditDetailVo importAndEditDetailVocount, ImportAndEditDetailVo importAndEditDetailVoAM,
              ImportAndEditDetailVo importAndEditDetailVoPM);

    void add1(DocDetailVo docDetailVocount, DocDetailVo docDetailVoAM, DocDetailVo docDetailVoPM);

    List<String> countDate(String startTime, String endTime);

    ResultVo ywztimesnew(String startTime, String endTime, String depName);

}
