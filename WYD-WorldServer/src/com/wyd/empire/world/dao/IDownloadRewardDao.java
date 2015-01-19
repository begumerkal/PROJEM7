package com.wyd.empire.world.dao;

import com.wyd.db.dao.UniversalDao;
import com.wyd.empire.world.bean.DownloadReward;

public interface IDownloadRewardDao extends UniversalDao {
	public void initData();

	/**
	 * 查询出下载奖励物品
	 * 
	 * @return
	 */
	public DownloadReward findDownloadReward(Integer playerLv);
}