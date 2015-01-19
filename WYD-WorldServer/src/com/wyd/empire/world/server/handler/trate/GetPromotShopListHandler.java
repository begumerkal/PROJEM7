package com.wyd.empire.world.server.handler.trate;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.trate.GetPromotShopListOk;
import com.wyd.empire.world.bean.ShopItem;
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
 * 类 <code> GetShopListHandler</code>Protocol.TRATE_GetShopListHandler获得商品列表协议处理
 * 
 * @since JDK 1.6
 */
public class GetPromotShopListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetPromotShopListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {

			GetPromotShopListOk getShopListOk = new GetPromotShopListOk(data.getSessionId(), data.getSerial());
			resutl(player.getId(), player.getPlayer().getSex(), getShopListOk);
			session.write(getShopListOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.TRATE_SHOPLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	private void resutl(int playerId, int sex, GetPromotShopListOk getShopListOk) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<PromotionVo> promotionsList = ServiceManager.getManager().getPromotionsService().getPromotionsList(sex);
		int size = promotionsList.size();
		int[] id = new int[size];
		byte[] type = new byte[size];
		byte[] subtype = new byte[size];
		boolean[] isOnSale = new boolean[size];
		int[] saledNum = new int[size];
		String[] onSaleTime = new String[size];
		String[] offSaleTime = new String[size];
		int[] payType = new int[size];
		boolean[] newMark = new boolean[size];
		int[] discount = new int[size]; // 折扣
		int[] limitLeave = new int[size]; // 剩余可以购买数量
		int[] saleType = new int[size];
		int[] floorPrice = new int[size];
		int remaTime = ServiceManager.getManager().getPromotionsService().getRemaTime(); // 刷新剩余时间(秒)
		ShopItem shopItem;
		PromotionVo promotion;
		int index = 0;
		for (int i = 0; i < size; i++) {
			promotion = promotionsList.get(i);
			shopItem = promotion.getPromotions().getShopItem();
			shopItem.check();
			id[index] = shopItem.getId();
			type[index] = shopItem.getType();
			subtype[index] = shopItem.getSubtype();
			isOnSale[index] = true;// 补救客户端BUG使促销的商品默认为上架
			saledNum[index] = shopItem.getSaledNum();
			onSaleTime[index] = df.format(shopItem.getOnSaleTime());
			offSaleTime[index] = df.format(shopItem.getOffSaleTime());
			if (promotion.getPrices().get(0)[0] == 0) {
				payType[index] = 0;
				floorPrice[index] = promotion.getPrices().get(0)[1];
			} else {
				payType[index] = 1;
				floorPrice[index] = promotion.getPrices().get(0)[2];
			}
			long onSaleLong = shopItem.getOnSaleTime().getTime();
			long nowLong = System.currentTimeMillis();
			if (nowLong - onSaleLong < 30 * Common.ONEDAYLONG) {
				newMark[index] = true;
			} else {
				newMark[index] = false;
			}
			discount[index] = promotion.getDiscount();
			if (promotion.isPersonal()) {
				limitLeave[i] = ServiceManager.getManager().getTaskService().getService()
						.remainPromotion(playerId, shopItem.getId(), promotion.getMaxCount());
			} else {
				limitLeave[i] = promotion.getPromotions().getQuantity();
			}
			saleType[index] = promotion.getPromotions().getPersonal();
			index++;
		}

		getShopListOk.setId(id);
		getShopListOk.setMaintype(type);
		getShopListOk.setSubtype(subtype);
		getShopListOk.setIsOnSale(isOnSale);
		getShopListOk.setSaledNum(saledNum);
		getShopListOk.setOnSaleTime(onSaleTime);
		getShopListOk.setOffSaleTime(offSaleTime);
		getShopListOk.setFloorPrice(floorPrice);
		getShopListOk.setPayType(payType);
		getShopListOk.setNewMark(newMark);
		getShopListOk.setDiscount(discount);
		getShopListOk.setLimitLeave(limitLeave);
		getShopListOk.setRemaTime(remaTime);
		getShopListOk.setSaleType(saleType);
	}

	public void sendShopList(WorldPlayer player) {
		GetPromotShopListOk getShopListOk = new GetPromotShopListOk();
		resutl(player.getId(), player.getPlayer().getSex(), getShopListOk);
		player.sendData(getShopListOk);
	}
}