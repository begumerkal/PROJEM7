package com.wyd.empire.world.server.service.base;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;

public interface IGetLogService extends UniversalManager {
	/**
	 * 获取发放物品日志列表
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageList getItemLogList(String key, int pageIndex, int pageSize);

	/**
	 * 获取发放金币日志列表
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageList getGoldCountLogList(String key, int pageIndex, int pageSize);

	/**
	 * 获取强化日志列表
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageList getStrongrecordLogList(String key, int pageIndex, int pageSize);
}
