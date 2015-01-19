package com.wyd.empire.world.server.handler.card;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.card.ExchangeListOk;
import com.wyd.empire.world.bean.ExchangeCard;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.handler.bulletin.GetAboutHandler;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 卡牌兑换列表
 * 
 * @author zengxc
 * 
 */
public class ExchangeListHandler implements IDataHandler {
	private Logger log;

	public ExchangeListHandler() {
		this.log = Logger.getLogger(GetAboutHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			List<ExchangeCard> cardList = player.getExchangeCardList();
			int size = cardList.size();
			int[] id = new int[size];
			int[] exchangePrice = new int[size];
			int[] exchangeNum = new int[size];
			String[] groupAddition = new String[size];
			int[] itemNum = new int[size];
			Calendar cal = Calendar.getInstance();
			long lastTimel = (player.getExchangeCardTime() - cal.getTime().getTime()) / 1000;
			int lastTime = (int) lastTimel;
			for (int i = 0; i < size; i++) {
				ExchangeCard card = cardList.get(i);
				id[i] = card.getCardId();
				exchangePrice[i] = card.getPrice();
				exchangeNum[i] = card.getMaxNum() - card.getUseNum();
				ShopItem cardItem = ServiceManager.getManager().getShopItemService().getShopItemById(card.getCardId());
				groupAddition[i] = ServiceManager.getManager().getPlayerCardsService().getGroupAddition(cardItem.getTkId());
				itemNum[i] = 1;
			}
			ExchangeListOk ok = new ExchangeListOk(data.getSessionId(), data.getSerial());
			ok.setId(id);
			ok.setExchangePrice(exchangePrice);
			ok.setLastTime(lastTime);
			ok.setExchangeNum(exchangeNum);
			ok.setGroupAddition(groupAddition);
			ok.setRefreshPrice(ServiceManager.getManager().getVersionService().getConfig("refCardDiam", 50));
			ok.setItemNum(itemNum);
			session.write(ok);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

}
