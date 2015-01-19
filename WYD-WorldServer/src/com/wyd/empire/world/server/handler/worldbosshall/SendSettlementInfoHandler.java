package com.wyd.empire.world.server.handler.worldbosshall;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.worldbosshall.SendOpenState;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 返回世界BOSS开启状态，在开启中返回true
 * 
 * @author zengxc
 *
 */
public class SendSettlementInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(SendSettlementInfoHandler.class);

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		SendOpenState ok = new SendOpenState(data.getSessionId(), data.getSerial());
		ok.setState(ServiceManager.getManager().getWorldBossService().isInTime());
		session.write(ok);
	}
}
