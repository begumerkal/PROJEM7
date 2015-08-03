package com.wyd.empire.world.server.handler.error;

import com.wyd.empire.protocol.data.error.ProtocolError;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.request.SessionRequest;
import com.wyd.empire.world.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.net.IRequest;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

public class ProtocolErrorHandler implements IDataHandler {
	public AbstractData handle(AbstractData message) throws Exception {
		ProtocolError msg = (ProtocolError) message;
		IRequest request = ServiceManager.getManager().getRequestService().remove(msg.getSerial());
		if (request == null) {
			return null;
		}
		ConnectSession session = ((SessionRequest) request).getConnectionSession();
		msg.setSerial(request.getId());
		msg.setCause(ErrorMessages.getErrorMesssage(msg.getCode()));
		msg.setSessionId(((SessionRequest) request).getSessionId());
		if (session != null)
			session.write(msg);
		return null;
	}
}