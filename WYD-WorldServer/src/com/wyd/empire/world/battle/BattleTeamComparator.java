package com.wyd.empire.world.battle;

import java.util.Comparator;

import com.wyd.empire.world.room.Room;

/**
 * @author zpl
 * @version 创建时间：2014-01-07 10:07:23 类说明
 */
public class BattleTeamComparator implements Comparator<Room> {
	public int compare(Room room1, Room room2) {
		return room2.getAvgFighting() - room1.getAvgFighting();
	}
}
