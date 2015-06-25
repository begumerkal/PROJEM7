package com.wyd.empire.world.dao.mysql;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.entity.mysql.DailyActivity;

public interface IDailyActivityDao extends UniversalDao {
	public List<DailyActivity> getAllDailyActivity();

	public PageList getDailyActivityByPage(int pageIndex, int pageSize);

	/**
	 * 根据多个活动奖励ID值删除记录
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteActivityByIds(String ids);
}
