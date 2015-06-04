package com.wyd.empire.world.server.handler.system;

import com.wyd.empire.protocol.data.system.GetSystemInfoOk;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

public class GetSystemInfoHandler implements IDataHandler {
	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetSystemInfoOk getSystemInfoOk = new GetSystemInfoOk(data.getSessionId(), data.getSerial());
		getSystemInfoOk.setRechargeUrl(ServiceManager.getManager().getConfiguration().getString("rechargeurl"));
		session.write(getSystemInfoOk);
		return null;
	}
}
