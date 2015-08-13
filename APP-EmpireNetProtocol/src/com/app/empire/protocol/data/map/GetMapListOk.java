package com.app.empire.protocol.data.map;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetMapListOk extends AbstractData {
	private int      mapCount;
	private int[]    mapId;
	private String[] mapName;	
	private int[]    mapStar;
	private int[]    mapChannel;
	private String[] mapIcon;
	private String[] mapAnimationIndexCode;
	private String[] mapDese;
	public GetMapListOk(int sessionId, int serial) {
		super(Protocol.MAIN_MAP, Protocol.MAP_GetMapListOk, sessionId, serial);
	}

	public GetMapListOk() {
		super(Protocol.MAIN_MAP, Protocol.MAP_GetMapListOk);
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

	public int[] getMapStar() {
		return mapStar;
	}

	public void setMapStar(int[] mapStar) {
		this.mapStar = mapStar;
	}

	public int[] getMapChannel() {
		return mapChannel;
	}

	public void setMapChannel(int[] mapChannel) {
		this.mapChannel = mapChannel;
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
}
