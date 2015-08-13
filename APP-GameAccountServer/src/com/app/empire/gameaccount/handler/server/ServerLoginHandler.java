package com.app.empire.gameaccount.handler.server;
import org.apache.log4j.Logger;

import com.app.empire.gameaccount.service.factory.ClientDetail;
import com.app.empire.gameaccount.service.factory.ServiceFactory;
import com.app.empire.gameaccount.session.AcceptSession;
import com.app.empire.protocol.data.server.ServerLogin;
import com.app.empire.protocol.data.server.ServerLoginOk;
import com.app.net.IConnector;
import com.app.protocol.data.AbstractData;
import com.app.protocol.handler.IDataHandler;
public class ServerLoginHandler implements IDataHandler {
    private static final Logger log = Logger.getLogger(ServerLoginHandler.class);

    public AbstractData handle(AbstractData message) throws Exception {
//        ServerLogin msg = (ServerLogin) message;
//        ClientDetail cd = ServiceFactory.getFactory().getClientListManager().getClientDetail(msg.getId());
//        if ((cd != null) && (cd.getPassWord().equals(msg.getPassword()))) {
//            AcceptSession session = (AcceptSession) message.getSource();
//            IConnector oldSession = ServiceFactory.getFactory().getSessionService().getSession(msg.getId());
//            if (oldSession != null) {
//                message.getSource().close();
//                log.info("ClientServer[" + session.getRemoteAddress() + "][" + msg.getId() + "] already exists.");
//                return null;
//            }
//            session.setId(msg.getId());
//            ServiceFactory.getFactory().getSessionService().addSession(session);
//            ServerLoginOk okMessage = new ServerLoginOk(-1, msg.getSerial());
//            session.send(okMessage);
//            log.info("ClientServer[" + session.getRemoteAddress() + "][" + msg.getId() + "]Connected");
//        } else {
//            log.info("ClientServer[" + message.getSource().getRemoteAddress() + "]LoginError");
//            message.getSource().close();
//        }
        
        return null;
    }
}
