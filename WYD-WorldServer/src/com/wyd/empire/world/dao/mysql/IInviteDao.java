package com.wyd.empire.world.dao.mysql;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.entity.mysql.InviteReward;
import com.wyd.empire.world.entity.mysql.InviteServiceInfo;

/**
 * The DAO interface for the TabTool entity.
 */
public interface IInviteDao extends UniversalDao {
	/**
	 * 获取本
	 * 
	 * @param areaId
	 * @return
	 */
	public InviteServiceInfo getInviteServiceInfo(String areaId);

	/**
	 * 获取玩家奖励信息
	 * 
	 * @param areaId
	 * @param rewardGrade
	 * @return
	 */
	public InviteReward getInviteReward(String areaId, int rewardGrade);

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
	public PageList findAllServerInfo(String key, int pageIndex, int pageSize);

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
	public PageList findAllInviteReward(String key, int pageIndex, int pageSize);

	/**
	 * 根据多个ID删除服务器成功邀请
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByServerInfoIds(String ids);

	/**
	 * 根据多个ID删除玩家成功邀请物品奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByInviteRewardIds(String ids);
}