package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.PlayerBossmap;

/**
 * The service interface for the TabPlayerBossmap entity.
 */
public interface IPlayerBossmapService extends UniversalManager {
	public void unLoadPlayerBossmap(Integer playerId);

	/**
	 * 获取玩家所有的副本进度
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerBossmap> getAllPlayerBossmapByPlayerId(int playerId);

	public PlayerBossmap loadPlayerBossMap(int playerId, int mapId);

	/**
	 * 清空通关次数
	 * 
	 * @param playerId
	 * @param validate
	 *            为true时会检查resettime是否跨天
	 */
	public void resetPassTimes(int playerId, boolean validate);
}