package com.wyd.empire.world.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.util.StringUtils;

import com.wyd.db.dao.impl.UniversalDaoHibernate;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.dao.IMapDao;

/**
 * The DAO class for the TabConsortiaright entity.
 */
public class MapDao extends UniversalDaoHibernate implements IMapDao {
	public MapDao() {
		super();
	}

	/**
	 * 获得普通战斗地图列表
	 * 
	 * @return 普通战斗地图列表
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getBattleMap() {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Map.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND mapType = ? ");
		values.add(Common.MAP_TYPE_GENERAL);
		hql.append(" ORDER BY seqmap ASC");
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * 获取非普通战斗副本地图列表
	 * 
	 * @return 非普通战斗副本地图列表
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getBossMap() {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Map.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND mapType = ? ");
		values.add(Common.MAP_TYPE_BIG);
		hql.append(" ORDER BY bossmapSerial ASC ");
		return getList(hql.toString(), values.toArray());
	}

	/**
	 * GM工具查询地图列表
	 * 
	 * @param key
	 *            查询条件
	 * @param pageIndex
	 *            当前页
	 * @param pageSize
	 *            每页显示多少条
	 * @return
	 */
	public PageList findAllMap(String key, int pageIndex, int pageSize) {
		StringBuffer hsql = new StringBuffer();
		List<Object> values = new Vector<Object>();
		hsql.append(" FROM  " + Map.class.getSimpleName() + " WHERE 1 = 1 ");
		String[] params = key.split("\\|");
		for (int i = 0; i < params.length; i++) {
			if (StringUtils.hasText(params[i])) {
				switch (i) {
					case 0 :

						break;
					default :
						break;
				}
			}
		}
		String hqlc = "SELECT COUNT(*) " + hsql.toString();
		return getPageList(hsql.toString(), hqlc, values.toArray(), pageIndex, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getWorldBossMap() {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Map.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND mapType = ? ");
		values.add(Common.MAP_TYPE_WORLD);
		hql.append(" ORDER BY bossmapSerial ASC ");
		return getList(hql.toString(), values.toArray());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> getSingleMap() {
		StringBuffer hql = new StringBuffer();
		List<Object> values = new ArrayList<Object>();
		hql.append(" FROM " + Map.class.getSimpleName() + " WHERE 1 = 1 ");
		hql.append(" AND mapType = ? ");
		values.add(Common.MAP_TYPE_SINGLE);
		hql.append(" ORDER BY bossmapSerial ASC ");
		return getList(hql.toString(), values.toArray());
	}
}