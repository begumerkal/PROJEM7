package com.wyd.empire.world.server.service.base;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.VigorPrice;

/**
 * The service interface for the TabPlayeritemsfromshop entity.
 */
public interface IVigorService extends UniversalManager {
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	static final String SERVICE_BEAN_ID = "VigorService";

	public VigorPrice getPriceByCount(int count);

	/**
	 * 根据VIP等级获得玩家最高可以购买的次数
	 * 
	 * @param vipLevl
	 * @return
	 */
	public int getMaxCount(int vipLevl);

}