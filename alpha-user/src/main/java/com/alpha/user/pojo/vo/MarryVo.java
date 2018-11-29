package com.alpha.user.pojo.vo;

/**
 * 妇科版婚育史数据交互类
 */
public class MarryVo {

    /**
     * 婚史
     */
    private String marriage;

    /**
     * 生育史
     */
    private String historyOfBorn;

    //足月产孩子个数
    private Integer matureChildCount;

    //早产孩子个数
    private Integer prematureChildCount;

    //流产孩子个数
    private Integer miscarryChildCount;

    //现有孩子个数
    private Integer nowChildCount;

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getHistoryOfBorn() {
        return historyOfBorn;
    }

    public void setHistoryOfBorn(String historyOfBorn) {
        this.historyOfBorn = historyOfBorn;
    }

    public Integer getMatureChildCount() {
        return matureChildCount;
    }

    public void setMatureChildCount(Integer matureChildCount) {
        this.matureChildCount = matureChildCount;
    }

    public Integer getPrematureChildCount() {
        return prematureChildCount;
    }

    public void setPrematureChildCount(Integer prematureChildCount) {
        this.prematureChildCount = prematureChildCount;
    }

    public Integer getMiscarryChildCount() {
        return miscarryChildCount;
    }

    public void setMiscarryChildCount(Integer miscarryChildCount) {
        this.miscarryChildCount = miscarryChildCount;
    }

    public Integer getNowChildCount() {
        return nowChildCount;
    }

    public void setNowChildCount(Integer nowChildCount) {
        this.nowChildCount = nowChildCount;
    }
}
