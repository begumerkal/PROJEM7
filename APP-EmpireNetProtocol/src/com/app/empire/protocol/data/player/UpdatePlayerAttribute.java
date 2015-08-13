package com.app.empire.protocol.data.player;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;

public class UpdatePlayerAttribute extends AbstractData {

	private String attributeInfo;
	
    public String getAttributeInfo() {
        return attributeInfo;
    }

    public void setAttributeInfo(String attributeInfo) {
        this.attributeInfo = attributeInfo;
    }

    public UpdatePlayerAttribute() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_UpdatePlayerAttribute);
    }
    
    public UpdatePlayerAttribute(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_UpdatePlayerAttribute, sessionId, serial);
    }

}
