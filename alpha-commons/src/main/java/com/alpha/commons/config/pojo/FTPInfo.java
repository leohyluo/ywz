package com.alpha.commons.config.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FTPInfo {

	@Value("${ftp.server.ip}")
	private String ip;
	@Value("${ftp.server.port}")
	private Integer port;
	@Value("${ftp.server.userName}")
	private String userName;
	@Value("${ftp.server.password}")
	private String password;
	@Value("${ftp.alpha.save-path.qr-code}")
	private String uploadPath4code;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		//因为密码带有#，yml文件将#后的默认为注释内容了
		if("ftpd.ebmsz.com".equals(ip)) {
			password = "zhys%vVUUqy#igK3Ft?I";
		} else if("112.124.107.73".equals(ip)) {
			password = "zhsy@!ftp_";
		}
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUploadPath4code() {
		return uploadPath4code;
	}
	public void setUploadPath4code(String uploadPath4code) {
		this.uploadPath4code = uploadPath4code;
	}
	
	
}
