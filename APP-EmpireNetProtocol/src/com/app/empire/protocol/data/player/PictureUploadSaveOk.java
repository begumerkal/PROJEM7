package com.app.empire.protocol.data.player;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class PictureUploadSaveOk extends AbstractData{
	

	public PictureUploadSaveOk(int sessionId, int serial) {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PictureUploadSaveOk,
				sessionId, serial);
	}

	public PictureUploadSaveOk() {
		super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PictureUploadSaveOk);
	}

}
