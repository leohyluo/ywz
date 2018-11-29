package com.alpha.service.impl;

import com.alpha.commons.core.mapper.*;
import com.alpha.commons.core.pojo.inspcetion.*;
import com.alpha.pojo.ao.YSInspectAO;
import com.alpha.pojo.vo.*;
import com.alpha.service.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/16.
 */
@Service
public class YSInspectionServiceImpl implements YSInspectionService {

    @Autowired
    private JYRequestMapper jyRequestMapper;
    @Resource
    private XNDRequestMapper xndRequestMapper;
    @Resource
    private FSRequestMapper fsRequestMapper;
    @Resource
    private CSReportMapper csReportMapper ;

    @Autowired
    private JYJobService jyJobService;
    @Autowired
    private XNDJobService xndJobService;
    @Autowired
    private FSJobService fsJobService;

    @Override
    public List<YSInspcetVO> detailInfo(YSInspectAO ysInspectAO) {
        List<YSInspcetVO> list=new ArrayList<>();
        Map<String,String> param=new HashMap<>();
        param.put("startTime",ysInspectAO.getStartTime()==null?null:ysInspectAO.getStartTime());
        param.put("endTime",ysInspectAO.getEndTime()==null?null:ysInspectAO.getEndTime());
        param.put("patient_id",ysInspectAO.getMedicalCardNo()==null?null:ysInspectAO.getMedicalCardNo());
       //检验
        PageHelper.startPage(ysInspectAO.getPage()==null?1:ysInspectAO.getPage(),ysInspectAO.getPageSize()==null?10:ysInspectAO.getPageSize());
        List<JYRequest> jyRequests=jyRequestMapper.selectByParam(param);
        if(jyRequests.size()>0) {
            jyRequests.stream().forEach(e -> {
                YSInspcetVO ysInspcetVO=YSInspcetVO.parse(e);
                List<InspectionDetailVO> detailVOS=jyJobService.detailByReportId(ysInspcetVO.getReportNo());
                ysInspcetVO.setDetails(detailVOS);
                list.add(ysInspcetVO);
            });
        }
        //心脑电
        List<XNDrequestVO> xnDrequestVOList= XNDrequest(ysInspectAO);
        if(null !=xnDrequestVOList && xnDrequestVOList.size()>0){
              list.addAll(YSInspcetVO.parseXND(xnDrequestVOList));
        }
        //放射
        List<FSrequestVO> fSrequestVOList=FSrequest(ysInspectAO);
        if(null !=xnDrequestVOList && xnDrequestVOList.size()>0){
            list.addAll(YSInspcetVO.parseFS(fSrequestVOList));
        }
        //超声
        List<CSreportVO> cSreportVOList= CSrequest(ysInspectAO);
        if(null !=cSreportVOList && xnDrequestVOList.size()>0){
            list.addAll(YSInspcetVO.parseCS(cSreportVOList));
        }
        return list;
    }

    @Override
    public List<XNDrequestVO> XNDrequest(YSInspectAO ysInspectAO) {
        List<XNDrequestVO> list=new ArrayList<>();
        Map<String,String> param=new HashMap<>();
        param.put("startTime",ysInspectAO.getStartTime()==null?null:ysInspectAO.getStartTime());
        param.put("endTime",ysInspectAO.getEndTime()==null?null:ysInspectAO.getEndTime());
        param.put("patient_id",ysInspectAO.getMedicalCardNo()==null?null:ysInspectAO.getMedicalCardNo());
        PageHelper.startPage(ysInspectAO.getPage()==null?1:ysInspectAO.getPage(),ysInspectAO.getPageSize()==null?10:ysInspectAO.getPageSize());
        List<XNDRequest> jyRequests=xndRequestMapper.selectByParam(param);
        if(jyRequests.size()>0) {
            jyRequests.stream().forEach(e -> {
                XNDrequestVO vo=new XNDrequestVO();
                BeanUtils.copyProperties(e,vo);
                List<XNDResult> results=xndJobService.xnd_resultByReportId(e.getReportId());
                if(null != results && results.size()>0){
                    List<XNDresultVO> vos=new ArrayList<>();
                    results.stream().forEach(x -> {
                        XNDresultVO vo1=new XNDresultVO();
                        BeanUtils.copyProperties(x,vo1);
                        vos.add(vo1);
                    });
                    vo.setDetail(vos);
                }
                list.add(vo);
            });
        }
        return list;
    }

    @Override
    public List<FSrequestVO> FSrequest(YSInspectAO ysInspectAO) {
        List<FSrequestVO> list=new ArrayList<>();
        Map<String,String> param=new HashMap<>();
        param.put("startTime",ysInspectAO.getStartTime()==null?null:ysInspectAO.getStartTime());
        param.put("endTime",ysInspectAO.getEndTime()==null?null:ysInspectAO.getEndTime());
        param.put("patient_id",ysInspectAO.getMedicalCardNo()==null?null:ysInspectAO.getMedicalCardNo());
        PageHelper.startPage(ysInspectAO.getPage()==null?1:ysInspectAO.getPage(),ysInspectAO.getPageSize()==null?10:ysInspectAO.getPageSize());
        List<FSRequest> jyRequests=fsRequestMapper.selectByParam(param);
        if(jyRequests.size()>0) {
            jyRequests.stream().forEach(e -> {
                FSrequestVO vo=new FSrequestVO();
                BeanUtils.copyProperties(e,vo);
                List<FSResult> results=fsJobService.fs_resultByReportId(e.getExamSno());
                if(null != results && results.size()>0){
                    List<FSresultVO> vos=new ArrayList<>();
                    results.stream().forEach(x -> {
                        FSresultVO vo1=new FSresultVO();
                        BeanUtils.copyProperties(x,vo1);
                        vos.add(vo1);
                    });
                    vo.setDetail(vos);
                }
                list.add(vo);
            });
        }
        return list;
    }


    @Override
    public List<CSreportVO> CSrequest(YSInspectAO ysInspectAO) {

        List<CSreportVO> list=new ArrayList<>();
        Map<String,String> param=new HashMap<>();
        param.put("startTime",ysInspectAO.getStartTime()==null?null:ysInspectAO.getStartTime());
        param.put("endTime",ysInspectAO.getEndTime()==null?null:ysInspectAO.getEndTime());
        param.put("patient_id",ysInspectAO.getMedicalCardNo()==null?null:ysInspectAO.getMedicalCardNo());
        PageHelper.startPage(ysInspectAO.getPage()==null?1:ysInspectAO.getPage(),ysInspectAO.getPageSize()==null?10:ysInspectAO.getPageSize());
        List<CSReport> jyRequests=csReportMapper.selectByParam(param);
        if(jyRequests.size()>0) {
            jyRequests.stream().forEach(e -> {
                CSreportVO vo=new CSreportVO();
                BeanUtils.copyProperties(e,vo);
                list.add(vo);
            });
        }
        return list;
    }

}
