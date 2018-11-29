package com.alpha.server.rpc.diagnosis.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "diagnosis_article")
public class DiagnosisArticle implements Serializable {

	private static final long serialVersionUID = -363499321565849408L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "article_code")
	private String articleCode;
	
	@Column(name = "article_title")
	private String articleTitle;
	
	@Column(name = "article_title2")
	private String articleTitle2;
	
	@Column(name = "article_content")
	private String articleContent;
	
	@Column(name = "article_icon")
	private String articleIcon;
	
	// 作者
	@Column(name = "article_author")
	private String author;

	// 来源
	@Column(name = "article_source")
	private String source;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "update_time")
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleTitle2() {
		return articleTitle2;
	}

	public void setArticleTitle2(String articleTitle2) {
		this.articleTitle2 = articleTitle2;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public String getArticleIcon() {
		return articleIcon;
	}

	public void setArticleIcon(String articleIcon) {
		this.articleIcon = articleIcon;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
}
