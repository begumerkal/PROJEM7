package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.mysql.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.DrawItem;
import com.wyd.empire.world.entity.mysql.DrawRate;
import com.wyd.empire.world.entity.mysql.DrawType;
import com.wyd.empire.world.entity.mysql.PlayerDraw;

/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IDrawDao extends UniversalDao {

	/**
	 * 初始化数据
	 */
	public void initData();

	/**
	 * 获得玩家的奖励物品
	 * 
	 * @param playerId
	 * @return
	 */
	public List<DrawItem> getRewardByPlayerId(int playerId, int type);

	/**
	 * 根据typeId获得抽奖类型
	 * 
	 * @param typeId
	 * @return
	 */
	public DrawType getDrawItemByItemId(int typeId);

	/**
	 * 刷新抽奖列表
	 * 
	 * @param player
	 * @param typeId
	 *            抽奖类型
	 * @param refreshMark
	 *            刷新标识，0表示类型刷新，1表示所有物品刷新
	 */
	public List<DrawItem> refreshDrawItem(int playerId, int typeId, int refreshMark);

	/**
	 * 获得玩家奖励物品
	 * 
	 * @param playerId
	 * @param starNum
	 * @param typeId
	 * @return
	 */
	public DrawItem getDrawRewardByPlayerIdAndStarNum(int playerId, int starNum, int typeId);

	/**
	 * 更新已抽中的物品
	 * 
	 * @param playerId
	 * @param typeId
	 * @param starNum
	 */
	public void updateDrawItem(int playerId, int typeId, int starNum, DrawItem di);

	public DrawRate getDrawRateById(int id);

	/**
	 * 获得玩家抽奖的记录
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerDraw getPlayerDrawByPlayerId(int playerId);
}