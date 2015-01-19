package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.StarSoul;
import com.wyd.empire.world.dao.IStarSoulDao;
import com.wyd.empire.world.server.service.base.IStarSoulService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class StarSoulService extends UniversalManagerImpl implements IStarSoulService {
	Logger log = Logger.getLogger(StarSoulService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IStarSoulDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "StarSoulService";

	public StarSoulService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IStarSoulService getInstance(ApplicationContext context) {
		return (IStarSoulService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IStarSoulDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IStarSoulDao getDao() {
		return this.dao;
	}

	/**
	 * 获取所有星图列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StarSoul> getAllStarSoul() {
		return dao.getAll(StarSoul.class);
	}
}