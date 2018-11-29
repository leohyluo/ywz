package com.alpha.service;

import com.alibaba.fastjson.JSON;
import com.alpha.commons.core.his.OutPatientService;
import com.alpha.commons.core.mapper.HisRegisterYyghMapper;
import com.alpha.commons.core.pojo.HisRegisterYygh;
import com.alpha.commons.core.pojo.NJResponse;
import com.alpha.commons.core.pojo.RegisterDTO;
import com.alpha.commons.core.util.DeptUtils;
import com.alpha.commons.core.util.MapToJsonUtil;
import com.alpha.fegin.SchedualServiceDataAdvance;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edz on 2018/10/18.
 * 南京逸夫医院
 */
@Service
public class YIFUYYJOBImpl implements OutPatientService {
    private static Logger logger = LoggerFactory.getLogger(YIFUYYJOBImpl.class);

    @Autowired
    private HisRegisterYyghMapper hisRegisterYyghMapper;

    @Resource
    private SchedualServiceDataAdvance schedualServiceDataAdvance;

    @Value("${Nj_WDSL}")
    private String NJwsdl;

    @Override
    public void NoticeData(String startTime, String endTime) {
        logger.info("南京逸夫医院 没有开启住院业务");
    }

    @Override
    public List<RegisterDTO> registrationInfo(String outPatientNo, String visitTime) {
        return null;
    }

    @Override
    public Object patientInfo(String cardNo) {
        return null;
    }

    @Override
    public List<HisRegisterYygh> hisRegisterYyghInfo(String startTime, String endTime) {
        logger.info("南京逸夫医院");
        long a=System.currentTimeMillis();
        Map map=new HashMap();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        String param= MapToJsonUtil.map2Json(map);
        logger.info("定时请求参数是：{}",param);
        try {
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(NJwsdl);
            Object[] objects = new Object[0];
            objects = client.invoke("DoTrans","9002",param);
            String data=(String)objects[0];
            NJResponse<HisRegisterYygh> response = null;
            try {
                response = JSON.parseObject(data,NJResponse.class);
                if(response.getResult().equals("success") && response.getData().size()>0){
                    List<HisRegisterYygh> list =JSON.parseArray(JSON.parseObject(data).getString("data"),HisRegisterYygh.class);
                    List<HisRegisterYygh> list1 =new ArrayList<>();
                    List<String> department=DeptUtils.getDepartment();
                    list.stream().forEach( e -> {
                        department.forEach(x -> {
                            if(e.getDeptName().contains(x))
                                e.setSex(e.getSex().equals("1")?"男":"女");
                            list1.add(e);
                        });
                    });
                    if(list1.size()>0){
                        logger.info("批量插入挂号信息: {} 条",list1.size());
                        hisRegisterYyghMapper.insertBatch(list1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("共计耗时：{} 毫秒",(System.currentTimeMillis()-a));
        return null;
    }


    public void push(){
        List<HisRegisterYygh> registerList = hisRegisterYyghMapper.listNeedPushRecord();
        if(null !=registerList && registerList.size()>0){
            registerList.stream().forEach(e -> {
                schedualServiceDataAdvance.invokeDataAdvanceService(e);
            });
        }
    }

}
