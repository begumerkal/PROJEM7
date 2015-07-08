package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.mysql.dao.UniversalDao;
import com.wyd.db.mysql.page.PageList;
import com.wyd.empire.world.entity.mysql.BossmapReward;

/**
 * The DAO interface for the TabBossmapReward entity.
 */
public interface IBossmapRewardDao extends UniversalDao {
	/**
	 * GM工具--根据BOSS地图获取副本奖励
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList findMapReward(String key, int pageIndex, int pageSize);

	/**
	 * 获取指定副本的专属物品
	 * 
	 * @param mapId
	 *            -1为通用奖励物品
	 * @return
	 */
	public List<BossmapReward> getBossmapRewardListByMapId(int mapId, int difficulty, int sex);

	public List<BossmapReward> getBossmapRewardListByMapId(int mapId, int difficulty);

	/**
	 * 获取指定副本的奖励物品的权值id
	 * 
	 * @param mapId
	 * @param difficulty
	 *            副本难度
	 * @param isDiamond
	 *            是否包含钻石专属物品
	 * @return
	 */
	public List<Integer> getProbabilityIdByMapId(int mapId, int difficulty, boolean isDiamond);

	/**
	 * 初始化数据
	 */
	public void initData();
}