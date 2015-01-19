package com.wyd.empire.world.server.service.base.impl;

import org.apache.log4j.Logger;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.LimitedPrice;
import com.wyd.empire.world.dao.ILimitedPriceDao;
import com.wyd.empire.world.server.service.base.ILimitedPriceService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class LimitedPriceService extends UniversalManagerImpl implements ILimitedPriceService {
	Logger log = Logger.getLogger(LimitedPriceService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private ILimitedPriceDao dao;

	public void setDao(ILimitedPriceDao dao) {
		this.dao = dao;
	}

	public LimitedPriceService() {
		super();
	}

	@Override
	public LimitedPrice getPriceByCount(int itemId, int count) {
		return dao.getByCount(itemId, count);
	}

	@Override
	public int getMaxCount(int itemId, int vipLevel) {
		return dao.getMaxCount(itemId, vipLevel);
	}

}