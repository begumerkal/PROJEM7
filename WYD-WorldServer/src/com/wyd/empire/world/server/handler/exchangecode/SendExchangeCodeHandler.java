package com.wyd.empire.world.server.handler.exchangecode;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.exchangecode.SendExchangeCode;
import com.wyd.empire.protocol.data.exchangecode.SendExchangeCodeOk;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.ExchangeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 
 * @author Administrator
 *
 */
public class SendExchangeCodeHandler implements IDataHandler {
	Logger log = Logger.getLogger(SendExchangeCodeHandler.class);

	// 发送礼包兑换码
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SendExchangeCode sendExchangeCode = (SendExchangeCode) data;
		SendExchangeCodeOk sendExchangeCodeOk = new SendExchangeCodeOk(data.getSessionId(), data.getSerial());
		try {
			ServiceManager
					.getManager()
					.getExchangeService()
					.addExchangeInfo(player.getId(), sendExchangeCode.getMessage(), ExchangeService.TYPE1, player.getLevel(),
							player.getClient().getChannel());
			sendExchangeCodeOk.setCode(TipMessages.EXCHANGESUCCESS);
		} catch (Exception ex) {
			sendExchangeCodeOk.setCode(TipMessages.EXCHANGEFAIL);
			log.error(ex, ex);
		}
		session.write(sendExchangeCodeOk);
	}
}
