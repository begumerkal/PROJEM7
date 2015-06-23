package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.entity.mysql.LoginReward;
import com.wyd.empire.world.entity.mysql.Player;
import com.wyd.empire.world.entity.mysql.PlayerInfo;
import com.wyd.empire.world.entity.mysql.PlayerOnline;
import com.wyd.empire.world.model.player.Record;

/**
 * The service interface for the Player entity.
 */
public interface IPlayerService extends UniversalManager {
	public List<Player> getPlayerList(int accountId);

	/**
	 * 检查名字是否可用
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkName(String name);

	public Player getPlayerByName(String name);

	public Player getPlayerById(int id);

	public List<Player> getFriendPlayerList(int[] ids);

	public List<Player> getPlayerByMoney(int moneyPlayers);

	public List<Player> getPlayerByLevel(int levelPlayers);

	public List<Player> getPlayerByEnemy(int killEnemyPlayers);

	/**
	 * 获取同一服务器的所有角色
	 * 
	 * @return 同一服务器的所有角色
	 */
	public List<Player> getAllPlayer();

	/**
	 * 获取本服务器所有禁言中的角色
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageList getGagPlayers(String key, int pageIndex, int pageSize);

	/**
	 * 根据玩家id或名称查询用户
	 * 
	 * @param key
	 * @return
	 */
	public Player getPlayerByKey(String key);

	/**
	 * 获取所有封禁状态下的玩家
	 * 
	 * @return
	 */
	public PageList getBannedPlayer(String key, int pageIndex, int pageSize);

	/**
	 * 获取本服所有玩家数
	 * 
	 * @return
	 */
	public Long getAllPlayerNum(boolean isArea);

	/**
	 * 根据玩家id或名称查询用户
	 * 
	 * @param key
	 * @return
	 */
	public PageList getPlayerByKey(String key, int pageIndex, int pageSize);

	/**
	 * 根据用户名返回对应有效用户信息(valid=true)
	 * 
	 * @param name
	 *            用户名
	 * @param isArea
	 *            是否区分区域
	 * @return 对应有效用户信息
	 */
	public Player getPlayerByName(String name, boolean isArea);

	/**
	 * 获取玩家最后离线时间
	 * 
	 * @param playerId
	 * @return
	 */
	public long getPlayerLastOnLinTime(int playerId);

	/**
	 * 获取排行数据
	 * 
	 * @param type
	 *            0level,1winNum,2gold,3ticket
	 * @return
	 */
	public List<Record> getPlayerRecord(int type);

	/**
	 * 获取加载的用
	 * 
	 * @return
	 */
	public List<Player> getLoginPlayer();

	/**
	 * 根据级别和战斗力获取玩家列表
	 * 
	 * @param level
	 * @param fight
	 * @return
	 */
	public List<Object[]> getPlayerLevelAndFight();

	/**
	 * 获取本区玩家最高等级
	 * 
	 * @return
	 */
	public int getMaxLevel();

	/**
	 * 获取本区玩家最高战力
	 * 
	 * @return
	 */
	public int getMaxFight();

	/**
	 * 根据玩家等级查询出召回奖励
	 * 
	 * @param level
	 *            玩家等级
	 * @return
	 */
	public LoginReward getRewardByLevel(int level);

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
	public PageList findAllRecalls(String key, int pageIndex, int pageSize);

	/**
	 * 根据多个ID删除召回奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByRewardIds(String ids);

	/**
	 * 根据玩家ID查询玩家最后一次登录游戏时间
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public PlayerOnline getLastOnlineByPlayerId(int playerId);

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
	public List<Object> getPlayerByLevel(int minLevel, int maxLevel, int first, int maxResult, String startTime, String endTime);

	/**
	 * 根据多个accountid查询出玩家信息
	 * 
	 * @param accountIds
	 *            分区id
	 * @return
	 */
	public List<Player> findByAccountIds(String accountIds);

	/**
	 * 获得玩家充值钻石数
	 * 
	 * @param playerId
	 * @return
	 */
	public int getPlayerRechargeDiamond(int playerId);

	/**
	 * 获取玩家的附加信息
	 * 
	 * @param playerInfoId
	 * @return
	 */
	public PlayerInfo getPlayerInfoById(int playerInfoId);

	/**
	 * 供定时器调用，用于定时所有在线玩家增加活力
	 */
	public void sysPlayersVigorUp();
}