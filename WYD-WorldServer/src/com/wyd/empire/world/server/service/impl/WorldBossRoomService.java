package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.worldbosshall.WorldBossRoom;

/**
 * 世界BOSS房间数据管理
 * 
 * @author zengxc
 */
public class WorldBossRoomService {
	private static WorldBossRoomService instance = null;
	private ConcurrentHashMap<Integer, WorldBossRoom> mapId_room;// KEY:地图ID,VAL:房间对象
	private ConcurrentHashMap<Integer, Integer> roomId_MapId; // KEY:房间ID,VAL:地图ID

	public final int defaultMap = Common.WORLDBOSS_DEFAULT_MAP;

	public WorldBossRoomService() {
		// 初始化MAP，每张地图创建一个房间
		mapId_room = new ConcurrentHashMap<Integer, WorldBossRoom>();
		roomId_MapId = new ConcurrentHashMap<Integer, Integer>();
		List<Map> maps = ServiceManager.getManager().getMapsService().getWorldBossMapList();
		for (Map map : maps) {
			int mapId = map.getId();
			WorldBossRoom room = new WorldBossRoom();
			room.setMapId(mapId);
			room.setLevel(map.getLevel());
			mapId_room.put(mapId, room);
			roomId_MapId.put(room.getId(), mapId);
		}
	}

	public List<WorldBossRoom> getAllRoom() {
		List<WorldBossRoom> roomList = new ArrayList<WorldBossRoom>();
		for (Integer key : mapId_room.keySet()) {
			roomList.add(mapId_room.get(key));
		}
		return roomList;
	}

	/**
	 * 按地图Id去查找房间
	 * 
	 * @param mapId
	 * @return
	 */
	public WorldBossRoom getRoomByMap(int mapId) {
		return mapId_room.get(mapId);
	}

	/**
	 * 按房间ID找房间
	 * 
	 * @param roomId
	 * @return
	 */
	public WorldBossRoom getRoomById(int roomId) {
		return mapId_room.get(roomId_MapId.get(roomId));
	}

	public static WorldBossRoomService getInstance() {
		synchronized (WorldBossRoomService.class) {
			if (null == instance) {
				instance = new WorldBossRoomService();
			}
		}
		return instance;
	}

	/**
	 * 开启房间
	 * 
	 * @param mapId
	 * @return
	 */
	public int openRoom(int mapId) {
		if (mapId == -1)
			mapId = defaultMap;
		WorldBossRoom room = getRoomByMap(mapId);
		room.open();
		return room.getId();
	}

	/**
	 * 创建世界BOSS
	 * 
	 * @param mapId
	 * @return
	 */
	public int createBoss(int mapId) {
		if (mapId == -1)
			mapId = defaultMap;
		WorldBossRoom room = getRoomByMap(mapId);
		room.createBoss();
		return room.getId();
	}

	/**
	 * 关闭房间
	 */
	public void closeRoom(int mapId) {
		if (mapId == -1)
			mapId = defaultMap;
		WorldBossRoom room = getRoomByMap(mapId);
		room.close();
	}

}
