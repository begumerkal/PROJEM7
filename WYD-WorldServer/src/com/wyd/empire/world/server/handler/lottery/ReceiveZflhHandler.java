package com.wyd.empire.world.server.handler.lottery;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.lottery.ReceiveZflh;
import com.wyd.empire.protocol.data.lottery.ReceiveZflhOk;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class ReceiveZflhHandler implements IDataHandler {
	Logger log = Logger.getLogger(ReceiveRewardHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		int girdId = ((ReceiveZflh) data).getId();
		try {
			ServiceManager.getManager().getLotteryService().playerGetGift(player, girdId);
			ReceiveZflhOk ok = new ReceiveZflhOk(data.getSessionId(), data.getSerial());
			session.write(ok);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TASK_GETEVERY_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
