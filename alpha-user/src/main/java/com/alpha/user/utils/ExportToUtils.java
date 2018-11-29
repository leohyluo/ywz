package com.alpha.user.utils;

import com.alpha.commons.core.util.ParamUtil;
import com.alpha.user.pojo.vo.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2018/5/18.
 */
public class ExportToUtils {

    public static void createCountExcel(HttpServletRequest request, HttpServletResponse response, ResultVo resultVo) {

        String[] cellValues = {"时间", "时段", "预约推送量", "预约推送成功量", "预约点击量", "预约提交量",
                                               "取号推送量", "取号推送成功量", "取号点击量", "取号提交量",
            "扫码量","扫码完成量","就诊用户完成量","病历展现量","病历导入量","医生使用人数"};
        String[] titlename = {"flag", "pushAppointmentTimes", "pushAppointmentSuccessTimes", "pushAppointmentClickTimes", "pushAppointmentSubmitTimes" ,
                                       "pushLiveTimes","pushLiveSuccessTimes", "pushLiveClickTimes", "pushLiveSubmitTimes",
                "scanTimes","scanSuccessTimes","wzSuccessTimes","ECHopenTiems", "ECHimportTimes", "doctorNum"};


        String[] ce = {"时间", "时段", "医生名字", "展现患者病历数", "导入患者个数"};
        String[] tit = {"docName", "showTimes", "times"};

        String[] ce1 = {"时间", "时段", "主诉", "现病史", "既往史","诊断结果"};
        String[] tit1 = {"flage","mainSymptomName", "presentIllnessHistory", "pastmedicalHistory","diseases"};

        Integer[] columnWidths = {25, 15, 15, 15, 15, 15, 15, 15};

        String titile = "预问诊使用统计";
        HSSFWorkbook hss = new HSSFWorkbook();
        HSSFSheet sheet = hss.createSheet(titile);

        //综合统计表格
        List<CountShowVO> allresult = resultVo.getAllresult();

        //居中
        HSSFCellStyle style = hss.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFRow row = sheet.createRow(sheet.getPhysicalNumberOfRows());//第一行

        HSSFCell cell = row.createCell(0);
        cell.setCellValue("综合统计信息");
        cell.setCellStyle(style);
        CellRangeAddress cra = new CellRangeAddress(0, 0, 0, 7);
        sheet.addMergedRegion(cra);
        for(int a=0;a<columnWidths.length;a++){
            int width=columnWidths[a];
            sheet.setColumnWidth(a, width * 256);
        }

        row = sheet.createRow( sheet.getPhysicalNumberOfRows());//第二行

        HSSFCell cell1 = row.createCell(0);
        for (int j = 0; j < cellValues.length; j++) {
            cell1 = row.createCell(j);
            cell1.setCellValue(cellValues[j]);
            cell1.setCellStyle(style);
        }

        for (int i = 0; i < allresult.size(); i++) {
            CountShowVO countShowVO = allresult.get(i);
            //数据
            String date = countShowVO.getDate();
            row=sheet.createRow(sheet.getPhysicalNumberOfRows());
            HSSFCell ccx=row.createCell(0);
            ccx.setCellValue(date);
            ccx.setCellStyle(style);
            CellRangeAddress XX=new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum()+2, 0, 0);
            sheet.addMergedRegion(XX);
            List<CountTimesVo> countTimesVoList = (List<CountTimesVo>) countShowVO.getList();
            for (int m = 0; m < countTimesVoList.size();m++) {
                CountTimesVo countTimesVo=countTimesVoList.get(m);
                Map map=null;
                try {
                     map= ParamUtil.objectToMap(countTimesVo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int n = 0; n < 15; n++) {
                    HSSFCell c = row.createCell(n + 1);
                    c.setCellValue(String.valueOf(map.get(titlename[n])));
                    c.setCellStyle(style);
                }
                row = sheet.createRow(sheet.getPhysicalNumberOfRows());
            }

        }

        //医生导入展现人数，导入人数 统计
        //医生导入人数详细统计
        List<CountShowVO> docDetail = resultVo.getDocDetail();
        row=sheet.createRow(sheet.getLastRowNum()+1);
        HSSFCell cell2=row.createCell(0);
        cell2.setCellValue("医生导入人数详细统计");
        cell2.setCellStyle(style);
        CellRangeAddress cra1 = new CellRangeAddress(sheet.getLastRowNum(), sheet
                .getLastRowNum()
                , 0, 7);
        sheet.addMergedRegion(cra1);
        row = sheet.createRow( sheet.getLastRowNum()+1);
        HSSFCell cell4 = row.createCell(0);
        for (int j = 0; j < ce.length; j++) {
            cell4 = row.createCell(j);
            cell4.setCellValue(ce[j]);
            cell4.setCellStyle(style);
        }
        for (int i = 0; i < docDetail.size(); i++) {
            CountShowVO docDetailVo = docDetail.get(i);//某一天
            List<DocDetailVo> doctorTimesList = (List<DocDetailVo>) docDetailVo.getList();//上下午总 三条
            int total=0;//计算出上下午总的条数 来合并0
            int am=0; //上午医生数，pm=total - am -1
            int pm=0;
            for (DocDetailVo vo : doctorTimesList) {
                total=total+vo.getDoctorTimes().size();
                if(vo.getFlag().equals("AM")){
                    am=am+vo.getDoctorTimes().size();
                }
                if(vo.getFlag().equals("PM")){
                    pm=pm+vo.getDoctorTimes().size();
                }
            }
            //数据
            String date = docDetailVo.getDate();
            row=sheet.createRow(sheet.getLastRowNum()+1);
            HSSFCell ccx=row.createCell(0);
            ccx.setCellValue(date);
            ccx.setCellStyle(style);
            CellRangeAddress qq=new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum()+total+1, 0, 0);
            sheet.addMergedRegion(qq);
            for (DocDetailVo vo : doctorTimesList) {
                String flag=vo.getFlag();
                HSSFCell c1=row.createCell(1);
                c1.setCellValue(flag);
                c1.setCellStyle(style);
                if(flag.equals("AM")){
                    CellRangeAddress X=new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum()+am, 1, 1);
                    sheet.addMergedRegion(X);
                }
                if(flag.equals("PM")){
                    CellRangeAddress X=new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum()+pm, 1, 1);
                    sheet.addMergedRegion(X);
                }

                if(flag.equals("总计")){
                    CellRangeAddress X=new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum()+total-am-pm, 1, 1);
                    sheet.addMergedRegion(X);
                }
                List<DoctorTimes> list=vo.getDoctorTimes();
                for (DoctorTimes doctorTimes : list) {
                    try {
                        Map  map= ParamUtil.objectToMap(doctorTimes);
                        for (int j = 0; j <3 ; j++) {
                            HSSFCell c = row.createCell(j + 2);
                            c.setCellValue(String.valueOf(map.get(tit[j])));
                            c.setCellStyle(style);
                        }
                        row=sheet.createRow(sheet.getLastRowNum()+1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                row=sheet.createRow(sheet.getLastRowNum()+1);
            }
        }

      //模块导入详情
        List<CountShowVO> importDetail = resultVo.getImportDetail();
        String xx="医生导入病历模块统计";
        setSheet(sheet,row,style,ce1,tit1,importDetail,xx);


      //模块编辑详情
        List<CountShowVO> editDetail = resultVo.getEditDetail();
        String aa="医生编辑病历模块统计";
        setSheet(sheet,row,style,ce1,tit1,editDetail,aa);

        OutputStream ouputStream = null;
        try
        {
            String agent = request.getHeader("User-Agent");
            boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
            String FileName ="";
            if(isMSIE){
                FileName=java.net.URLEncoder.encode(titile,"UTF8");
            }else{
                FileName = new String(titile.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename="+FileName+".xls");
            ouputStream = response.getOutputStream();
            hss.write(ouputStream);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally{
            try{
                ouputStream.flush();
                ouputStream.close();
            }catch(Exception e){

            }

        }

    }

public static void setSheet(HSSFSheet sheet,HSSFRow row,HSSFCellStyle style,String[] ce,String[] tit, List<CountShowVO>
        importDetail,String title){
    row=sheet.createRow(sheet.getLastRowNum()+1);
    HSSFCell cell7=row.createCell(0);
    cell7.setCellValue(title);
    cell7.setCellStyle(style);
    CellRangeAddress Xss=new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum(), 0, 7);
    sheet.addMergedRegion(Xss);

    row=sheet.createRow(sheet.getLastRowNum()+1);
    HSSFCell cell8=row.createCell(0);
    for (int j = 0; j < ce.length; j++) {
        cell8 = row.createCell(j);
        cell8.setCellValue(ce[j]);
        cell8.setCellStyle(style);
    }

    for (CountShowVO countShowVO : importDetail) {
        String date =countShowVO.getDate();
        List<ImportAndEditDetailVo> list =(List<ImportAndEditDetailVo>)countShowVO.getList();
        row=sheet.createRow(sheet.getLastRowNum()+1);
        HSSFCell cell6=row.createCell(0);
        cell6.setCellValue(date);
        cell6.setCellStyle(style);
        CellRangeAddress kk=new CellRangeAddress(sheet.getLastRowNum(), sheet.getLastRowNum()+2, 0, 0);
        sheet.addMergedRegion(kk);

        for (ImportAndEditDetailVo importAndEditDetailVo : list) {
            try {
                Map  map= ParamUtil.objectToMap(importAndEditDetailVo);
                for (int i = 0; i < tit.length; i++) {
                    HSSFCell c = row.createCell(i + 1);
                    c.setCellValue(String.valueOf(map.get(tit[i])));
                    c.setCellStyle(style);
                }
                row=sheet.createRow(sheet.getLastRowNum()+1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        row=sheet.createRow(sheet.getLastRowNum()+1);
    }






}


//    public static void createExcel(HttpServletRequest request,HttpServletResponse response,String title,Integer[] columnWidths,String[] cellValues,List<Map<String,Object>> list,String[] titlename){
//        HSSFWorkbook hss = new HSSFWorkbook();
//        HSSFSheet sheet = hss.createSheet(title);
//        HSSFRow row = sheet.createRow(0);
//        for(int i=0;i<columnWidths.length;i++){
//            int width=columnWidths[i];
//            sheet.setColumnWidth(i, width * 256);
//        }
//
//        HSSFCellStyle style = hss.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
//        sheet.setDefaultColumnStyle(0, style);
//        HSSFCell cell = row.createCell(0);
//        for(int i=0;i<cellValues.length;i++){
//            cell = row.createCell(i);
//            cell.setCellValue(cellValues[i]);
//            cell.setCellStyle(style);
//
//        }
//        for (int i = 0; i < list.size(); i++){
//            row = sheet.createRow(i + 1);
//            Map<String,Object> map = list.get(i);
//            for(int j=0;j<titlename.length;j++){
//                HSSFCell cells = row.createCell(j);
//                Object value=map.get(titlename[j]);
//                String valuestr=(value==null)?"":value.toString();
//                cells.setCellValue(valuestr);
//                cells.setCellStyle(style);
//            }
//        }
//
//
//
//
//        OutputStream ouputStream = null;
//        try
//        {
//            String agent = request.getHeader("User-Agent");
//            boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
//            String FileName ="";
//            if(isMSIE){
//                FileName=java.net.URLEncoder.encode(title,"UTF8");
//            }else{
//                FileName = new String(title.getBytes("UTF-8"), "ISO-8859-1");
//            }
//            response.setHeader("Pragma", "No-cache");
//            response.setHeader("Cache-Control", "no-cache");
//            response.setDateHeader("Expires", 0);
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-disposition", "attachment;filename="+FileName+".xls");
//            ouputStream = response.getOutputStream();
//            hss.write(ouputStream);
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }finally{
//            try{
//                ouputStream.flush();
//                ouputStream.close();
//            }catch(Exception e){
//
//            }
//
//        }
//    }



}
