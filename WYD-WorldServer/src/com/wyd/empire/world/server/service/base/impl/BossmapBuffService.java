package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.BossmapBuff;
import com.wyd.empire.world.dao.IBossmapBuffDao;
import com.wyd.empire.world.server.service.base.IBossmapBuffService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class BossmapBuffService extends UniversalManagerImpl implements IBossmapBuffService {
	Logger log = Logger.getLogger(BossmapBuffService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IBossmapBuffDao dao;

	public BossmapBuffService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IBossmapBuffService getInstance(ApplicationContext context) {
		return (IBossmapBuffService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IBossmapBuffDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IBossmapBuffDao getDao() {
		return this.dao;
	}

	@Override
	public List<BossmapBuff> findByGroup(int group) {
		return dao.findByGroup(group);
	}

	@Override
	public void initData() {
		dao.initData();
	}

	@Override
	public BossmapBuff getBossmapBuffById(int bbId) {
		return dao.getBossmapBuffById(bbId);
	}
}