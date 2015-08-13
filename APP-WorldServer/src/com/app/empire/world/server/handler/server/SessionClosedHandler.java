package com.app.empire.world.server.handler.server;

import org.apache.log4j.Logger;

import com.app.empire.protocol.data.server.SessionClosed;
import com.app.empire.world.model.Client;
import com.app.empire.world.session.ConnectSession;
import com.app.protocol.data.AbstractData;
import com.app.protocol.handler.IDataHandler;
/**
 * 用户下线
*/
public class SessionClosedHandler implements IDataHandler {
	Logger log;

	public SessionClosedHandler() {
		this.log = Logger.getLogger(SessionClosedHandler.class);
	}

	public AbstractData handle(AbstractData data) throws Exception {
		SessionClosed closed = (SessionClosed) data;
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		Client client = session.getClient(closed.getSession());
		if (client != null) {
			log.info("SessionClosed SessionId:" + client.getSessionId() + "----playerId:" + client.getPlayerId());
			session.removeClient(client);
			// ServiceManager.getManager().getAbstractService().removeAllAbstractInfoById(session.getSessionId()
			// + "-" + client.getSessionId());
		}
		return null;
	}
}