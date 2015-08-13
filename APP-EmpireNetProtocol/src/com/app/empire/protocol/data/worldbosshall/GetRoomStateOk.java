package com.app.empire.protocol.data.worldbosshall;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取房间状态
 * @author zengxc
 *
 */
public class GetRoomStateOk extends AbstractData {
	private int roomId;              // 房间ID
	private int[] mapId;             // 地图ID
	private int bossBloodMax;        // BOSS总血量
	private int bossBloodCurrent;    // BOSS当前血量
	private int[] reward;            // 奖励物品Id
	private String[] rankPlayerName; // 排行榜玩家名字
	private int[] rankHurt;          // 排位赛伤害输出
	private int hurt;                // 自己对boss造成的伤害合计值
	private int cdTime;              // 冷却时间
	private String[] mapIcon;        // 房间地图icon
	private String explain;          // 说明内容
	private int accelerateCost;      // 加速所需钻石

	public int getAccelerateCost() {
		return accelerateCost;
	}
	public void setAccelerateCost(int accelerateCost) {
		this.accelerateCost = accelerateCost;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int[] getMapId() {
		return mapId;
	}
	public void setMapId(int[] mapId) {
		this.mapId = mapId;
	}
	public int getBossBloodMax() {
		return bossBloodMax;
	}
	public void setBossBloodMax(int bossBloodMax) {
		this.bossBloodMax = bossBloodMax;
	}
	public int getBossBloodCurrent() {
		return bossBloodCurrent;
	}
	public void setBossBloodCurrent(int bossBloodCurrent) {
		this.bossBloodCurrent = bossBloodCurrent;
	}
	public int[] getReward() {
		return reward;
	}
	public void setReward(int[] reward) {
		this.reward = reward;
	}
	public String[] getRankPlayerName() {
		return rankPlayerName;
	}
	public void setRankPlayerName(String[] rankPlayerName) {
		this.rankPlayerName = rankPlayerName;
	}
	public int[] getRankHurt() {
		return rankHurt;
	}
	public void setRankHurt(int[] rankHurt) {
		this.rankHurt = rankHurt;
	}
	public int getHurt() {
		return hurt;
	}
	public void setHurt(int hurt) {
		this.hurt = hurt;
	}
	public int getCdTime() {
		return cdTime;
	}
	public void setCdTime(int cdTime) {
		this.cdTime = cdTime;
	}
	public String[] getMapIcon() {
		return mapIcon;
	}
	public void setMapIcon(String[] mapIcon) {
		this.mapIcon = mapIcon;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public GetRoomStateOk(int sessionId, int serial) {
        super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_GetRoomStateOk, sessionId, serial);
    }
	public GetRoomStateOk(){
		super(Protocol.MAIN_WORLDBOSSHALL, Protocol.WORLDBOSSHALL_GetRoomStateOk);
	}

}
