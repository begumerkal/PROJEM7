package com.wyd.empire.protocol.data.square;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
public class GetMhUrl extends AbstractData {
    public GetMhUrl(int sessionId, int serial) {
        super(Protocol.MAIN_SQUARE, Protocol.SQUARE_GetMhUrl, sessionId, serial);
    }

    public GetMhUrl() {
        super(Protocol.MAIN_SQUARE, Protocol.SQUARE_GetMhUrl);
    }
}
