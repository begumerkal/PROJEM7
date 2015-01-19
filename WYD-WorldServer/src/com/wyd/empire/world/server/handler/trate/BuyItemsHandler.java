package com.wyd.empire.world.server.handler.trate;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.trate.BuyItems;
import com.wyd.empire.protocol.data.trate.BuyResult;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.ShopItemsPrice;
import com.wyd.empire.world.bean.VipRate;
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
public class BuyItemsHandler implements IDataHandler {
	Logger log = Logger.getLogger(BuyItemsHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			BuyItems buyItems = (BuyItems) data;
			BuyResult buyResult = new BuyResult(data.getSessionId(), data.getSerial());
			int moneyNum = player.getDiamond();
			int goldNum = player.getMoney();
			int buyItemsMoney = 0;
			int buyItemsGold = 0;
			int itemId = 0;
			ShopItemsPrice sip = new ShopItemsPrice();
			ShopItem si = null;
			OperationConfig operationConfig = ServiceManager.getManager().getVersionService().getVersion();
			List<Object[]> magnificationList = ServiceManager.getManager().getShopItemService().findByTime(new Date());
			PromotionVo promotion;
			// 获得购买所需点券数量
			for (int i = 0; i < buyItems.getCount(); i++) {
				sip = (ShopItemsPrice) ServiceManager.getManager().getiShopItemsPriceService()
						.get(ShopItemsPrice.class, buyItems.getItemPriceId()[i]);
				buyItemsMoney = sip.getCostUseTickets();
				buyItemsGold = sip.getCostUseGold();
				buyItems.getItemId()[i] = sip.getShopItem().getId();
				int buyItemId = buyItems.getItemId()[i];
				boolean isDiscount = true;
				// TODO 购买金币等不走这个协议了 见:BuyLimitedItemHandler
				if (buyItemId == Common.GOLDID || buyItemId == Common.BADGEID || buyItemId == Common.STARSOULDEBRISID) {
					return;
				}
				// 判断是否上架
				if (sip.getShopItem().getType() < 5
						&& (!sip.getShopItem().getIsOnSale() || !(sip.getShopItem().getOnSaleTime().before(new Date()) && sip.getShopItem()
								.getOffSaleTime().after(new Date())))) {
					PlayerItemsFromShop myitem = ServiceManager.getManager().getPlayerItemsFromShopService()
							.uniquePlayerItem(player.getId(), buyItemId);
					if (null == myitem || sip.getShopItem().getSaleAgain() == 1) {
						throw new ProtocolException(ErrorMessages.TRATE_OVERTIME_MESSAGE, data.getSerial(), data.getSessionId(),
								data.getType(), data.getSubType());
					}
				}

				if (operationConfig.getShopDiscount() < Common.SHOP_DISCOUNT_DEF) {
					if (operationConfig.getShopNoDiscountId() != null && !("").equals(operationConfig.getShopNoDiscountId())) {
						String[] shopIds = operationConfig.getShopNoDiscountId().split(",");
						for (String shopId : shopIds) {
							if (Integer.parseInt(shopId) == buyItemId) {
								isDiscount = false;
								break;
							}
						}
					}
					if (isDiscount) {
						buyItemsMoney = (int) Math.ceil(buyItemsMoney * (operationConfig.getShopDiscount() / 100.0));
						buyItemsGold = (int) Math.ceil(buyItemsGold * (operationConfig.getShopDiscount() / 100.0));
					}
				} else {
					if (magnificationList != null && magnificationList.size() > 0) {
						for (Object[] obj : magnificationList) {
							if (Integer.parseInt(obj[0].toString()) == buyItemId) {
								buyItemsMoney = (int) Math.ceil(buyItemsMoney * (Integer.parseInt(obj[1].toString()) / 100.0));
								buyItemsGold = (int) Math.ceil(buyItemsGold * (Integer.parseInt(obj[1].toString()) / 100.0));
								break;
							}
						}
					}
				}
				if (player.isVip()) {
					VipRate vr = ServiceManager.getManager().getPlayerItemsFromShopService()
							.getVipRateById(player.getPlayer().getVipLevel());
					buyItemsMoney = (int) Math.ceil(buyItemsMoney * (vr.getSaleRate() / 100.0));
					buyItemsGold = (int) Math.ceil(buyItemsGold * (vr.getSaleRate() / 100.0));
				}
				if (sip.getCostType() == 1) {// 玩家用金币购买
					if (goldNum < buyItemsGold) {
						buyResult.setBuyResult(false);
						buyResult.setContent(ErrorMessages.PLAYER_INOC_MESSAGE);
						buyResult.setCostTicks(0);
						buyResult.setCostGold(0);
						buyResult.setCostMedal(0);
						buyResult.setLastTime("");
						session.write(buyResult);
					} else {
						si = ServiceManager.getManager().getShopItemService().getShopItemById(buyItemId);
						// 玩家获得物品
						ServiceManager
								.getManager()
								.getPlayerItemsFromShopService()
								.playerGetItem(player.getPlayer().getId(), buyItemId, buyItems.getItemPriceId()[i], 0, 0, 0, null, 0,
										buyItemsGold, 0);
						// 扣除相应金币
						ServiceManager.getManager().getPlayerService().updatePlayerGold(player, -buyItemsGold, TipMessages.LOG5, "");
						buyResult.setBuyResult(true);
						buyResult.setContent(ErrorMessages.TRATE_BUYSUSSESS_MESSAGE);
						buyResult.setCostTicks(0);
						buyResult.setCostGold(player.getMoney());
						buyResult.setCostMedal(0);
						PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
								.uniquePlayerItem(player.getId(), buyItemId);
						String itemLastTime = "";
						int lastTimeMark = 0;
						if (pifs.getPLastTime() != -1) {
							if (pifs.getPLastTime() == 0) {
								itemLastTime = TipMessages.OVERTIME;
								lastTimeMark = 0;
							} else {
								long timeLong = pifs.getBuyTime().getTime() + pifs.getPLastTime() * Common.ONEDAYLONG;
								long timeTemp = timeLong - System.currentTimeMillis();
								long day;
								if (timeTemp < 0) {
									day = 0;
								} else {
									day = timeTemp / (Common.ONEDAYLONG);
								}
								if (day != 0) {
									itemLastTime = (day + 1) + TipMessages.DAY;
									lastTimeMark = (int) (day + 1);
								} else {
									itemLastTime = "1" + TipMessages.DAY;
									lastTimeMark = 1;
								}
							}
						} else {
							itemLastTime = TipMessages.INFINITE;
							lastTimeMark = -1;
						}
						buyResult.setLastTime(itemLastTime);
						buyResult.setLastTimeMark(lastTimeMark);
						promotion = ServiceManager.getManager().getPromotionsService()
								.getPromotionVo(si.getId(), player.getPlayer().getSex());
						if (promotion != null) {
							if (promotion.isPersonal()) {
								buyResult.setLimitLeave(ServiceManager.getManager().getTaskService().getService()
										.remainPromotion(player.getId(), si.getId(), promotion.getMaxCount()));
							} else {
								buyResult.setLimitLeave(promotion.getPromotions().getQuantity());
							}
						}
						// 发送购买成功协议
						session.write(buyResult);
						int count = sip.getCount();
						count = count > 0 ? count : 1;
						// 更新任务
						ServiceManager.getManager().getTaskService().buySomething(player, buyItemId, count);
						ServiceManager.getManager().getLogSerivce().updateShopItemLog(si.getId());
					}
				} else {
					itemId = buyItemId;
					si = ServiceManager.getManager().getShopItemService().getShopItemById(buyItemId);
					// 判断用户点券是否足够
					if (moneyNum < buyItemsMoney) {
						buyResult.setBuyResult(false);
						buyResult.setContent(ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE);
						buyResult.setCostTicks(0);
						buyResult.setCostGold(0);
						buyResult.setCostMedal(0);
						buyResult.setLastTime("");
						buyResult.setItemId(itemId);
						session.write(buyResult);
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

		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY)) {
				this.log.error(ex, ex);
			}
			throw new ProtocolException(ErrorMessages.TRATE_BUYFAILD_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());

		}
	}

}