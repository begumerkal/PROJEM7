package com.wyd.empire.world.server.service.base.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.context.ApplicationContext;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.PlayerBossmap;
import com.wyd.empire.world.common.util.DateUtil;
import com.wyd.empire.world.dao.IPlayerBossmapDao;
import com.wyd.empire.world.server.service.base.IPlayerBossmapService;

/**
 * The service class for the TabPlayerBossmap entity.
 */
public class PlayerBossmapService extends UniversalManagerImpl implements IPlayerBossmapService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IPlayerBossmapDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "PlayerBossmapService";

	public PlayerBossmapService() {
		super();
	}

	/**
	 * Returns the singleton <code>IPlayerBossmapService</code> instance.
	 */
	public static IPlayerBossmapService getInstance(ApplicationContext context) {
		return (IPlayerBossmapService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IPlayerBossmapDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPlayerBossmapDao getDao() {
		return this.dao;
	}

	/**
	 * 获取玩家所有的副本进度
	 * 
	 * @param playerId
	 * @return
	 */
	public List<PlayerBossmap> getAllPlayerBossmapByPlayerId(int playerId) {
		return dao.getAllPlayerBossmapByPlayerId(playerId);
	}

	@Override
	public PlayerBossmap loadPlayerBossMap(int playerId, int mapId) {
		PlayerBossmap playerMap = dao.getPlayerBossMap(playerId, mapId);
		if (playerMap == null) {
			playerMap = new PlayerBossmap();
			playerMap.setMapId(mapId);
			playerMap.setPlayerId(playerId);
			playerMap.setStar(0);
			Date date = new Date();
			playerMap.setCreateTime(date);
			playerMap.setLastPlayTime(date);
			playerMap.setResetTime(date);
			playerMap.setPassTimes(0);
			playerMap.setTotalPassTimes(0);
			playerMap = dao.addPlayerBossmap(playerMap);
		}
		return playerMap;
	}

	@Override
	public void resetPassTimes(int playerId, boolean validate) {
		List<PlayerBossmap> playerMapList = dao.getAllPlayerBossmapByPlayerId(playerId);
		Calendar cal = Calendar.getInstance();
		for (PlayerBossmap playerMap : playerMapList) {
			if (playerMap.getPassTimes() > 0) {
				// 在需要验证时，跳过当天重置过的记录
				if (validate && DateUtil.isSameDate(playerMap.getResetTime(), cal.getTime())) {
					continue;
				}
				playerMap.setPassTimes(0);
				playerMap.setResetTime(cal.getTime());
				dao.update(playerMap);
			}
		}
	}

	@Override
	public void unLoadPlayerBossmap(Integer playerId) {
		dao.unLoadPlayerBossmap(playerId);
	}
}