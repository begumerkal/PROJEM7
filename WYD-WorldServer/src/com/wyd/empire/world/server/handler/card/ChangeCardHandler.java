package com.wyd.empire.world.server.handler.card;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.card.ChangeCard;
import com.wyd.empire.protocol.data.card.ChangeCardOk;
import com.wyd.empire.world.bean.PlayerCards;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.handler.bulletin.GetAboutHandler;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 拆/装卡牌
 * 
 * @author zengxc
 * 
 */
public class ChangeCardHandler implements IDataHandler {
	private Logger log;

	public ChangeCardHandler() {
		this.log = Logger.getLogger(GetAboutHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		ChangeCard changeCard = (ChangeCard) data;
		int playerCardId = changeCard.getId();
		try {
			//
			PlayerCards playerCards = player.getPlayerCards();
			playerCards = playerCards == null ? new PlayerCards() : playerCards;
			PlayerItemsFromShop newCard = ServiceManager.getManager().getPlayerItemsFromShopService()
					.getPlayerItemById(player.getId(), playerCardId);
			if (null == newCard || newCard.getPLastNum() < 1) {
				throw new ProtocolException(ErrorMessages.PLAYER_TAKEONOVERTIME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			ShopItem card = newCard.getShopItem();
			PlayerItemsFromShop oldCard = null;
			if (card.getType() == Common.SHOP_ITEM_TYPE_ARMS_CARD) {
				int subtype = card.getSubtype();
				// 如果卡已使用有这张卡则表示拆卡
				if (newCard.getIsInUsed()) {
					newCard = null;
				}
				switch (subtype) {
					case 1 :
						oldCard = playerCards.getJk() == null ? null : playerCards.getJk();
						playerCards.setJk(newCard);
						break;
					case 2 :
						oldCard = playerCards.getMk() == null ? null : playerCards.getMk();
						playerCards.setMk(newCard);
						break;
					case 3 :
						oldCard = playerCards.getSk() == null ? null : playerCards.getSk();
						playerCards.setSk(newCard);
						break;
					case 4 :
						oldCard = playerCards.getHk() == null ? null : playerCards.getHk();
						playerCards.setHk(newCard);
						break;
					case 5 :
						oldCard = playerCards.getTk() == null ? null : playerCards.getTk();
						playerCards.setTk(newCard);
						break;

				}
				player.updateFight();
				try {
					ServiceManager.getManager().getPlayerItemsFromShopService().changeCards(player, oldCard, newCard);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			//
			ChangeCardOk ok = new ChangeCardOk(data.getSessionId(), data.getSerial());
			session.write(ok);
		} catch (ProtocolException ex) {
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		} catch (Exception ex) {
			log.error(ex, ex);
			ex.printStackTrace();
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

}
