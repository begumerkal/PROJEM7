package com.wyd.empire.world.server.service.base;

import java.util.Collection;
import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.DailyActivity;

public interface IDailyActivityService extends UniversalManager {
	public List<DailyActivity> getAllDailyActivity();

	public PageList getDailyActivityByPage(int pageIndex, int pageSize);

	/**
	 * 根据多个活动奖励ID值删除记录
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	public void deleteActivityByIds(String ids);

	/**
	 * 批量保存
	 */
	public void saveOrUpdateAll(Collection<Object> coll);
}
