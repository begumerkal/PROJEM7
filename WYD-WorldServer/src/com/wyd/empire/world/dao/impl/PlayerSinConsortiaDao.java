package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.ConsortiaContribute;
import com.wyd.empire.world.bean.PlayerSinConsortia;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.dao.IPlayerSinConsortiaDao;

/**
 * The DAO class for the TabPlayersinconsortia entity.
 */
public class PlayerSinConsortiaDao extends UniversalDaoHibernate implements IPlayerSinConsortiaDao {

	public PlayerSinConsortiaDao() {
		super();
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
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("SELECT COUNT(p) FROM " + PlayerSinConsortia.class.getSimpleName() + " AS p WHERE 1 = 1 ");
		hql.append(" AND p.player.id = ? ");
		values.add(playerId);
		hql.append(" AND p.consortia.id = ? ");
		values.add(consortiaId);
		Long count = this.count(hql.toString(), values.toArray());
		if (count != null && count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据公会ID获取有效会员人数
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @return 公会有效会员人数
	 */
	public Integer getMemberNum(int id) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("SELECT COUNT(p) FROM " + PlayerSinConsortia.class.getSimpleName() + " AS p WHERE 1 = 1 ");
		hql.append(" AND p.consortia.id = ? ");
		values.add(id);
		hql.append(" AND p.identity = ? ");
		values.add(Common.CONSORTIA_IDENTITY_ALREADY_APPROVAL);
		Long count = this.count(hql.toString(), values.toArray());
		count = count == null ? 0 : count;
		return count.intValue();
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
	@SuppressWarnings("unchecked")
	public List<PlayerSinConsortia> getCommunityMemberList(int consortiaId, int status) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + PlayerSinConsortia.class.getSimpleName() + " AS psc WHERE 1 = 1 ");
		hql.append(" AND psc.consortia.id = ? ");
		values.add(consortiaId);
		if (status != -1) {
			hql.append(" AND psc.identity = ? ");
			values.add(status);
		}
		hql.append(" ORDER BY psc.contribute desc ");
		return this.getList(hql.toString(), values.toArray());
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
	public PageList getCommunityMemberList(int consortiaId, int status, int pageNum, int pageSize) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + PlayerSinConsortia.class.getSimpleName() + " AS psc WHERE 1 = 1 ");
		hql.append(" AND psc.consortia.id = ? ");
		values.add(consortiaId);
		if (status != -1) {
			hql.append(" AND psc.identity = ? ");
			values.add(status);
		}
		hql.append(" ORDER BY psc.contribute desc ");
		String countHql = " SELECT COUNT(id) " + hql;
		return this.getPageList(hql.toString(), countHql, values.toArray(), pageNum - 1, pageSize);
	}

	/**
	 * 根据玩家ID，公会ID，查询会员信息
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param consortiaId
	 *            公会ID
	 */
	@SuppressWarnings("unchecked")
	public PlayerSinConsortia getCommunityMember(int consortiaId, int playerId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + PlayerSinConsortia.class.getSimpleName() + " AS p WHERE 1 = 1 ");
		hql.append(" AND p.player.id = ? ");
		values.add(playerId);
		hql.append(" AND p.consortia.id = ? ");
		values.add(consortiaId);
		// return (PlayerSinConsortia)getUniqueResult(hql.toString(),
		// values.toArray());
		// getUniqueResult 转 getList
		List<PlayerSinConsortia> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据公会ID解散工会
	 * 
	 * @param consortiaId
	 *            公会ID
	 */
	public void deleteConsortiaMember(int consortiaId) {
		StringBuffer hql = new StringBuffer();
		hql.append("DELETE FROM " + PlayerSinConsortia.class.getSimpleName() + " WHERE consortia.id = ?");
		execute(hql.toString(), new Object[]{consortiaId});
		hql.setLength(0);
		hql.append("DELETE FROM " + ConsortiaContribute.class.getSimpleName() + " WHERE consortia.id = ?");
		execute(hql.toString(), new Object[]{consortiaId});
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
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("UPDATE " + PlayerSinConsortia.class.getSimpleName() + " SET identity = ? WHERE 1 = 1 ");
		values.add(Common.CONSORTIA_IDENTITY_ALREADY_APPROVAL);
		hql.append(" AND player.id = ? ");
		values.add(playerId);
		hql.append(" AND consortia.id = ? ");
		values.add(consortiaId);
		execute(hql.toString(), values.toArray());
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
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append("DELETE FROM " + PlayerSinConsortia.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND player.id = ? ");
		values.add(playerId);
		hql.append(" AND consortia.id = ? ");
		values.add(consortiaId);
		hql.append(" AND identity = ? ");
		values.add(identity);
		execute(hql.toString(), values.toArray());
	}

	/**
	 * 判断一个玩家与一批玩家是否有在同一公会的
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param playerdList
	 *            另一批玩家ID列表
	 * @return
	 */
	public boolean checkPlayerInSameConsortia(int playerId, List<Integer> playerIdList) {
		// 另一批玩家ID列表这空，直接返回false
		if (playerIdList == null || playerIdList.isEmpty()) {
			return false;
		}
		// 如果玩家没加入任何公会，并返回false
		int consortiaId = findConsortiaIdByPlayerId(playerId);
		if (consortiaId == 0) {
			return false;
		}
		StringBuilder hql = new StringBuilder();
		List<Object> values = new Vector<Object>();
		hql.append("SELECT COUNT(psa) FROM " + PlayerSinConsortia.class.getSimpleName() + " AS psa WHERE 1 =  1 ");
		hql.append(" AND psa.consortia.id = ? ");
		values.add(consortiaId);
		hql.append(" AND psa.identity = ? ");
		values.add(Common.CONSORTIA_IDENTITY_ALREADY_APPROVAL);
		hql.append(" AND psa.player.id IN " + playerIdList.toString().replace("[", "(").replace("]", "") + "," + playerId + ")");
		Long count = this.count(hql.toString(), values.toArray());
		if (count != null && count > 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据用户ID，获取所属于公会ID(条件必须是这个玩家已经是公会的正式会员)
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 所属于公会ID
	 */
	@SuppressWarnings("unchecked")
	public int findConsortiaIdByPlayerId(int playerId) {
		int consortiaId = 0;
		StringBuilder hql = new StringBuilder();
		List<Object> values = new Vector<Object>();
		hql.append("SELECT psa.consortia.id FROM " + PlayerSinConsortia.class.getSimpleName() + " AS psa WHERE 1 =  1 ");
		hql.append(" AND psa.player.id = ? ");
		values.add(playerId);
		hql.append(" AND psa.identity = ? ");
		values.add(Common.CONSORTIA_IDENTITY_ALREADY_APPROVAL);
		List<Object> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			consortiaId = Integer.parseInt(list.get(0).toString());
		}
		return consortiaId;
	}

	/**
	 * 根据用户ID，获取所属公会(条件必须是这个玩家已经是公会的正式会员)
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 所属于公会ID
	 */
	@SuppressWarnings("unchecked")
	public PlayerSinConsortia findPlayerSinConsortiaByPlayerId(int playerId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new Vector<Object>();
		hql.append("FROM " + PlayerSinConsortia.class.getSimpleName() + " AS psa WHERE 1 =  1 ");
		hql.append(" AND psa.player.id = ? ");
		values.add(playerId);
		hql.append(" AND psa.identity = ? ");
		values.add(Common.CONSORTIA_IDENTITY_ALREADY_APPROVAL);
		List<PlayerSinConsortia> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据用户ID，判断玩家是否是工会成员
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean checkPlayerIsMember(int playerId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new Vector<Object>();
		hql.append("select count(id) FROM " + PlayerSinConsortia.class.getSimpleName() + " AS psa WHERE 1 =  1 ");
		hql.append(" AND psa.player.id = ? ");
		values.add(playerId);
		hql.append(" AND psa.identity = ? ");
		values.add(Common.CONSORTIA_IDENTITY_ALREADY_APPROVAL);
		// Object o = this.getUniqueResult(hql.toString(), values.toArray());
		// getUniqueResult 转 getList
		List<Object> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			if (null == list.get(0) || Integer.parseInt(list.get(0).toString()) == 0) {
				return false;
			}
		}
		return true;
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
		StringBuffer hql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hql.append("DELETE FROM " + PlayerSinConsortia.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND player.id = ? ");
		values.add(playerId);
		hql.append(" AND identity = ? ");
		values.add(identity);
		execute(hql.toString(), values.toArray());
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
		StringBuffer hql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hql.append("UPDATE " + ConsortiaContribute.class.getSimpleName()
				+ " SET contribute = contribute+?, discontribute = discontribute+?,everydayAdd = everydayAdd +?  WHERE 1 = 1");
		values.add(num);
		values.add(num);
		values.add(num);
		hql.append(" AND player.id = ? ");
		values.add(playerId);
		hql.append(" AND consortia.id = ? ");
		values.add(consortiaId);
		execute(hql.toString(), values.toArray());

		hql.setLength(0);
		hql.append("UPDATE " + PlayerSinConsortia.class.getSimpleName()
				+ " SET contribute = contribute+?, discontribute = discontribute+?,everydayAdd = everydayAdd +?  WHERE 1 = 1");
		hql.append(" AND player.id = ? ");
		hql.append(" AND consortia.id = ? ");
		execute(hql.toString(), new Object[]{num, num, num, playerId, consortiaId});
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
	public int checkPositionNum(int consortiaId, int position) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hql.append("SELECT COUNT(psa) FROM " + PlayerSinConsortia.class.getSimpleName() + " AS psa WHERE 1 = 1 ");
		hql.append(" AND psa.consortia.id = ? ");
		values.add(consortiaId);
		hql.append(" AND psa.position = ? ");
		values.add(position);
		Long count = this.count(hql.toString(), values.toArray());
		if (count != null) {
			return count.intValue();
		} else {
			return 0;
		}
	}

	/**
	 * 根据公会ID，获取此公会玩家数量
	 * 
	 * @param consortiaId
	 *            公会ID
	 * @return 此公会玩家数量
	 */
	public int checkCommunityNum(int consortiaId) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hql.append("SELECT COUNT(psa) FROM " + PlayerSinConsortia.class.getSimpleName() + " AS psa WHERE 1 = 1 ");
		hql.append(" AND psa.consortia.id = ? ");
		values.add(consortiaId);
		hql.append(" AND psa.identity = ? ");
		values.add(Common.CONSORTIA_IDENTITY_ALREADY_APPROVAL);
		Long count = this.count(hql.toString(), values.toArray());
		if (count != null) {
			return count.intValue();
		} else {
			return 0;
		}
	}

	/**
	 * 根据玩家Id和公会Id获得贡献度
	 * 
	 * @param playerId
	 * @param consortiaId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ConsortiaContribute getConsortiaContributeByPlayerId(int playerId, int consortiaId) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new Vector<Object>();
		hql.append("FROM " + ConsortiaContribute.class.getSimpleName() + " AS psa WHERE 1 =  1 ");
		hql.append(" AND psa.player.id = ? ");
		values.add(playerId);
		hql.append(" AND psa.consortia.id = ? ");
		values.add(consortiaId);
		// return (ConsortiaContribute)this.getUniqueResult(hql.toString(),
		// values.toArray());
		// getUniqueResult 转 getList
		List<ConsortiaContribute> list = this.getList(hql.toString(), values.toArray());
		if (null != list && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据玩家其他公会记录
	 * 
	 * @param consortiaId
	 *            公会ID
	 */
	public void deleteConsortiaContribute(int consortiaId, int playerId) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hql.append("DELETE FROM " + ConsortiaContribute.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND consortia.id != ? and player.id = ?");
		values.add(consortiaId);
		values.add(playerId);
		execute(hql.toString(), values.toArray());
	}

	/**
	 * 随机获得公会玩家列表
	 * 
	 * @param consortiaId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getRondomPlayerSinConsortia(int consortiaId) {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" SELECT t1.playerId FROM tab_playersinconsortia AS t1 ,"
				+ "(SELECT ROUND(RAND() * ((SELECT MAX(c.id) FROM tab_consortia c,tab_player tp where tp.id = c.presidentId and c.id != ? AND tp.areaId = ?)"
				+ "-(SELECT MIN(c.id) FROM tab_consortia c,tab_player tp where tp.id = c.presidentId and c.id != ? AND tp.areaId = ? ))"
				+ "+(SELECT MIN(c.id) FROM tab_consortia c,tab_player tp where tp.id = c.presidentId and c.id != ? AND tp.areaId = ? )) AS id) AS t2 "
				+ "WHERE t1.consortiaId = t2.id ");
		values.add(consortiaId);
		values.add(Server.config.getMachineCode());
		values.add(consortiaId);
		values.add(Server.config.getMachineCode());
		values.add(consortiaId);
		values.add(Server.config.getMachineCode());
		try {
			List<Integer> list = getListBySql(hql.toString(), values.toArray());
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}