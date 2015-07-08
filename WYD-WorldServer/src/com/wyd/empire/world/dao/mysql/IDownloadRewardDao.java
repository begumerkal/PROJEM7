package com.wyd.empire.world.dao.mysql;

import com.wyd.db.mysql.dao.UniversalDao;
import com.wyd.empire.world.entity.mysql.DownloadReward;

public interface IDownloadRewardDao extends UniversalDao {
	public void initData();

	/**
	 * 查询出下载奖励物品
	 * 
	 * @return
	 */
	public DownloadReward findDownloadReward(Integer playerLv);
}