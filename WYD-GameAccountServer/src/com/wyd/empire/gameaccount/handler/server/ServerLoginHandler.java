package com.wyd.empire.gameaccount.handler.server;
import org.apache.log4j.Logger;

import com.wyd.empire.gameaccount.service.factory.ClientDetail;
import com.wyd.empire.gameaccount.service.factory.ServiceFactory;
import com.wyd.empire.gameaccount.session.AcceptSession;
import com.wyd.empire.protocol.data.server.ServerLogin;
import com.wyd.empire.protocol.data.server.ServerLoginOk;
import com.wyd.net.IConnector;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
public class ServerLoginHandler implements IDataHandler {
    private static final Logger log = Logger.getLogger(ServerLoginHandler.class);

    public void handle(AbstractData message) throws Exception {
        ServerLogin msg = (ServerLogin) message;
        ClientDetail cd = ServiceFactory.getFactory().getClientListManager().getClientDetail(msg.getId());
        if ((cd != null) && (cd.getPassWord().equals(msg.getPassword()))) {
            AcceptSession session = (AcceptSession) message.getSource();
            IConnector oldSession = ServiceFactory.getFactory().getSessionService().getSession(msg.getId());
            if (oldSession != null) {
                message.getSource().close();
                log.info("ClientServer[" + session.getRemoteAddress() + "][" + msg.getId() + "] already exists.");
                return;
            }
            session.setId(msg.getId());
            ServiceFactory.getFactory().getSessionService().addSession(session);
            ServerLoginOk okMessage = new ServerLoginOk(-1, msg.getSerial());
            session.send(okMessage);
            log.info("ClientServer[" + session.getRemoteAddress() + "][" + msg.getId() + "]Connected");
        } else {
            log.info("ClientServer[" + message.getSource().getRemoteAddress() + "]LoginError");
            message.getSource().close();
        }
    }
}
