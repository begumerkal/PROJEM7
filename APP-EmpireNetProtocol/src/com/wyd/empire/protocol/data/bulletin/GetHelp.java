package com.wyd.empire.protocol.data.bulletin;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 获取服务器公告
 * @see AbstractData
 * @author mazheng
 */
public class GetHelp extends AbstractData {
    public GetHelp(int sessionId, int serial) {
        super(Protocol.MAIN_BULLETIN, Protocol.BULLETIN_GetHelp, sessionId, serial);
    }

    public GetHelp() {
        super(Protocol.MAIN_BULLETIN, Protocol.BULLETIN_GetHelp);
    }
}
