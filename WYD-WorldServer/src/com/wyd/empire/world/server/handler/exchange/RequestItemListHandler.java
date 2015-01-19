package com.wyd.empire.world.server.handler.exchange;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.exchange.ResponseItemList;
import com.wyd.empire.world.bean.Exchange;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class RequestItemListHandler implements IDataHandler {
	Logger log = Logger.getLogger(RequestItemListHandler.class);

	/**
	 * 获取兑换商品列表
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			Exchange ex;
			List<Exchange> list = ServiceManager.getManager().getPlayerItemsFromShopService().checkExchangeList(player);
			int[] itemId = new int[list.size()];
			int[] strengthenlevel = new int[list.size()];
			int[] day = new int[list.size()];
			int[] useTimes = new int[list.size()];
			int[] price = new int[list.size()];
			int lastTime = 0;
			OperationConfig version = ServiceManager.getManager().getVersionService().getVersion();
			int refreshDramond = version.getReDramond();
			for (int i = 0; i < list.size(); i++) {
				ex = list.get(i);
				itemId[i] = ex.getShopItem().getId();
				day[i] = ex.getDays();
				useTimes[i] = ex.getCount();
				// 添加武器熟练度
				if (ex.getShopItem().isWeapon()) {
					PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
							.uniquePlayerItem(player.getId(), ex.getShopItem().getId());
					strengthenlevel[i] = null == pifs ? 0 : pifs.getStrongLevel();
				} else {
					strengthenlevel[i] = 0;
				}
				lastTime = (int) ((ex.getTime().getTime() + 2 * 60 * 60 * 1000 - new Date().getTime()) / 1000);
				price[i] = ex.getCostUseBadge();
			}
			ResponseItemList responseItemList = new ResponseItemList(data.getSessionId(), data.getSerial());
			responseItemList.setDay(day);
			responseItemList.setItemId(itemId);
			responseItemList.setLastTime(lastTime);
			responseItemList.setPrice(price);
			responseItemList.setStrengthenlevel(strengthenlevel);
			responseItemList.setUseTimes(useTimes);
			responseItemList.setRefreshDramond(refreshDramond);
			session.write(responseItemList);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TRATE_SHOPLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
