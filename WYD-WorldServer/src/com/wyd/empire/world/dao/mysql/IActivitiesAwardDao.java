package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.mysql.dao.UniversalDao;
import com.wyd.db.mysql.page.PageList;
import com.wyd.empire.world.entity.mysql.ActivitiesAward;

/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IActivitiesAwardDao extends UniversalDao {
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
	public PageList findAllActivity(String key, int pageIndex, int pageSize);

	/**
	 * 根据多个活动奖励ID值删除记录
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteActivityByIds(String ids);

	/**
	 * 根据区域号查询出活动奖励记录
	 * 
	 * @param areaId
	 *            区域号
	 * @return
	 */
	public List<ActivitiesAward> findAllActivity(String areaId);

	/**
	 * 根据时间和玩家ID查询出是否已经发放奖励
	 * 
	 * @param dateTime
	 *            时间日期
	 * @param playerId
	 *            玩家ID
	 * @return
	 */
	public long isSend(String dateTime, int playerId, String activityName);

	/**
	 * 根据充值记录表判断玩家是否已经发放过奖励
	 * 
	 * @param playerId
	 *            玩家id
	 * @param playerBillId
	 *            充值记录表id
	 * @return
	 */
	public long isGive(int playerId, int playerBillId);

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
	public PageList findLogActivity(String key, int pageIndex, int pageSize);

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
	public int findCountLogActivity(String key);

}