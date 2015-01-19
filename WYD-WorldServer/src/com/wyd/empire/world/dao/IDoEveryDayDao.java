package com.wyd.empire.world.dao;

import java.util.List;
import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.bean.DoEveryDay;
import com.wyd.empire.world.dao.impl.DoEveryDayDao.DoEveryDayVo;

/**
 * The DAO interface for the TabChatRecord entity.
 */
public interface IDoEveryDayDao extends UniversalDao {
	/**
	 * 获取每日必做,我要变强表最大ID
	 * 
	 * @param playerId
	 * @return
	 */
	public Object getMaxId();

	/**
	 * 初始化每日必做,我要变强列表
	 */
	public void initDoEveryDay();

	/**
	 * 根据类型返回每日必做，我要变强 数据列表
	 * 
	 * @param type
	 *            1：每日必做，2：我要变强
	 * @return
	 */
	public List<DoEveryDay> getDoEveryDayListByType(byte type);

	/**
	 * 获取每日必做列表
	 * 
	 * @return
	 */
	public List<DoEveryDayVo> getDoEveryDayList();
}