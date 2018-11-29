package com.alpha.commons.core.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HP on 2018/3/22.
 */
public class InitializeDataVo  implements Serializable{

    private List<Nation> nations;  //民族字典
    private List<Nationality> nationalities;//国家字典
    private List<GuardianIdCardType> guardianIdCardTypes; //监护人字典
    private List<PatientIdCardType> patientIdCardTypes;//患者证件类型
    private List<PayType> payTypes;//患者支付类型
    private List<RelationshipType> relationshipTypes; //患者与填表关系类型

    public List<Nation> getNations() {
        return nations;
    }

    public void setNations(List<Nation> nations) {
        this.nations = nations;
    }

    public List<Nationality> getNationalities() {
        return nationalities;
    }

    public void setNationalities(List<Nationality> nationalities) {
        this.nationalities = nationalities;
    }

    public List<GuardianIdCardType> getGuardianIdCardTypes() {
        return guardianIdCardTypes;
    }

    public void setGuardianIdCardTypes(List<GuardianIdCardType> guardianIdCardTypes) {
        this.guardianIdCardTypes = guardianIdCardTypes;
    }

    public List<PatientIdCardType> getPatientIdCardTypes() {
        return patientIdCardTypes;
    }

    public void setPatientIdCardTypes(List<PatientIdCardType> patientIdCardTypes) {
        this.patientIdCardTypes = patientIdCardTypes;
    }

    public List<PayType> getPayTypes() {
        return payTypes;
    }

    public void setPayTypes(List<PayType> payTypes) {
        this.payTypes = payTypes;
    }

    public List<RelationshipType> getRelationshipTypes() {
        return relationshipTypes;
    }
    public void setRelationshipTypes(List<RelationshipType> relationshipTypes) {
        this.relationshipTypes = relationshipTypes;
    }


}
