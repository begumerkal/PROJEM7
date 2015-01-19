package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bean.BossmapReward;
import com.wyd.empire.world.item.RewardItemsVo;

/**
 * The service interface for the TabBossmapReward entity.
 */
public interface IBossmapRewardService extends UniversalManager {
	/**
	 * 根据副本ID获得副本的显示奖励的物品
	 * 
	 * @param mapId
	 *            副本ID
	 * @param count
	 *            返回的奖励物品数量
	 * @return
	 */
	public List<BossmapReward> getBossmapRewardBymapId(int mapId, int sex, int count);

	/**
	 * 随机获取6*n件奖励物品
	 * 
	 * @return
	 */
	public List<RewardItemsVo> getRewardItems(List<Combat> combatList, int battleId);

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
	 * 初始化数据
	 */
	public void initData();
}