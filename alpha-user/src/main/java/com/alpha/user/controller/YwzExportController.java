package com.alpha.user.controller;

import com.alpha.user.pojo.vo.ResultVo;
import com.alpha.user.service.CountService;
import com.alpha.user.utils.ExportToUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by HP on 2018/5/18.
 */
@Controller
@RequestMapping("/export")
public class YwzExportController {
    /**
     * data1/index.html 数据导出：
     *
     * @param request
     * @param response
     * @param startTime
     * @param endTime
     *
     */

    @Resource
    CountService countService;

    @RequestMapping(value = "/exportCount/{startTime}/{endTime}",method = RequestMethod.GET)
    public void exportCount(HttpServletRequest request, HttpServletResponse response, @PathVariable String startTime,
                            @PathVariable String endTime){
        ResultVo resultVo=countService.ywztimesnew(startTime,endTime);
        ExportToUtils.createCountExcel(request,response,resultVo);

    }

    @RequestMapping(value = "/exportCount/dep/{startTime}/{endTime}/{depName}",method = RequestMethod.GET)
    public void exportCountDep(HttpServletRequest request, HttpServletResponse response, @PathVariable String startTime,
                            @PathVariable String endTime, @PathVariable String depName){
        ResultVo resultVo=countService.ywztimesnew(startTime,endTime,depName);
        ExportToUtils.createCountExcel(request,response,resultVo);

    }
}
