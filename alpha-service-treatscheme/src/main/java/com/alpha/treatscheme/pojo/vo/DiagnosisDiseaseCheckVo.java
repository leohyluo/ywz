package com.alpha.treatscheme.pojo.vo;

import org.springframework.beans.BeanUtils;

import com.alpha.treatscheme.pojo.DiagnosisDiseaseCheck;

/**
 * 疾病建议检查展示类
 * @author Administrator
 *
 */
public class DiagnosisDiseaseCheckVo {
	private String checkCode;
	
	private String checkName;
		
	private String symbol;
	
	public DiagnosisDiseaseCheckVo() {}
	
	public DiagnosisDiseaseCheckVo(DiagnosisDiseaseCheck check) {
		BeanUtils.copyProperties(check, this);
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	
}
