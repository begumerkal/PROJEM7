package com.wyd.empire.world.server.service.base.impl;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.ActivitiesAward;
import com.wyd.empire.world.dao.IActivitiesAwardDao;
import com.wyd.empire.world.server.service.base.IActivitiesAwardService;

/**
 * The service class for the TabConsortiaright entity.
 */
public class ActivitiesAwardService extends UniversalManagerImpl implements IActivitiesAwardService {
	Logger log = Logger.getLogger(ActivitiesAwardService.class);
	/**
	 * The dao instance injected by Spring.
	 */
	private IActivitiesAwardDao dao;
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "ActivitiesAwardService";

	public ActivitiesAwardService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiarightService</code> instance.
	 */
	public static IActivitiesAwardService getInstance(ApplicationContext context) {
		return (IActivitiesAwardService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IActivitiesAwardDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IActivitiesAwardDao getDao() {
		return this.dao;
	}

	/**
	 * 获取活动奖励列表
	 * 
	 * @param key
	 *            关键字
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public PageList findAllActivity(String key, int pageIndex, int pageSize) {
		return dao.findAllActivity(key, pageIndex, pageSize);
	}

	/**
	 * 根据多个活动奖励ID值删除记录
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteActivityByIds(String ids) {
		dao.deleteActivityByIds(ids);
	}

	/**
	 * 根据区域号查询出活动奖励记录
	 * 
	 * @param areaId
	 *            区域号
	 * @return
	 */
	public List<ActivitiesAward> findAllActivity(String areaId) {
		return dao.findAllActivity(areaId);
	}

	/**
	 * 根据时间和玩家ID查询出是否已经发放奖励
	 * 
	 * @param dateTime
	 *            时间日期
	 * @param playerId
	 *            玩家ID
	 * @param activityName
	 *            为空则不发奖励
	 * @return
	 */
	public boolean isSend(String dateTime, int playerId, String activityName) {
		if (!StringUtils.hasText(activityName))
			return true;
		long count = dao.isSend(dateTime, playerId, activityName);
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据充值记录表判断玩家是否已经发放过奖励
	 * 
	 * @param playerId
	 *            玩家id
	 * @param playerBillId
	 *            充值记录表id
	 * @return
	 */
	public boolean isGive(int playerId, int playerBillId) {
		long count = dao.isGive(playerId, playerBillId);
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Gm工具 根据条件查询出所有活动奖励日志（提供分页） 没有参数用null替代，此key中必须有5个参数
	 * 的长度，否则会报错例如“你好|null|null|null|null”
	 * 
	 * @param activityName
	 * @param playerId
	 *            玩家ID
	 * @param stime
	 *            时间日期
	 * @param etime
	 *            时间日期
	 * @param isSend
	 *            是否发送（默认为null）
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public PageList findLogActivity(String key, int pageIndex, int pageSize) {
		return dao.findLogActivity(key, pageIndex, pageSize);
	}

	/**
	 * Gm工具 根据条件查询出所有活动奖励日志总数 没有参数用null替代，此key中必须有5个参数
	 * 的长度，否则会报错例如“你好|null|null|null|null”
	 * 
	 * @param activityName
	 *            活动名称
	 * @param stime
	 *            时间日期
	 * @param etime
	 *            时间日期
	 * @return
	 */
	public int findCountLogActivity(String key) {
		return dao.findCountLogActivity(key);
	}

	/**
	 * 批量保存
	 */
	@Override
	public void saveOrUpdateAll(Collection<Object> coll) {
		dao.saveOrUpdateAll(coll);
	}
}