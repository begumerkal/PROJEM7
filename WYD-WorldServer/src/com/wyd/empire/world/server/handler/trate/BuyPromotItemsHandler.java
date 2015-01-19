package com.wyd.empire.world.server.handler.trate;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.trate.BuyPromotItems;
import com.wyd.empire.protocol.data.trate.BuyResult;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItemsPrice;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.PromotionsService.PromotionVo;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> BuyItemsHandler</code>Protocol.TRATE_BuyItemsHandler读取购买商品协议处理
 * 
 * @since JDK 1.6
 */
public class BuyPromotItemsHandler implements IDataHandler {
	Logger log = Logger.getLogger(BuyPromotItemsHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		boolean mark = false;
		try {
			BuyPromotItems buyItems = (BuyPromotItems) data;
			for (int itemId : buyItems.getItemId()) {
				PromotionVo promotionVo = ServiceManager.getManager().getPromotionsService()
						.getPromotionVo(itemId, player.getPlayer().getSex());
				if (null == promotionVo) {
					mark = true;
					throw new Exception(TipMessages.PROMOSELLOUT);
				}
				if (promotionVo.isPersonal()) {
					if (ServiceManager.getManager().getTaskService().getService()
							.remainPromotion(player.getId(), itemId, promotionVo.getMaxCount()) < 1) {
						mark = true;
					}
				} else {
					if (promotionVo.getPromotions().getQuantity() < 1) {
						mark = true;
					}
				}
				if (mark == true) {
					BuyResult buyResult = new BuyResult(data.getSessionId(), data.getSerial());
					buyResult.setLimitLeave(promotionVo.getPromotions().getQuantity());
					buyResult.setBuyResult(false);
					buyResult.setContent(TipMessages.PROMOSELLOUT);
					buyResult.setLimitLeave(-2);
					buyResult.setCostTicks(0);
					buyResult.setCostGold(0);
					buyResult.setCostMedal(0);
					buyResult.setLastTime("");
					session.write(buyResult);
					return;
				}
			}
			BuyResult buyResult = new BuyResult(data.getSessionId(), data.getSerial());
			int moneyNum = player.getDiamond();
			int goldNum = player.getMoney();
			int buyItemsMoney = 0;
			int buyItemsGold = 0;
			ShopItemsPrice sip;
			// 获得购买所需点券数量
			for (int index = 0; index < buyItems.getCount(); index++) {
				PromotionVo promotionVo = ServiceManager.getManager().getPromotionsService()
						.getPromotionVo(buyItems.getItemId()[index], player.getPlayer().getSex());
				sip = ServiceManager.getManager().getPromotionsService().getPrice(promotionVo, buyItems.getItemPriceId()[index]);
				buyItemsMoney += (int) Math.ceil(sip.getCostUseTickets() * (promotionVo.getDiscount() / 10000f));
				buyItemsGold += (int) Math.ceil(sip.getCostUseGold() * (promotionVo.getDiscount() / 10000f));
				buyResult.setLimitLeave(promotionVo.getPromotions().getQuantity());
				if (sip.getCostType() == 1) {// 玩家用金币购买
					if (goldNum < buyItemsGold) {
						buyResult.setBuyResult(false);
						buyResult.setContent(ErrorMessages.PLAYER_INOC_MESSAGE);
						buyResult.setCostTicks(0);
						buyResult.setCostGold(0);
						buyResult.setCostMedal(0);
						buyResult.setLastTime("");
						session.write(buyResult);
						return;
					} else {
						for (int i = 0; i < buyItems.getCount(); i++) {
							// 玩家获得物品
							PlayerItemsFromShop playerItem = ServiceManager
									.getManager()
									.getPlayerItemsFromShopService()
									.playerGetItem(player.getPlayer().getId(), buyItems.getItemId()[i], buyItems.getItemPriceId()[i],
											sip.getDays(), sip.getCount(), TradeService.ORIGIN_PROMOT, null, 0, buyItemsGold, 0);

							// 扣除相应金币
							ServiceManager.getManager().getPlayerService().updatePlayerGold(player, -buyItemsGold, TipMessages.LOG5, "");
							buyResult.setBuyResult(true);
							buyResult.setContent(ErrorMessages.TRATE_BUYSUSSESS_MESSAGE);
							buyResult.setCostTicks(0);
							buyResult.setCostGold(player.getMoney());
							buyResult.setCostMedal(0);
							String itemLastTime = "";
							int lastTimeMark = 0;
							buyResult.setLastTime(itemLastTime);
							buyResult.setLastTimeMark(lastTimeMark);

							// 发送购买成功协议
							session.write(buyResult);
							int count = sip.getCount();
							count = count > 0 ? count : 1;
							// 更新任务
							ServiceManager.getManager().getTaskService().buySomething(player, buyItems.getItemId()[i], count);
							ServiceManager.getManager().getLogSerivce().updateShopItemLog(playerItem.getShopItem().getId());
							// 将购买物品装备上
							if (playerItem.getShopItem().isEquipment()) {
								ServiceManager.getManager().getPlayerService().changeEquipment(player, playerItem);
							}
						}
					}
				} else {
					// 判断用户点券是否足够
					if (moneyNum < buyItemsMoney) {
						buyResult.setBuyResult(false);
						buyResult.setContent(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE);
						buyResult.setCostTicks(0);
						buyResult.setCostGold(0);
						buyResult.setCostMedal(0);
						buyResult.setLastTime("");
						session.write(buyResult);
						return;
					} else {
						JSONArray jsonArray = JSONArray.fromObject(buyItems.getItemId());
						ServiceManager
								.getManager()
								.getPlayerService()
								.useTicket(player, buyItemsMoney, TradeService.ORIGIN_BUY, buyItems.getItemId(), buyItems.getItemPriceId(),
										jsonArray.toString());
					}
				}
			}
			for (int itemId : buyItems.getItemId()) {
				PromotionVo promotionVo = ServiceManager.getManager().getPromotionsService()
						.getPromotionVo(itemId, player.getPlayer().getSex());
				if (null != promotionVo) {
					if (promotionVo.isPersonal()) {
						ServiceManager.getManager().getTaskService().getService().addPromotionRecord(player.getId(), itemId);
					} else {
						promotionVo.getPromotions().setQuantity(promotionVo.getPromotions().getQuantity() - 1);
					}
				}

			}
			// 推送促销商品列表
			new GetPromotShopListHandler().sendShopList(player);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				throw new ProtocolException(ErrorMessages.TRATE_BUYFAILD_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
		}
	}
}