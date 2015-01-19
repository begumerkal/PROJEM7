package com.wyd.empire.world.server.handler.spree;

import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.spree.GetGift;
import com.wyd.empire.protocol.data.spree.GetGiftOk;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.bean.SpreeGift;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.TradeService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 礼包开启
 * 
 * @author Administrator
 */
public class GetGiftHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetGiftHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetGift getGift = (GetGift) data;
		try {
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService()
					.uniquePlayerItem(player.getId(), getGift.getItemId());
			if (null == pifs || !(pifs.getPLastNum() > 0)) {
				throw new ProtocolException(ErrorMessages.GETGIFT_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			} else {
				ServiceManager.getManager().getPlayerItemsFromShopService()
						.saveGetItemRecord(player.getId(), getGift.getItemId(), -1, -1, 5, 1, "礼包消耗");
				List<SpreeGift> list = ServiceManager.getManager().getSpereeGiftService()
						.getSpreeGiftResult(pifs.getShopItem().getId(), pifs.getShopItem().getSubtype());
				if (list.size() == 0) {
					throw new ProtocolException(ErrorMessages.GETGIFT_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				// 检查礼包内容
				checkGift(list, player.getId(), player.getLevel(), data);
				String[] itemName = new String[list.size()];
				String[] itemIcon = new String[list.size()];
				int[] itemNum = new int[list.size()];
				int i = 0;
				String noticeStr = "";
				for (SpreeGift sg : list) {
					int countOrDay = 0;
					if (player.getPlayer().getSex() == 0) {
						itemName[i] = sg.getShopItem1().getName();
						itemIcon[i] = sg.getShopItem1().getIcon();
						if (sg.getShopItem1().getId() == Common.DIAMONDID) {
							ServiceManager.getManager().getPlayerService()
									.addTicket(player, sg.getCount(), 0, TradeService.ORIGIN_GIFT, 0, "", "礼包获得", "", "");
							countOrDay = sg.getCount();
						} else if (sg.getShopItem1().getId() == Common.GOLDID) {
							ServiceManager.getManager().getPlayerService().updatePlayerGold(player, sg.getCount(), "礼包获得", "-- " + " --");
							countOrDay = sg.getCount();
						} else {
							ServiceManager
									.getManager()
									.getPlayerItemsFromShopService()
									.playerGetItem(player.getId(), sg.getShopItem1().getId(), -1, sg.getDays(), sg.getCount(), 5, null, 0,
											0, 0);
							if (sg.getDays() == -1 && sg.getCount() == -1) {
								countOrDay = -1;
							} else if (sg.getDays() != -1) {
								countOrDay = sg.getDays();
							} else {
								countOrDay = sg.getCount();
							}
						}
						if (list.size() == 1 && sg.getChance() < 200) {
							if (countOrDay == -1) {
								noticeStr = itemName[i] + " X " + TipMessages.INFINITE;
							} else {
								noticeStr = itemName[i] + " X " + countOrDay;
							}
						}
					} else {
						itemName[i] = sg.getShopItem2().getName();
						itemIcon[i] = sg.getShopItem2().getIcon();
						if (sg.getShopItem2().getId() == Common.DIAMONDID) {
							ServiceManager.getManager().getPlayerService()
									.addTicket(player, sg.getCount(), 0, TradeService.ORIGIN_GIFT, 0, "", "礼包获得", "", "");
							countOrDay = sg.getCount();
						} else if (sg.getShopItem2().getId() == Common.GOLDID) {
							ServiceManager.getManager().getPlayerService().updatePlayerGold(player, sg.getCount(), "礼包获得", "-- " + " --");
							countOrDay = sg.getCount();
						} else {
							ServiceManager
									.getManager()
									.getPlayerItemsFromShopService()
									.playerGetItem(player.getId(), sg.getShopItem2().getId(), -1, sg.getDays(), sg.getCount(), 5, null, 0,
											0, 0);
							if (sg.getDays() == -1 && sg.getCount() == -1) {
								countOrDay = -1;
							} else if (sg.getDays() != -1) {
								countOrDay = sg.getDays();
							} else {
								countOrDay = sg.getCount();
							}
						}
						if (list.size() == 1 && sg.getChance() < 200) {
							if (countOrDay == -1) {
								noticeStr = itemName[i] + " X " + TipMessages.INFINITE;
							} else {
								noticeStr = itemName[i] + " X " + countOrDay;
							}
						}
					}

					itemNum[i] = Math.max(sg.getCount(), sg.getDays());
					++i;

				}
				if (noticeStr.length() != 0) {
					ServiceManager
							.getManager()
							.getChatService()
							.sendBulletinToWorld(
									TipMessages.SPREE_NOTICE.replace("XXX", player.getName()).replace("YYY", pifs.getShopItem().getName())
											.replace("ZZZ", noticeStr), player.getName(), false);
				}

				pifs.setPLastNum(pifs.getPLastNum() - 1);
				ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
				// 更新玩家拥有的物品
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pifs);
				GetGiftOk getGiftOk = new GetGiftOk(data.getSessionId(), data.getSerial());
				getGiftOk.setItemName(itemName);
				getGiftOk.setItemIcon(itemIcon);
				getGiftOk.setItemNum(itemNum);
				session.write(getGiftOk);
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.GETGIFT_FAIL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}

	/**
	 * 判断卡牌栏位是否已满。卡牌栏位与玩家等级挂钩
	 * 
	 * @param list
	 * @param id
	 * @param level
	 * @param data
	 * @throws ProtocolException
	 */
	private void checkGift(List<SpreeGift> list, int id, int level, AbstractData data) throws ProtocolException {
		for (SpreeGift gift : list) {
			ShopItem item = ServiceManager.getManager().getShopItemService().getShopItemById(gift.getShopItem1().getId());
			if (item.getType() == Common.SHOP_ITEM_TYPE_ARMS_CARD) {
				int playerCardCount = ServiceManager.getManager().getPlayerItemsFromShopService().getPlayerCardCount(id);
				// 判断卡牌栏位是否已满。卡牌栏位与玩家等级挂钩
				if (level <= playerCardCount) {
					throw new ProtocolException(ErrorMessages.CARD_GRID_FULL, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
			}
		}
	}
}
