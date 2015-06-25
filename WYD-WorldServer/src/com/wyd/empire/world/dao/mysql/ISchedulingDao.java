package com.wyd.empire.world.dao.mysql;

import java.util.Date;
import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.LogActivitiesAward;

/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface ISchedulingDao extends UniversalDao {

	/**
	 * 根据区域和是否已发放查询出活动发放日志记录
	 * 
	 * @param areaId
	 *            区域ID
	 * @param isSend
	 *            是否已发放
	 * @return
	 */
	public List<LogActivitiesAward> findAllLogAward(String areaId, String isSend);

	/**
	 * 获取今天已发送的登陆奖励
	 */
	public List<String> getLoginAward(int playerId, Date date);
}