package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.entity.mysql.ConsortiaContribute;
import com.wyd.empire.world.entity.mysql.PlayerSinConsortia;

/**
 * The DAO interface for the TabPlayersinconsortia entity.
 */
public interface IPlayerSinConsortiaDao extends UniversalDao {

	/**
	 * 根据玩家ID，公会ID，检测该玩家是否已申请过该公会
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param consortiaId
	 *            公会ID
	 * @return <tt>true :</tt>已经申请过该公会<br/>
	 *         <tt>false:</tt>未申请过该公会
	 */
	public boolean checkPlayerCanApply(int playerId, int consortiaId);

	/**
	 * 根据公会ID获取有效会员人数
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @return 公会有效会员人数
	 */
	public Integer getMemberNum(int id);

	/**
	 * 根据公会ID，会员状态获得公会成员列表
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @param status
	 *            会员状态
	 * @return 传值成员列表
	 */
	public List<PlayerSinConsortia> getCommunityMemberList(int consortiaId, int status);

	/**
	 * 根据公会ID，会员状态获得公会成员列表
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @param status
	 *            会员状态
	 * @return 传值成员列表
	 */
	public PageList getCommunityMemberList(int consortiaId, int status, int pageNum, int pageSize);

	/**
	 * 根据玩家ID，公会ID，查询会员信息
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param consortiaId
	 *            公会ID
	 */
	public PlayerSinConsortia getCommunityMember(int consortiaId, int playerId);

	// /**
	// * 更改旧的会长的职位（会长让位）
	// * @param oldPId
	// */
	// public void updateOldPresident(int oldPId);

	/**
	 * 根据公会ID解散工会
	 * 
	 * @param consortiaId
	 *            公会ID
	 */
	public void deleteConsortiaMember(int consortiaId);

	/**
	 * 更改会员的状态（审批申请加入的会员）
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param consortiaId
	 *            公会ID
	 */
	public void approveMember(int playerId, int consortiaId);

	/**
	 * 审核不通过，删除对象
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @param playerId
	 *            玩家ID
	 * @param identity
	 *            审核状态
	 */
	public void deleteConsortiaMemberByPlayerId(int consortiaId, int playerId, int identity);

	/**
	 * 判断一个玩家与一批玩家是否有在同一公会的
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param playerdList
	 *            另一批玩家列表ID
	 * @return <tt>true : </tt>是存在同一公会玩家<br/>
	 *         <tt>false: </tt>不存在同一公会玩家
	 */
	public boolean checkPlayerInSameConsortia(int playerId, List<Integer> playerIdList);

	/**
	 * 根据用户ID，获取所属于公会ID(条件必须是这个玩家已经是公会的正式会员)
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 所属于公会ID
	 */
	public int findConsortiaIdByPlayerId(int playerId);

	/**
	 * 根据用户ID，获取所属公会(条件必须是这个玩家已经是公会的正式会员)
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 所属于公会ID
	 */
	public PlayerSinConsortia findPlayerSinConsortiaByPlayerId(int playerId);

	/**
	 * 根据玩家id删除公会玩家记录
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param identity
	 *            玩家在公会中状态
	 */
	public void deletePlayerSinConsortiaByPlayerId(int playerId, int identity);

	/**
	 * 更新玩家公会贡献度
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param num
	 *            贡献值
	 */
	public void updatePlayerContribute(int playerId, int num, int consortiaId);

	/**
	 * 获得升职时该职位的玩家数量
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @param position
	 *            会员职务
	 * @return 该职位的玩家数量
	 */
	public int checkPositionNum(int communityId, int position);

	/**
	 * 根据公会ID，获取此公会玩家数量
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @return 此公会玩家数量
	 */
	public int checkCommunityNum(int communityId);

	/**
	 * 根据玩家Id和公会Id获得贡献度对象
	 * 
	 * @param playerId
	 * @param consortiaId
	 * @return
	 */
	public ConsortiaContribute getConsortiaContributeByPlayerId(int playerId, int consortiaId);

	/**
	 * 根据玩家其他公会记录
	 * 
	 * @param consortiaId
	 *            公会ID
	 */
	public void deleteConsortiaContribute(int consortiaId, int playerId);

	/**
	 * 根据用户ID，判断玩家是否是工会成员
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public boolean checkPlayerIsMember(int playerId);

	/**
	 * 随机获得公会玩家列表
	 * 
	 * @param consortiaId
	 * @return
	 */
	public List<Integer> getRondomPlayerSinConsortia(int consortiaId);
}