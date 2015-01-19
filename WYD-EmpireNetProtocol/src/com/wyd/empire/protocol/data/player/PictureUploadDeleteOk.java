package com.wyd.empire.protocol.data.player;

import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;

public class PictureUploadDeleteOk extends AbstractData{
	
	//private int pDeleteFlag;
	
	public PictureUploadDeleteOk(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PictureUploadDeleteOk, sessionId, serial);
    }

    public PictureUploadDeleteOk() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_PictureUploadDeleteOk);
    }

//	public int getpDeleteFlag() {
//		return pDeleteFlag;
//	}
//
//	public void setpDeleteFlag(int pDeleteFlag) {
//		this.pDeleteFlag = pDeleteFlag;
//	}
    
    

}
