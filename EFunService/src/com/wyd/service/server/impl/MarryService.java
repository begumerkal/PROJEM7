package com.wyd.service.server.impl;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.app.db.service.impl.UniversalManagerImpl;
import com.wyd.service.bean.MarryRecord;
import com.wyd.service.dao.IMarryDao;
import com.wyd.service.server.factory.IMarryService;

/**
 * 
 * The service class for the TabConsortiaright entity.
 */
public class MarryService extends UniversalManagerImpl implements IMarryService {
	Logger log = Logger.getLogger(MarryService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IMarryDao dao;

	public MarryService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IMarryService getInstance(ApplicationContext context) {
		return (IMarryService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IMarryDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	@Override
	public MarryRecord getSingleMarryRecordByPlayerId(int sexmark, int playerId, int status) {
		return dao.getSingleMarryRecordByPlayerId(sexmark, playerId, status);
	}

	
}