package com.wyd.empire.world.server.handler.draw;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.draw.Draw;
import com.wyd.empire.protocol.data.draw.DrawOk;
import com.wyd.empire.world.bean.DrawRate;
import com.wyd.empire.world.bean.DrawType;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * @author Administrator
 */
public class DrawHandler implements IDataHandler {
	Logger log = Logger.getLogger(DrawHandler.class);

	/**
	 * 秘境探险
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		Draw draw = (Draw) data;

		try {
			int typeId = draw.getTypeId();

			if (null == player.getDrawNumMap().get(typeId)) {
				player.getDrawNumMap().put(typeId, 0);
			} else if (player.getStarNumMap().get(typeId) == 8) {
				return;
			}
			DrawType dt = ServiceManager.getManager().getDrawService().getDrawItemByItemId(draw.getTypeId());
			int price = dt.getCostNum() + player.getDrawNumMap().get(typeId) * dt.getAccumulatedValue();
			int useDiamond = 0, useGold = 0, useBadge = 0;
			// 抽奖类型
			if (typeId == Common.GOLDID) {// 金币
				if (price > player.getMoney()) {
					throw new ProtocolException(ErrorMessages.COMMUNITY_NEEDGOLD_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
				useGold = price;
				ServiceManager.getManager().getPlayerService().updatePlayerGold(player, -price, "抽奖系统消耗", "");
				ServiceManager.getManager().getTaskService().exploreGold(player);
			} else if (typeId == Common.DIAMONDID) {// 钻石
				if (price > player.getDiamond()) {
					throw new ProtocolException(ErrorMessages.COMMUNITY_NEEDDEMOND_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
				useDiamond = price;
				ServiceManager.getManager().getPlayerService().useTicket(player, price, TradeService.ORIGIN_DRAWUSED, null, null, "抽奖使用");
				ServiceManager.getManager().getTaskService().exploreDragon(player);
			} else {// 其他
				PlayerItemsFromShop playerItem = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(player.getId(), typeId);
				if (null == playerItem || price > playerItem.getPLastNum()) {
					throw new ProtocolException(ErrorMessages.TRATE_ITEMENOUGH_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
				useBadge = price;
				playerItem.setPLastNum(playerItem.getPLastNum() - price);
				ServiceManager.getManager().getPlayerItemsFromShopService().update(playerItem);
				player.setMedalNum(player.getMedalNum() - price);
				// 更新玩家拥有的物品
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, playerItem);
				ServiceManager.getManager().getPlayerItemsFromShopService()
						.saveGetItemRecord(player.getPlayer().getId(), typeId, -1, price, 21, 1, null);
				ServiceManager.getManager().getTaskService().explorePirates(player);
			}
			int starNum = null == player.getStarNumMap().get(typeId) ? 0 : player.getStarNumMap().get(typeId);
			int randomNum = 0;
			DrawRate dr;
			boolean isSuccess = false;
			for (int i = starNum; i < 8; i++) {
				randomNum = ServiceUtils.getRandomNum(0, 10000);
				dr = ServiceManager.getManager().getDrawService().getDrawRateById(i + 1);
				if (null == player.getDrawFailNumMap().get(typeId)) {
					player.getDrawFailNumMap().put(typeId, 0);
				}
				if (randomNum <= dr.getRate() || !(player.getDrawFailNumMap().get(typeId) < dr.getMaxNum())) {
					// player.setStarNum(i+1);
					player.getStarNumMap().put(typeId, i + 1);
					player.getDrawFailNumMap().put(typeId, 0);
					isSuccess = true;
				} else {
					player.getDrawFailNumMap().put(typeId, player.getDrawFailNumMap().get(typeId) + 1);
					break;
				}

			}
			int star = player.getStarNumMap().get(typeId);
			// 记录成就
			if (typeId == Common.GOLDID) {
				ServiceManager.getManager().getTitleService().exploreGold(player, star);
			} else if (typeId == Common.DIAMONDID) {
				ServiceManager.getManager().getTitleService().exploreDragon(player, star);
			} else {
				ServiceManager.getManager().getTitleService().explorePirates(player, star);
			}
			player.getDrawNumMap().put(typeId, player.getDrawNumMap().get(typeId) + 1);
			price = dt.getCostNum() + player.getDrawNumMap().get(typeId) * dt.getAccumulatedValue();
			String name = "";
			if (player.getStarNumMap().get(typeId).intValue() == 8) {
				Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
				ShopItem si = ServiceManager.getManager().getShopItemService().getShopItemById(map.get("otherReward" + typeId));
				name = si.getName();
				ServiceManager.getManager().getPlayerItemsFromShopService()
						.playerGetItem(player.getId(), si.getId(), -1, -1, 1, 22, null, 0, 0, 0);
			}
			DrawOk drawOk = new DrawOk(data.getSessionId(), data.getSerial());
			drawOk.setPrice(price);
			drawOk.setStarNum(player.getStarNumMap().get(typeId));
			drawOk.setOtherReward(name);
			session.write(drawOk);
			player.setDrawCanRefresh(typeId, false);
			ServiceManager.getManager().getDrawService().savePlayerDraw(player);
			GameLogService.explore(player.getId(), player.getLevel(), typeId, isSuccess, star, useDiamond, useGold, useBadge);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());

		}
	}
}
