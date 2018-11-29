package com.alpha.server.rpc.diagnosis.pojo;

import com.alpha.commons.util.DateUtils;
import com.alpha.server.rpc.diagnosis.pojo.enums.ObjectVersionEnum;
import com.alpha.server.rpc.user.pojo.UserInfo;

import javax.persistence.*;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "diagnosis_main_symptoms")
public class DiagnosisMainSymptoms implements Serializable {


    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 主症状编码
     */
    @Column(name = "symp_code")
    private String sympCode;

    /**
     * 主症状名称
     */
    @Column(name = "symp_name")
    private String sympName;

    /**
     * 通俗化名称
     */
    @Column(name = "popu_name")
    private String popuName;

    /**
     * 拼音助记符
     */
    @Column(name = "symbol")
    private String symbol;

    /**
     * 性别
     */
    @Column(name = "gender")
    private Integer gender;

    /**
     * 最小年龄
     */
    @Column(name = "min_age")
    private Double minAge;

    /**
     * 最大年龄
     */
    @Column(name = "max_age")
    private Double maxAge;

    /**
     * 序号
     */
    @Column(name = "default_order")
    private Integer defaultOrder;

    /**
     * 病历模版id
     */
    @Column(name = "template_id")
    private Long templateId;

    /**
     * 特殊时期
     */
    @Column(name = "special_period")
    private Integer specialPeriod;
    /**
     * 特殊时期
     */
    @Column(name = "object_version")
    private Integer objectVersion;

    /**
     * 频次
     */
    @Column(name = "frequency")
    private Integer frequency;

    /**
     *
     */
    @Column(name = "incre_flag")
    private String increFlag;

    /**
     *
     */
    @Column(name = "opera_flag")
    private String operaFlag;

    /**
     *
     */
    @Column(name = "operate_type")
    private String operateType;

    /**
     *
     */
    @Column(name = "data_version")
    private Integer dataVersion;

    /**
     *
     */
    @Column(name = "version_evolution")
    private String versionEvolution;

    //一般症状（1：大便 2：小便 3：食欲 4：精神）
    @Column(name = "general_symptom")
    private Integer generalSymptom;

    @Column(name = "normal_symptom_code")
    private String normalSymptomCode;

    @Column(name = "is_show")
    private Integer isShow;

    //病程
    @Transient
    private Double illPeriod;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }


    public void setSympCode(String sympCode) {
        this.sympCode = sympCode;
    }

    public String getSympCode() {
        return this.sympCode;
    }

    public Integer getDefaultOrder() {
        return defaultOrder;
    }

    public void setDefaultOrder(Integer defaultOrder) {
        this.defaultOrder = defaultOrder;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public void setSympName(String sympName) {
        this.sympName = sympName;
    }

    public String getSympName() {
        return this.sympName;
    }


    public void setPopuName(String popuName) {
        this.popuName = popuName;
    }

    public String getPopuName() {
        return this.popuName;
    }


    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }


    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getGender() {
        return this.gender;
    }


    public void setMinAge(Double minAge) {
        this.minAge = minAge;
    }

    public Double getMinAge() {
        return this.minAge;
    }


    public void setMaxAge(Double maxAge) {
        this.maxAge = maxAge;
    }

    public Double getMaxAge() {
        return this.maxAge;
    }


    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateId() {
        return this.templateId;
    }


    public void setSpecialPeriod(Integer specialPeriod) {
        this.specialPeriod = specialPeriod;
    }

    public Integer getSpecialPeriod() {
        return this.specialPeriod;
    }


    public void setIncreFlag(String increFlag) {
        this.increFlag = increFlag;
    }

    public String getIncreFlag() {
        return this.increFlag;
    }


    public void setOperaFlag(String operaFlag) {
        this.operaFlag = operaFlag;
    }

    public String getOperaFlag() {
        return this.operaFlag;
    }


    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateType() {
        return this.operateType;
    }


    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Integer getDataVersion() {
        return this.dataVersion;
    }


    public void setVersionEvolution(String versionEvolution) {
        this.versionEvolution = versionEvolution;
    }

    public String getVersionEvolution() {
        return this.versionEvolution;
    }

    public Integer getObjectVersion() {
        return objectVersion;
    }

    public void setObjectVersion(Integer objectVersion) {
        this.objectVersion = objectVersion;
    }

    public Integer getGeneralSymptom() {
        return generalSymptom;
    }

    public void setGeneralSymptom(Integer generalSymptom) {
        this.generalSymptom = generalSymptom;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Double getIllPeriod() {
        return illPeriod;
    }

    public void setIllPeriod(Double illPeriod) {
        this.illPeriod = illPeriod;
    }

    /**
     * 过滤主症状
     *
     * @param inType
     * @param userInfo
     * @return
     */
    public Boolean mainSymptomPredicate(UserInfo userInfo,int inType, int objectVersion) {
        if(this.isShow == null || this.isShow == 0) {
            return false;
        }
        //10表示阿尔法医生进来，显示所有主症状
        if(objectVersion == 10) {
            if(this.objectVersion == null) {
                return false;
            } else {
                return true;
            }
        }
        //按性别过滤
        //Integer gender = mainSymptom.getGender();
        Date birth = userInfo.getBirth();
        float age = DateUtils.getAge(birth);
        Integer userGender = userInfo.getGender();
        if (this.gender != null && this.gender > 0 && userGender != this.gender) {
            return false;
        }
        //按年龄过滤
        //Double minAge = mainSymptom.getMinAge();
        //Double maxAge = mainSymptom.getMaxAge();
        if (this.minAge != null && this.maxAge != null) {
            if (age >= this.minAge.doubleValue() && age <= this.maxAge.doubleValue()) {
                //flag = true;
            	//do nothing
            } else {
            	return false;
            }
        }
        //过滤女性特殊时期
        if(StringUtils.isNotEmpty(userInfo.getSpecialPeriod()) && this.specialPeriod != null && this.specialPeriod != 0) {
        	if(Integer.parseInt(userInfo.getSpecialPeriod()) != this.specialPeriod) {
        		return false;
        	}
        }
        if (objectVersion != this.objectVersion) {
            return false;
        }
        //过滤版本
        /*Integer version = this.getObjectVersion();
        if(version == null || version == 0){
        	return false;
        }
        if(version!= null&&version!=0){
	        List<ObjectVersionEnum> clientVersion = ObjectVersionEnum.getSupportTypes(inType);
	        List<ObjectVersionEnum> serverVersion =  ObjectVersionEnum.getSupportTypes(this.objectVersion);
	        serverVersion.retainAll(clientVersion);
	        if(serverVersion.size()==0)
	        	return false;
        }*/
        
        return true;
    }

    public String getNormalSymptomCode() {
        return normalSymptomCode;
    }

    public void setNormalSymptomCode(String normalSymptomCode) {
        this.normalSymptomCode = normalSymptomCode;
    }
}
