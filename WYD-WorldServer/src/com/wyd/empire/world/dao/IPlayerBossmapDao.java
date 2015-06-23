package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.PlayerBossmap;

/**
 * The DAO interface for the TabPlayerBossmap entity.
 */
public interface IPlayerBossmapDao extends UniversalDao {
	public void unLoadPlayerBossmap(Integer playerId);

	/**
	 * 获取玩家所有的副本进度
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerBossmap> getAllPlayerBossmapByPlayerId(int playerId);

	public PlayerBossmap addPlayerBossmap(PlayerBossmap playerBossmap);

	public PlayerBossmap getPlayerBossMap(int playerId, int mapId);
}