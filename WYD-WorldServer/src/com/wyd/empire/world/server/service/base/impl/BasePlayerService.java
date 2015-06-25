package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.dao.mysql.IPlayerDao;
import com.wyd.empire.world.entity.mysql.LoginReward;
import com.wyd.empire.world.entity.mysql.Player;
import com.wyd.empire.world.entity.mysql.PlayerInfo;
import com.wyd.empire.world.entity.mysql.PlayerOnline;
import com.wyd.empire.world.model.player.Record;
import com.wyd.empire.world.server.service.base.IPlayerService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The service class for the Player entity.
 */
public class BasePlayerService extends UniversalManagerImpl implements IPlayerService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IPlayerDao playerDao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "PlayerService";

	public BasePlayerService() {
		super();
	}

	/**
	 * Returns the singleton <code>IPlayerService</code> instance.
	 */
	public static IPlayerService getInstance(ApplicationContext context) {
		return (IPlayerService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setPlayerDao(IPlayerDao playerDao) {
		this.playerDao = playerDao;
		super.setDao(playerDao);
	}

	public IPlayerDao getPlayerDao() {
		return this.playerDao;
	}

	public List<Player> getPlayerList(int accountId) {
		List<Player> listPlayer = null;
		try {
			listPlayer = this.playerDao.getPlayerList(accountId);
			return listPlayer;
		} catch (Exception e) {
			return null;
		}
	}

	public Player getPlayerByName(String name) {
		try {
			return this.playerDao.getPlayerByName(name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 检查名字是否可用
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkName(String name) {
		return this.playerDao.checkName(name);
	}

	public Player getPlayerById(int id) {
		try {
			return this.playerDao.getPlayerById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Player> getFriendPlayerList(int[] ids) {

		return this.playerDao.getFriendPlayerList(ids);
	}

	@Override
	public List<Player> getPlayerByMoney(int moneyPlayers) {

		return this.playerDao.getPlayerByMoney(moneyPlayers);
	}

	// @Override
	// public List<Player> getPlayerByAlly(int killAllyPlayers) {
	//
	// return this.playerDao.getPlayerByAlly(killAllyPlayers);
	// }
	@Override
	public List<Player> getPlayerByEnemy(int killEnemyPlayers) {

		return this.playerDao.getPlayerByEnemy(killEnemyPlayers);
	}

	@Override
	public List<Player> getPlayerByLevel(int levelPlayers) {

		return this.playerDao.getPlayerByLevel(levelPlayers);
	}

	/**
	 * 获取同一服务器的所有角色
	 * 
	 * @return 同一服务器的所有角色
	 */
	public List<Player> getAllPlayer() {
		return this.playerDao.getAllPlayer();
	}

	/**
	 * 获取本服务器所有禁言中的角色
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageList getGagPlayers(String key, int pageIndex, int pageSize) {
		return this.playerDao.getGagPlayers(key, pageIndex, pageSize);
	}

	public Player getPlayerByKey(String key) {
		return this.playerDao.getPlayerByKey(key);
	}

	public PageList getBannedPlayer(String key, int pageIndex, int pageSize) {
		return this.playerDao.getBannedPlayer(key, pageIndex, pageSize);
	}

	public Long getAllPlayerNum(boolean isArea) {
		return playerDao.getAllPlayerNum(isArea);
	}

	public PageList getPlayerByKey(String key, int pageIndex, int pageSize) {
		return this.playerDao.getPlayerByKey(key, pageIndex, pageSize);
	}

	/**
	 * 根据用户名返回对应有效用户信息(valid=true)
	 * 
	 * @param name
	 *            用户名
	 * @param isArea
	 *            是否区分区域
	 * @return 对应有效用户信息
	 */
	public Player getPlayerByName(String name, boolean isArea) {
		try {
			return this.playerDao.getPlayerByName(name, isArea);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public long getPlayerLastOnLinTime(int playerId) {
		return this.playerDao.getPlayerLastOnLinTime(playerId);
	}

	/**
	 * 获取排行数据
	 * 
	 * @param type
	 *            0level,1winNum,2gold,3ticket
	 * @return
	 */
	public List<Record> getPlayerRecord(int type) {
		return this.playerDao.getPlayerRecord(type);
	}

	public List<Player> getLoginPlayer() {
		return this.playerDao.getLoginPlayer();
	}

	/**
	 * 根据玩家等级查询出召回奖励
	 * 
	 * @param level
	 *            玩家等级
	 * @return
	 */
	public LoginReward getRewardByLevel(int level) {
		return playerDao.getRewardByLevel(level);
	}

	/**
	 * 查询出所有的召回奖励列表
	 * 
	 * @param key
	 *            查询参数
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少
	 * @return
	 */
	public PageList findAllRecalls(String key, int pageIndex, int pageSize) {
		return playerDao.findAllRecalls(key, pageIndex, pageSize);
	}

	/**
	 * 根据多个ID删除召回奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByRewardIds(String ids) {
		playerDao.deleteByRewardIds(ids);
	}

	/**
	 * 根据玩家ID查询玩家最后一次登录游戏时间
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public PlayerOnline getLastOnlineByPlayerId(int playerId) {
		return playerDao.getLastOnlineByPlayerId(playerId);
	}

	/**
	 * 根据玩家等级获取玩家列表
	 * 
	 * @param minLevel
	 *            最小等级
	 * @param maxLevel
	 *            最高等级
	 * @param first
	 *            当前页面
	 * @param maxResult
	 *            每页大小
	 * @return
	 */
	public List<Object> getPlayerByLevel(int minLevel, int maxLevel, int first, int maxResult, String startTime, String endTime) {
		return playerDao.getPlayerByLevel(minLevel, maxLevel, first, maxResult, startTime, endTime);
	}

	/**
	 * 根据多个accountid查询出玩家信息
	 * 
	 * @param accountIds
	 *            分区id
	 * @return
	 */
	public List<Player> findByAccountIds(String accountIds) {
		return playerDao.findByAccountIds(accountIds);
	}

	/**
	 * 获得玩家充值钻石数
	 * 
	 * @param playerId
	 * @return
	 */
	public int getPlayerRechargeDiamond(int playerId) {
		return playerDao.getPlayerRechargeDiamond(playerId);
	}

	/**
	 * 获取玩家的附加信息
	 * 
	 * @param playerInfoId
	 * @return
	 */
	public PlayerInfo getPlayerInfoById(int playerInfoId) {
		return playerDao.getPlayerInfoById(playerInfoId);
	}

	/**
	 * 供定时器调用，用于定时所有在线玩家增加活力
	 */
	public void sysPlayersVigorUp() {
		ServiceManager.getManager().getPlayerService().sysPlayersVigorUp();
	}

	@Override
	public List<Object[]> getPlayerLevelAndFight() {
		return playerDao.getPlayerLevelAndFight();
	}

	@Override
	public int getMaxLevel() {
		return playerDao.getMaxLevel();
	}

	@Override
	public int getMaxFight() {
		return playerDao.getMaxFight();
	}
}