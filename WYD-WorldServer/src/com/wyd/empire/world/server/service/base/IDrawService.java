package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.DrawItem;
import com.wyd.empire.world.bean.DrawRate;
import com.wyd.empire.world.bean.DrawType;
import com.wyd.empire.world.bean.PlayerDraw;
import com.wyd.empire.world.player.WorldPlayer;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IDrawService extends UniversalManager {

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
	public List<DrawItem> toGetRewardByPlayerId(WorldPlayer player, int type);

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

	public DrawRate getDrawRateById(int id);

	/**
	 * 更新已抽中的物品
	 * 
	 * @param playerId
	 * @param typeId
	 * @param starNum
	 */
	public void updateDrawItem(int playerId, int typeId, int starNum, DrawItem di);

	/**
	 * 获得玩家抽奖的记录
	 * 
	 * @param playerId
	 * @return
	 */
	public PlayerDraw checkPlayerDrawByPlayerId(int playerId);

	/**
	 * 保存抽奖记录
	 * 
	 * @param player
	 */
	public void savePlayerDraw(WorldPlayer player);
}