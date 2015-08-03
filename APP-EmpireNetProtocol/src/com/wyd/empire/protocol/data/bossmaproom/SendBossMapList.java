package com.wyd.empire.protocol.data.bossmaproom;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class SendBossMapList extends AbstractData {
	
	private int			mapCount;	//地图数量
	private int[]		mapId;	    //地图id
	private String[]	mapName;	//地图名称
	private String[]	mapShortName;	//地图名称缩写
	private String[]	mapIcon;	//地图图标
	private String[]	mapAnimationIndexCode;	//地图资源名称
	private String[]	mapDese;	//地图说明
	private boolean[]	canPlay;	//地图是否已开启
	private int[]		playLevel;	//地图开启等级
	private int[]       useVigor;   //消耗的活力值
	private int[]       passTime;   // 已挑战次数
	private int[]       totalTime;  // 总挑战次数

	
	public SendBossMapList(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_SendBossMapList, sessionId, serial);
	}

	public SendBossMapList() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_SendBossMapList);
	}

	public int getMapCount() {
		return mapCount;
	}

	public void setMapCount(int mapCount) {
		this.mapCount = mapCount;
	}

	public int[] getMapId() {
		return mapId;
	}

	public void setMapId(int[] mapId) {
		this.mapId = mapId;
	}

	public String[] getMapName() {
		return mapName;
	}

	public void setMapName(String[] mapName) {
		this.mapName = mapName;
	}

	public String[] getMapShortName() {
		return mapShortName;
	}

	public void setMapShortName(String[] mapShortName) {
		this.mapShortName = mapShortName;
	}

	public String[] getMapIcon() {
		return mapIcon;
	}

	public void setMapIcon(String[] mapIcon) {
		this.mapIcon = mapIcon;
	}

	public String[] getMapAnimationIndexCode() {
		return mapAnimationIndexCode;
	}

	public void setMapAnimationIndexCode(String[] mapAnimationIndexCode) {
		this.mapAnimationIndexCode = mapAnimationIndexCode;
	}

	public String[] getMapDese() {
		return mapDese;
	}

	public void setMapDese(String[] mapDese) {
		this.mapDese = mapDese;
	}

	
	public boolean[] getCanPlay() {
		return canPlay;
	}

	public void setCanPlay(boolean[] canPlay) {
		this.canPlay = canPlay;
	}

	public int[] getPlayLevel() {
		return playLevel;
	}

	public void setPlayLevel(int[] playLevel) {
		this.playLevel = playLevel;
	}

	public int[] getUseVigor() {
		return useVigor;
	}

	public void setUseVigor(int[] useVigor) {
		this.useVigor = useVigor;
	}

	public int[] getPassTime() {
		return passTime;
	}

	public void setPassTime(int[] passTime) {
		this.passTime = passTime;
	}

	public int[] getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int[] totalTime) {
		this.totalTime = totalTime;
	}
	
	
}
