package com.alpha.self.diagnosis.pojo.vo;

import com.alpha.commons.util.BeanCopierUtil;
import com.alpha.server.rpc.diagnosis.pojo.SyDiagnosisAnswer;

/**
 * 小类答案
 * @author Administrator
 *
 */
public class Level2AnswerVo implements IAnswerVo {


    /**
     * 答案值
     */
    private String answerValue;

    /**
     * 问题内容
     */
    private String answerTitle;

    /**
     * 描述
     */
    private String description;
    /**
     * 序号
     */
    private Integer defaultOrder;
    /**
     * 客户端展示类型
     */
    private String displayType;
    
    /**
     * 答案图片
     */
    private String image;

    public Level2AnswerVo() {}

    public Level2AnswerVo(SyDiagnosisAnswer answer) {
    	BeanCopierUtil.copy(answer, this);
    }
    
    public Level2AnswerVo(BasicAnswerVo bav) {
    	BeanCopierUtil.copy(bav, this);
    }

    public String getAnswerTitle() {
        return answerTitle;
    }

    public void setAnswerTitle(String answerTitle) {
        this.answerTitle = answerTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDefaultOrder() {
        return defaultOrder;
    }

    public void setDefaultOrder(Integer defaultOrder) {
        this.defaultOrder = defaultOrder;
    }

    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }
    

    public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
}
