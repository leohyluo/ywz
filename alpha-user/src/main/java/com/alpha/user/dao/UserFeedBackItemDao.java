package com.alpha.user.dao;

import com.alpha.commons.core.dao.IBaseDao;
import com.alpha.commons.enums.AppType;
import com.alpha.user.pojo.UserFeedBackItem;

import java.util.List;

public interface UserFeedBackItemDao extends IBaseDao<UserFeedBackItem, Long> {

	List<UserFeedBackItem> listUserFeedBackItem(AppType appType);
}
