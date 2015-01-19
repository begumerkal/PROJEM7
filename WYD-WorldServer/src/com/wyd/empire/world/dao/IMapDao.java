package com.wyd.empire.world.dao;

import java.util.List;

import com.wyd.db.dao.UniversalDao;
import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.Map;

/**
 * The DAO interface for the TabConsortiaright entity.
 */
public interface IMapDao extends UniversalDao {

	/**
	 * 获得普通战斗地图列表
	 * 
	 * @return 普通战斗地图列表
	 */
	public List<Map> getBattleMap();

	/**
	 * 获取战斗大副本地图列表
	 * 
	 * @return
	 */
	public List<Map> getBossMap();

	/**
	 * 获取世界BOSS地图
	 * 
	 * @return
	 */
	public List<Map> getWorldBossMap();

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
	public PageList findAllMap(String key, int pageIndex, int pageSize);

	/**
	 * 单人副本地图
	 * 
	 * @return
	 */
	public List<Map> getSingleMap();
}