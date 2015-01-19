package com.wyd.empire.world.bossmaproom;

import java.util.List;

import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 房间数据对象
 * 
 * @author Administrator
 */
public class BossRoom {
	private int roomId; // 房间id
	private String roomName; // 房间名称
	private int battleStatus = 0; // 房间战斗状态 0等待中，1战斗中
	private int battleMode; // 战斗模式,1、小副本，2、大副本
	private List<BossSeat> playerList; // 房间里面的用户
	private int playerNumMode; // 对战人数模式,1=1v1，2=2v2，3=3v3
	private String passWord; // 房间密码，空密码为"-1"
	private int mapId; // 房间地图id
	private int wnersId; // 房主id
	private int playerNum = 0; // 房间当前人数
	private int progress; // 房间等级
	private boolean leaveRoom = false;
	private int level; // 进入房间需求等级
	private int times; // 地图冷却时间
	private int difficulty; // 副本难度(0=普通,1=困难,2=地狱)
	// 2.1修改内容 通关副本后需要进行更新
	private int points = 0; // 当前副本的进度
	private String roomShortName; // 房间名称简称
	private String reward; // 通关副本获得奖励（固定）
	private int mapType; // 关卡类型：1 小怪关卡，2 BOSS关卡
	private String mapIcon; // 副本完成条件资源
	private String guaiList; // 副本关联怪物列表
	private String runTime1; // 简单难度回合数限制
	private String runTime2; // 困难难度回合数限制
	private String runTime3; // 地狱难度回合数限制
	private String npcNumber; // 怪物出生数量
	private String animationIndexCode; // 战斗地图资源名称
	private int bossmapSerial; // 副本进度
	private int mapVigor; // 副本消耗活力值

	public BossRoom() {
		roomId = this.hashCode();
		while (roomId > 999999) {
			roomId = roomId / 100;
		}
		while (null != ServiceManager.getManager().getRoomService().getRoom(roomId)) {
			roomId = (int) (roomId * Math.random());
			if (roomId < 50) {
				roomId = (int) (999999 * Math.random());
			}
		}
	}

	public BossRoom(int roomId) {
		this.roomId = roomId;
	}

	public int getRoomId() {
		return roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomShortName() {
		String[] rsn = roomShortName.split("\\|");
		points = points < rsn.length ? points : rsn.length - 1;
		return rsn[points];
	}

	public void setRoomShortName(String roomShortName) {
		this.roomShortName = roomShortName;
	}

	public int getBattleStatus() {
		return battleStatus;
	}

	public void setBattleStatus(int battleStatus) {
		this.battleStatus = battleStatus;
	}

	/**
	 * 是否满员
	 * 
	 * @param playerNumMode副本模式非Boss级为0
	 *            ，Boss级为1
	 * @return
	 */
	public boolean isFull(int playerNumMode) {
		if (playerNumMode == playerNum) {
			return true;
		}
		return false;
	}

	public List<BossSeat> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(List<BossSeat> playerList) {
		this.playerList = playerList;
	}

	public int getPlayerNumMode() {
		return playerNumMode;
	}

	public void setPlayerNumMode(int playerNumMode) {
		this.playerNumMode = playerNumMode;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public int getWnersId() {
		return wnersId;
	}

	public void setWnersId(int wnersId) {
		this.wnersId = wnersId;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public boolean isLeaveRoom() {
		return leaveRoom;
	}

	public void setLeaveRoom(boolean leaveRoom) {
		this.leaveRoom = leaveRoom;
	}

	public int getBattleMode() {
		return battleMode;
	}

	public void setBattleMode(int battleMode) {
		this.battleMode = battleMode;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * 获取副本进度
	 * 
	 * @return
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * 增加副本进度
	 */
	public void addPoints() {
		this.points++;
	}

	/**
	 * 副本类型：1前置关卡，2BOSS关卡
	 * 
	 * @return
	 */
	public int getMapType() {
		// System.out.println("mapType:" + mapType + "-------points:" + points +
		// "-------roomShortName:" + roomShortName);
		return mapType != Common.MAP_TYPE_BIG ? mapType : points == (roomShortName.split("\\|").length - 1)
				? Common.MAP_TYPE_BIG
				: Common.MAP_TYPE_SMALL;
	}

	public void setMapType(int mapType) {
		this.mapType = mapType;
	}

	/**
	 * 副本完成条件资源
	 * 
	 * @return
	 */
	public String getMapIcon() {
		return mapIcon.split("\\|")[points];
	}

	public void setMapIcon(String mapIcon) {
		this.mapIcon = mapIcon;
	}

	/**
	 * 副本关联怪物列表
	 * 
	 * @return
	 */
	public String getGuaiList() {
		return guaiList.split("\\|")[points];
	}

	public void setGuaiList(String guaiList) {
		this.guaiList = guaiList;
	}

	/**
	 * 简单难度回合数限制
	 * 
	 * @return
	 */
	private int getRunTime1() {
		return Integer.parseInt(runTime1.split("\\|")[points]);
	}

	public void setRunTime1(String runTime1) {
		this.runTime1 = runTime1;
	}

	/**
	 * 困难难度回合数限制
	 * 
	 * @return
	 */
	private int getRunTime2() {
		return Integer.parseInt(runTime2.split("\\|")[points]);
	}

	public void setRunTime2(String runTime2) {
		this.runTime2 = runTime2;
	}

	/**
	 * 地狱难度回合数限制
	 * 
	 * @return
	 */
	private int getRunTime3() {
		return Integer.parseInt(runTime3.split("\\|")[points]);
	}

	public void setRunTime3(String runTime3) {
		this.runTime3 = runTime3;
	}

	/**
	 * 怪物出生数量
	 * 
	 * @return
	 */
	public int getNpcNumber() {
		return Integer.parseInt(npcNumber.split("\\|")[points]);
	}

	public void setNpcNumber(String npcNumber) {
		this.npcNumber = npcNumber;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getAnimationIndexCode() {
		return animationIndexCode;
	}

	public void setAnimationIndexCode(String animationIndexCode) {
		this.animationIndexCode = animationIndexCode;
	}

	public int getBossmapSerial() {
		return bossmapSerial;
	}

	public void setBossmapSerial(int bossmapSerial) {
		this.bossmapSerial = bossmapSerial;
	}

	public int getRunTimes() {
		switch (difficulty) {
			case 1 :
				return getRunTime1();
			case 2 :
				return getRunTime2();
			case 3 :
				return getRunTime3();
		}
		return 0;
	}

	public int getMapVigor() {
		return mapVigor;
	}

	public void setMapVigor(int mapVigor) {
		this.mapVigor = mapVigor;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

}
