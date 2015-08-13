package com.app.empire.protocol.data.player;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class GetPlayerInfoNovice extends AbstractData {  

    public GetPlayerInfoNovice(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPlayerInfoNovice, sessionId, serial);
    }

    public GetPlayerInfoNovice() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPlayerInfoNovice);
    }

   

}
