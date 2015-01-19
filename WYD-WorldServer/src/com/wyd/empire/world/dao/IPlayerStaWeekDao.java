package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.bean.PlayerStaWeek;

/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IPlayerStaWeekDao extends UniversalDao {

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
	public PlayerStaWeek getPlayerStaWeekByPlayerId(int playerId, int type, int wrmNum);

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
	public List<PlayerStaWeek> getTopRecord(int type, int wrmNum, String property, String orderBy, int max);

	/**
	 * 清理过期的排行数据
	 * 
	 * @param type
	 *            0周，1月
	 * @param wrmNum
	 * @param lastWrmNum
	 */
	public void clearTopData(int type, int wrmNum, int lastWrmNum);
}