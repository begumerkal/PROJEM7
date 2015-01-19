package com.wyd.empire.world.server.handler.draw;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.draw.GetItemList;
import com.wyd.empire.protocol.data.draw.GetReward;
import com.wyd.empire.world.bean.DrawItem;
import com.wyd.empire.world.bean.DrawType;
import com.wyd.empire.world.bean.ShopItem;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
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
public class GetRewardHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetRewardHandler.class);

	/**
	 * 领奖
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		GetReward getReward = (GetReward) data;
		try {
			int starNum = getReward.getStarNum();
			int typeId = getReward.getTypeId();
			if (null == player.getStarNumMap()) {
				player.getStarNumMap().put(typeId, 0);
			}
			if (starNum > player.getStarNumMap().get(typeId)) {
				log.info("玩家抽奖异常--领取奖励的星数--" + starNum + "--玩家拥有星数--" + player.getStarNumMap().get(typeId) + "---" + typeId);
				throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			player.setDrawCanRefresh(typeId, true);
			DrawItem di = ServiceManager.getManager().getDrawService().getDrawRewardByPlayerIdAndStarNum(player.getId(), starNum, typeId);
			ShopItem si;
			if (player.getPlayer().getSex() == 0) {
				si = di.getDrawReward().getShopitem();
			} else {
				si = di.getDrawReward().getShopitem2();
			}
			int num = di.getDrawReward().getNum();
			player.getStarNumMap().put(typeId, 0);
			player.getDrawNumMap().put(typeId, 0);
			player.getDrawFailNumMap().put(typeId, 0);
			ServiceManager.getManager().getDrawService().savePlayerDraw(player);
			if (si.getId() == Common.GOLDID) {// 金币
				ServiceManager.getManager().getPlayerService().updatePlayerGold(player, num, "抽奖系统", "-- " + " --");
			} else if (si.getId() == Common.DIAMONDID) {// 钻石
				ServiceManager.getManager().getPlayerService().addTicket(player, num, 0, TradeService.ORIGIN_DRAW, 0, "", "抽奖系统", "", "");
			} else {
				if (si.getUseType() == 0) {
					ServiceManager.getManager().getPlayerItemsFromShopService()
							.playerGetItem(player.getId(), si.getId(), -1, -1, num, 21, null, 0, 0, 0);
				} else {
					ServiceManager.getManager().getPlayerItemsFromShopService()
							.playerGetItem(player.getId(), si.getId(), -1, num, -1, 21, null, 0, 0, 0);
				}
			}
			// if(null!=player.getStarNumMap()){
			// player.getStarNumMap().put(typeId, 0);
			// }
			// player.getRefreshNumMap().put(typeId, 0);
			ServiceManager.getManager().getDrawService().updateDrawItem(player.getId(), typeId, starNum, di);
			if (di.getDrawReward().getAnnouncement() == 1) {// 发送公告
				DrawType dt = ServiceManager.getManager().getDrawService().getDrawItemByItemId(typeId);
				ServiceManager
						.getManager()
						.getChatService()
						.sendBulletinToWorld(
								TipMessages.DRAW_NOTICE.replace("XXX", player.getName()).replace("YYY", dt.getName())
										.replace("[AAA]", "[" + si.getName() + "]" + "X" + num), player.getName(), false);
			}
			GetItemList getItemList = new GetItemList(data.getSessionId(), data.getSerial());
			getItemList.setHandlerSource(data.getHandlerSource());
			getItemList.setSource(data.getSource());
			getItemList.setTypeId(typeId);
			GetItemListHandler getItemListHandler = new GetItemListHandler();
			getItemListHandler.handle(getItemList);
			GameLogService.exploreReward(player.getId(), player.getLevel(), typeId, player.getStarNumMap().get(typeId), si.getId() + "*"
					+ num);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
