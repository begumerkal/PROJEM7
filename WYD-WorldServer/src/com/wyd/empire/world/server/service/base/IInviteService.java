package com.wyd.empire.world.server.service.base;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.InviteReward;
import com.wyd.empire.world.bean.InviteServiceInfo;

/**
 * The service interface for the TabGuai entity.
 */
public interface IInviteService extends UniversalManager {
	/**
	 * 获取本
	 * 
	 * @param areaId
	 * @return
	 */
	public InviteServiceInfo getInviteServiceInfo();

	/**
	 * 获取玩家奖励信息
	 * 
	 * @param areaId
	 * @param rewardGrade
	 * @return
	 */
	public InviteReward getInviteReward(int rewardGrade);

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