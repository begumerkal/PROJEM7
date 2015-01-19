package com.wyd.empire.world.server.handler.card;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.card.GetPlayerCards;
import com.wyd.empire.protocol.data.card.GetPlayerCardsOk;
import com.wyd.empire.world.bean.PlayerCards;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class GetPlayerCardsHandler implements IDataHandler {
	private Logger log;

	public GetPlayerCardsHandler() {
		this.log = Logger.getLogger(GetPlayerCardsHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		int playerId = ((GetPlayerCards) data).getPlayerId();
		try {
			PlayerCards cards = ServiceManager.getManager().getPlayerCardsService().getByPlayerId(playerId);
			if (cards != null) {
				List<PlayerItemsFromShop> cardList = new ArrayList<PlayerItemsFromShop>();
				if (cards.getJk() != null) {
					cardList.add(cards.getJk());
				}
				if (cards.getMk() != null) {
					cardList.add(cards.getMk());
				}
				if (cards.getSk() != null) {
					cardList.add(cards.getSk());
				}
				if (cards.getHk() != null) {
					cardList.add(cards.getHk());
				}
				if (cards.getTk() != null) {
					cardList.add(cards.getTk());
				}

				int size = cardList.size(), i = 0;
				int[] cardId = new int[size];
				String[] info = new String[size];
				String[] groupAddition = new String[size];
				for (PlayerItemsFromShop card : cardList) {
					cardId[i] = card.getShopItem().getId();
					info[i] = "{}";
					groupAddition[i] = ServiceManager.getManager().getPlayerCardsService().getGroupAddition(card.getShopItem().getTkId());
					i++;
				}
				GetPlayerCardsOk ok = new GetPlayerCardsOk(data.getSessionId(), data.getSerial());
				ok.setCardId(cardId);
				ok.setData(info);
				ok.setGroupAddition(groupAddition);
				session.write(ok);
			} else {
				int[] cardId = new int[0];
				String[] info = new String[0];
				String[] groupAddition = new String[0];
				GetPlayerCardsOk ok = new GetPlayerCardsOk(data.getSessionId(), data.getSerial());
				ok.setCardId(cardId);
				ok.setData(info);
				ok.setGroupAddition(groupAddition);
				session.write(ok);
			}

		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

}
