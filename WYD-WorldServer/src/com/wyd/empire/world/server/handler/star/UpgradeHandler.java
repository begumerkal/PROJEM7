package com.wyd.empire.world.server.handler.star;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.star.Upgrade;
import com.wyd.empire.protocol.data.star.UpgradeOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.StarsInfo;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 升星
 * 
 * @author zengxc
 *
 */
public class UpgradeHandler implements IDataHandler {
	private Logger log;

	public UpgradeHandler() {
		this.log = Logger.getLogger(UpgradeHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		Upgrade upgrade = (Upgrade) data;
		int playerItemId = upgrade.getItemId();
		int[] stoneId = upgrade.getStoneId();
		// 升星最大10级
		int maxLevel = 10;
		try {
			// 检查物品
			PlayerItemsFromShop playerItem = ServiceManager.getManager().getPlayerItemsFromShopService()
					.getPlayerItemById(player.getId(), playerItemId);
			if (null == playerItem || playerItem.getPLastTime() == 0 || !playerItem.getShopItem().isEquipment()) {
				throw new ProtocolException(ErrorMessages.PLAYER_TAKEONOVERTIME_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}

			int level = playerItem.getStars();// 装备星级

			if (level == maxLevel)
				return;
			int targetLevel = level + 1;
			StarsInfo config = ServiceManager.getManager().getStarsInfoService().getStarConfig(targetLevel);
			// 支付 升星需要消耗的金币=升星石数量*系数*(当前星级+1）
			int gold = stoneId.length * config.getGoldRate() * targetLevel;
			if (player.getMoney() < gold) {
				throw new ProtocolException(ErrorMessages.PLAYER_INOC_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			} else {
				ServiceManager.getManager().getPlayerService()
						.updatePlayerGold(player, -gold, "升星", stoneId.length + "｜" + config.getGoldRate() + "|" + targetLevel);
			}
			// 升级需要经验
			int needExp = config.getNeedExp();
			// 石头里的经验
			int addExp = 0;
			for (int sid : stoneId) {
				PlayerItemsFromShop playerStone = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(player.getId(), sid);
				if (playerStone == null) {
					// 非法操作
					return;
				}
				ShopItem stone = playerStone.getShopItem();
				// 是否是升星石
				if (stone.getType() != 6 || stone.getSubtype().intValue() != 6 || playerStone.getPLastNum() < 1) {
					// 非法操作
					return;
				}
				int lastNum = playerStone.getPLastNum();
				playerStone.setPLastNum(lastNum - 1);
				useItem(player, playerStone);
				addExp += stone.getUpStarExp();
			}
			int exp = playerItem.getStarExp() + addExp;

			boolean upLevel = false;
			while (exp >= needExp) {
				upLevel = true;
				level = playerItem.getStars();
				playerItem.updateStars(level + 1);
				exp -= needExp;
				// 达到最大级别跳出循环
				if (playerItem.getStars() == maxLevel) {
					exp = 0;
					break;
				}
				config = ServiceManager.getManager().getStarsInfoService().getStarConfig(level + 2);
				needExp = config.getNeedExp();
			}
			playerItem.setStarExp(exp);
			ServiceManager.getManager().getPlayerItemsFromShopService().update(playerItem);
			if (upLevel) {
				player.updateFight();
			}

			ServiceManager.getManager().getPlayerItemsFromShopService().upstarItem(player, playerItem);
			UpgradeOk ok = new UpgradeOk(data.getSessionId(), data.getSerial());
			session.write(ok);
			ServiceManager.getManager().getTaskService().upStar(player, playerItem.getShopItem().getId(), playerItem.getStars());
			ServiceManager.getManager().getTitleService().upgrade(player, playerItem.getStars());
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

	private void useItem(WorldPlayer player, PlayerItemsFromShop playerStone) {
		ServiceManager.getManager().getPlayerItemsFromShopService().update(playerStone);
		ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, playerStone);
	}
}
