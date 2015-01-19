package com.wyd.empire.world.server.handler.card;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.card.MeltCard;
import com.wyd.empire.protocol.data.card.MeltCardOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
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
 * 卡牌熔炼成碎片
 * 
 * @author zengxc
 * 
 */
public class MeltCardHandler implements IDataHandler {
	private Logger log;

	public MeltCardHandler() {
		this.log = Logger.getLogger(GetAboutHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		MeltCard card = (MeltCard) data;
		int playerCardId = card.getId();
		try {
			PlayerItemsFromShop playerCard = ServiceManager.getManager().getPlayerItemsFromShopService()
					.getPlayerItemById(player.getId(), playerCardId);
			if (null == playerCard || playerCard.getPLastNum() < 1 || playerCard.getIsInUsed()) {
				throw new ProtocolException(ErrorMessages.PLAYER_TAKEONOVERTIME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			int meltNum = ServiceManager.getManager().getPlayerCardsService().getMeltNum(playerCard.getShopItem().getId());
			// 发物品给玩家
			ServiceManager.getManager().getPlayerItemsFromShopService()
					.playerGetItem(player.getId(), Common.MELTITEMID, meltNum, 32, null, 0, 0, 0);
			int lastNum = playerCard.getPLastNum();
			playerCard.setPLastNum(lastNum - 1);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(playerCard);
			ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, playerCard);
			MeltCardOk ok = new MeltCardOk(data.getSessionId(), data.getSerial());
			session.write(ok);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

}
