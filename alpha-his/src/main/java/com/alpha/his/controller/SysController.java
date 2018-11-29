package com.alpha.his.controller;

import com.alpha.commons.core.annotation.JSONS;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.WebUtils;
import com.alpha.his.service.etyy.SysDictService;
import com.alpha.his.service.etyy.SysNationService;
import com.alpha.his.service.etyy.SysNationalityService;
import com.alpha.server.rpc.his.pojo.SysConstant;
import com.alpha.server.rpc.his.pojo.SysDict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SysController {

    @Resource
    SysNationService sysNationService;
    @Resource
    SysNationalityService sysNationalityService;

    @Resource
    SysDictService sysDictService;

    @JSONS({
            @com.alpha.commons.core.annotation.JSON(type = SysConstant.class,exclude = "createTime,updateTime")
    })
    @GetMapping("/nation/query")
    public ResponseMessage queryNation(SysConstant constant) {
        return WebUtils.buildSuccessResponseMessage(sysNationService.query(constant));
    }

    @JSONS({
            @com.alpha.commons.core.annotation.JSON(type = SysConstant.class,exclude = "createTime,updateTime")
    })
    @GetMapping("/nationality/query")
    public ResponseMessage queryNationality(SysConstant constant) {
        return WebUtils.buildSuccessResponseMessage(sysNationalityService.query(constant));
    }

    @JSONS({
            @com.alpha.commons.core.annotation.JSON(type = SysDict.class,exclude = "createTime,updateTime")
    })
    @GetMapping("/dict/query")
    public ResponseMessage queryDict(SysDict sysDict) {
        return WebUtils.buildSuccessResponseMessage(sysDictService.query(sysDict));
    }

}
