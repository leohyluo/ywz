package com.alpha.push.domain;

/**
 * Created by MR.Wu on 2018-07-23.
 */
public class RetryPolicyMessagePush {

    /**
     * 重试开始时间
     */
    private String startTime;

    /**
     * 重试次数
     */
    private int retryTimes = 1;

    /**
     * 余下未能重试次数
     */
    private int surplusTimes = 1;

    /**
     * 重试表达式，如下所示
     * <p> *     *     *      * </p>
     * <p> 天    时    分    秒</p>
     * 可以使用逗号
     * 例： * * 3,5,10 * 代表 每隔3分钟、五分钟、十分钟各重试一次
     */
    private String retryCron;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public int getSurplusTimes() {
        return surplusTimes;
    }

    public void setSurplusTimes(int surplusTimes) {
        this.surplusTimes = surplusTimes;
    }

    public String getRetryCron() {
        return retryCron;
    }

    public void setRetryCron(String retryCron) {
        this.retryCron = retryCron;
    }
}
