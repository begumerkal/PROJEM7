package com.wyd.empire.world.dao.mysql.impl;

import java.util.List;
import java.util.Vector;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.dao.mysql.IInviteDao;
import com.wyd.empire.world.entity.mysql.InviteReward;
import com.wyd.empire.world.entity.mysql.InviteServiceInfo;

/**
 * The DAO class for the TabConsortia entity.
 */
public class InviteDao extends UniversalDaoHibernate implements IInviteDao {
	public InviteDao() {
		super();
	}

	public InviteServiceInfo getInviteServiceInfo(String areaId) {
		return (InviteServiceInfo) getClassObj("from InviteServiceInfo where areaId=?", new Object[]{areaId});
	}

	public InviteReward getInviteReward(String areaId, int rewardGrade) {
		return (InviteReward) getClassObj("from InviteReward where areaId=? and rewardGrade=?", new Object[]{areaId, rewardGrade});
	}

	/**
	 * 查询出所有的服务器成功邀请
	 * 
	 * @param key
	 *            查询参数
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少
	 * @return
	 */
	public PageList findAllServerInfo(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + InviteServiceInfo.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND areaId = ? ");
		values.add(WorldServer.config.getAreaId());
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 查询出所有的玩家成功邀请物品奖励
	 * 
	 * @param key
	 *            查询参数
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少
	 * @return
	 */
	public PageList findAllInviteReward(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + InviteReward.class.getSimpleName() + " WHERE 1 = 1 ");
		hsql.append(" AND areaId = ? ");
		values.add(WorldServer.config.getAreaId());
		hsql.append(" ORDER BY rewardGrade desc  ");
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 根据多个ID删除服务器成功邀请
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByServerInfoIds(String ids) {
		this.execute("DELETE " + InviteServiceInfo.class.getSimpleName() + " WHERE id in (" + ids + ")", new Object[]{});
	}

	/**
	 * 根据多个ID删除玩家成功邀请物品奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByInviteRewardIds(String ids) {
		this.execute("DELETE " + InviteReward.class.getSimpleName() + " WHERE id in (" + ids + ")", new Object[]{});
	}
}