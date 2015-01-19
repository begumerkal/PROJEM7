package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.BossmapBuff;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IBossmapBuffService extends UniversalManager {
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	static final String SERVICE_BEAN_ID = "BossmapBuffService";

	public List<BossmapBuff> findByGroup(int group);

	public void initData();

	public BossmapBuff getBossmapBuffById(int bbId);

}