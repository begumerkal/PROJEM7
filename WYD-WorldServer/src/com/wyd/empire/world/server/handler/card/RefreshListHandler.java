package com.wyd.empire.world.server.handler.card;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.card.ExchangeList;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.handler.bulletin.GetAboutHandler;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.net.ProtocolFactory;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class RefreshListHandler implements IDataHandler {
	private Logger log;

	public RefreshListHandler() {
		this.log = Logger.getLogger(GetAboutHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			int pay = ServiceManager.getManager().getVersionService().getConfig("refCardDiam", 50);
			// 支付
			if (player.getDiamond() < pay) {
				throw new ProtocolException(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			ServiceManager.getManager().getPlayerService().useTicket(player, pay, TradeService.ORIGIN_RECARDEXCHANGE, null, null, "");
			player.reExchangeCardList();
			IDataHandler exchangeListHandler = ProtocolFactory.getDataHandler(ExchangeList.class);
			exchangeListHandler.handle(data);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

}