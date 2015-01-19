package com.wyd.empire.world.server.handler.draw;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.draw.DrawRefresh;
import com.wyd.empire.protocol.data.draw.GetItemList;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
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
public class DrawRefreshHandler implements IDataHandler {
	Logger log = Logger.getLogger(DrawRefreshHandler.class);

	/**
	 * 刷新抽奖
	 */
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		DrawRefresh drawRefresh = (DrawRefresh) data;
		boolean mark = false;
		try {
			int typeId = drawRefresh.getTypeId();
			if (!player.isDrawCanRefresh(typeId)) { // 如果当前不允许刷新则不刷新
				return;
			}
			Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
			if (null == player.getRefreshNumMap().get(typeId)) {
				player.getRefreshNumMap().put(typeId, 0);
			}
			int refreshCost = (map.get("freeNum") - player.getRefreshNumMap().get(typeId)) > 0 ? 0 : map.get("refreshCost")
					+ (player.getRefreshNumMap().get(typeId) - map.get("freeNum")) * map.get("refreshAdd");
			if (refreshCost > player.getDiamond()) {
				mark = true;
				throw new Exception(Common.ERRORKEY + ErrorMessages.COMMUNITY_NEEDDEMOND_MESSAGE);
			}
			player.getRefreshNumMap().put(typeId, player.getRefreshNumMap().get(typeId) + 1);
			ServiceManager.getManager().getDrawService().refreshDrawItem(player.getId(), typeId, 0);

			if (refreshCost > 0) {
				ServiceManager.getManager().getPlayerService()
						.useTicket(player, refreshCost, TradeService.ORIGIN_DRAWREF, null, null, "抽奖刷新使用");
			}

			GetItemList getItemList = new GetItemList(data.getSessionId(), data.getSerial());
			getItemList.setHandlerSource(data.getHandlerSource());
			getItemList.setSource(data.getSource());
			getItemList.setTypeId(typeId);

			GetItemListHandler getItemListHandler = new GetItemListHandler();
			getItemListHandler.handle(getItemList);

			ServiceManager.getManager().getDrawService().savePlayerDraw(player);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				throw new ProtocolException(ErrorMessages.COMMUNITY_GETLIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
		}
	}
}
