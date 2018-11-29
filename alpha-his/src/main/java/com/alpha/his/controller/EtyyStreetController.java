package com.alpha.his.controller;

import com.alpha.commons.core.mapper.StreetMapper;
import com.alpha.commons.core.pojo.Street;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by HP on 2018/7/11.
 */
@RestController
@RequestMapping("/street")
public class EtyyStreetController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private StreetMapper streetMapper;

    @GetMapping("/{address}")
    public ResponseMessage getStreetByName(@PathVariable String address){
        logger.info("查询街道办");
        try {
            List<String> list =streetMapper.getStreetByName(address);
            //湖北省襄阳市襄州区深圳工业园@
            list = list.stream().map(e->{
                String result = e.replace("@", "");
                return result;
            }).collect(Collectors.toList());
            return WebUtils.buildSuccessResponseMessage(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }

}
