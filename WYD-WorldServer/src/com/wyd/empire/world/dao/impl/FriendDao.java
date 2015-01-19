package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.bean.Friend;
import com.wyd.empire.world.bean.MarryRecord;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.dao.IFriendDao;

/**
 * The DAO class for the TabConsortia entity.
 */
public class FriendDao extends UniversalDaoHibernate implements IFriendDao {
	public FriendDao() {
		super();
	}

	/**
	 * 获得我的好友玩家列表
	 * 
	 * @param myId
	 *            我的ID
	 * @return 我的好友玩家列表
	 */
	@SuppressWarnings("unchecked")
	public List<Player> getFriendList(int myId, int sex) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT f.otherPlayer FROM " + Friend.class.getSimpleName() + " AS f WHERE 1 = 1 ");
		hql.append(" AND f.blackList = ? ");
		values.add(false);
		hql.append(" AND f.minePlayer.id = ? ");
		values.add(myId);
		if (sex != -1) {
			hql.append(" AND f.otherPlayer.sex = ? ");
			values.add(Byte.parseByte(sex + ""));
		}
		hql.append(" ORDER BY f.otherPlayer.name ASC ");
		return getList(hql.toString(), values.toArray());
	}

	public int getFriendNum(int myId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT count(*) FROM " + Friend.class.getSimpleName() + " AS f WHERE 1 = 1 ");
		hql.append(" AND f.blackList = ? ");
		values.add(false);
		hql.append(" AND f.minePlayer.id = ? ");
		values.add(myId);
		return Integer.parseInt(getClassObj(hql.toString(), values.toArray()).toString());
	}

	/**
	 * 将好友设为私聊
	 * 
	 * @param myId
	 *            我的角色ID
	 * @param friendId
	 *            朋友的角色ID
	 */
	public void setPrivateChat(int myId, int friendId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" UPDATE " + Friend.class.getSimpleName() + " SET privateChat = 1 WHERE 1 = 1 ");
		hql.append(" AND minePlayer.id = ? ");
		values.add(myId);
		hql.append(" AND otherPlayer.id = ? ");
		values.add(friendId);
		execute(hql.toString(), values.toArray());
	}

	/**
	 * 将好友设为黑名单
	 * 
	 * @param myId
	 *            我的角色ID
	 * @param friendId
	 *            朋友的角色ID
	 */
	public void setBlackList(int myId, int friendId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("UPDATE " + Friend.class.getSimpleName() + " SET blackList = ? WHERE 1 = 1 ");
		values.add(Common.FRIEND_BLACK_LIST_YES);
		hql.append(" AND minePlayer.id = ? ");
		values.add(myId);
		hql.append(" AND otherPlayer.id = ? ");
		values.add(friendId);
		execute(hql.toString(), values.toArray());
	}

	/**
	 * 删除好友
	 * 
	 * @param myId
	 *            我的角色ID
	 * @param friendId
	 *            朋友的角色ID
	 */
	public void deleteFriend(int myId, int friendId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("DELETE FROM " + Friend.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND minePlayer.id = ? ");
		values.add(myId);
		hql.append(" AND otherPlayer.id = ? ");
		values.add(friendId);
		execute(hql.toString(), values.toArray());
	}

	/**
	 * 根据我的角色ID获得黑名单列表
	 * 
	 * @param myId
	 *            我的角色ID
	 * @return 黑名单列表
	 */
	@SuppressWarnings("unchecked")
	public List<Player> getBlackList(int myId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT f.otherPlayer FROM " + Friend.class.getSimpleName() + " AS f WHERE 1 = 1 ");
		hql.append(" AND f.blackList = ? ");
		values.add(true);
		hql.append(" AND f.minePlayer.id = ? ");
		values.add(myId);
		hql.append(" ORDER BY f.otherPlayer.name ASC ");
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 根据我的角色ID和玩家ID检测该玩家是否是我的好友
	 * 
	 * @param myId
	 *            我的角色ID
	 * @param friendId
	 *            玩家ID
	 * @return 如果返回NULL则此玩家并非我的好友，如果返回好友对象，则说明此玩家是我的好友
	 */
	@SuppressWarnings("unchecked")
	public Friend checkPlayerIsFriend(int myId, int friendId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Friend.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND minePlayer.id = ? ");
		values.add(myId);
		hql.append(" AND otherPlayer.id = ? ");
		values.add(friendId);
		List<Friend> list = getList(hql.toString(), values.toArray());
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 把黑名单换成好友
	 * 
	 * @param myId
	 *            我的角色ID
	 * @param friendId
	 *            玩家ID
	 */
	public void updateBlackList(int myId, int friendId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" UPDATE " + Friend.class.getSimpleName() + " SET blackList = 0 WHERE 1 = 1 ");
		hql.append(" AND minePlayer.id = ? ");
		values.add(myId);
		hql.append(" AND otherPlayer.id = ? ");
		values.add(friendId);
		execute(hql.toString(), values.toArray());
	}

	/**
	 * 检测一批玩家中是否有我的好友
	 * 
	 * @param myId
	 *            我的角色ID
	 * @param friendId
	 *            玩家ID
	 * @return <tt>true:  </tt>有我的好友<br/>
	 *         <tt>false: </tt>没有我的好友
	 */
	public boolean checkSomePlayerIsFriend(int myId, List<Integer> playerIdList) {
		boolean result = false;
		// 判断玩家List是否是空，如果为空，直接返回false
		if (playerIdList == null || playerIdList.isEmpty()) {
			return result;
		}
		StringBuilder hql = new StringBuilder();
		List<Object> values = new Vector<Object>();
		hql.append("SELECT COUNT(f) FROM " + Friend.class.getSimpleName() + " AS f WHERE 1 = 1 ");
		hql.append(" AND f.minePlayer.id = ? ");
		values.add(myId);
		hql.append(" AND f.otherPlayer.id IN " + playerIdList.toString().replace("[", "(").replace("]", ")"));
		Long count = this.count(hql.toString(), values.toArray());
		if (count != null && count > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * 判断一批玩家中是否有我的配偶
	 * 
	 * @param myId
	 * @param sex
	 * @param playerIdList
	 * @return
	 */
	public boolean checkSomePlayerIsMarry(int myId, int sex, List<Integer> playerIdList) {
		boolean result = false;
		// 判断玩家List是否是空，如果为空，直接返回false
		if (playerIdList == null || playerIdList.isEmpty()) {
			return result;
		}
		StringBuilder hql = new StringBuilder();
		List<Object> values = new Vector<Object>();
		hql.append("SELECT COUNT(*) FROM ");
		hql.append(MarryRecord.class.getSimpleName());
		hql.append(" WHERE statusMode=2 ");
		if (0 == sex) {
			hql.append(" AND manId = ? ");
			values.add(myId);
			hql.append(" AND womanId IN ");
			hql.append(playerIdList.toString().replace("[", "(").replace("]", ")"));
		} else {
			hql.append(" AND womanId = ? ");
			values.add(myId);
			hql.append(" AND manId IN ");
			hql.append(playerIdList.toString().replace("[", "(").replace("]", ")"));
		}
		Long count = this.count(hql.toString(), values.toArray());
		if (count != null && count > 0) {
			result = true;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Player> getFansList(int playerId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT f.minePlayer FROM " + Friend.class.getSimpleName() + " AS f WHERE 1 = 1 ");
		hql.append(" AND f.blackList = ? ");
		values.add(false);
		hql.append(" AND f.otherPlayer.id = ? ");
		values.add(playerId);
		hql.append(" ORDER BY f.minePlayer.name ASC ");
		return getList(hql.toString(), values.toArray());
	}
}