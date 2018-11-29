package com.alpha.user.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.commons.pojo.SysConfig;

import java.util.List;

public interface SysConfigDao extends IBaseDao<SysConfig, Long> {

	/**
	 * 获取系统参数配置
	 * @param key
	 * @return
	 */
	SysConfig querySysConfig(String key);

	List<SysConfig> findAll();
}
