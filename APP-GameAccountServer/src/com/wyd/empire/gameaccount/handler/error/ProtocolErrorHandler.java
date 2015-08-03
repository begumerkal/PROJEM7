package com.wyd.empire.gameaccount.handler.error;
import com.wyd.empire.gameaccount.session.AcceptSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
public class ProtocolErrorHandler implements IDataHandler {
    public AbstractData handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        session.send(data);
        return null;
    }
}
