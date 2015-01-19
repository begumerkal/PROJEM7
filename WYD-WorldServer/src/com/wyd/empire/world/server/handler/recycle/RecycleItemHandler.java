package com.wyd.empire.world.server.handler.recycle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.recycle.RecycleItem;
import com.wyd.empire.protocol.data.recycle.RecycleItemOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * @author Administrator
 */
public class RecycleItemHandler implements IDataHandler {
	Logger log = Logger.getLogger(RecycleItemHandler.class);

	/**
	 * 回收物品
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		RecycleItem recycleItem = (RecycleItem) data;
		try {
			int[] playerItemIds = recycleItem.getItemId();
			int[] itemNum = recycleItem.getItemNum();
			int index = 0;
			PlayerItemsFromShop playerItem;
			for (int playerItemId : playerItemIds) {
				playerItem = ServiceManager.getManager().getPlayerItemsFromShopService().getPlayerItemById(player.getId(), playerItemId);
				// 正在使用的物品不能回收
				if (playerItem == null || playerItem.getIsInUsed())
					return;
				int itemId = playerItem.getShopItem().getId();
				int oldNum = 0;
				int num = itemNum[index];

				if (playerItem.getShopItem().getUseType() == 0) {
					if (num > playerItem.getPLastNum() || num < 0) {
						throw new ProtocolException(ErrorMessages.RECYCLE_NOT_ENOUGH.replace("XXX", playerItem.getShopItem().getName()),
								data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
					} else {
						// 扣物品
						oldNum = playerItem.getPLastNum();
						playerItem.setPLastNum(playerItem.getPLastNum() - num);
						ServiceManager.getManager().getPlayerItemsFromShopService().update(playerItem);
						// 更新玩家拥有的物品
						ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, playerItem);
					}
				} else {
					int dayNum = playerItem.getLastDay();
					if (dayNum < 1) {
						throw new ProtocolException(ErrorMessages.RECYCLE_NOT_ENOUGH.replace("XXX", playerItem.getShopItem().getName()),
								data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
					} else {
						// 扣物品
						oldNum = dayNum;
						num = dayNum;
						// 时间类的只能一次全卖完
						playerItem.setPLastTime(0);
						ServiceManager.getManager().getPlayerItemsFromShopService()
								.removeItem(player, new int[]{playerItem.getId()}, new int[]{playerItem.getShopItem().getId()});
						//
						ServiceManager.getManager().getPlayerItemsFromShopService().remove(playerItem);
					}
				}
				int gold = num * ServiceManager.getManager().getShopItemService().getItemRecycleById(itemId).getGold();

				// 加金币
				ServiceManager.getManager().getPlayerService().updatePlayerGold(player, gold, "回收物品", itemId + "--" + oldNum + "--" + num);
				index++;
				GameLogService.recover(player.getId(), player.getLevel(), itemId, num, gold);
				RecycleItemOk ok = new RecycleItemOk(data.getSessionId(), data.getSerial());
				session.write(ok);
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.PLAYER_STOREEQU_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
