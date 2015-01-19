package com.wyd.empire.world.server.service.base.impl;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.dao.ILogStatisticsDao;
import com.wyd.empire.world.server.service.base.ILogStatisticsService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class LogStatisticsService extends UniversalManagerImpl implements ILogStatisticsService {
	/**
	 * The dao instance injected by Spring.
	 */
	private ILogStatisticsDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "LogStatisticsService";

	public LogStatisticsService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static ILogStatisticsService getInstance(ApplicationContext context) {
		return (ILogStatisticsService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public ILogStatisticsDao getDao() {
		return dao;
	}

	public void setDao(ILogStatisticsDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

}