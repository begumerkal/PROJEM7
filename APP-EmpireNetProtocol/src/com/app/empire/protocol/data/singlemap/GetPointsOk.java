package com.app.empire.protocol.data.singlemap;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class GetPointsOk extends AbstractData {
	private int id;              // 大关卡ID
	private String desc;         // 单人副本说明
	private int[] pointX;        // 小关卡图标坐标点X轴
	private int[] pointY;        // 小关卡图标坐标点Y轴
	private int[] useVigor;      // 消耗活力值
	private int[] pointId;       // 小关卡id
	private String[] pointTitle; // 小关卡标题
	private String[] pointDesc;  // 小关卡介绍
	private int[]    reward;     // 奖励物品ID
	private int[]    mode;       // 模式0:挑战 1：扫荡 模式为挑战时不能扫荡，模式为扫荡时既可以扫荡也可以挑战
	private int[]    passTime;   // 已挑战次数
	private int[]    totalTime;  // 总挑战次数
	private int[]    rewardCount;// 奖励物品个数
	
	public GetPointsOk(int sessionId, int serial) {
        super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetPointsOk, sessionId, serial);
    }
	public GetPointsOk(){
		 super(Protocol.MAIN_SINGLEMAP, Protocol.SINGLEMAP_GetPointsOk);
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int[] getPointX() {
		return pointX;
	}
	public void setPointX(int[] pointX) {
		this.pointX = pointX;
	}
	public int[] getPointY() {
		return pointY;
	}
	public void setPointY(int[] pointY) {
		this.pointY = pointY;
	}
	public int[] getUseVigor() {
		return useVigor;
	}
	public void setUseVigor(int[] useVigor) {
		this.useVigor = useVigor;
	}
	
	public int[] getPointId() {
		return pointId;
	}
	public void setPointId(int[] pointId) {
		this.pointId = pointId;
	}
	public String[] getPointTitle() {
		return pointTitle;
	}
	public void setPointTitle(String[] pointTitle) {
		this.pointTitle = pointTitle;
	}
	public String[] getPointDesc() {
		return pointDesc;
	}
	public void setPointDesc(String[] pointDesc) {
		this.pointDesc = pointDesc;
	}
	
	public int[] getMode() {
		return mode;
	}
	public void setMode(int[] mode) {
		this.mode = mode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
	public int[] getRewardCount() {
		return rewardCount;
	}
	public void setRewardCount(int[] rewardCount) {
		this.rewardCount = rewardCount;
	}
	public int[] getReward() {
		return reward;
	}
	public void setReward(int[] reward) {
		this.reward = reward;
	}
	
}
