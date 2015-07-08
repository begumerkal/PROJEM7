package com.wyd.empire.world.dao.mysql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.dao.mysql.IPlayerBossmapDao;
import com.wyd.empire.world.entity.mysql.PlayerBossmap;

/**
 * The DAO class for the TabPlayerBossmap entity.
 */
public class PlayerBossmapDao extends UniversalDaoHibernate implements IPlayerBossmapDao {
	private Map<Integer, Map<Integer, PlayerBossmap>> playerBossmapMap = new HashMap<Integer, Map<Integer, PlayerBossmap>>();

	public PlayerBossmapDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	private void loadPlayerBossmap(Integer playerId) {
		if (!playerBossmapMap.containsKey(playerId)) {
			Map<Integer, PlayerBossmap> psMap = new HashMap<Integer, PlayerBossmap>();
			playerBossmapMap.put(playerId, psMap);
			StringBuilder hql = new StringBuilder();
			hql.append("from PlayerBossmap where playerId = ?");
			List<PlayerBossmap> pbList = getList(hql.toString(), new Object[]{playerId});
			for (PlayerBossmap pb : pbList) {
				psMap.put(pb.getMapId(), pb);
			}
		}
	}

	public void unLoadPlayerBossmap(Integer playerId) {
		playerBossmapMap.remove(playerId);
	}

	public PlayerBossmap addPlayerBossmap(PlayerBossmap playerBossmap) {
		loadPlayerBossmap(playerBossmap.getPlayerId());
		playerBossmap = (PlayerBossmap) save(playerBossmap);
		playerBossmapMap.get(playerBossmap.getPlayerId()).put(playerBossmap.getMapId(), playerBossmap);
		return playerBossmap;
	}

	public List<PlayerBossmap> getAllPlayerBossmapByPlayerId(int playerId) {
		loadPlayerBossmap(playerId);
		List<PlayerBossmap> list = new ArrayList<PlayerBossmap>(playerBossmapMap.get(playerId).values());
		return list;
	}

	public PlayerBossmap getPlayerBossMap(int playerId, int mapId) {
		loadPlayerBossmap(playerId);
		return playerBossmapMap.get(playerId).get(mapId);
	}
}