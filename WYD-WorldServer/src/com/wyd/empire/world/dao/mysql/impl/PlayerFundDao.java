package com.wyd.empire.world.dao.mysql.impl;

import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.dao.mysql.IPlayerFundDao;
import com.wyd.empire.world.entity.mysql.FundRecord;
import com.wyd.empire.world.entity.mysql.PlayerFund;

/**
 * The DAO class for the PlayerFund entity.
 */
public class PlayerFundDao extends UniversalDaoHibernate implements IPlayerFundDao {
	public PlayerFundDao() {
		super();
	}

	/**
	 * 根据玩家ID获取玩家基金购买信息
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 玩家基金购买信息
	 */
	@SuppressWarnings("unchecked")
	public PlayerFund getPlayerFund(int playerId) {
		StringBuffer hsql = new StringBuffer();
		hsql.append(" FROM " + PlayerFund.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND player.id = ? ");
		List<PlayerFund> playerFundList = this.getList(hsql.toString(), new Object[]{playerId}, 1);
		if (playerFundList != null && !playerFundList.isEmpty()) {
			return playerFundList.get(0);
		} else {
			return null;
		}
	}

	public void deletePlayerFundByPlayer(int playerId) {
		StringBuffer hsql = new StringBuffer();
		hsql.append("DELETE FROM ");
		hsql.append(PlayerFund.class.getSimpleName());
		hsql.append(" WHERE player.id = ? ");
		this.execute(hsql.toString(), new Object[]{playerId});
	}

	/**
	 * 根据用户信息获取用户基金领取记录
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 用户信息获取用户基金领取记录
	 */
	@SuppressWarnings("unchecked")
	public List<FundRecord> getFundRecord(int playerId) {
		StringBuffer hsql = new StringBuffer();
		hsql.append(" FROM " + FundRecord.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND player.id = ? ");
		hsql.append(" ORDER BY level DESC ");
		return this.getList(hsql.toString(), new Object[]{playerId});
	}

	public void deleteFundRecordByPlayer(int playerId) {
		StringBuffer hsql = new StringBuffer();
		hsql.append("DELETE FROM ");
		hsql.append(FundRecord.class.getSimpleName());
		hsql.append(" WHERE player.id = ? ");
		this.execute(hsql.toString(), new Object[]{playerId});
	}
}
