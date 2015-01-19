package com.wyd.empire.world.server.handler.card;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.card.MergeCard;
import com.wyd.empire.protocol.data.card.MergeCardOk;
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
 * 碎片合成卡牌
 * 
 * @author zengxc
 * 
 */
public class MergeCardHandler implements IDataHandler {
	private Logger log;

	public MergeCardHandler() {
		this.log = Logger.getLogger(GetAboutHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		MergeCard mergeCard = (MergeCard) data;
		int debrisId = mergeCard.getId();
		int cardId = -1;
		try {
			PlayerItemsFromShop playerDebris = ServiceManager.getManager().getPlayerItemsFromShopService()
					.getPlayerItemById(player.getId(), debrisId);
			if (null == playerDebris || playerDebris.getPLastNum() < 1) {
				throw new ProtocolException(ErrorMessages.PLAYER_TAKEONOVERTIME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			int playerHasCount = ServiceManager.getManager().getPlayerItemsFromShopService().getPlayerCardCount(player.getId());
			// 卡牌栏位与玩家等级挂钩
			if (player.getLevel() <= playerHasCount) {
				throw new Exception("非法操作，卡牌栏位已满");
			}
			ShopItem debris = playerDebris.getShopItem();
			if (debris.getType() == Common.SHOP_ITEM_TYPE_ARMS_CARDDEBRIS) {
				List<PlayerItemsFromShop> debrisList = ServiceManager.getManager().getPlayerItemsFromShopService()
						.getPlayerDebrisByLevel(player.getId(), debris.getLevel());
				int debrisNum = 0;
				for (PlayerItemsFromShop playerDb : debrisList) {
					debrisNum += playerDb.getPLastNum();
				}
				// 判断够不够合成数量
				int hcsl = Integer.parseInt(debris.getStrengthen().split("=")[1]);
				if (debrisNum < hcsl) {
					throw new Exception("非法操作，数量不够");
				}
				// 扣除物品 规则 ：每一种都先扣完再扣下一种
				int useNum = 0;
				for (PlayerItemsFromShop playerDb : debrisList) {
					int lastNum = playerDb.getPLastNum();
					int needNum = hcsl - useNum;
					if (needNum >= lastNum) {
						playerDb.setPLastNum(0);
						useNum += lastNum;
						useItem(player, playerDb);
					} else {
						playerDb.setPLastNum(lastNum - needNum);
						useItem(player, playerDb);
						break;
					}
				}
				// 抽取卡牌
				cardId = ServiceManager.getManager().getPlayerCardsService().debrisMergeCard(debris.getLevel());
				// 发给玩家
				ServiceManager.getManager().getPlayerItemsFromShopService().playerGetItem(player.getId(), cardId, 1, 31, null, 0, 0, 0);
			}
			MergeCardOk ok = new MergeCardOk(data.getSessionId(), data.getSerial());
			ok.setId(cardId);
			session.write(ok);
		} catch (ProtocolException ex) {
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

	private void useItem(WorldPlayer player, PlayerItemsFromShop playerDb) {
		ServiceManager.getManager().getPlayerItemsFromShopService().update(playerDb);
		ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, playerDb);
	}
}
