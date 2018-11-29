package com.alpha.his.controller;

import com.alpha.commons.core.util.ImageUtil;
import com.alpha.commons.web.ResponseMessage;
import com.alpha.commons.web.WebUtils;
import com.alpha.his.service.etyy.UserCardInfoService;
import com.alpha.server.rpc.his.pojo.UserCard;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserCardInfoController {
    @Resource
    UserCardInfoService userCardService;

    @GetMapping("/userCard/query")
    public ResponseMessage query(UserCard userCard) {
        userCard.setOrderByClause("createTime desc ");
        List<UserCard> userCardList = userCardService.query(userCard);
        for (UserCard userCardInfo : userCardList) {
            if(null != userCardInfo.getCardorder()){
                userCardInfo.setQrCode(genImage(String.valueOf(userCardInfo.getCardorder())));
            }else{
                continue;
            }
        }
        return WebUtils.buildSuccessResponseMessage(userCardList);
    }

    private String genImage(String map) {
        String a = null;
        try {
            a = ImageUtil.getBase64QRCode(map);
            a = "data:image/png;base64," + a;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }
}
