package com.alpha.user.controller;

import com.alpha.commons.core.pojo.YwzCountTimes;
import com.alpha.commons.core.service.YwzCountTimesService;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.ResponseStatus;
import com.alpha.commons.web.WebUtils;
import com.alpha.user.pojo.vo.ResultVo;
import com.alpha.user.service.CountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by HP on 2018/5/3.
 * 儿童医院统计数据接口
 *
 */
@RestController
@RequestMapping("count")
public class YwzCountTimesController {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

    @Resource
    private YwzCountTimesService ywzCountTimesService;

    @Resource
    private CountService countService;

    @GetMapping("/ywztimes/{startTime}/{endTime}")
    public ResponseMessage ywztimes(@PathVariable String startTime,@PathVariable String endTime ){
        Map<Object,Object> map=new HashMap<>();
        if(!StringUtils.isBlank(startTime) && !StringUtils.isBlank(endTime)){
            try {
                if(!endTime.contains(":") && !startTime.contains(":")){
                    Date datestart =sdf.parse(startTime);
                    Date dateend =sdf.parse(endTime);
                      startTime=sdf2.format(datestart);
                      endTime=sdf1.format(dateend);
                }

                List<YwzCountTimes> typelist=  ywzCountTimesService.gettimes(startTime,endTime, null);
                List<YwzCountTimes> editlist=  ywzCountTimesService.getedittimes(startTime,endTime);
                List<YwzCountTimes> importlist=  ywzCountTimesService.getimporttimes(startTime,endTime);
                List<YwzCountTimes> doctorlist=  ywzCountTimesService.getDoctorimporttimes(startTime,endTime);
                List<YwzCountTimes> patientlist=  ywzCountTimesService.doctorpatienttimes(startTime,endTime);
                List<YwzCountTimes> nodoctorlist=  ywzCountTimesService.nodoctortimes(startTime,endTime);

                //综合统计
                Map typelistmap=new HashMap();
                if(null != typelist && typelist.size()>0){
                    typelistmap= typelist.stream().collect(Collectors.toMap(YwzCountTimes::getType, Function.identity()));
                }
                Map editlistmap= new HashMap();
                if(null != editlist && editlist.size()>0) {
                    editlistmap = editlist.stream().collect(Collectors.toMap(YwzCountTimes::getDescri, Function.identity()));
                }
                Map importlistmap =new HashMap();
                if(null != importlist && importlist.size()>0){
                     importlistmap=importlist.stream().collect(Collectors.toMap(YwzCountTimes::getDescri, Function.identity()));
                }

                Map typeMap=new HashMap();
                if(null !=typelistmap.get(1)){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(typelistmap.get(1));
                    typeMap.put("开卡次数",ywzCountTimes.getTimes());
                }else {
                    typeMap.put("开卡次数",0);
                }
                if(null !=typelistmap.get(2)){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(typelistmap.get(2));
                    typeMap.put("扫码次数",ywzCountTimes.getTimes());
                }else {
                    typeMap.put("扫码次数",0);
                }
                if(null !=typelistmap.get(3)){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(typelistmap.get(3));
                    typeMap.put("进入预问诊次数",ywzCountTimes.getTimes());
                }else {
                    typeMap.put("进入预问诊次数",0);
                }
                if(null !=typelistmap.get(4)){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(typelistmap.get(4));
                    typeMap.put("预问诊提交次数",ywzCountTimes.getTimes());
                }else {
                    typeMap.put("预问诊提交次数",0);
                }
                if(null !=typelistmap.get(5)){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(typelistmap.get(5));
                    typeMap.put("电子病历打开次数",ywzCountTimes.getTimes());
                }else {
                    typeMap.put("电子病历打开次数",0);
                }

                int pasize=0;
                int nosize=(null == nodoctorlist || nodoctorlist.size()<1)?0:nodoctorlist.size();
                if(null != patientlist && patientlist.size()>0){
                  for (YwzCountTimes ywzCountTimes:patientlist){
                      pasize+=ywzCountTimes.getTimes();
                  }
                }
                typeMap.put("患者被导入人数",pasize+nosize);

                if(null !=typelistmap.get(7)){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(typelistmap.get(7));
                    typeMap.put("医生修改次数",ywzCountTimes.getTimes());
                }else {
                    typeMap.put("医生修改次数",0);
                }

                //编辑详情
                Map editMap=new HashMap();
                if(null !=editlistmap.get("71")){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(editlistmap.get("71"));
                    editMap.put("修改主诉次数",ywzCountTimes.getTimes());
                }else {
                    editMap.put("修改主诉次数",0);
                }
                if(null !=editlistmap.get("72")){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(editlistmap.get("72"));
                    editMap.put("修改现病史次数",ywzCountTimes.getTimes());
                }else {
                    editMap.put("修改现病史次数",0);
                }
                if(null !=editlistmap.get("73")){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(editlistmap.get("73"));
                    editMap.put("修改既往史次数",ywzCountTimes.getTimes());
                }else {
                    editMap.put("修改既往史次数",0);
                }
                if(null !=editlistmap.get("74")){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(editlistmap.get("74"));
                    editMap.put("修改诊断结果次数",ywzCountTimes.getTimes());
                }else {
                    editMap.put("修改诊断结果次数",0);
                }

                //导入详情
                Map importMap=new HashMap();
                if(null !=importlistmap.get("61")){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(importlistmap.get("61"));
                    importMap.put("导入主诉次数",ywzCountTimes.getTimes());
                }else {
                    importMap.put("导入主诉次数",0);
                }
                if(null !=importlistmap.get("62")){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(importlistmap.get("62"));
                    importMap.put("导入现病史次数",ywzCountTimes.getTimes());
                }else {
                    importMap.put("导入现病史次数",0);
                }
                if(null !=importlistmap.get("63")){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(importlistmap.get("63"));
                    importMap.put("导入既往史次数",ywzCountTimes.getTimes());
                }else {
                    importMap.put("导入既往史次数",0);
                }
                if(null !=importlistmap.get("64")){
                    YwzCountTimes ywzCountTimes=(YwzCountTimes)(importlistmap.get("64"));
                    importMap.put("导入诊断结果次数",ywzCountTimes.getTimes());
                }else {
                    importMap.put("导入诊断结果次数",0);
                }

                //具体医生导入详情
                Map doctormap=new HashMap();
                if(null != doctorlist && doctorlist.size()>0){
                    Set<String> nameset=new HashSet<>();
                    doctorlist.stream().forEach(e -> nameset.add(e.getDoctorName()));
                    List<String> doclist=new ArrayList<>(nameset);
                    for (String doctor:doclist){

                        Integer a=0;
                        Integer b=0;
                        Integer c=0;
                        Integer d=0;
                        for(YwzCountTimes ywzCountTimes:doctorlist){
                            if(doctor.equals(ywzCountTimes.getDoctorName())) {
                                if ("61".equals(ywzCountTimes.getDescri())) {
                                    a += ywzCountTimes.getTimes();
                                }
                                if ("62".equals(ywzCountTimes.getDescri())) {
                                    b += ywzCountTimes.getTimes();
                                }
                                if ("63".equals(ywzCountTimes.getDescri())) {
                                    c += ywzCountTimes.getTimes();
                                }
                                if ("64".equals(ywzCountTimes.getDescri())) {
                                    d += ywzCountTimes.getTimes();
                                }
                            }
                        }
                        Map map1=new HashMap();
                        map1.put("导入主诉次数",a);
                        map1.put("导入现病史次数",b);
                        map1.put("导入既往史次数",c);
                        map1.put("导入诊断结果次数",d);
                        doctormap.put(doctor,map1);
                    }
                }
                //医生导入人次统计
                Map patimeMap=new HashMap();
                if(null != patientlist && patientlist.size()>0){
                    patientlist.stream().forEach(e -> {
                        patimeMap.put(e.getDoctorName(),e.getTimes());
                    });
                }
                //获取不到医生名字的情况
                if(null != nodoctorlist && nodoctorlist.size()>0){
                    patimeMap.put("未知姓名",nodoctorlist.size());
                }

               //返回结果统计
                map.put("综合统计",typeMap);
                map.put("医生编辑病历模块统计",editMap);
                map.put("医生导入病历模块统计",importMap);
                map.put("具体医生导入模块详情统计",doctormap);
                map.put("医生导入人次统计",patimeMap);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
           return    WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        return WebUtils.buildSuccessResponseMessage(map);
    }

    @GetMapping("/ywzcount/{startTime}/{endTime}")
    public ResponseMessage ywzcount(@PathVariable String startTime,@PathVariable String endTime){
        if(com.alpha.commons.util.StringUtils.isEmpty(startTime,endTime)){
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        if(checkDate(startTime,endTime)){
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        ResultVo resultVo=countService.ywztimesnew(startTime,endTime);
        return WebUtils.buildSuccessResponseMessage(resultVo);
    }

    @PostMapping("/ywzcountnew/{startTime}/{endTime}/{depName}")
    public ResponseMessage ywzcountOfDepType(@PathVariable String startTime,@PathVariable String endTime, @PathVariable String depName){
        if(com.alpha.commons.util.StringUtils.isEmpty(startTime,endTime)){
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        if(checkDate(startTime,endTime)){
            return WebUtils.buildResponseMessage(ResponseStatus.REQUIRED_PARAMETER_MISSING);
        }
        ResultVo resultVo=countService.ywztimesnew(startTime, endTime, depName);
        return WebUtils.buildSuccessResponseMessage(resultVo);
    }


    public static boolean checkDate(String startTime,String endTime){
        try {
            long star=sdf.parse(startTime).getTime();
            long end=sdf.parse(endTime).getTime();
            long a=1000*60*60*24L;
            end =end +a;
            if (end-star< a){
               return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
