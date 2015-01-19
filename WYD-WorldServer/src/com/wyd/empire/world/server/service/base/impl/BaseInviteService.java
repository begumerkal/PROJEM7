package com.wyd.empire.world.server.service.base.impl;

import org.springframework.context.ApplicationContext;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.bean.InviteReward;
import com.wyd.empire.world.bean.InviteServiceInfo;
import com.wyd.empire.world.dao.IInviteDao;
import com.wyd.empire.world.server.service.base.IGuaiService;
import com.wyd.empire.world.server.service.base.IInviteService;

/**
 * The service class for the TabGuai entity.
 */
public class BaseInviteService extends UniversalManagerImpl implements IInviteService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IInviteDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "InviteService";

	public BaseInviteService() {
		super();
	}

	/**
	 * Returns the singleton <code>IGuaiService</code> instance.
	 */
	public static IGuaiService getInstance(ApplicationContext context) {
		return (IGuaiService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IInviteDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IInviteDao getDao() {
		return this.dao;
	}

	public InviteServiceInfo getInviteServiceInfo() {
		return this.dao.getInviteServiceInfo(Server.config.getAreaId());
	}

	/**
	 * 获取玩家奖励信息
	 * 
	 * @param areaId
	 * @param rewardGrade
	 * @return
	 */
	public InviteReward getInviteReward(int rewardGrade) {
		return this.dao.getInviteReward(Server.config.getAreaId(), rewardGrade);
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
		return dao.findAllServerInfo(key, pageIndex, pageSize);
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
		return dao.findAllInviteReward(key, pageIndex, pageSize);
	}

	/**
	 * 根据多个ID删除服务器成功邀请
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByServerInfoIds(String ids) {
		dao.deleteByServerInfoIds(ids);
	}

	/**
	 * 根据多个ID删除玩家成功邀请物品奖励
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteByInviteRewardIds(String ids) {
		dao.deleteByInviteRewardIds(ids);
	}
}