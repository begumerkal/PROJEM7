package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.MapEvent;

/**
 * The service interface for the TabConsortiaright entity.
 */
public interface IMapService extends UniversalManager {

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

	/**
	 * 获取天气对象
	 * 
	 * @param weatherId
	 * @return
	 */
	public MapEvent getMapEvent(int eventId);
}