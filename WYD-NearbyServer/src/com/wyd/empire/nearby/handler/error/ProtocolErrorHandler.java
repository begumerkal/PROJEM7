package com.wyd.empire.nearby.handler.error;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
public class ProtocolErrorHandler implements IDataHandler {
    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        session.send(data);
    }
}
