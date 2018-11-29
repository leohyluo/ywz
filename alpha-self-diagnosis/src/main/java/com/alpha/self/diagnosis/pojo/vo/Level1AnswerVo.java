package com.alpha.self.diagnosis.pojo.vo;

import java.util.List;

import com.alpha.server.rpc.diagnosis.pojo.SyDiagnosisAnswer;

/**
 * 大类答案
 * @author Administrator
 *
 */
public class Level1AnswerVo implements IAnswerVo {


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
    
    /**
     * 小类答案
     */
    private List<Level2AnswerVo> level2Answers;
    
    public Level1AnswerVo() {}
    
    public Level1AnswerVo(SyDiagnosisAnswer sda) {
    	this.answerValue = sda.getAnswerCode();
    	this.answerTitle = sda.getContent();
    	this.image = sda.getImage();
    }
    
    public Level1AnswerVo(BasicAnswerVo bav) {
    	this.answerValue = bav.getAnswerValue();
    	this.answerTitle = bav.getAnswerTitle();
    	this.description = bav.getDescription();
    	this.defaultOrder = bav.getDefaultOrder();
    	this.displayType = bav.getDisplayType();
    	this.image = bav.getImage();
    }

	public String getAnswerValue() {
		return answerValue;
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
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

	public List<Level2AnswerVo> getLevel2Answers() {
		return level2Answers;
	}

	public void setLevel2Answers(List<Level2AnswerVo> level2Answers) {
		this.level2Answers = level2Answers;
	}

   
}
