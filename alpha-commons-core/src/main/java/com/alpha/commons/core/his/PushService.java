package com.alpha.commons.core.his;

/**
 * Created by HP on 2018/10/8.
 * 推送接口
 */
public interface PushService {

    /**
     * 推送微信公众号
     * @param obj
     * @return
     */
    public Object pushWX(Object obj);

    /**
     * 推送短信
     * @param obj
     * @return
     */
    public Object pushMsg(Object obj);

    /**
     * 推送其他的
     * @param obj
     * @return
     */
    public Object pushOtherS(Object obj);


}
