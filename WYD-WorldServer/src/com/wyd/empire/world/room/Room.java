package com.wyd.empire.world.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;

/**
 * 类 <code>Room</code> 房间数据对象
 * 
 * @author Administrator
 */
public class Room {

	/** 房间状态： 0表示等待中 */
	public static final int BATTLE_STATUS_WAIT = 0;

	/** 房间状态： 1表示战斗 */
	public static final int BATTLE_STATUS_BATTLE = 1;

	/** 房间类型： 0表示正常房间 */
	public static final int BATTLE_TYPE_NORMAL = 0;

	/** 房间类型： 1表示机器人房间 */
	public static final int BATTLE_TYPE_MACHINE = 1;

	/** 房间id */
	private int roomId;

	/** 房间名称 */
	private String roomName;

	/** 房间战斗状态: 0等待中，1战斗中 */
	private int battleStatus = 0;

	/** 战斗模式: 1、竞技模式，2、复活模式，3、混战模式，4、排位赛，5、公会战,6、挑战赛 */
	private int battleMode;
	/** 跨服还是本服对战 0本服，1跨服 */
	private int serviceMode;
	/** 房间里面的用户 */
	private Vector<Seat> playerList;

	/** 对战人数模式: 1=1v1，2=2v2， 3=3v3 */
	private int playerNumMode;

	/** 房间密码，空密码为"-1" */
	private String passWord;

	/** 房间地图id */
	private int mapId;

	/** 房主id */
	private int wnersId;

	/** 房间当前人数 */
	private int playerNum = 0;

	/** 撮合方式,1、随机撮合, 2、自由模式 */
	private int startMode;

	/** 房间所属频道，1、初级， 2、高级 */
	private int roomChannel;

	/** 房间类型： 0正常房间， 1机器人房间 */
	private int roomType;

	/** 假房间 */
	private boolean leaveRoom = false;

	/** 等级分数差（排位赛配对时使用） */
	private int gap;

	/** 排位赛分数（排位赛配对时使用） */
	private int rankScore;

	/** 敌对公会（公会战使用） */
	private boolean enemyCon = false;

	/** 房间创建时间 */
	private long createTime;
	/** 排位赛de等级（排位赛配对时使用） */
	private int honorLevel;
	/** 是否是新手房间 */
	private boolean newPlayerRoom = false;
	/** 是否开启地图特殊事件 */
	private boolean eventMode = false;

	/** 挑战赛申请列表 */
	private List<WorldPlayer> applyList = new ArrayList<WorldPlayer>();

	public Room() {
		roomId = this.hashCode();
		createTime = System.currentTimeMillis();
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

	public Room(int roomId) {
		this.roomId = roomId;
		createTime = System.currentTimeMillis();
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

	public int getBattleStatus() {
		return battleStatus;
	}

	public void setBattleStatus(int battleStatus) {
		this.battleStatus = battleStatus;
	}

	/**
	 * 判断房间是否满员
	 * 
	 * @return true: 满员<br/>
	 *         false: 非满员
	 */
	public boolean isFull() {
		int num = (startMode == 1 ? playerNumMode : playerNumMode * 2);
		if (playerNum == num) {
			return true;
		} else {
			return false;
		}
	}

	public int getBattleMode() {
		return battleMode;
	}

	public void setBattleMode(int battleMode) {
		this.battleMode = battleMode;
	}

	public int getServiceMode() {
		return serviceMode;
	}

	public void setServiceMode(int serviceMode) {
		this.serviceMode = serviceMode;
	}

	public Vector<Seat> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(Vector<Seat> playerList) {
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

	public int getStartMode() {
		return startMode;
	}

	public void setStartMode(int startMode) {
		this.startMode = startMode;
	}

	public int getRoomChannel() {
		return roomChannel;
	}

	public void setRoomChannel(int roomChannel) {
		this.roomChannel = roomChannel;
	}

	public int getRoomType() {
		return roomType;
	}

	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}

	public boolean isLeaveRoom() {
		return leaveRoom;
	}

	public void setLeaveRoom(boolean leaveRoom) {
		this.leaveRoom = leaveRoom;
	}

	public int getGap() {
		return gap;
	}

	public void setGap(int gap) {
		this.gap = gap;
	}

	public int getRankScore() {
		return rankScore;
	}

	public void setRankScore(int rankScore) {
		this.rankScore = rankScore;
	}

	public boolean isEnemyCon() {
		return enemyCon;
	}

	public void setEnemyCon(boolean enemyCon) {
		this.enemyCon = enemyCon;
	}

	/**
	 * 判断房间里面的玩家是否都是机器人
	 * 
	 * @return true: 是机器人<br/>
	 *         false: 非机器人
	 */
	public boolean isAllRobot() {
		for (Seat seat : playerList) {
			if (!seat.isRobot() && null != seat.getPlayer()) {
				return false;
			}
		}
		return true;
	}

	public long getCreateTime() {
		return createTime;
	}

	public int getHonorLevel() {
		return honorLevel;
	}

	public void setHonorLevel(int honorLevel) {
		this.honorLevel = honorLevel;
	}

	/**
	 * 获取平均积分
	 * 
	 * @return
	 */
	public int getAvgIntegral() {
		int count = 0;
		int fighting = 0;
		for (Seat seat : playerList) {
			if (null != seat.getPlayer()) {
				fighting += ServiceManager.getManager().getChallengeSerService().getIntegral(seat.getPlayer().getId());
				count++;
			}
		}
		if (count > 0) {
			fighting = fighting / count;
		}
		return fighting;
	}

	/**
	 * 获取玩家平均战斗力
	 * 
	 * @return
	 */
	public int getAvgFighting() {
		int count = 0;
		int fighting = 0;
		for (Seat seat : playerList) {
			if (null != seat.getPlayer()) {
				fighting += seat.getPlayer().getFighting();
				count++;
			}
		}
		if (count > 0) {
			fighting = fighting / count;
		}
		return fighting;
	}

	/**
	 * 获取玩家平均等级
	 * 
	 * @return
	 */
	public int getAvgLevel() {
		int count = 0;
		int level = 0;
		for (Seat seat : playerList) {
			if (null != seat.getPlayer()) {
				level += seat.getPlayer().getLevel();
				count++;
			}
		}
		if (count > 0) {
			level = level / count;
		}
		return level;
	}

	public boolean isNewPlayerRoom() {
		return newPlayerRoom;
	}

	public void setNewPlayerRoom(boolean newPlayerRoom) {
		this.newPlayerRoom = newPlayerRoom;
	}

	public List<WorldPlayer> getApplyList() {
		return applyList;
	}

	public void setApplyList(List<WorldPlayer> applyList) {
		this.applyList = applyList;
	}

	public boolean getEventMode() {
		return eventMode;
	}

	public void setEventMode(boolean eventMode) {
		this.eventMode = eventMode;
	}

}