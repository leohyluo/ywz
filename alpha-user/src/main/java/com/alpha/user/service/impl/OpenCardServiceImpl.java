package com.alpha.user.service.impl;

import com.alpha.commons.core.mapper.UserCardInfoMapper;
import com.alpha.commons.core.pojo.ResultData;
import com.alpha.commons.core.pojo.UserCardInfo;
import com.alpha.commons.core.pojo.ao.UserCardInfoAO;
import com.alpha.commons.core.util.ImageUtil;
import com.alpha.commons.core.util.MatrixToImageWriter;
import com.alpha.commons.util.DateUtils;
import com.alpha.commons.util.XStreamUtils;
import com.alpha.user.service.OpenCardService;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.ibatis.executor.ReuseExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
 * Created by Administrator on 2018/3/16.
 */
@Service
public class OpenCardServiceImpl implements OpenCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenCardServiceImpl.class);
    @Autowired
    UserCardInfoMapper userCardInfoMapper;

    @Override
    public Integer openCard(UserCardInfoAO ao) {
        Integer num= 0;
        try {
            UserCardInfo userCardInfo=new UserCardInfo(ao);
            int order=(int)((Math.random()+1)*11111111);
            userCardInfo.setCardOrder(order);
            userCardInfo.setCreateTime(DateUtils.date2String(new Date(),"yyyy-MM-dd HH:mm:ss"));
            num = userCardInfoMapper.saveUserCardInfo(userCardInfo);
            if(num >0){
                num=order;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("用户保存开卡异常"+e);
        }
        return num;
    }
    @Override
    public String imageNew(String idCard,String id) {
        String num ="";
        UserCardInfo param=new UserCardInfo();
        param.setCardOrder(Integer.parseInt(id));
        UserCardInfo userCardInfo=userCardInfoMapper.selectOne(param);
        if (userCardInfo != null ) {
            String   result=id;
          num= Image(result);
        }
        return num;
    }

    private String Image(String map) {
        String a= null;
        try {
            a = ImageUtil.getBase64QRCode(map);
            a="data:image/png;base64,"+a;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

}
