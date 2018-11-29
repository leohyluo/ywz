package com.alpha.server.rpc.user.pojo;


import com.alpha.commons.constants.GlobalConstants;
import com.alpha.commons.core.util.StringUtil;
import com.alpha.commons.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "user_diagnosis_detail")
public class UserDiagnosisDetail implements Serializable {


    private static final long serialVersionUID = 4709483755609783413L;
    /**
     * id
     */
    @Column(name = "id")
    @Id
    private Long id;

    /**
     * 诊断号唯一编码
     */
    @Column(name = "diagnosis_id")
    private Long diagnosisId;

    /**
     * 主症状编码
     */
    @Column(name = "symp_code")
    private String sympCode;

    /**
     * 被问诊人ID
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 问诊人Id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 问题编码
     */
    @Column(name = "question_code")
    private String questionCode;

    /**
     * 问题内容
     */
    @Column(name = "question_content")
    private String questionContent;
    /**
     * 问题内容
     */
    @Column(name = "question_title")
    private String questioTitle;

    /**
     * 答案编码
     */
    @Column(name = "answer_code")
    private String answerCode;

    /**
     * 答案内容
     */
    @Column(name = "answer_content")
    private String answerContent;

    @Column(name = "answer_popu_content")
    private String answerPopuContent;

    /**
     * 问题类型 1主症状 2普通问题 3伴随症状 4诊断结果
     */
    @Column(name = "question_type")
    private Integer questionType;

    /**
     * 回答问题时间
     */
    @Column(name = "answer_time")
    private Date answerTime;

    /**
     * 答案的JSON串
     */
    @Column(name = "answer_json")
    private String answerJson;

    /**
     * 反向特异性疾病
     */
    @Column(name = "reverse_disease_code")
    private String reverseDiseaseCode;

    /**
     * 正向特异性疾病
     */
    @Column(name = "forward_disease_code")
    private String forwardDiseaseCode;

    /**
     * 无异性疾病
     */
    @Column(name = "nothing_disease_code")
    private String nothingDiseaseCode;

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

    /**
     * 来源
     */
    @Column(name = "source_")
    private String source;

    /**
     *
     */
    @Column(name = "version_")
    private String version;

    /**
     *
     */
    @Column(name = "creator")
    private String creator;

    /**
     *
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     *
     */
    @Column(name = "reviewer")
    private String reviewer;

    /**
     *
     */
    @Column(name = "review_time")
    private Date reviewTime;

    @Column(name = "is_additional")
    private Integer isAdditional;

    @Column(name = "update_time")
    private Date updateTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public String getReverseDiseaseCode() {
        return reverseDiseaseCode;
    }

    public void setReverseDiseaseCode(String reverseDiseaseCode) {
        this.reverseDiseaseCode = reverseDiseaseCode;
    }

    public String getForwardDiseaseCode() {
        return forwardDiseaseCode;
    }

    public void setForwardDiseaseCode(String forwardDiseaseCode) {
        this.forwardDiseaseCode = forwardDiseaseCode;
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public Long getDiagnosisId() {
        return this.diagnosisId;
    }


    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return this.memberId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }


    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestionCode() {
        return this.questionCode;
    }


    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionContent() {
        return this.questionContent;
    }


    public void setAnswerCode(String answerCode) {
        this.answerCode = answerCode;
    }

    public String getAnswerCode() {
        return this.answerCode;
    }


    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getAnswerContent() {
        return this.answerContent;
    }


    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getQuestionType() {
        return this.questionType;
    }


    public void setAnswerTime(Date answerTime) {
        this.answerTime = answerTime;
    }

    public Date getAnswerTime() {
        return this.answerTime;
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


    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return this.source;
    }


    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreator() {
        return this.creator;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }


    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewer() {
        return this.reviewer;
    }


    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Date getReviewTime() {
        return this.reviewTime;
    }

    public String getAnswerJson() {
        return answerJson;
    }

    public void setAnswerJson(String answerJson) {
        this.answerJson = answerJson;
    }

    public String getNothingDiseaseCode() {
        return nothingDiseaseCode;
    }

    public void setNothingDiseaseCode(String nothingDiseaseCode) {
        this.nothingDiseaseCode = nothingDiseaseCode;
    }

    public String getSympCode() {
        return sympCode;
    }

    public void setSympCode(String sympCode) {
        this.sympCode = sympCode;
    }

    public String getQuestioTitle() {
        return questioTitle;
    }

    public void setQuestioTitle(String questioTitle) {
        this.questioTitle = questioTitle;
    }

    public Integer getIsAdditional() {
        return isAdditional;
    }

    public void setIsAdditional(Integer isAdditional) {
        this.isAdditional = isAdditional;
    }

    public String getAnswerPopuContent() {
        return answerPopuContent;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setAnswerPopuContent(String answerPopuContent) {
        this.answerPopuContent = answerPopuContent;
    }

    public static Map<String, String> questionMap = new HashMap<>();

    static {
        questionMap.put("55_3328", "最高体温为");
    }

    public static String getAdditionQuestionComment(String mainSympCode,Map<String, String> answerMap) {
        StringBuffer sb = new StringBuffer();
        //发热
        if("55".equals(mainSympCode)) {
            sb.append("发热，");
            String answerContent = StringUtils.toString(answerMap.get("3328"));//最高体温
            String answerContent2 = StringUtils.toString(answerMap.get("2001"));//现在体温

            if(!GlobalConstants.TEMPERATURE_UNKNOWN.equals(answerContent2)) {
                answerContent = answerContent.substring(0, answerContent.indexOf("℃"));
                answerContent2 = answerContent2.substring(0, answerContent2.indexOf("℃"));

                String max = StringUtil.getMax(answerContent, answerContent2);
                String min = StringUtil.getMin(answerContent, answerContent2);

                sb.append("热峰").append(max + "℃").append("，");

                //如果现在大于最高的则不显示现在的
                if(StringUtil.isPass(answerContent, answerContent2))
                    sb.append("目前").append(min + "℃").append("，");
            } else {
                sb.append("热峰").append(answerContent).append("，");
            }
        }
        //咳嗽
        else if("57".equals(mainSympCode)) {
            sb.append("咳嗽，");
            String answerContent = StringUtils.toString(answerMap.get("3377"));//有无咳痰
            String answerContent2 = StringUtils.toString(answerMap.get("3378"));//痰的性状
            //伴（咳嗽），无痰（有无咳痰选无的答案）/咳（答案为有痰的该处显示痰的性质答案）/有痰（答案为有痰的痰的性质答案为不清楚时）
            if("没有".equals(answerContent)) {
                sb.append("无痰，");
            } else if ("有".equals(answerContent) && GlobalConstants.UNKNOWN_ANSWER.equals(answerContent2)) {
                sb.append("有痰，");
            } else {
                sb.append("咳").append(answerContent2).append("，");
            }
        }
        //皮疹
        else if("59".equals(mainSympCode)) {
            //在（皮疹部位的答案，答案为多个时用顿号隔开）出现皮疹
            String answerContent = StringUtils.toString(answerMap.get("3558"));//皮疹部位
            if(StringUtils.isNotEmpty(answerContent)) {
                sb.append(answerContent).append("皮疹，");
            }
        }
        //呕吐
        else if("65".equals(mainSympCode)) {
            sb.append("呕吐");
            //伴（呕吐）（呕吐次数的答案）/呕吐物为（呕吐物性质的答案）/无（呕吐）
            String answerContent = StringUtils.toString(answerMap.get("3661"));//呕吐物性质
            String answerContent2 = StringUtils.toString(answerMap.get("2002"));//呕吐次数
            if(StringUtils.isNotEmpty(answerContent2)) {
                sb.append(answerContent2).append("，");
            }
            if(StringUtils.isNotEmpty(answerContent) && !answerContent.equals("不清楚")) {
                sb.append("呕吐物为").append(answerContent).append("，");
            }
        }
        //腹痛
        else if("66".equals(mainSympCode)) {
            //（选择腹痛部位的答案）疼痛
            String answerContent = StringUtils.toString(answerMap.get("3428"));//部位
            if(StringUtils.isNotEmpty(answerContent)) {
                sb.append(answerContent).append("疼痛，");
            }
        }
        //外阴瘙痒
        else if("76".equals(mainSympCode)) {
            //（选择腹痛部位的答案）疼痛
            sb.append("外阴瘙痒，");
            String answerContent = StringUtils.toString(answerMap.get("3523"));//部位
            if(StringUtils.isNotEmpty(answerContent) && !"不清楚".equals(answerContent)) {
                sb.append(answerContent).append("，");
            }
            answerContent = StringUtils.toString(answerMap.get("3525"));//部位
            if(StringUtils.isNotEmpty(answerContent) && !"不清楚".equals(answerContent)) {
                sb.append(answerContent).append("，");
            }
        }
        //白带异常
        else if ("85".equals(mainSympCode)) {
            sb.append("白带异常，");
            String answerContent = StringUtils.toString(answerMap.get("3518"));//部位
            if(StringUtils.isNotEmpty(answerContent) && !"不清楚".equals(answerContent)) {
                sb.append("呈").append(answerContent).append("，");
            }
            answerContent = StringUtils.toString(answerMap.get("3519"));//颜色
            if(StringUtils.isNotEmpty(answerContent) && !"不清楚".equals(answerContent)) {
                sb.append(answerContent).append("，");
            }
            answerContent = StringUtils.toString(answerMap.get("3520"));//气味
            sb.append(answerContent).append("。");
        }
        //下腹部疼痛
        else if ("79".equals(mainSympCode)) {
            sb.append("下腹部疼痛，");
            String answerContent = StringUtils.toString(answerMap.get("3709"));//性质
            if(StringUtils.isNotEmpty(answerContent) && !"不清楚".equals(answerContent)) {
                sb.append(answerContent).append("，");
            }
            answerContent = StringUtils.toString(answerMap.get("3715"));//性质
            if(StringUtils.isNotEmpty(answerContent) && !"不清楚".equals(answerContent)) {
                sb.append(answerContent).append("。");
            }
        }
        //阴道流血
        else if ("77".equals(mainSympCode)) {
            sb.append("阴道流血，");
            String answerContent = StringUtils.toString(answerMap.get("3529"));//出血量
            if(StringUtils.isNotEmpty(answerContent) && !"不清楚".equals(answerContent)) {
                answerContent = StringUtils.removeBracketsChar(answerContent);
                sb.append(answerContent).append("，");
            }
            answerContent = StringUtils.toString(answerMap.get("3530"));//规律
            if(StringUtils.isNotEmpty(answerContent) && !"不清楚".equals(answerContent)) {
                sb.append(answerContent).append("。");
            }
        }
        return sb.toString();
    }
}
