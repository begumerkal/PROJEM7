package com.wyd.empire.world.server.handler.exchange;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.exchange.RequestExchange;
import com.wyd.empire.protocol.data.exchange.ResponseExchange;
import com.wyd.empire.world.bean.Exchange;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> RequestExchangeHandler</code>
 * Protocol.EXCHANGE_RequestExchangeHandler读取兑换商品协议处理
 * 
 * @since JDK 1.6
 */
public class RequestExchangeHandler implements IDataHandler {
	Logger log = Logger.getLogger(RequestExchangeHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		RequestExchange requestExchange = (RequestExchange) data;
		ResponseExchange responseExchange = new ResponseExchange(data.getSessionId(), data.getSerial());
		try {
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService().getBadgeByPlayerId(player.getId());
			Exchange exchange = ServiceManager.getManager().getiShopItemsPriceService()
					.getShopItemsExchangeNum(requestExchange.getItemId(), player.getId());
			if (exchange == null) {
				throw new NullPointerException();// 传过来参数不正确，找不到相应对象
			}
			// 校验代币是否足够
			if (player.getMedalNum() < exchange.getCostUseBadge() || null == pifs || pifs.getPLastNum() < exchange.getCostUseBadge()) {
				responseExchange.setCode(1);
				responseExchange.setMessage(ErrorMessages.TRATE_BADGEENOUGH_MESSAGE);
				session.write(responseExchange);
			} else {
				int useDiamond = 0, useGold = 0, useBadge = 0;
				useBadge = exchange.getCostUseBadge();
				pifs.setPLastNum(pifs.getPLastNum() - useBadge);
				ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
				// 更新玩家拥有的物品
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pifs);
				ServiceManager.getManager().getPlayerItemsFromShopService()
						.saveGetItemRecord(player.getPlayer().getId(), Common.BADGEID, -1, exchange.getCostUseBadge(), -4, 1, null);
				player.setMedalNum(pifs.getPLastNum());
				ServiceManager.getManager().getTaskService().useSomething(player, pifs.getShopItem().getId(), 1);
				PlayerItemsFromShop playerItem = ServiceManager
						.getManager()
						.getPlayerItemsFromShopService()
						.playerGetItem(player.getId(), requestExchange.getItemId(), -1, exchange.getDays(), exchange.getCount(), 1, null,
								useDiamond, useGold, useBadge);
				responseExchange.setCode(0);
				responseExchange.setMessage(ErrorMessages.TRATE_EXCHANGESUCCESS_MESSAGE);
				// 将物品装备上
				ShopItem item = exchange.getShopItem();
				if (item.isEquipment()) {
					ServiceManager.getManager().getPlayerService().changeEquipment(player, playerItem);
				}
			}
			session.write(responseExchange);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TRATE_EXCHANGEFAILD_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}