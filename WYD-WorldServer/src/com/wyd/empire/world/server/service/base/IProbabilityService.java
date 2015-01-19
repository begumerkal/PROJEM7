package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IProbabilityService extends UniversalManager {
	/**
	 * 根据权值随机获取一个概率id
	 * 
	 * @param idList
	 * @return
	 */
	public int getProbabilityId(List<Integer> idList);

	/**
	 * 初始化数据
	 */
	public void initData();
}