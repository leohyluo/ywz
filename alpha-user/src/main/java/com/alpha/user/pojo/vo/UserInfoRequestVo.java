package com.alpha.user.pojo.vo;

public class UserInfoRequestVo {


    private Integer inType;

    /**
     * 就诊编号
     */
    private Long diagnosisId;
    /**
     * 用户成员信息
     */
    private MemberInfoVo memberInfo;
    /**
     * 用户基础信息
     */
    private SaveUserInfoVo userInfo;

    /**
     * 其它医院就诊信息
     */
    private OtherHospitalInfo otherHospitalInfo;

    /**
     * 现病史信息
     */
    private PresentIllnessVo presentIllness;


    public SaveUserInfoVo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(SaveUserInfoVo userInfo) {
        this.userInfo = userInfo;
    }

    public OtherHospitalInfo getOtherHospitalInfo() {
        return otherHospitalInfo;
    }

    public void setOtherHospitalInfo(OtherHospitalInfo otherHospitalInfo) {
        this.otherHospitalInfo = otherHospitalInfo;
    }

    public Integer getInType() {
        return inType;
    }

    public void setInType(Integer inType) {
        this.inType = inType;
    }

    public Long getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

	public MemberInfoVo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfoVo memberInfo) {
		this.memberInfo = memberInfo;
	}

    public PresentIllnessVo getPresentIllness() {
        return presentIllness;
    }

    public void setPresentIllness(PresentIllnessVo presentIllness) {
        this.presentIllness = presentIllness;
    }
}
