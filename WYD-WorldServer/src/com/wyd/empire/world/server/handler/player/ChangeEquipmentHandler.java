package com.wyd.empire.world.server.handler.player;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.player.ChangeEquipment;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> TakeOnEquipmentHandler</code>
 * Protocol.TRATE_TakeOnEquipmentHandler装备物品协议处理
 * 
 * @since JDK 1.6
 */
public class ChangeEquipmentHandler implements IDataHandler {
	Logger log = Logger.getLogger(ChangeEquipmentHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		ChangeEquipment changeEquipment = (ChangeEquipment) data;
		PlayerItemsFromShop oldItem = null, newItem = null;
		newItem = getPlayerItem(player.getId(), changeEquipment.getNewItemId());
		try {
			if (newItem == null || newItem.getPlayerId() != player.getId() || !newItem.getShopItem().isEquipment()) {
				throw new Exception();
			}
			if (newItem.getLastDay() == 0) {
				throw new ProtocolException(ErrorMessages.PLAYER_TAKEONOVERTIME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			if (newItem.getIsInUsed()) {
				int newItemId = newItem.getId().intValue();
				// 如果物品在使用中并且是戒指、项链、翅膀则表示脱下
				if (player.getRing_L() != null && newItemId == player.getRing_L().getId().intValue()) {
					oldItem = newItem;
					player.setRing_L(null);
				} else if (player.getRing_R() != null && newItemId == player.getRing_R().getId().intValue()) {
					oldItem = newItem;
					player.setRing_R(null);
				} else if (player.getNecklace() != null && newItemId == player.getNecklace().getId().intValue()) {
					oldItem = newItem;
					player.setNecklace(null);
				} else if (player.getWing() != null && newItemId == player.getWing().getId().intValue()) {
					oldItem = newItem;
					player.setWing(null);
				}
				if (oldItem != null) {
					ServiceManager
							.getManager()
							.getPlayerService()
							.writeLog(
									"保存玩家换装信息：id=" + player.getId() + "---name=" + player.getName() + "---oldItemId="
											+ oldItem.getShopItem().getId() + "---newItemId=0");
					// 更改playeritemsfromshop的isInUsed状态
					ServiceManager.getManager().getPlayerItemsFromShopService().takeOffEquipment(oldItem, null);
					// 更新信息并返回协议
					player.updateFight();
					ServiceManager.getManager().getTaskService().useEquipment(player, changeEquipment.getNewItemId());
				}
			} else {
				ServiceManager.getManager().getPlayerService().changeEquipment(player, newItem);
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			ServiceManager.getManager().getPlayerItemsFromShopService().takeOffEquipment(newItem, oldItem);
			player.updateFight();
			throw new ProtocolException(ErrorMessages.PLAYER_TAKEON_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	private PlayerItemsFromShop getPlayerItem(int playerId, int playerItemId) {
		return ServiceManager.getManager().getPlayerItemsFromShopService().getPlayerItemById(playerId, playerItemId);
	}
}
