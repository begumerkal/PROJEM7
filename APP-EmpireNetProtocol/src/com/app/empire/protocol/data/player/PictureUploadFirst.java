package com.app.empire.protocol.data.player;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class PictureUploadFirst extends AbstractData{
	private int playerId;
	
	
	

	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public PictureUploadFirst(int sessionId, int serial) {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PictureUploadFirst, sessionId, serial);
	}
	public PictureUploadFirst() {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PictureUploadFirst);
	}

}
