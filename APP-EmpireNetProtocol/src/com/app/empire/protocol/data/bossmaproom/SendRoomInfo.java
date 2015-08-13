package com.app.empire.protocol.data.bossmaproom;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class SendRoomInfo extends AbstractData {
	
	private String	mapShortName;	//地图名称缩写
	private int	playLevel;	    //地图开启等级
	private int[]	rewardList;	//奖励物品ID


	public SendRoomInfo(int sessionId, int serial) {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_SendRoomInfo, sessionId, serial);
	}

	public SendRoomInfo() {
		super(Protocol.MAIN_BOSSMAPROOM, Protocol.BOSSMAPROOM_SendRoomInfo);
	}

	public String getMapShortName() {
		return mapShortName;
	}

	public void setMapShortName(String mapShortName) {
		this.mapShortName = mapShortName;
	}

	public int getPlayLevel() {
		return playLevel;
	}

	public void setPlayLevel(int playLevel) {
		this.playLevel = playLevel;
	}

	

	public int[] getRewardList() {
		return rewardList;
	}

	public void setRewardList(int[] rewardList) {
		this.rewardList = rewardList;
	}

	

}
