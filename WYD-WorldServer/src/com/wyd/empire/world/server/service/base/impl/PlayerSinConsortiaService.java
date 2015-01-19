package com.wyd.empire.world.server.service.base.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.ConsortiaContribute;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.dao.IPlayerSinConsortiaDao;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerSinConsortiaService;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * The service class for the TabPlayersinconsortia entity.
 */
public class PlayerSinConsortiaService extends UniversalManagerImpl implements IPlayerSinConsortiaService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IPlayerSinConsortiaDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "PlayersinconsortiaService";

	public PlayerSinConsortiaService() {
		super();
	}

	/**
	 * Returns the singleton <code>IPlayersinconsortiaService</code> instance.
	 */
	public static IPlayerSinConsortiaService getInstance(ApplicationContext context) {
		return (IPlayerSinConsortiaService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IPlayerSinConsortiaDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IPlayerSinConsortiaDao getDao() {
		return this.dao;
	}

	/**
	 * 根据公会ID获取有效会员人数
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @return 公会有效会员人数
	 */
	public Integer getMemberNum(int id) {
		return dao.getMemberNum(id);
	}

	/**
	 * 根据公会ID，会员状态获得公会成员列表
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @param status
	 *            会员状态
	 * @return 传值成员列表
	 */
	public List<PlayerSinConsortia> getCommunityMemberList(int consortiaId, int status) {
		return dao.getCommunityMemberList(consortiaId, status);
	}

	/**
	 * 根据公会ID，会员状态获得公会成员列表
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @param status
	 *            会员状态
	 * @return 传值成员列表
	 */
	public List<PlayerSinConsortia> getCommunityMembers(int consortiaId, int status) {
		List<PlayerSinConsortia> pscList = dao.getCommunityMemberList(consortiaId, status);
		for (PlayerSinConsortia psc : pscList) {
			Hibernate.initialize(psc.getPlayer());
			Hibernate.initialize(psc.getConsortia());
			// Hibernate.initialize(psc.getConsortiaContribute());
		}
		return pscList;
	}

	/**
	 * 根据公会ID，会员状态获得公会成员列表
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @param status
	 *            会员状态
	 * @return 传值成员列表
	 */
	@Override
	public PageList getCommunityMembers(int consortiaId, int status, int pageNum, int pageSize) {
		PageList pageList = dao.getCommunityMemberList(consortiaId, status, pageNum, pageSize);
		List<Object> pscList = pageList.getList();
		for (Object obj : pscList) {
			PlayerSinConsortia psc = (PlayerSinConsortia) obj;
			Hibernate.initialize(psc.getPlayer());
			Hibernate.initialize(psc.getConsortia());
			// Hibernate.initialize(psc.getConsortiaContribute());
		}
		return pageList;
	}

	/**
	 * 根据玩家ID，公会ID，查询会员信息
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param consortiaId
	 *            公会ID
	 */
	@Override
	public PlayerSinConsortia getCommunityMember(int consortiaId, int playerId) {
		PlayerSinConsortia psc = dao.getCommunityMember(consortiaId, playerId);
		Hibernate.initialize(psc.getPlayer());
		Hibernate.initialize(psc.getConsortia());
		return psc;
	}

	/**
	 * 根据公会ID解散工会
	 * 
	 * @param consortiaId
	 *            公会ID
	 */
	public void deleteConsortiaMember(int consortiaId) {
		dao.deleteConsortiaMember(consortiaId);
	}

	/**
	 * 更改会员的状态（审批申请加入的会员）
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param consortiaId
	 *            公会ID
	 */
	public void approveMember(int playerId, int consortiaId) {
		dao.approveMember(playerId, consortiaId);
	}

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
	public boolean checkPlayerCanApply(int playerId, int consortiaId) {
		return dao.checkPlayerCanApply(playerId, consortiaId);
	}

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
	public void deleteConsortiaMemberByPlayerId(int consortiaId, int playerId, int identity) {
		dao.deleteConsortiaMemberByPlayerId(consortiaId, playerId, identity);
	}

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
	public boolean checkPlayerInSameConsortia(int playerId, List<Integer> playerIdList) {
		return dao.checkPlayerInSameConsortia(playerId, playerIdList);
	}

	/**
	 * 根据用户ID，获取所属公会(条件必须是这个玩家已经是公会的正式会员)
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 所属于公会ID
	 */
	public PlayerSinConsortia findPlayerSinConsortia(int playerId) {
		PlayerSinConsortia psc = dao.findPlayerSinConsortiaByPlayerId(playerId);
		if (null != psc) {
			Hibernate.initialize(psc.getPlayer());
			Hibernate.initialize(psc.getConsortia());
		}
		return psc;
	}

	/**
	 * 根据玩家id删除公会玩家记录
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param identity
	 *            玩家在公会中状态
	 */
	public void deletePlayerSinConsortiaByPlayerId(int playerId, int identity) {
		dao.deletePlayerSinConsortiaByPlayerId(playerId, identity);
	}

	/**
	 * 更新玩家公会贡献度
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param num
	 *            贡献值
	 */
	public void updatePlayerContribute(WorldPlayer player, int num) {
		if (player.getGuildId() != 0) {
			dao.updatePlayerContribute(player.getId(), num, player.getGuildId());
			ServiceManager.getManager().getChatService().sendSystemMessage(player, TipMessages.COMTRIBUTE_Add + num + TipMessages.DIAN);
		}
	}

	/**
	 * 更新玩家公会贡献度
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param num
	 *            贡献值
	 */
	public void updatePlayerContribute(int playerId, int num, int consortiaId) {
		dao.updatePlayerContribute(playerId, num, consortiaId);
	}

	/**
	 * 获得升职时该职位的玩家数量
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @param position
	 *            会员职务
	 * @return 该职位的玩家数量
	 */
	public int checkPositionNum(int communityId, int position) {
		return dao.checkPositionNum(communityId, position);
	}

	/**
	 * 根据公会ID，获取此公会玩家数量
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @return 此公会玩家数量
	 */
	public int checkCommunityNum(int communityId) {
		return dao.checkCommunityNum(communityId);
	}

	/**
	 * 根据玩家Id和公会Id获得贡献度对象
	 * 
	 * @param playerId
	 * @param consortiaId
	 * @return
	 */
	public ConsortiaContribute getConsortiaContributeByPlayerId(int playerId, int consortiaId) {
		return dao.getConsortiaContributeByPlayerId(playerId, consortiaId);
	}

	/**
	 * 根据玩家其他公会记录
	 * 
	 * @param consortiaId
	 *            公会ID
	 */
	public void deleteConsortiaContribute(int consortiaId, int playerId) {
		dao.deleteConsortiaContribute(consortiaId, playerId);
	}

	/**
	 * 根据用户ID，判断玩家是否是工会成员
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public boolean checkPlayerIsMember(int playerId) {
		return dao.checkPlayerIsMember(playerId);
	}

	/**
	 * 随机获得公会玩家列表
	 * 
	 * @param consortiaId
	 * @return
	 */
	public List<Integer> getRondomPlayerSinConsortia(int consortiaId) {
		List<Integer> pscList = dao.getRondomPlayerSinConsortia(consortiaId);
		return pscList;
	}
}