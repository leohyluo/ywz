package com.alpha.user.pojo.vo;

import java.util.List;

import com.alpha.server.rpc.user.pojo.UserInfo;

public class SaveUserInfoVo extends UserInfo {

    private static final long serialVersionUID = -7403810824414902472L;
    
    /**
     * 医院挂号号码
     */
    private String hisRegisterNo;

    /**
     * 既往史
     */
    private List<DiseaseHistoryVo> pastmedicalHistory;

    /**
     * 妇科版月经史
     */
    private MenstruationVo menstruation;

    /**
     * 妇科版婚育史
     */
    private MarryVo marry;

    /**
     * 过敏史
     */
    private List<DiseaseHistoryVo> allergicHistory;

    public List<DiseaseHistoryVo> getPastmedicalHistory() {
        return pastmedicalHistory;
    }

    public void setPastmedicalHistory(List<DiseaseHistoryVo> pastmedicalHistory) {
        this.pastmedicalHistory = pastmedicalHistory;
    }

    public List<DiseaseHistoryVo> getAllergicHistory() {
        return allergicHistory;
    }

    public void setAllergicHistory(List<DiseaseHistoryVo> allergicHistory) {
        this.allergicHistory = allergicHistory;
    }

	public String getHisRegisterNo() {
		return hisRegisterNo;
	}

	public void setHisRegisterNo(String hisRegisterNo) {
		this.hisRegisterNo = hisRegisterNo;
	}

    public MenstruationVo getMenstruation() {
        return menstruation;
    }

    public void setMenstruation(MenstruationVo menstruation) {
        this.menstruation = menstruation;
    }

    public MarryVo getMarry() {
        return marry;
    }

    public void setMarry(MarryVo marry) {
        this.marry = marry;
    }
}
