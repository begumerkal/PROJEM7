package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;

import com.wyd.db.page.PageList;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.MapEvent;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.server.service.base.IMapService;

/**
 * 地图管理服务
 * 
 * @author Administrator
 */
public class MapsService {
	private IMapService service;
	private ConcurrentHashMap<Integer, Map> mapMap = new ConcurrentHashMap<Integer, Map>();
	private ConcurrentHashMap<Integer, Map> bossMap = new ConcurrentHashMap<Integer, Map>();
	private ConcurrentHashMap<Integer, Map> worldBossMap = new ConcurrentHashMap<Integer, Map>();
	private ConcurrentHashMap<Integer, Map> singleMap = new ConcurrentHashMap<Integer, Map>();
	private List<Map> mapList = null;
	private List<Map> bossMapList = null;
	private List<Map> worldBossMapList = null;
	private List<Map> singleMapList = null;
	/**
	 * 随机地图id
	 */
	public static final int RANDOMID = 1;

	public MapsService(IMapService service) {
		this.service = service;
		initDate();
	}

	/**
	 * 初始化地图数据
	 */
	public void initDate() {
		mapMap.clear();
		bossMap.clear();
		worldBossMap.clear();
		singleMap.clear();
		mapList = this.service.getBattleMap();
		for (Map map : mapList) {
			mapMap.put(map.getId(), map);
		}
		bossMapList = this.service.getBossMap();
		for (Map map : bossMapList) {
			bossMap.put(map.getId(), map);
		}
		worldBossMapList = this.service.getWorldBossMap();
		for (Map map : worldBossMapList) {
			worldBossMap.put(map.getId(), map);
		}
		singleMapList = this.service.getSingleMap();
		for (Map map : singleMapList) {
			singleMap.put(map.getId(), map);
		}
	}

	/**
	 * 添加地图信息到缓存集合
	 * 
	 * @param map
	 */
	public void addMap(Map map) {
		if (map.getMapType() == Common.MAP_TYPE_SINGLE) {
			singleMap.put(map.getId(), map);
		} else if (map.getMapType() == Common.MAP_TYPE_WORLD) {
			worldBossMap.put(map.getId(), map);
		} else if (map != null && map.getStar() > 0) {
			mapMap.put(map.getId(), map);
		} else {
			bossMap.put(map.getId(), map);
		}
	}

	/**
	 * 更新地图信息到缓存集合
	 * 
	 * @param map
	 */
	public void updateMap(Map map) {
		Map oldMap = null;
		if (map.getMapType() == Common.MAP_TYPE_SINGLE) {
			oldMap = singleMap.get(map.getId());
		} else if (map.getMapType() == Common.MAP_TYPE_WORLD) {
			oldMap = worldBossMap.get(map.getId());
		} else if (map != null && map.getStar() > 0) {
			oldMap = mapMap.get(map.getId());
		} else {
			oldMap = bossMap.get(map.getId());
		}
		try {
			BeanUtils.copyProperties(oldMap, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public IMapService getService() {
		return service;
	}

	/**
	 * 获取随机地图id
	 * 
	 * @return
	 */
	public int getRandomId() {
		return RANDOMID;
	}

	public Map getRandomMap(int roomChannel) {
		Map randomMap = null;
		while (randomMap == null) {
			int index = ServiceUtils.getRandomNum(0, mapList.size());
			randomMap = mapList.get(index);
			if (randomMap.getChannel() > roomChannel || RANDOMID == randomMap.getId()) {
				randomMap = null;
			}
		}
		return randomMap;
	}

	public Map getMapById(int mapId) {
		return mapMap.get(mapId);
	}

	public Map getBossMapById(int mapId) {
		return bossMap.get(mapId);
	}

	public Map getWorldBossMapById(int mapId) {
		return worldBossMap.get(mapId);
	}

	public Map getSingleMapById(int mapId) {
		return singleMap.get(mapId);
	}

	public List<Map> getMapList() {
		return mapList;
	}

	public List<Map> getBossMapList() {
		return bossMapList;
	}

	public List<Map> getWorldBossMapList() {
		return worldBossMapList;
	}

	public List<Map> getSingleMapList() {
		return singleMapList;
	}

	/**
	 * 分页取出List，如果大于最大页数则返回第一页
	 * 
	 * @param pageIndex
	 * @return
	 */
	public PageList getSingleMapList(int pageIndex) {
		pageIndex = pageIndex < 1 ? 1 : pageIndex;
		int pageSize = 5;
		int start = (pageIndex - 1) * pageSize;
		int end = pageIndex * pageSize;
		int maxSize = singleMapList.size();
		// 如果超出最大值则返回第一页
		if (start >= maxSize) {
			pageIndex = 1;
			end = pageSize;
			start = 0;
		}
		end = end > maxSize ? maxSize : end;
		List<Object> list = new ArrayList<Object>();
		for (int i = start; i < end; i++) {
			list.add(singleMapList.get(i));
		}
		PageList pageList = new PageList(0, list, 0, pageIndex);
		return pageList;
	}

	public MapEvent getMapEvent(int eventId) {
		return service.getMapEvent(eventId);
	}
}
