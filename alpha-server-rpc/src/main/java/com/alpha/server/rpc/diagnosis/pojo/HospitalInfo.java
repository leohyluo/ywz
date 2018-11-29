package com.alpha.server.rpc.diagnosis.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hospital_info")
public class HospitalInfo {

	/**
     * id
     */
    @Column(name = "id")
    @Id
    private Long id;
    
    @Column(name = "hospital_code")
    private String hospitalCode;
    
    @Column(name = "hospital_name")
    private String hospitalName;
    
    @Column(name = "hospital_icon")
    private String hospitalIcon;

    //医院公众号二维码
    @Column(name = "qr_code_offical_account")
    private String qrCode4OfficalAccount;

    @Column(name = "hospital_key")
    private String key;

    @Column(name = "domain_url")
    private String domainUrl;

    @Column(name = "image_of_index")
    private String imageOfIndex;

    @Column(name = "image_of_login_type")
    private String imageOfLoginType;

    @Column(name = "image_of_partners")
    private String imageOfPartners;
    
    @Column(name = "create_time")
    private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHospitalIcon() {
		return hospitalIcon;
	}

	public void setHospitalIcon(String hospitalIcon) {
		this.hospitalIcon = hospitalIcon;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getQrCode4OfficalAccount() {
		return qrCode4OfficalAccount;
	}

	public void setQrCode4OfficalAccount(String qrCode4OfficalAccount) {
		this.qrCode4OfficalAccount = qrCode4OfficalAccount;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

	public String getImageOfIndex() {
		return imageOfIndex;
	}

	public void setImageOfIndex(String imageOfIndex) {
		this.imageOfIndex = imageOfIndex;
	}

	public String getImageOfLoginType() {
		return imageOfLoginType;
	}

	public void setImageOfLoginType(String imageOfLoginType) {
		this.imageOfLoginType = imageOfLoginType;
	}

	public String getImageOfPartners() {
		return imageOfPartners;
	}

	public void setImageOfPartners(String imageOfPartners) {
		this.imageOfPartners = imageOfPartners;
	}
}
