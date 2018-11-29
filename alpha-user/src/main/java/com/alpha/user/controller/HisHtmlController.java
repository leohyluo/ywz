package com.alpha.user.controller;

import com.alpha.his.pojo.dto.HisHtmlDTO;
import com.alpha.his.service.etyy.HisHtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by HP on 2018/5/29.
 * 下载 嘉禾电子病历
 *
 */
@Controller
@RequestMapping("/html")
public class HisHtmlController {

    @Autowired
    HisHtmlService hisHtmlService;



    @GetMapping("/{doctorName}/{visitTime}")
    public void hisHtml(HttpServletRequest request, HttpServletResponse response,@PathVariable String doctorName,
                        @PathVariable String visitTime){
        List<HisHtmlDTO> list=hisHtmlService.getHtml(doctorName,visitTime);




        
    }

}
