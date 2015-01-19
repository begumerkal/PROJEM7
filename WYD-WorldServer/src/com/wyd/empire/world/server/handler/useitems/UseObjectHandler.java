package com.wyd.empire.world.server.handler.useitems;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.useitems.UseObject;
import com.wyd.empire.protocol.data.useitems.UseObjectOK;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

public class UseObjectHandler implements IDataHandler {
	Logger log = Logger.getLogger(UseObjectHandler.class);

	public void handle(AbstractData data) throws Exception {
		try {
			UseObjectOK ok = new UseObjectOK(data.getSessionId(), data.getSerial());
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			UseObject useObject = (UseObject) data;
			int playerItemId = useObject.getItemId();
			int count = useObject.getCount();
			PlayerItemsFromShop playerItem = ServiceManager.getManager().getPlayerItemsFromShopService()
					.getPlayerItemById(player.getPlayer().getId(), playerItemId);
			if (playerItem == null)
				return;

			int lastNum = playerItem.getPLastNum();
			if (lastNum < count)
				throw new ProtocolException(ErrorMessages.INSUFFICIENT_NUMBER_OF, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			ShopItem shopItem = playerItem.getShopItem();

			if (shopItem.getType() == 8 && shopItem.getSubtype() == 25) { // 鸡腿
				int vigor = Integer.valueOf(shopItem.getStrengthen());
				vigor = vigor * count;
				player.useGoodsUpVigor(vigor);
				playerItem.setPLastNum(lastNum - count);
				ServiceManager.getManager().getPlayerItemsFromShopService().update(playerItem);
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, playerItem);
				ok.setStatus(1);
			}

			session.write(ok);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException("Error", data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}
}