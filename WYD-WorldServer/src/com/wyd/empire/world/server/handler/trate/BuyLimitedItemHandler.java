package com.wyd.empire.world.server.handler.trate;

import org.apache.log4j.Logger;

import com.wyd.empire.world.bean.LimitedPrice;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.empire.protocol.data.trate.BuyLimitedItem;
import com.wyd.empire.protocol.data.trate.BuyLimitedItemOk;

/**
 * 购买限量物品，物品每天有一个最大购买次数。最大购买次数随VIP等级而变化。每次购买花费与得到的数量并不一样
 * 
 * @author zengxc
 *
 */
public class BuyLimitedItemHandler implements IDataHandler {
	Logger log = Logger.getLogger(BuyLimitedItemHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		ServiceManager manager = ServiceManager.getManager();
		int itemId = ((BuyLimitedItem) data).getItemId();
		try {
			int vipLevel = player.isVip() ? player.getPlayer().getVipLevel() : 0;
			Integer count = player.getBuyLimitedCount().get(itemId);
			count = count == null ? 0 : count;
			int lastNum = manager.getLimitedPriceService().getMaxCount(itemId, vipLevel) - count;
			if (lastNum < 1) {
				return;
			}
			// 根据次数去获得配置
			LimitedPrice limitedPrice = manager.getLimitedPriceService().getPriceByCount(itemId, count + 1);
			if (limitedPrice == null) {
				return;
			}
			int diamondNum = limitedPrice.getPrice();
			if (player.getDiamond() < diamondNum) {
				throw new ProtocolException(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE, data.getSessionId(), data.getType(),
						data.getSubType());
			}
			int origin = TradeService.ORIGIN_BADGEID;
			if (itemId == Common.GOLDID) {
				origin = TradeService.ORIGIN_GOLD;
			} else if (itemId == Common.STARSOULDEBRISID) {
				origin = TradeService.ORIGIN_STARSOULDEBRISID;
			}
			manager.getPlayerService().useTicket(player, diamondNum, origin, null, null, "");
			// 记录一次购买次数
			player.getBuyLimitedCount().put(itemId, count + 1);
			// 发放物品
			if (itemId == Common.GOLDID) {
				ServiceManager.getManager().getPlayerService().updatePlayerGold(player, limitedPrice.getNum(), "使用钻石购买", "");
			} else {
				manager.getPlayerItemsFromShopService().playerGetItem(player.getId(), itemId, limitedPrice.getNum(), 0, "",
						limitedPrice.getPrice(), 0, 0);
			}
			BuyLimitedItemOk ok = new BuyLimitedItemOk(data.getSessionId(), data.getSerial());
			ok.setItemId(itemId);
			ok.setAddItemNum(limitedPrice.getNum());
			int itemCurNum = 0;
			if (itemId == Common.GOLDID) {
				itemCurNum = player.getMoney();
			} else if (itemId == Common.BADGEID) {
				itemCurNum = player.getMedalNum();
			} else {
				itemCurNum = manager.getPlayerItemsFromShopService().getPlayerItemNum(player.getId(), itemId);
			}
			ok.setItemCurNum(itemCurNum);
			session.write(ok);

		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
		}
	}

}
