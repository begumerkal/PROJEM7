package com.wyd.empire.world.server.service.base;

import com.wyd.db.service.UniversalManager;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IRandomNameService extends UniversalManager {
	/**
	 * 获取随机名称
	 * 
	 * @param sex
	 *            角色性别
	 * @return
	 * @throws Exception
	 */
	public String getName(int sex) throws Exception;
}