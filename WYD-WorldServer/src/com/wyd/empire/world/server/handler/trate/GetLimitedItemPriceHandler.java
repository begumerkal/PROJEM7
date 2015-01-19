package com.wyd.empire.world.server.handler.trate;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.trate.GetLimitedItemPrice;
import com.wyd.empire.protocol.data.trate.GetLimitedItemPriceOk;
import com.wyd.empire.world.bean.LimitedPrice;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取购买金币/勋章/星魂信息（花费钻石数，获得物品个数，购买剩余次数）
 * 
 * @author zengxc
 * 
 */
public class GetLimitedItemPriceHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetLimitedItemPriceHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		ServiceManager manager = ServiceManager.getManager();
		int itemId = ((GetLimitedItemPrice) data).getItemId();
		try {
			Integer count = player.getBuyLimitedCount().get(itemId);
			count = count == null ? 0 : count;
			int vipLevel = player.isVip() ? player.getPlayer().getVipLevel() : 0;
			int lastNum = manager.getLimitedPriceService().getMaxCount(itemId, vipLevel) - count;
			int addItemNum = 0, itemCurNum = 0, useDiam = 0;
			if (lastNum > 0) {
				// 根据次数去获得配置
				LimitedPrice limitedPrice = manager.getLimitedPriceService().getPriceByCount(itemId, count + 1);
				addItemNum = limitedPrice.getNum();
				useDiam = limitedPrice.getPrice();

			}
			if (itemId == Common.GOLDID) {
				itemCurNum = player.getMoney();
			} else if (itemId == Common.BADGEID) {
				itemCurNum = player.getMedalNum();
			} else {
				itemCurNum = manager.getPlayerItemsFromShopService().getPlayerItemNum(player.getId(), itemId);
			}

			GetLimitedItemPriceOk ok = new GetLimitedItemPriceOk(data.getSessionId(), data.getSerial());
			ok.setAddItemNum(addItemNum);
			ok.setItemCurNum(itemCurNum);
			ok.setItemId(itemId);
			ok.setUseDiam(useDiam);
			ok.setLastNum(lastNum);
			session.write(ok);
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
		}
	}
}
