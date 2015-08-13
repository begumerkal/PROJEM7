package com.app.empire.protocol.data.room;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class UpdateRoom extends AbstractData {
    private int    roomId;
    private int    battleMode;
    private int    playerNumMode;
    private String passWord;
    private int    mapId;
    private int    wnersId;
    private int    startMode;
    private int    serviceMode;
    

    public UpdateRoom(int sessionId, int serial) {
        super(Protocol.MAIN_ROOM, Protocol.ROOM_UpdateRoom, sessionId, serial);
    }

    public UpdateRoom() {
        super(Protocol.MAIN_ROOM, Protocol.ROOM_UpdateRoom);
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

	public int getStartMode() {
		return startMode;
	}

	public void setStartMode(int startMode) {
		this.startMode = startMode;
	}

    public int getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(int serviceMode) {
        this.serviceMode = serviceMode;
    }
   
}
