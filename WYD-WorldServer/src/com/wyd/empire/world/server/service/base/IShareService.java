package com.wyd.empire.world.server.service.base;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.Share;

/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface IShareService extends UniversalManager {
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	static final String SERVICE_BEAN_ID = "ShareService";

	/**
	 * 随机取可编辑的分享内容
	 * 
	 * @return
	 */
	public String getEditContent();

	/**
	 * 按触发条件查找分享内容
	 */
	public Share getShareByTarget(String target);

}