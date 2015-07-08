package com.wyd.empire.world.dao.mysql.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wyd.db.mysql.dao.impl.UniversalDaoHibernate;
import com.wyd.empire.world.dao.mysql.IStarsInfoDao;
import com.wyd.empire.world.entity.mysql.StarsInfo;

/**
 * The DAO class for the TabTool entity.
 */
public class StarsInfoDao extends UniversalDaoHibernate implements IStarsInfoDao {
	private Map<Integer, StarsInfo> starsInfoMap = null;

	public StarsInfoDao() {
		super();
	}

	/**
	 * 初始化数据
	 */
	@SuppressWarnings("unchecked")
	public void initData() {
		Map<Integer, StarsInfo> starsInfoMap = new HashMap<Integer, StarsInfo>();
		List<StarsInfo> starsInfoList = getList("FROM StarsInfo", new Object[]{});
		for (StarsInfo starsInfo : starsInfoList) {
			starsInfoMap.put(starsInfo.getLevel(), starsInfo);
		}
		this.starsInfoMap = starsInfoMap;
	}

	/**
	 * 根据星级获取升星信息
	 * 
	 * @param stars
	 * @return
	 */
	public StarsInfo getByLevel(int level) {
		return starsInfoMap.get(level);
	}

	/**
	 * 获取所有的升星信息
	 * 
	 * @return
	 */
	public Collection<StarsInfo> getAllStarsInfo() {
		return starsInfoMap.values();
	}

	@Override
	public StarsInfo getStarConfig(int level) {
		return starsInfoMap.get(level);
	}

}