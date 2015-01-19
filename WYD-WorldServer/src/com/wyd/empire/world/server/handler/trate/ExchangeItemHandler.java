package com.wyd.empire.world.server.handler.trate;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.trate.ExchangeItem;
import com.wyd.empire.protocol.data.trate.ExchangeOk;
import com.wyd.empire.world.bean.Exchange;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> ExchangeItemHandler</code>
 * Protocol.TRATE_ExchangeItemHandler读取兑换商品协议处理
 * 
 * @since JDK 1.6
 */
public class ExchangeItemHandler implements IDataHandler {
	Logger log = Logger.getLogger(ExchangeItemHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		ExchangeItem exchangeItem = (ExchangeItem) data;
		ExchangeOk exchangeOk = new ExchangeOk(data.getSessionId(), data.getSerial());
		boolean mark = false;
		try {
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService().getBadgeByPlayerId(player.getId());
			if (pifs == null) {
				exchangeOk.setBuyResult(false);
				exchangeOk.setContent(ErrorMessages.TRATE_BADGEENOUGH_MESSAGE);
				exchangeOk.setCostTicks(0);
				exchangeOk.setCostGold(0);
				exchangeOk.setCostMedal(0);
				session.write(exchangeOk);
			} else {
				for (int i = 0; i < exchangeItem.getItemId().length; i++) {
					Exchange exchange = ServiceManager.getManager().getiShopItemsPriceService()
							.getShopItemsExchangeNum(exchangeItem.getItemId()[i], 0);
					if (exchange == null) {
						throw new NullPointerException();// 传过来参数不正确，找不到相应对象
					}
					if (pifs.getPLastNum() < exchange.getCostUseBadge()) {
						exchangeOk.setBuyResult(false);
						exchangeOk.setContent(ErrorMessages.TRATE_BADGEENOUGH_MESSAGE);
						exchangeOk.setCostTicks(0);
						exchangeOk.setCostGold(0);
						exchangeOk.setCostMedal(0);
						session.write(exchangeOk);
					} else {
						PlayerItemsFromShop playerItem = ServiceManager
								.getManager()
								.getPlayerItemsFromShopService()
								.playerGetItem(player.getId(), exchangeItem.getItemId()[i], -1, exchange.getDays(), exchange.getCount(), 1,
										null, 0, 0, exchange.getCostUseBadge());
						pifs.setPLastNum(pifs.getPLastNum() - exchange.getCostUseBadge());
						ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
						player.setMedalNum(pifs.getPLastNum());
						exchangeOk.setBuyResult(true);
						exchangeOk.setContent(ErrorMessages.TRATE_EXCHANGESUCCESS_MESSAGE);
						exchangeOk.setCostTicks(0);
						exchangeOk.setCostGold(0);
						exchangeOk.setCostMedal(pifs.getPLastNum());
						session.write(exchangeOk);
						ServiceManager.getManager().getTaskService().useSomething(player, pifs.getShopItem().getId(), 1);
						ServiceManager.getManager().getTitleService().useSomething(player, pifs.getShopItem().getId());
						// 将购买物品装备上
						if (playerItem.getShopItem().isEquipment()) {
							ServiceManager.getManager().getPlayerService().changeEquipment(player, playerItem);
						}
					}
				}
			}
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				throw new ProtocolException(ErrorMessages.TRATE_EXCHANGEFAILD_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
		}
	}
}