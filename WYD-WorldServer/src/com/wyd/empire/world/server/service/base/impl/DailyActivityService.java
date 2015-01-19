package com.wyd.empire.world.server.service.base.impl;

import java.util.Collection;
import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.bean.DailyActivity;
import com.wyd.empire.world.dao.IDailyActivityDao;
import com.wyd.empire.world.server.service.base.IDailyActivityService;

public class DailyActivityService extends UniversalManagerImpl implements IDailyActivityService {
	private IDailyActivityDao dao;

	public void setDao(IDailyActivityDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	@Override
	public List<DailyActivity> getAllDailyActivity() {
		return dao.getAllDailyActivity();
	}

	@Override
	public PageList getDailyActivityByPage(int pageIndex, int pageSize) {
		return dao.getDailyActivityByPage(pageIndex, pageSize);
	}

	/**
	 * 根据多个活动奖励ID值删除记录
	 * 
	 * @param ids
	 *            多个ID值，中间已,分割
	 */
	@Override
	public void deleteActivityByIds(String ids) {
		dao.deleteActivityByIds(ids);
	}

	/**
	 * 批量保存
	 */
	@Override
	public void saveOrUpdateAll(Collection<Object> coll) {
		dao.saveOrUpdateAll(coll);
	}
}
