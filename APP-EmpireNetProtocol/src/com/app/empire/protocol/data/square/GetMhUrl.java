package com.app.empire.protocol.data.square;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class GetMhUrl extends AbstractData {
    public GetMhUrl(int sessionId, int serial) {
        super(Protocol.MAIN_SQUARE, Protocol.SQUARE_GetMhUrl, sessionId, serial);
    }

    public GetMhUrl() {
        super(Protocol.MAIN_SQUARE, Protocol.SQUARE_GetMhUrl);
    }
}
