package com.wyd.empire.world.server.handler.card;

import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.card.Exchange;
import com.wyd.empire.protocol.data.card.ExchangeOk;
import com.wyd.empire.world.bean.ExchangeCard;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.handler.bulletin.GetAboutHandler;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class ExchangeHandler implements IDataHandler {
	private Logger log;

	public ExchangeHandler() {
		this.log = Logger.getLogger(GetAboutHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		Exchange exchange = (Exchange) data;
		int cardId = exchange.getId();
		try {
			ExchangeCard exCard = getExCard(player, cardId);
			if (exCard == null) {
				throw new Exception("兑换的卡，不在兑换列表里");
			}
			if (!exCard.canExchange()) {
				throw new Exception("兑换次数已用完");
			}
			int price = exCard.getPrice();

			// 扣除熔炼碎片
			PlayerItemsFromShop meltItem = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), Common.MELTITEMID);
			if (meltItem == null || price > meltItem.getPLastNum()) {
				throw new Exception("熔炼碎片不够 ");
			}
			// 记录兑换次数
			exCard.addUseNum(1);
			meltItem.setPLastNum(meltItem.getPLastNum() - price);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(meltItem);
			ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, meltItem);
			// 发卡给玩家
			ServiceManager.getManager().getPlayerItemsFromShopService().playerGetItem(player.getId(), cardId, 1, 35, null, 0, 0, 0);
			ShopItem card = ServiceManager.getManager().getShopItemService().getShopItemById(cardId);
			ExchangeOk ok = new ExchangeOk(data.getSessionId(), data.getSerial());
			session.write(ok);
			ServiceManager.getManager().getTaskService().dhkp(player, card.getLevel());
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

	private ExchangeCard getExCard(WorldPlayer player, int cardId) {
		List<ExchangeCard> exchangeCardList = player.getExchangeCardList();
		for (ExchangeCard excard : exchangeCardList) {
			if (excard.getCardId() == cardId)
				return excard;
		}
		return null;
	}

}
