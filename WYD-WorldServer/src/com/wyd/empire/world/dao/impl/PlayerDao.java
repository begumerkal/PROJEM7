package com.wyd.empire.world.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.springframework.util.StringUtils;
import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.LoginReward;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.bean.PlayerInfo;
import com.wyd.empire.world.bean.PlayerOnline;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.dao.IPlayerDao;
import com.wyd.empire.world.player.Record;
import com.wyd.empire.world.server.handler.player.GetTopRecordHandler;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.ChatService;
import com.wyd.empire.world.server.service.impl.SystemLogService;

/**
 * The DAO class for the Player entity.
 */
public class PlayerDao extends UniversalDaoHibernate implements IPlayerDao {
	public PlayerDao() {
		super();
	}

	/**
	 * 根据账户id，返回相对应玩家列表
	 * 
	 * @param accountId
	 *            据账户id
	 * @return 玩家列表
	 */
	@SuppressWarnings("unchecked")
	public List<Player> getPlayerList(int accountId) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" and accountId = ? ");
		values.add(accountId);
		List<Player> playerList = getList(hsql.toString(), values.toArray());
		if (playerList != null && !playerList.isEmpty()) {
			return playerList;
		} else {
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
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append("SELECT COUNT(*) FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" and name = ? ");
		values.add(name.trim());
		int count = Integer.parseInt(this.getClassObj(hsql.toString(), values.toArray()).toString());
		if (count > 0)
			return false;
		else
			return true;
	}

	/**
	 * 根据用户名返回对应有效用户信息(valid=true)
	 * 
	 * @param name
	 *            用户名
	 * @return 对应有效用户信息
	 */
	public Player getPlayerByName(String name) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" and name = ? ");
		values.add(name.trim());
		hsql.append(" and areaId = ? ");
		values.add(Server.config.getMachineCode());
		return (Player) this.getClassObj(hsql.toString(), values.toArray());
	}

	/**
	 * 根据用户名返回对应有效用户信息(valid=true)
	 * 
	 * @param name
	 *            用户名
	 * @return 有效用户信息
	 */
	public Player getPlayerById(int id) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" and id = ? ");
		values.add(id);
		hsql.append(" and areaId = ? ");
		values.add(Server.config.getMachineCode());
		return (Player) this.getClassObj(hsql.toString(), values.toArray());
	}

	/**
	 * 根据好友玩家ID数组，获取玩家好友列表
	 * 
	 * @param ids
	 *            好友玩家ID数组
	 * @return 好友玩家列表信息
	 */
	@SuppressWarnings("unchecked")
	public List<Player> getFriendPlayerList(int[] ids) {
		if (ids == null || ids.length == 0) {
			return null;
		}
		StringBuilder idsTemp = new StringBuilder();
		for (int i = 0; i < ids.length - 1; i++) {
			idsTemp.append(ids[i]);
			idsTemp.append(",");
		}
		idsTemp.append(ids[ids.length - 1]);
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" and id in (" + idsTemp.toString() + ")");
		List<Player> listPlayer = getList(hsql.toString(), values.toArray());
		if (listPlayer != null && !listPlayer.isEmpty()) {
			return listPlayer;
		} else {
			return null;
		}
	}

	/**
	 * 根据金钱大小排序，获取金钱大于0的有效玩家列表
	 * 
	 * @param moneyPlayers
	 *            返回列表最大值限制
	 * @return 玩家信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<Player> getPlayerByMoney(int moneyPlayers) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" and areaId = ? ");
		values.add(Server.config.getMachineCode());
		hsql.append(" and not (name like 'GM-%') ");
		hsql.append(" and money > 0 ");
		hsql.append(" and status = ? ");
		values.add(Common.PLAYER_USESTATE_NORMAL);
		hsql.append(" ORDER BY money DESC ");
		List<Player> listPlayer = getList(hsql.toString(), values.toArray(), moneyPlayers);
		if (listPlayer != null && !listPlayer.isEmpty()) {
			return listPlayer;
		} else {
			return null;
		}
	}

	/**
	 * 根据杀死敌人次数排序，获取玩家信息列表
	 * 
	 * @param killEnemyPlayers
	 *            返回列表最大值限制
	 * @return 玩家信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<Player> getPlayerByEnemy(int killEnemyPlayers) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" and areaId = ? ");
		values.add(Server.config.getMachineCode());
		hsql.append(" and not (name like 'GM-%') ");
		hsql.append(" and status = ? ");
		values.add(Common.PLAYER_USESTATE_NORMAL);
		hsql.append(" ORDER BY killEnemy DESC ");
		List<Player> listPlayer = getList(hsql.toString(), values.toArray(), killEnemyPlayers);
		if (listPlayer != null && !listPlayer.isEmpty()) {
			return listPlayer;
		} else {
			return null;
		}
	}

	/**
	 * 根据级别数排序，获取玩家信息列表
	 * 
	 * @param levelPlayers
	 *            返回列表最大值限制
	 * @return 玩家信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<Player> getPlayerByLevel(int levelPlayers) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" and areaId = ? ");
		values.add(Common.PLAYER_USESTATE_NORMAL);
		hsql.append(" and not (name like 'GM-%') ");
		hsql.append(" and status = ? ");
		values.add(Server.config.getMachineCode());
		hsql.append(" ORDER BY level DESC ");
		List<Player> listPlayer = getList(hsql.toString(), values.toArray(), levelPlayers);
		if (listPlayer != null && !listPlayer.isEmpty()) {
			return listPlayer;
		} else {
			return null;
		}
	}

	/**
	 * 获取同一服务器的所有角色
	 * 
	 * @return 同一服务器的所有角色
	 */
	@SuppressWarnings("unchecked")
	public List<Player> getAllPlayer() {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND areaId = ? ");
		values.add(Server.config.getMachineCode());
		return getList(hsql.toString(), values.toArray());
	}

	/**
	 * 获取本服务器所有禁言中的角色
	 * 
	 * @return 本服务器所有禁言中的角色
	 */
	public PageList getGagPlayers(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND areaId = ? ");
		values.add(Server.config.getMachineCode());
		hsql.append(" AND chatStatus != ? ");
		values.add(ChatService.CHAT_STATUS1);
		if (null != key && key.length() > 0) {
			if (ServiceUtils.isNumeric(key)) {
				hsql.append(" AND id = ? ");
				values.add(Integer.parseInt(key));
			} else {
				hsql.append(" AND name = ? ");
				values.add(key);
			}
		}
		hsql.append(" AND prohibitTime > ? ");
		values.add(new Date());
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 根据玩家id或名称查询用户
	 * 
	 * @param key
	 * @return
	 */
	public Player getPlayerByKey(String key) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND areaId = ? ");
		values.add(Server.config.getMachineCode());
		if (ServiceUtils.isNumeric(key)) {
			hsql.append(" AND id = ? ");
			values.add(Integer.parseInt(key));
		} else {
			hsql.append(" AND name = ? ");
			values.add(key);
		}
		return (Player) getClassObj(hsql.toString(), values.toArray());
	}

	public PageList getBannedPlayer(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND areaId = ? ");
		values.add(Server.config.getMachineCode());
		hsql.append(" AND status = ? ");
		values.add((byte) 0);
		if (null != key && key.length() > 0) {
			if (ServiceUtils.isNumeric(key)) {
				hsql.append(" AND id = ? ");
				values.add(Integer.parseInt(key));
			} else {
				hsql.append(" AND name = ? ");
				values.add(key);
			}
		}
		hsql.append(" AND ? BETWEEN bsTime and beTime");
		values.add(new Date());
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	public Long getAllPlayerNum(boolean isArea) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hsql.append("select count(*) from " + Player.class.getSimpleName() + " ");
		if (isArea) {
			hsql.append(" WHERE areaId = ? ");
			values.add(Server.config.getMachineCode());
		}
		return (Long) getClassObj(hsql.toString(), values.toArray());
	}

	/**
	 * 根据玩家id或名称,角色创建时间查询用户
	 * 
	 * @param key
	 * @return
	 */
	public PageList getPlayerByKey(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND areaId = ? ");
		values.add(Server.config.getMachineCode());
		String[] params = key.split("\\|");
		for (int i = 0; i < params.length; i++) {
			if (StringUtils.hasText(params[i])) {
				switch (i) {
					case 0 :
						if (ServiceUtils.isNumeric(params[0])) {
							hsql.append(" AND id = ? ");
							values.add(Integer.parseInt(params[0]));
						} else {
							hsql.append(" AND (name like '" + params[0] + "%' or name like '%" + params[0] + "' or name like '%"
									+ params[0] + "%') ");
						}
						break;
					case 1 :
						try {
							hsql.append(" AND createTime BETWEEN ? and ? ");
							values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[1]));
							values.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(params[2]));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						break;
					case 3 :
						hsql.append(" ORDER BY " + params[3] + " DESC");
						break;
					default :
						break;
				}
			}
		}
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
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
	@SuppressWarnings("unchecked")
	public Player getPlayerByName(String name, boolean isArea) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Player.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND name = ? ");
		values.add(name.trim());
		if (isArea) {
			hsql.append(" AND areaId = ? ");
			values.add(Server.config.getMachineCode());
		}
		hsql.append(" AND status = ? ");
		values.add(Common.PLAYER_USESTATE_NORMAL);
		// return (Player) this.getUniqueResult(hsql.toString(),
		// values.toArray());
		// getUniqueResult 转 getList
		List<Player> list = this.getList(hsql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	public long getPlayerLastOnLinTime(int playerId) {
		String hql = "from PlayerOnline where playerId=? order by id desc";
		@SuppressWarnings("unchecked")
		List<PlayerOnline> poList = getList(hql, new Object[]{playerId}, 1);
		if (null != poList && poList.size() > 0) {
			return poList.get(0).getOffTime().getTime();
		} else {
			return System.currentTimeMillis();
		}
	}

	/**
	 * 获取排行数据
	 * 
	 * @param type
	 *            0level,1winNum,2gold,3ticket
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Record> getPlayerRecord(int type) {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, name,");
		switch (type) {
			case 0 :
				sql.append(" level");
				break;
			case 1 :
				sql.append(" (winTimes1v1_athletics+winTimes1v1_champion+winTimes1v1_relive+winTimes2v2_athletics+winTimes2v2_champion+winTimes2v2_relive+winTimes3v3_athletics+winTimes3v3_champion+winTimes3v3_relive)");
				break;
			case 2 :
				sql.append(" money_gold");
				break;
			case 3 :
				sql.append(" amount");
				break;
		}
		sql.append(" as record from tab_player where areaId=? order by record desc");
		List<Object[]> objList = this.getListBySql(sql.toString(), new Object[]{Server.config.getMachineCode()},
				GetTopRecordHandler.MAX_COUNT);
		List<Record> recordList = new ArrayList<Record>();
		Record record;
		for (Object[] objs : objList) {
			record = new Record();
			recordList.add(record);
			record.setId(Integer.parseInt(objs[0].toString()));
			record.setName(objs[1].toString());
			record.setData(Integer.parseInt(objs[2].toString()));
		}
		objList = null;
		return recordList;
	}

	/**
	 * 获取加载的用
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Player> getLoginPlayer() {
		String hql = "from Player where areaId=? order by level desc";
		List<Player> playerList = new ArrayList<Player>();
		playerList.addAll(this.getList(hql, new Object[]{Server.config.getMachineCode()}, SystemLogService.RECORD_COUNT));
		hql = "from Player where areaId=? order by (winTimes1v1Athletics+winTimes1v1Champion+winTimes1v1Relive+winTimes2v2Athletics+winTimes2v2Champion+winTimes2v2Relive+winTimes3v3Athletics+winTimes3v3Champion+winTimes3v3Relive) desc";
		playerList.addAll(this.getList(hql, new Object[]{Server.config.getMachineCode()}, SystemLogService.RECORD_COUNT));
		hql = "from Player where areaId=? order by moneyGold desc";
		playerList.addAll(this.getList(hql, new Object[]{Server.config.getMachineCode()}, SystemLogService.RECORD_COUNT));
		hql = "from Player where areaId=? order by amount desc";
		playerList.addAll(this.getList(hql, new Object[]{Server.config.getMachineCode()}, SystemLogService.RECORD_COUNT));
		hql = "from Player where areaId=? and level <= 10";
		playerList.addAll(this.getList(hql, new Object[]{Server.config.getMachineCode()}, SystemLogService.RECORD_COUNT));
		return playerList;
	}

	@SuppressWarnings({"unchecked"})
	public List<Object[]> getPlayerLevelAndFight() {
		String sql = "SELECT id,level,fight FROM Player WHERE areaId=?";
		return this.getList(sql, new Object[]{Server.config.getMachineCode()});
	}

	public int getMaxLevel() {
		String hsql = "SELECT MAX(level) FROM Player WHERE areaId=?";
		Object maxLevel = getClassObj(hsql, new Object[]{Server.config.getMachineCode()});
		if (null == maxLevel) {
			return 0;
		}
		return Integer.parseInt(maxLevel.toString());
	}

	public int getMaxFight() {
		String hsql = "SELECT MAX(fight) FROM Player WHERE areaId=?";
		Object maxFight = getClassObj(hsql, new Object[]{Server.config.getMachineCode()});
		if (null == maxFight) {
			return 0;
		}
		return Integer.parseInt(maxFight.toString());
	}

	/**
	 * 根据玩家等级查询出召回奖励
	 * 
	 * @param level
	 *            玩家等级
	 * @return
	 */
	public LoginReward getRewardByLevel(int level) {
		return (LoginReward) this.getUniqueResult("FROM " + LoginReward.class.getSimpleName()
				+ " WHERE minLevel<=? AND maxLevel>=? AND areaId=?", new Object[]{level, level, Server.config.getAreaId()});
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
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + LoginReward.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND areaId = ? ");
		values.add(Server.config.getAreaId());
		hsql.append(" ORDER BY id desc ");
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 根据多个ID删除召回奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByRewardIds(String ids) {
		this.execute("DELETE " + LoginReward.class.getSimpleName() + " WHERE id in (" + ids + ")", new Object[]{});
	}

	/**
	 * 根据玩家ID查询玩家最后一次登录游戏时间
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PlayerOnline getLastOnlineByPlayerId(int playerId) {
		List<PlayerOnline> list = getList(" FROM PlayerOnline WHERE player.id = ? ORDER BY onTime DESC", new Object[]{playerId}, 1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
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
	@SuppressWarnings("unchecked")
	public List<Object> getPlayerByLevel(int minLevel, int maxLevel, int first, int maxResult, String startTime, String endTime) {
		return (List<Object>) getLimitedList("SELECT id FROM Player WHERE updateTime BETWEEN '" + startTime + "' AND '" + endTime
				+ "' AND level > " + minLevel + " AND level < " + maxLevel + " AND areaId = "
				+ ServiceManager.getManager().getConfiguration().getInt("machinecode"), first, maxResult);
	}

	/**
	 * 根据多个accountid查询出玩家信息
	 * 
	 * @param accountIds
	 *            分区id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Player> findByAccountIds(String accountIds) {
		StringBuilder hql = new StringBuilder();
		hql.append(" FROM Player WHERE 1 = 1 ");
		hql.append(" AND ( ");
		for (String id : accountIds.split(",")) {
			hql.append(" accountId = " + id + " or ");
		}
		return this.getList(hql.substring(0, hql.lastIndexOf("or")) + " ) ", new Object[]{});
	}

	/**
	 * 获得玩家充值钻石数
	 * 
	 * @param playerId
	 * @return
	 */
	public int getPlayerRechargeDiamond(int playerId) {
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT sum(amount) From PlayerBill where playerId = ? and origin in (1,14)");
		Object obj = getUniqueResult(hql.toString(), new Object[]{playerId});
		if (null == obj) {
			return 0;
		} else {
			return Integer.parseInt(obj.toString());
		}
	}

	/**
	 * 获取玩家的附加信息
	 * 
	 * @param playerInfoId
	 * @return
	 */
	public PlayerInfo getPlayerInfoById(int playerInfoId) {
		return (PlayerInfo) get(PlayerInfo.class, playerInfoId);
	}
}