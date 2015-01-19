package com.wyd.empire.world.server.handler.admin;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.util.StringUtils;

import com.wyd.empire.protocol.data.admin.AddByJson;
import com.wyd.empire.protocol.data.admin.AddByJsonResult;
import com.wyd.empire.world.bean.Promotions;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.ShopItemsPrice;
import com.wyd.empire.world.common.util.KeyProcess;
import com.wyd.empire.world.common.util.KeyProcessService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * gm工具json数据新增
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author mazheng
 */
public class AddByJsonHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		AddByJson addByJson = (AddByJson) data;
		boolean resuit = true; // 操作是否成功
		AddByJsonResult addByJsonResult = new AddByJsonResult(data.getSessionId(), data.getSerial());
		try {
			int addType = addByJson.getAddType();
			switch (addType) {
				case 0 :
					JSONArray array = JSONArray.fromObject(addByJson.getContent());
					if (array.size() > 0) {
						for (int i = 0; i < array.size(); i++) {
							JSONObject object = (JSONObject) array.get(i);
							if (StringUtils.hasText(object.getString("id")) && object.getInt("id") != 0) {
								ShopItemsPrice shopItemsPrice = (ShopItemsPrice) ServiceManager.getManager().getiShopItemsPriceService()
										.get(ShopItemsPrice.class, object.getInt("id"));
								shopItemsPrice.setDays(object.getInt("days"));
								shopItemsPrice.setCount(object.getInt("count"));
								shopItemsPrice.setCostType((byte) object.getInt("costType"));
								shopItemsPrice.setCostUseTickets(object.getInt("costUseTickets"));
								shopItemsPrice.setCostUseGold(object.getInt("costUseGold"));
								ServiceManager.getManager().getiShopItemsPriceService().update(shopItemsPrice);
							} else {
								ShopItemsPrice shopItemsPrice = new ShopItemsPrice();
								shopItemsPrice.setDays(object.getInt("days"));
								shopItemsPrice.setCount(object.getInt("count"));
								shopItemsPrice.setCostType((byte) object.getInt("costType"));
								shopItemsPrice.setCostUseTickets(object.getInt("costUseTickets"));
								shopItemsPrice.setCostUseGold(object.getInt("costUseGold"));
								shopItemsPrice.getShopItem().setId(object.getInt("shopId"));
								ServiceManager.getManager().getiShopItemsPriceService().save(shopItemsPrice);
							}
						}
					}
					break;
				case 5 :
					// if (null == ServiceManager.getManager().getPushService())
					// break;
					// JSONObject pushObj =
					// JSONObject.fromObject(addByJson.getContent());
					// if (("G").equals(pushObj.getString("pushType"))) {// 推送个人
					// WorldPlayer worldPlayer =
					// ServiceManager.getManager().getPlayerService().getWorldPlayerById(pushObj.getInt("playerId"));
					// ServiceManager.getManager().getPushService().SendMassageByPlayer(worldPlayer,
					// pushObj.getString("content"));
					// } else {// 推送全部
					// Collection<WorldPlayer> worldPlayerList =
					// ServiceManager.getManager().getPlayerService().getAllPlayer();
					// for (WorldPlayer worldPlayer : worldPlayerList) {
					// ServiceManager.getManager().getPushService().SendMassageByPlayer(worldPlayer,
					// pushObj.getString("content"));
					// }
					// worldPlayerList = null;
					// }
					break;
				case 6 :
					JSONObject promotionsObj = JSONObject.fromObject(addByJson.getContent());
					Promotions promotions = new Promotions();
					promotions.setShopItem((ShopItem) ServiceManager.getManager().getShopItemService()
							.get(ShopItem.class, promotionsObj.getInt("shopId")));
					promotions.setQuantity(promotionsObj.getInt("quantity"));
					promotions.setIsActivate(promotionsObj.getString("isActivate"));
					promotions.setDiscount(promotionsObj.getInt("discount"));
					promotions.setGold(promotionsObj.getInt("gold"));
					promotions.setCount(promotionsObj.getInt("count"));
					promotions.setDays(promotionsObj.getInt("days"));
					promotions.setPersonal(promotionsObj.getInt("personal"));
					ServiceManager.getManager().getPromotionsService().getIPromotionsService().save(promotions);
					break;
				case 7 :// 添加外挂信息
					JSONObject keyProcessObj = JSONObject.fromObject(addByJson.getContent());
					if (keyProcessObj != null && !keyProcessObj.getString("value").equals("")) {
						KeyProcessService service = KeyProcessService.getInstance();
						List<KeyProcess> keyprocesses = service.getKeyProcesses();
						for (KeyProcess keyProcess : keyprocesses) {
							if (keyProcess.getValue().equals(keyProcessObj.getString("value"))) {
								resuit = false;
								break;
							}
						}
						service.addProcess(keyProcessObj.getString("name"), keyProcessObj.getString("value"));
					} else {
						resuit = false;
					}
					break;
				default :
					break;
			}
			addByJsonResult.setSuccess(resuit);
		} catch (Exception e) {
			e.printStackTrace();
			addByJsonResult.setSuccess(false);
		}
		session.write(addByJsonResult);
	}
}