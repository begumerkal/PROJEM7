package com.wyd.empire.world.server.service.base.impl;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.dao.IGetLogDao;
import com.wyd.empire.world.server.service.base.IGetLogService;

public class GetLogService extends UniversalManagerImpl implements IGetLogService {

	private IGetLogDao dao;

	/**
	 * 获取发放物品日志列表
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageList getItemLogList(String key, int pageIndex, int pageSize) {
		return dao.getItemLogList(key, pageIndex, pageSize);
	}

	/**
	 * 获取发放金币日志列表
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageList getGoldCountLogList(String key, int pageIndex, int pageSize) {
		return dao.getGoldCountLogList(key, pageIndex, pageSize);
	}

	/**
	 * 获取强化日志列表
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageList getStrongrecordLogList(String key, int pageIndex, int pageSize) {
		return dao.getStrongrecordLogList(key, pageIndex, pageSize);
	}

	public IGetLogDao getDao() {
		return dao;
	}

	public void setDao(IGetLogDao dao) {
		this.dao = dao;
	}

}
