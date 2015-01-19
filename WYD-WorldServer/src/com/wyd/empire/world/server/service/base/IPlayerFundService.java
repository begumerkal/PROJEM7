package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.FundRecord;
import com.wyd.empire.world.bean.PlayerFund;

/**
 * The service interface for the PlayerFund entity.
 */
public interface IPlayerFundService extends UniversalManager {

	/**
	 * 根据玩家ID获取玩家基金购买信息
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 玩家基金购买信息
	 */
	public PlayerFund getPlayerFund(int playerId);

	/**
	 * 删除玩家的基金购买记录
	 * 
	 * @param playerId
	 */
	public void deletePlayerFundByPlayer(int playerId);

	/**
	 * 根据用户信息获取用户基金领取记录
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 用户信息获取用户基金领取记录
	 */
	public List<FundRecord> getFundRecord(int playerId);

	/**
	 * 删除玩家的基金领取记录
	 * 
	 * @param playerId
	 */
	public void deleteFundRecordByPlayer(int playerId);
}
