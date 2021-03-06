package com.wyd.empire.nearby.handler.server;
import org.apache.log4j.Logger;

import com.wyd.empire.nearby.service.factory.ClientDetail;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.server.ServerLogin;
import com.wyd.empire.protocol.data.server.ServerLoginOk;
import com.wyd.net.ISession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
public class ServerLoginHandler implements IDataHandler {
    private static final Logger log = Logger.getLogger(ServerLoginHandler.class);

    public void handle(AbstractData message) throws Exception {
        ServerLogin msg = (ServerLogin) message;
        ClientDetail cd = ServiceManager.getManager().getClientListManager().getClientDetail(msg.getId());
        if ((cd != null) && (cd.getPassWord().equals(msg.getPassword()))) {
            AcceptSession session = (AcceptSession) message.getSource();
            ISession oldSession = ServiceManager.getManager().getSessionService().getSession(msg.getId());
            if (oldSession != null) {
                message.getSource().close();
                log.info("ClientServer[" + session.getRemoteAddress() + "][" + msg.getId() + "] already exists.");
                return;
            }
            session.setId(msg.getId());
            ServiceManager.getManager().getSessionService().addSession(session);
            ServerLoginOk okMessage = new ServerLoginOk(-1, msg.getSerial());
            session.send(okMessage);
            log.info("ClientServer[" + session.getRemoteAddress() + "][" + msg.getId() + "]Connected");
        } else {
            log.info("ClientServer[" + message.getSource().getRemoteAddress() + "]LoginError");
            message.getSource().close();
        }
    }
}
