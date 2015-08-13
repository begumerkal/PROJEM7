package com.app.empire.protocol.data.room;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class EnterRoomOk extends AbstractData {
	private int       roomId;
	private int       battleMode;
	private int       roomChannel;
	private int       playerNumMode;
	private int       mapId;
	private int       wnersId;
	private int       startMode;
	private int       playerNum;
	private boolean[] seatUsed;
	private int[]     playerId;
	private String[]  playerName;
	private int[]     playerLevel;
	private boolean[] playerReady;
	private int[]     playerSex;
	private String[]  playerEquipment;
	private int[]     playerProficiency;
	private int[]     playerEquipmentLevel;
    private int[]     vipLevel;
    private String[]  playerWing;
    private String[]  player_title;
    private int[]     qualifyingLevel;//排位等级
    private int[]	  skillType;
    private int[]	  skillLevel;
    private int[]     zsleve;
    private boolean[] doubleMark;
    private boolean[] isvip;
    private int       serviceMode;    
    private boolean   eventMode; //特殊事件模式（true开启）

	public EnterRoomOk(int sessionId, int serial) {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_EnterRoomOk, sessionId, serial);
	}

	public EnterRoomOk() {
		super(Protocol.MAIN_ROOM, Protocol.ROOM_EnterRoomOk);
	}
	
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public int getBattleMode() {
		return battleMode;
	}

	public void setBattleMode(int battleMode) {
		this.battleMode = battleMode;
	}

	public int getRoomChannel() {
		return roomChannel;
	}

	public void setRoomChannel(int roomChannel) {
		this.roomChannel = roomChannel;
	}

	public boolean[] getSeatUsed() {
		return seatUsed;
	}

	public void setSeatUsed(boolean[] seatUsed) {
		this.seatUsed = seatUsed;
	}

	public int getPlayerNumMode() {
		return playerNumMode;
	}

	public void setPlayerNumMode(int playerNumMode) {
		this.playerNumMode = playerNumMode;
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

	public int getStartMode() {
		return startMode;
	}

	public void setStartMode(int startMode) {
		this.startMode = startMode;
	}
	
	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public int[] getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int[] playerId) {
		this.playerId = playerId;
	}

	public String[] getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String[] playerName) {
		this.playerName = playerName;
	}

	public int[] getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(int[] playerLevel) {
		this.playerLevel = playerLevel;
	}

	public boolean[] getPlayerReady() {
		return playerReady;
	}

	public void setPlayerReady(boolean[] playerReady) {
		this.playerReady = playerReady;
	}

	public int[] getPlayerSex() {
		return playerSex;
	}

	public void setPlayerSex(int[] playerSex) {
		this.playerSex = playerSex;
	}

	public String[] getPlayerEquipment() {
		return playerEquipment;
	}

	public void setPlayerEquipment(String[] playerEquipment) {
		this.playerEquipment = playerEquipment;
	}

	public int[] getPlayerProficiency() {
		return playerProficiency;
	}

	public void setPlayerProficiency(int[] playerProficiency) {
		this.playerProficiency = playerProficiency;
	}

	public int[] getPlayerEquipmentLevel() {
		return playerEquipmentLevel;
	}

	public void setPlayerEquipmentLevel(int[] playerEquipmentLevel) {
		this.playerEquipmentLevel = playerEquipmentLevel;
	}

    public int[] getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int[] vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String[] getPlayerWing() {
        return playerWing;
    }

    public void setPlayerWing(String[] playerWing) {
        this.playerWing = playerWing;
    }

    public String[] getPlayer_title() {
        return player_title;
    }

    public void setPlayer_title(String[] player_title) {
        this.player_title = player_title;
    }

	public int[] getQualifyingLevel() {
		return qualifyingLevel;
	}

	public void setQualifyingLevel(int[] qualifyingLevel) {
		this.qualifyingLevel = qualifyingLevel;
	}

	public int[] getSkillType() {
		return skillType;
	}

	public void setSkillType(int[] skillType) {
		this.skillType = skillType;
	}

	public int[] getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int[] skillLevel) {
		this.skillLevel = skillLevel;
	}

    public int[] getZsleve() {
        return zsleve;
    }

    public void setZsleve(int[] zsleve) {
        this.zsleve = zsleve;
    }

	public boolean[] getDoubleMark() {
		return doubleMark;
	}

	public void setDoubleMark(boolean[] doubleMark) {
		this.doubleMark = doubleMark;
	}

    public boolean[] getIsvip() {
        return isvip;
    }

    public void setIsvip(boolean[] isvip) {
        this.isvip = isvip;
    }

    public int getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(int serviceMode) {
        this.serviceMode = serviceMode;
    }

	public boolean getEventMode() {
		return eventMode;
	}

	public void setEventMode(boolean eventMode) {
		this.eventMode = eventMode;
	}
}
