package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.Friend;
import com.wyd.empire.world.entity.mysql.Player;

/**
 * The DAO interface for the TabConsortia entity.
 */
public interface IFriendDao extends UniversalDao {

	/**
	 * 获得我的好友玩家列表
	 * 
	 * @param myId
	 *            我的ID
	 * @return 我的好友玩家列表
	 */
	public List<Player> getFriendList(int myId, int sex);

	/**
	 * 查询玩家好友数据
	 * 
	 * @param myId
	 * @return
	 */
	public int getFriendNum(int myId);

	/**
	 * 将好友设为私聊
	 * 
	 * @param myId
	 *            我的角色ID
	 * @param friendId
	 *            朋友的角色ID
	 */
	public void setPrivateChat(int myId, int friendId);

	/**
	 * 将好友设为黑名单
	 * 
	 * @param myId
	 *            我的角色ID
	 * @param friendId
	 *            朋友的角色ID
	 */
	public void setBlackList(int myId, int friendId);

	/**
	 * 删除好友
	 * 
	 * @param myId
	 *            我的角色ID
	 * @param friendId
	 *            朋友的角色ID
	 */
	public void deleteFriend(int myId, int friendId);

	/**
	 * 根据我的角色ID获得黑名单列表
	 * 
	 * @param myId
	 *            我的角色ID
	 * @return 黑名单列表
	 */
	public List<Player> getBlackList(int myId);

	/**
	 * 根据我的角色ID和玩家ID检测该玩家是否是我的好友
	 * 
	 * @param myId
	 *            我的角色ID
	 * @param friendId
	 *            玩家ID
	 * @return 如果返回NULL则此玩家并非我的好友，如果返回好友对象，则说明此玩家是我的好友
	 */
	public Friend checkPlayerIsFriend(int myId, int friendId);

	/**
	 * 把黑名单换成好友
	 * 
	 * @param myId
	 *            我的角色ID
	 * @param friendId
	 *            玩家ID
	 */
	public void updateBlackList(int myId, int friendId);

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
	public boolean checkSomePlayerIsFriend(int myId, List<Integer> playerIdList);

	/**
	 * 判断一批玩家中是否有我的配偶
	 * 
	 * @param myId
	 * @param sex
	 * @param playerIdList
	 * @return
	 */
	public boolean checkSomePlayerIsMarry(int myId, int sex, List<Integer> playerIdList);

	/**
	 * 获取我的粉丝列表（我是他的好友）
	 * 
	 * @param playerId
	 * @return
	 */
	public List<Player> getFansList(int playerId);
}