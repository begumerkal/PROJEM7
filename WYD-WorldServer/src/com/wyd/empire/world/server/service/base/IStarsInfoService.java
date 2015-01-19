package com.wyd.empire.world.server.service.base;

import java.util.Collection;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.StarsInfo;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The service interface for the TabTool entity.
 */
public interface IStarsInfoService extends UniversalManager {
	/**
	 * 初始化数据
	 */
	public void initData();

	/**
	 * 根据星级获取升星信息
	 * 
	 * @param stars
	 * @return
	 */
	public StarsInfo getByLevel(int stars);

	/**
	 * 获取所有的升星信息
	 * 
	 * @return
	 */
	public Collection<StarsInfo> getAllStarsConfig();

	/**
	 * 获取升星属性加成
	 * 
	 * @param item
	 *            物品
	 * @param star
	 *            装备星级
	 * @param type
	 *            属性类型 1攻击，2血量，3防御 ，4暴击，5免暴,6破防
	 * @return
	 */
	public int getStarsAddition(ShopItem item, int star, int type);

	public StarsInfo getStarConfig(int level);

	public void sendConfig(WorldPlayer player);
}