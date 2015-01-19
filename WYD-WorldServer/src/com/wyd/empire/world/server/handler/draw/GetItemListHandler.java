package com.wyd.empire.world.server.handler.draw;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.draw.GetItemList;
import com.wyd.empire.protocol.data.draw.SendItemList;
import com.wyd.empire.world.bean.DrawItem;
import com.wyd.empire.world.bean.DrawType;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * @author Administrator
 */
public class GetItemListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetItemListHandler.class);

	/**
	 * 获得抽奖物品
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		GetItemList getItemList = (GetItemList) data;
		try {
			player.initDrawData();
			int typeId = getItemList.getTypeId();
			List<DrawItem> list = ServiceManager.getManager().getDrawService().toGetRewardByPlayerId(player, typeId);
			DrawType dt = ServiceManager.getManager().getDrawService().getDrawItemByItemId(typeId);
			Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
			if (null == player.getDrawNumMap().get(typeId)) {
				player.getDrawNumMap().put(typeId, 0);
			}
			int price = dt.getCostNum() + player.getDrawNumMap().get(typeId) * dt.getAccumulatedValue();
			// int starNum = player.getStarNum();
			int starNum = null == player.getStarNumMap().get(typeId) ? 0 : player.getStarNumMap().get(typeId);
			if (null == player.getRefreshNumMap().get(typeId)) {
				player.getRefreshNumMap().put(typeId, 0);
			}
			int freeNum = (map.get("freeNum") - player.getRefreshNumMap().get(typeId)) > 0 ? map.get("freeNum")
					- player.getRefreshNumMap().get(typeId) : 0;
			int refreshCost = (map.get("freeNum") - player.getRefreshNumMap().get(typeId)) > 0 ? 0 : map.get("refreshCost")
					+ (player.getRefreshNumMap().get(typeId) - map.get("freeNum")) * map.get("refreshAdd");
			String miniIcon = dt.getMiniIcon();
			int totalNum = 0;
			// 抽奖类型
			if (typeId == Common.GOLDID) {// 金币
				totalNum = player.getMoney();
			} else if (typeId == Common.DIAMONDID) {// 钻石
				totalNum = player.getDiamond();
			} else if (typeId == Common.BADGEID) {// 勋章
				totalNum = player.getMedalNum();
			} else {// 其他
				PlayerItemsFromShop p = ServiceManager.getManager().getPlayerItemsFromShopService()
						.uniquePlayerItem(player.getId(), typeId);
				totalNum = p == null ? 0 : p.getPLastNum();
			}
			int[] itemId = new int[list.size()];
			int[] itemNum = new int[list.size()];

			int index = 0;
			ShopItem si;
			for (DrawItem di : list) {
				if (player.getPlayer().getSex() == 0) {
					si = di.getDrawReward().getShopitem();
				} else {
					si = di.getDrawReward().getShopitem2();
				}
				itemId[index] = si.getId();
				itemNum[index] = di.getDrawReward().getNum();
				index++;
			}

			SendItemList sendItemList = new SendItemList(data.getSessionId(), data.getSerial());
			sendItemList.setFreeNum(freeNum);
			sendItemList.setItemNum(itemNum);
			sendItemList.setMiniIcon(miniIcon);
			sendItemList.setPrice(price);
			sendItemList.setRefreshCost(refreshCost);
			sendItemList.setStarNum(starNum);
			sendItemList.setTotalNum(totalNum);
			sendItemList.setItemId(itemId);
			session.write(sendItemList);

		} catch (Exception ex) {
			ex.printStackTrace();
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
