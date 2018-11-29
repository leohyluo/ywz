package com.alpha.his;


import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * Created by edz on 2018/10/23.
 */
public class xxtest {
    public static void main(String[] args) {
        String xx="<html>\n" +
                "<head>\n" +
                "<title>深 圳 市 儿 童 医 院</title>\n" +
                "<style type=\"text/css\">\n" +
                ".table1 {\n" +
                "BORDER-RIGHT: #000000 0px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; BORDER-BOTTOM: #000000 0px solid\n" +
                "}\n" +
                ".td1 {\n" +
                "\tBORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 0px solid; BORDER-LEFT: #000000 0px solid; BORDER-BOTTOM: #000000 1px solid\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body bgcolor=\"#005757\">\n" +
                "<table width=\"794\" align=\"center\"  border=\"1\" cellspacing=\"0\" bordercolor=black  rules=none bgcolor=\"#FFFFFF\">\n" +
                "<tr><td>\n" +
                "<table width=\"492\" align=\"center\">\n" +
                "<tr><td>\n" +
                "<table  height=\"21\" align=\"center\">\n" +
                "<tr><td>\n" +
                "</td></tr>\n" +
                "</table>\n" +
                "<p><span style=\"font-family: 宋体; font-size: 16px;\">&nbsp;</span></p>\n" +
                "<table  height=\"36\" align=\"center\">\n" +
                "<tr><td>\n" +
                "</td></tr>\n" +
                "</table>\n" +
                "<p><span style=\"font-family: 宋体; font-size: 16px;\">&nbsp;</span></p>\n" +
                "<table width=\"492\" height=\"252\" class=\"table1\" cellSpacing=0 cellPadding=0 >\n" +
                "<tr style=\"display:none\">\n" +
                "<td width=\"164\">&nbsp;</td>\n" +
                "<td width=\"164\">&nbsp;</td>\n" +
                "<td width=\"164\">&nbsp;</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=td1 colspan=\"3\">\n" +
                "<p align=\"center\"><span style=\"font-family: 楷体; font-size: 21px;font-weight: bold;\">初</span><span style=\"font-family: 楷体; font-size: 21px;font-weight: bold;\">诊病历记录</span></p>\n" +
                "<p align=\"center\"><span style=\"font-family: 楷体; font-size: 14px;\">First&nbsp;Diagnosis&nbsp;Record</span></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=td1>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;\">&nbsp;</span><span style=\"font-family: 楷体; font-size: 14px;\">日期Date\t:</span></p>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;\">2018-05-20&nbsp;09:15</span></p>\n" +
                "</td>\n" +
                "<td class=td1>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;\">医疗机构hospital:</span></p>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;\">深圳市儿童医院</span></p>\n" +
                "</td>\n" +
                "<td class=td1>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;\">科别Section:</span></p>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;\">内科门诊</span></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=td1 colspan=\"3\">\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;font-weight: bold;\">姓名:</span><span style=\"font-family: 楷体; font-size: 14px;\">陈扬彬</span><span style=\"font-family: 楷体; font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-family: 楷体; font-size: 14px;font-weight: bold;\">性别:</span><span style=\"font-family: 楷体; font-size: 14px;\">男</span><span style=\"font-family: 楷体; font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-family: 楷体; font-size: 14px;font-weight: bold;\">年龄:</span><span style=\"font-family: 楷体; font-size: 14px;\">10岁</span></p>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;font-weight: bold;\">电话:</span><span style=\"font-family: 楷体; font-size: 14px;\">13632967561</span><span style=\"font-family: 楷体; font-size: 14px;font-weight: bold;\">&nbsp;&nbsp;&nbsp;门诊号码:</span><span style=\"font-family: 楷体; font-size: 14px;\">0712233190</span></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=td1 colspan=\"3\">\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;font-weight: bold;\">主&nbsp;&nbsp;诉:</span><span style=\"font-family: 楷体; font-size: 14px;\">反复头晕1-2月</span></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=td1 colspan=\"3\">\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;font-weight: bold;\">现病史:</span><span style=\"font-family: 楷体; font-size: 14px;\">近1-2月来反复有头晕，无头痛，无乏力，数天前流鼻血1次，精神可，不发热，无咳嗽，纳可，未吐，无皮疹，大小便正常。</span></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=td1 colspan=\"3\">\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;font-weight: bold;\">既往史、个人史、家族史:</span><span style=\"font-family: 楷体; font-size: 14px;\">无特殊</span></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=td1 colspan=\"3\">\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;font-weight: bold;\">体格检查:</span><span style=\"font-family: 楷体; font-size: 14px;\">神清，精神反应可，颜面无苍白，皮肤弹性可，浅表淋巴结不肿大，无皮疹，咽部无红肿，扁桃体不肿大，颈软，两肺呼吸音稍粗，心音有力，心律齐，无杂音，腹平软，NS征（-）。</span></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table width=\"492\" height=\"36\" class=\"table1\" cellSpacing=0 cellPadding=0 >\n" +
                "<tr style=\"display:none\">\n" +
                "<td width=\"492\">&nbsp;</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=td1>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;font-weight: bold;\">辅助检查:</span><span style=\"font-family: 楷体; font-size: 14px;\">血涂片,血常规（五分类）,超敏C反应蛋白测定</span></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=td1>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;font-weight: bold;\">初步诊断:</span><span style=\"font-family: 楷体; font-size: 14px;\">&nbsp;</span><span style=\"font-family: 楷体; font-size: 14px;\">头晕查因：</span></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table width=\"492\" height=\"18\" class=\"table1\" cellSpacing=0 cellPadding=0 >\n" +
                "<tr style=\"display:none\">\n" +
                "<td width=\"492\">&nbsp;</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=td1>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;font-weight: bold;\">处理：必要时看神经内科</span></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table width=\"492\" height=\"18\" class=\"table1\" cellSpacing=0 cellPadding=0 >\n" +
                "<tr style=\"display:none\">\n" +
                "<td width=\"492\">&nbsp;</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=td1>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;\">&nbsp;</span></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table width=\"492\" height=\"72\" class=\"table1\" cellSpacing=0 cellPadding=0 >\n" +
                "<tr style=\"display:none\">\n" +
                "<td width=\"492\">&nbsp;</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td class=td1>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;\">加强护理，有情况随诊。</span></p>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;\">&nbsp;</span></p>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 14px;\">&nbsp;</span></p>\n" +
                "<p align=\"right\"><span style=\"font-family: 楷体; font-size: 14px;\">&nbsp;\t医生签名:</span><span style=\"font-family: 宋体; font-size: 14px;\">&nbsp;&nbsp;</span><span style=\"font-family: 宋体; font-size: 14px;\">&nbsp;</span><span style=\"font-family: 楷体; font-size: 14px;\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\t盖章:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<p><span style=\"font-family: 楷体; font-size: 16px;font-weight: bold;\">&nbsp;</span></p>\n" +
                "<p align=\"center\"><span style=\"font-family: 宋体; font-size: 14px;\">&nbsp;</span></p>\n" +
                "<table  height=\"40\" align=\"center\">\n" +
                "<tr><td>\n" +
                "</td></tr>\n" +
                "</table>\n" +
                "</td></tr>\n" +
                "</table>\n" +
                "</td></tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>\n";

        Document doc = Jsoup.parse(xx);
        Elements tables=doc.getElementsByClass("table1");//每个表格
        //1.初诊病历记录-》体格检查
        //2.辅助检查，初步诊断
        //3.处理:
        //4 处理:
        //5 处理意见+医生签名，盖章
        //如果table=5 一定是存在严格的病历行为，

//        getTables(tables,2); //处理
//        getTables(tables,3); //处理
        getTables(tables,4); //处理 +签名

    }

    /**
     *
     * @param tables
     * @param index
     * @return
     */
    public static Map<String,String> getTables(Elements tables,Integer index){
        Element table1=tables.get(index);
        Elements tds=table1.getElementsByClass("td1");
        Map<String,String> map=new HashMap<>();
        List<String> list=new ArrayList<>();
        for (int i = 0; i <tds.size() ; i++) {
            Elements spans=tds.get(i).getElementsByTag("span");
            Elements nobank=new Elements();
            spans.stream().forEach(e ->{
                if(!StringUtils.isBlank(e.text())){
                    nobank.add(e);
                }
            });
            if(nobank.size()>0){
                for (int i1 = 0; i1 <(nobank.size()); i1++) {
                    if(nobank.size()==1){
                        map.put(nobank.get(0).text().trim(),"");
                    }else {
                        String key=nobank.get(i1).text().trim();

                        if(index ==4){
                            String p=nobank.get(i1).toString();
                            if(p.contains("医生签名:")){
                               Element element= nobank.get(i1).parent();
                               boolean flag=element.toString().contains("src");
                               String base64="";
                               if (flag){
                                    base64=element.getElementsByTag("img").get(0).attr("src").trim();
                               }
                                map.put("医生签名:",base64);
                            }else if(p.contains("盖章")){
                               continue;
                            }else {
                                map.put(key,"");
                            }
                            continue;
                        }
                        if(index == 3){
                            map.put(key,"");
                            continue;
                        }
                        if(i1%2==0 && i1!=(nobank.size()-1)){
                            if(i==0 && index==0){
                                map.put(nobank.get(i1).text().trim()+nobank.get(i1+1).text().trim(),nobank.get(i1+2).text().trim());
                            }else {
                                String value=nobank.get(i1+1).text().trim();
                                map.put(key,value);
                            }
                        }
                    }
                }
            }
        }
        System.out.println(map);
        return map;
    }

}
