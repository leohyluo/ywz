package com.alpha.user.service;

import com.alpha.commons.core.pojo.ao.UserCardInfoAO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/14.
 */
public interface OpenCardService{

    Integer openCard(UserCardInfoAO vo);

    String imageNew(String idCard,String id);
}
