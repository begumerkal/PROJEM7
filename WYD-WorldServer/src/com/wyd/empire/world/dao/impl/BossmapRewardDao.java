package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.dao.IBossmapRewardDao;
import com.wyd.empire.world.entity.mysql.BossmapReward;

/**
 * The DAO class for the TabBossmapReward entity.
 */
public class BossmapRewardDao extends UniversalDaoHibernate implements IBossmapRewardDao {
	private List<BossmapReward> rewardList = null;

	public BossmapRewardDao() {
		super();
	}

	/**
	 * 初始化数据
	 */
	@SuppressWarnings("unchecked")
	public void initData() {
		rewardList = getList("FROM BossmapReward ", new Object[]{});
	}

	/**
	 * 获取指定副本的专属物品
	 * 
	 * @param mapId
	 *            -1为通用奖励物品
	 * @param difficulty
	 *            副本难度 4为钻石专属奖励
	 * @return
	 */
	public List<BossmapReward> getBossmapRewardListByMapId(int mapId, int difficulty, int sex) {
		List<BossmapReward> brList = new ArrayList<BossmapReward>();
		for (BossmapReward bossmapReward : rewardList) {
			int rewardType = bossmapReward.getRewardType();
			if (bossmapReward.getBossMapId().intValue() == mapId && (difficulty == rewardType || rewardType == 4)) {
				if (sex == -1 || sex == bossmapReward.getSex().intValue() || 2 == bossmapReward.getSex().intValue()) {
					brList.add(bossmapReward);
				}
			}
		}
		return brList;
	}

	public List<BossmapReward> getBossmapRewardListByMapId(int mapId, int difficulty) {
		return getBossmapRewardListByMapId(mapId, difficulty, -1);
	}

	/**
	 * GM工具--根据BOSS地图获取副本奖励
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList findMapReward(String key, int pageIndex, int pageSize) {
		StringBuilder hql = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		hql.append("FROM " + BossmapReward.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] dates = key.split("\\|");
		for (int i = 0; i < dates.length; i++) {
			if (StringUtils.hasText(dates[i])) {
				switch (i) {
					case 0 :
						hql.append(" AND bossMapId = ?");
						values.add(Integer.parseInt(dates[0]));
						break;
				}
			}
		}
		String hqlc = "SELECT COUNT(*) " + hql.toString();
		return getPageList(hql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	/**
	 * 获取指定副本的奖励物品的权值id
	 * 
	 * @param mapId
	 * @param difficulty
	 *            副本难度
	 * @param isDiamond
	 *            是否包含钻石专属物品
	 * @return
	 */
	public List<Integer> getProbabilityIdByMapId(int mapId, int difficulty, boolean isDiamond) {
		List<BossmapReward> brList = new ArrayList<BossmapReward>();
		for (BossmapReward bossmapReward : rewardList) {
			if (bossmapReward.getBossMapId().intValue() == -1 || bossmapReward.getBossMapId().intValue() == mapId) {
				brList.add(bossmapReward);
			}
		}
		StringBuffer ids = new StringBuffer(",");
		List<Integer> idList = new ArrayList<Integer>();
		for (BossmapReward bossmapReward : brList) {
			int rewardType = bossmapReward.getRewardType();
			if ((rewardType != difficulty && rewardType != 4) || (!isDiamond && rewardType == 4)) {
				continue;
			}
			if (ids.indexOf("," + bossmapReward.getProbabilityId() + ",") < 0) {
				ids.append(bossmapReward.getProbabilityId());
				ids.append(",");
				idList.add(bossmapReward.getProbabilityId());
			}
		}
		return idList;
	}
}