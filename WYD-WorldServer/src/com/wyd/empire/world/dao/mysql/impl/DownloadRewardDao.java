package com.wyd.empire.world.dao.mysql.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.dao.mysql.IDownloadRewardDao;
import com.wyd.empire.world.entity.mysql.DownloadReward;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class DownloadRewardDao extends UniversalDaoHibernate implements IDownloadRewardDao {
	private Map<Integer, DownloadReward> drMap = null;

	public DownloadRewardDao() {
		super();
	}

	@SuppressWarnings("unchecked")
	public void initData() {
		List<DownloadReward> rewardList = getList("FROM " + DownloadReward.class.getSimpleName(), new Object[]{});
		Map<Integer, DownloadReward> drMap = new HashMap<Integer, DownloadReward>();
		for (DownloadReward dr : rewardList) {
			drMap.put(dr.getPlayerLv(), dr);
		}
		this.drMap = drMap;
	}

	/**
	 * 查询出所有下载奖励物品
	 * 
	 * @return
	 */
	@Override
	public DownloadReward findDownloadReward(Integer playerLv) {
		return drMap.get(playerLv);
	}
}