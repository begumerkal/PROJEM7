package com.wyd.empire.world.server.service.base.impl;

import org.apache.log4j.Logger;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.VigorPrice;
import com.wyd.empire.world.dao.IVigorPriceDao;
import com.wyd.empire.world.server.service.base.IVigorService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class VigorService extends UniversalManagerImpl implements IVigorService {
	Logger log = Logger.getLogger(VigorService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IVigorPriceDao dao;

	public void setDao(IVigorPriceDao dao) {
		this.dao = dao;
	}

	public VigorService() {
		super();
	}

	@Override
	public VigorPrice getPriceByCount(int count) {
		return dao.getByCount(count);
	}

	@Override
	public int getMaxCount(int vipLevl) {
		return dao.getMaxCount(vipLevl);
	}

}