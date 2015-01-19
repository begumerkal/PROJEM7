package com.wyd.empire.world.server.service.base.impl;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.dao.IInterfaceDao;
import com.wyd.empire.world.server.service.base.IInterfaceService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class InterfaceService extends UniversalManagerImpl implements IInterfaceService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IInterfaceDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "InterfaceService";

	public InterfaceService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IInterfaceService getInstance(ApplicationContext context) {
		return (IInterfaceService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IInterfaceDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IInterfaceDao getDao() {
		return this.dao;
	}
}