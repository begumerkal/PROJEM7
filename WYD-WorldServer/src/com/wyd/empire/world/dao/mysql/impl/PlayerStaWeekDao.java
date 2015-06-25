package com.wyd.empire.world.dao.mysql.impl;

import java.util.ArrayList;
import java.util.List;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.dao.mysql.IPlayerStaWeekDao;
import com.wyd.empire.world.entity.mysql.PlayerStaWeek;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class PlayerStaWeekDao extends UniversalDaoHibernate implements IPlayerStaWeekDao {
	public PlayerStaWeekDao() {
		super();
	}

	/**
	 * 根据玩家id获取玩家指定周或月的记录
	 * 
	 * @param playerId
	 *            用户角色
	 * @param type
	 *            0周，1月
	 * @param wrmNum
	 *            周或月数
	 * @return 用户角色排行数据
	 */
	public PlayerStaWeek getPlayerStaWeekByPlayerId(int playerId, int type, int wrmNum) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + PlayerStaWeek.class.getSimpleName() + " WHERE 1 = 1");
		hql.append(" AND playerId = ? ");
		values.add(playerId);
		hql.append(" AND isWeek = ? ");
		values.add(type);
		hql.append(" AND wrmNum = ? ");
		values.add(wrmNum);
		return (PlayerStaWeek) this.getClassObj(hql.toString(), values.toArray());
	}

	/**
	 * 获得玩家排行榜
	 * 
	 * @param type
	 *            0周榜，1月榜
	 * @param orderBy
	 * @param max
	 *            返回最大记录数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlayerStaWeek> getTopRecord(int type, int wrmNum, String property, String orderBy, int max) {
		StringBuffer hql = new StringBuffer();
		hql.append("FROM PlayerStaWeek WHERE areaId = ? AND isWeek = ? AND wrmNum = ? AND " + property + ">0 ORDER BY " + orderBy + " DESC");
		try {
			List<PlayerStaWeek> psList = this.getList(hql.toString(), new Object[]{WorldServer.config.getAreaId(), type, wrmNum}, max);
			return psList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void clearTopData(int type, int wrmNum, int lastWrmNum) {
		this.execute("delete from PlayerStaWeek where isWeek=? and wrmNum != ? and wrmNum != ?", new Object[]{type, wrmNum, lastWrmNum});
	}
}