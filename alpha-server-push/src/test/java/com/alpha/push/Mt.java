package com.alpha.push;

import com.alpha.commons.core.framework.SpringContextHolder;
import com.alpha.push.service.NotifyService;

/**
 * Created by MR.Wu on 2018-07-19.
 */
public class Mt {
    public static void main(String[] args) {
        NotifyService notifyService = SpringContextHolder.getBean("NotifyServiceImpl");
    }
}
