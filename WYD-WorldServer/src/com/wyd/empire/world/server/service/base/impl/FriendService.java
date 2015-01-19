package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.Friend;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.dao.IFriendDao;
import com.wyd.empire.world.server.service.base.IFriendService;

/**
 * The service class for the TabConsortia entity.
 */
public class FriendService extends UniversalManagerImpl implements IFriendService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IFriendDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "FriendService";

	public FriendService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiaService</code> instance.
	 */
	public static IFriendService getInstance(ApplicationContext context) {
		return (IFriendService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IFriendDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IFriendDao getDao() {
		return this.dao;
	}

	/**
	 * 获得我的好友玩家列表
	 * 
	 * @param myId
	 *            我的ID
	 * @return 我的好友玩家列表
	 */
	public List<Player> getFriendList(int myId, int sex) {
		return dao.getFriendList(myId, sex);
	}

	/**
	 * 查询玩家好友数据
	 * 
	 * @param myId
	 * @return
	 */
	public int getFriendNum(int myId) {
		return dao.getFriendNum(myId);
	}

	/**
	 * 添加好友
	 * 
	 * @param myId
	 * @param friendId
	 */
	public void addFriend(int myId, int friendId) {
		Friend friend = new Friend();
		friend.setMinePlayer(new Player());
		friend.setOtherPlayer(new Player());
		friend.getMinePlayer().setId(myId);
		friend.getOtherPlayer().setId(friendId);
		friend.setPrivateChat(false);
		friend.setBlackList(false);

		dao.save(friend);
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
		dao.setPrivateChat(myId, friendId);
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
		dao.setBlackList(myId, friendId);
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
		dao.deleteFriend(myId, friendId);
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
	public Friend checkPlayerIsFriend(int myId, int friendId) {
		return dao.checkPlayerIsFriend(myId, friendId);
	}

	/**
	 * 根据我的角色ID获得黑名单列表
	 * 
	 * @param myId
	 *            我的角色ID
	 * @return 黑名单列表
	 */
	public List<Player> getBlackList(int myId) {
		return dao.getBlackList(myId);
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
		dao.updateBlackList(myId, friendId);
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
		return dao.checkSomePlayerIsFriend(myId, playerIdList);
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
		return dao.checkSomePlayerIsMarry(myId, sex, playerIdList);
	}

	@Override
	public List<Player> getFansList(int playerId) {
		return dao.getFansList(playerId);
	}
}