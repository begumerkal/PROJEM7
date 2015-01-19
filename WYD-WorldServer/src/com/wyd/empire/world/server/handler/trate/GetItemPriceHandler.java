package com.wyd.empire.world.server.handler.trate;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.trate.GetItemPrice;
import com.wyd.empire.protocol.data.trate.GetItemPriceOk;
import com.wyd.empire.world.bean.Exchange;
import com.wyd.empire.world.bean.OperationConfig;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.ShopItemsPrice;
import com.wyd.empire.world.bean.VipRate;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.PromotionsService.PromotionVo;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> GetItemPriceHandler</code>
 * Protocol.TRATE_GetItemPriceHandler读取商品价格协议处理
 * 
 * @since JDK 1.6
 */
public class GetItemPriceHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetItemPriceHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetItemPrice getItemPrice = (GetItemPrice) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetItemPriceOk getItemPriceOk = new GetItemPriceOk(data.getSessionId(), data.getSerial());
		boolean mark = false;
		try {
			int priceCount;
			int[] id;
			int[] shopItemId;
			int[] days;
			int[] count;
			byte[] costType;
			int[] costUseTickets;
			int[] costUseGold;
			int[] costUseBadge;
			int[] costUseTicketsPrev;
			int[] costUseGoldPrev;
			int[] costUseBadgePrev;
			ShopItemsPrice shopItemsPrice;
			int saleRate = 100;
			if (player.isVip()) {
				VipRate vr = ServiceManager.getManager().getPlayerItemsFromShopService().getVipRateById(player.getPlayer().getVipLevel());
				saleRate = vr.getSaleRate();
			}
			switch (getItemPrice.getShopType()) {
				case 8 :
					PromotionVo promotionVo = ServiceManager.getManager().getPromotionsService()
							.getPromotionVo(getItemPrice.getItemId(), player.getPlayer().getSex());
					id = new int[promotionVo.getSipList().size()];
					shopItemId = new int[promotionVo.getSipList().size()];
					days = new int[promotionVo.getSipList().size()];
					count = new int[promotionVo.getSipList().size()];
					costType = new byte[promotionVo.getSipList().size()];
					costUseTickets = new int[promotionVo.getSipList().size()];
					costUseGold = new int[promotionVo.getSipList().size()];
					costUseBadge = new int[promotionVo.getSipList().size()];
					costUseTicketsPrev = new int[promotionVo.getSipList().size()]; // 需要花费的点劵（打折前）
					costUseGoldPrev = new int[promotionVo.getSipList().size()]; // 需要花费的金币（打折前）
					costUseBadgePrev = new int[promotionVo.getSipList().size()]; // 需花费的勋章数（打折前）
					priceCount = promotionVo.getSipList().size();
					for (int i = 0; i < promotionVo.getSipList().size(); i++) {
						shopItemsPrice = promotionVo.getSipList().get(i);
						id[i] = shopItemsPrice.getId();
						shopItemId[i] = promotionVo.getPromotions().getShopItem().getId();
						days[i] = shopItemsPrice.getDays();
						count[i] = shopItemsPrice.getCount();
						costType[i] = shopItemsPrice.getCostType();
						costUseTickets[i] = promotionVo.getPrices().get(i)[1];
						costUseGold[i] = promotionVo.getPrices().get(i)[2];
						costUseBadge[i] = 0;
						costUseTicketsPrev[i] = shopItemsPrice.getCostUseTickets();
						costUseGoldPrev[i] = shopItemsPrice.getCostUseGold();
						costUseBadgePrev[i] = 0;
					}
					break;
				case 7 :
					Exchange exchange = ServiceManager.getManager().getiShopItemsPriceService()
							.getShopItemsExchangeNum(getItemPrice.getItemId(), 0);
					if (exchange == null) {
						throw new Exception();// 数据不正确
					}
					id = new int[1];
					shopItemId = new int[1];
					days = new int[1];
					count = new int[1];
					costType = new byte[1];
					costUseTickets = new int[1];
					costUseGold = new int[1];
					costUseBadge = new int[1];
					costUseTicketsPrev = new int[1];
					costUseGoldPrev = new int[1];
					costUseBadgePrev = new int[1];
					priceCount = 1;
					shopItemId[0] = exchange.getShopItem().getId();
					id[0] = exchange.getId();
					days[0] = exchange.getDays();
					count[0] = exchange.getCount();
					costType[0] = 3;
					costUseTickets[0] = 0;
					costUseGold[0] = 0;
					costUseBadge[0] = exchange.getCostUseBadge();
					costUseTicketsPrev[0] = 0;
					costUseGoldPrev[0] = 0;
					costUseBadgePrev[0] = 0;
					break;
				default :
					OperationConfig operationConfig = ServiceManager.getManager().getVersionService().getVersion();
					List<Object[]> magnificationList = ServiceManager.getManager().getShopItemService().findByTime(new Date());
					ShopItem si = ServiceManager.getManager().getShopItemService().getShopItemById(getItemPrice.getItemId());
					if (si.getSaleAgain() == 1 && si.getType() < 5) {
						mark = true;
						throw new Exception(Common.ERRORKEY + ErrorMessages.TRATE_OVERTIME_MESSAGE);
					}
					List<ShopItemsPrice> list = ServiceManager.getManager().getiShopItemsPriceService()
							.getItemPrice(getItemPrice.getItemId());
					id = new int[list.size()];
					shopItemId = new int[list.size()];
					days = new int[list.size()];
					count = new int[list.size()];
					costType = new byte[list.size()];
					costUseTickets = new int[list.size()];
					costUseGold = new int[list.size()];
					costUseBadge = new int[list.size()];
					costUseTicketsPrev = new int[list.size()]; // 需要花费的点劵（打折前）
					costUseGoldPrev = new int[list.size()]; // 需要花费的金币（打折前）
					costUseBadgePrev = new int[list.size()]; // 需花费的勋章数（打折前）
					priceCount = list.size();
					boolean isDiscount = true;
					for (int i = 0; i < list.size(); i++) {
						shopItemsPrice = list.get(i);
						id[i] = shopItemsPrice.getId();
						shopItemId[i] = shopItemsPrice.getShopItem().getId();
						days[i] = shopItemsPrice.getDays();
						count[i] = shopItemsPrice.getCount();
						costType[i] = shopItemsPrice.getCostType();
						costUseTickets[i] = (int) Math.ceil(shopItemsPrice.getCostUseTickets() * (saleRate / 100.0));
						costUseGold[i] = (int) Math.ceil(shopItemsPrice.getCostUseGold() * (saleRate / 100.0));
						costUseBadge[i] = 0;
						costUseTicketsPrev[i] = shopItemsPrice.getCostUseTickets();
						costUseGoldPrev[i] = shopItemsPrice.getCostUseGold();
						costUseBadgePrev[i] = 0;
						if (operationConfig.getShopDiscount() < Common.SHOP_DISCOUNT_DEF) {
							if (operationConfig.getShopNoDiscountId() != null && !("").equals(operationConfig.getShopNoDiscountId())) {
								String[] shopIds = operationConfig.getShopNoDiscountId().split(",");
								for (String shopId : shopIds) {
									if (Integer.parseInt(shopId) == shopItemsPrice.getShopItem().getId()) {
										isDiscount = false;
										break;
									}
								}
							}
							if (isDiscount) {
								costUseTickets[i] = (int) Math.ceil(costUseTickets[i] * (operationConfig.getShopDiscount() / 100.0));
								costUseGold[i] = (int) Math.ceil(costUseGold[i] * (operationConfig.getShopDiscount() / 100.0));
								costUseBadge[i] = (int) Math.ceil(costUseBadge[i] * (operationConfig.getShopDiscount() / 100.0));
							}
						} else {
							if (magnificationList != null && magnificationList.size() > 0) {
								for (Object[] obj : magnificationList) {
									if (Integer.parseInt(obj[0].toString()) == shopItemsPrice.getShopItem().getId()) {
										costUseTickets[i] = (int) Math.ceil(costUseTickets[i]
												* (Integer.parseInt(obj[1].toString()) / 100.0));
										costUseGold[i] = (int) Math.ceil(costUseGold[i] * (Integer.parseInt(obj[1].toString()) / 100.0));
										costUseBadge[i] = (int) Math.ceil(costUseBadge[i] * (Integer.parseInt(obj[1].toString()) / 100.0));
										break;
									}
								}
							}
						}
					}
					break;
			}
			getItemPriceOk.setCostType(costType);
			getItemPriceOk.setCostUseGold(costUseGold);
			getItemPriceOk.setCostUseTickets(costUseTickets);
			getItemPriceOk.setCount(count);
			getItemPriceOk.setDays(days);
			getItemPriceOk.setId(id);
			getItemPriceOk.setPriceCount(priceCount);
			getItemPriceOk.setShopItemId(shopItemId);
			getItemPriceOk.setCostUseBadge(costUseBadge);
			getItemPriceOk.setCostUseBadgePrev(costUseBadgePrev);
			getItemPriceOk.setCostUseGoldPrev(costUseGoldPrev);
			getItemPriceOk.setCostUseTicketsPrev(costUseTicketsPrev);
			session.write(getItemPriceOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				throw new ProtocolException(ErrorMessages.TRATE_ITEMPRICE_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
		}
	}
}