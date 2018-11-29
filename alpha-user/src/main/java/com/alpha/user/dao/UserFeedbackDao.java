package com.alpha.user.dao;

import java.util.List;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.server.rpc.user.pojo.UserFeedback;

public interface UserFeedbackDao extends IBaseDao<UserFeedback, Long> {

	/**
	 * 查询反馈
	 * @param diagnosisId
	 * @return
	 */
	List<UserFeedback> listUserFeedback(Long diagnosisId);
}
