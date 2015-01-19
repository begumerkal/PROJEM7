package com.wyd.empire.protocol.data.player;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetPlayerInfoNovice extends AbstractData {  

    public GetPlayerInfoNovice(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPlayerInfoNovice, sessionId, serial);
    }

    public GetPlayerInfoNovice() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPlayerInfoNovice);
    }

   

}
