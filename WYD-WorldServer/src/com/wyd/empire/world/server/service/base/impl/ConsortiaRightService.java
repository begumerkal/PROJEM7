package com.wyd.empire.world.server.service.base.impl;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.dao.IConsortiaRightDao;
import com.wyd.empire.world.server.service.base.IConsortiaRightService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class ConsortiaRightService extends UniversalManagerImpl implements IConsortiaRightService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IConsortiaRightDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "ConsortiarightService";

	public ConsortiaRightService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IConsortiaRightService getInstance(ApplicationContext context) {
		return (IConsortiaRightService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IConsortiaRightDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IConsortiaRightDao getDao() {
		return this.dao;
	}
}