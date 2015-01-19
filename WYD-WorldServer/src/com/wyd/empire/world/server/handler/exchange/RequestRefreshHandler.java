package com.wyd.empire.world.server.handler.exchange;

import java.util.Date;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.exchange.RequestRefresh;
import com.wyd.empire.protocol.data.exchange.ResponseRefresh;
import com.wyd.empire.world.bean.PlayerItemsFromShop;
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
 * 兑换刷新
 * 
 * @author Administrator
 */
public class RequestRefreshHandler implements IDataHandler {
	Logger log = Logger.getLogger(RequestRefreshHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		RequestRefresh requestRefresh = (RequestRefresh) data;
		boolean mark = false;
		try {
			int payType = requestRefresh.getPayType();
			int payNum = requestRefresh.getPayType() == 0
					? ServiceManager.getManager().getVersionService().getVersion().getReDramond()
					: ServiceManager.getManager().getVersionService().getVersion().getReBadge();
			PlayerItemsFromShop pifs = ServiceManager.getManager().getPlayerItemsFromShopService().getBadgeByPlayerId(player.getId());
			if (payType == 0 && payNum > player.getDiamond()) {
				mark = true;
				throw new Exception(Common.ERRORKEY + ErrorMessages.TRATE_LOWTICKETNUM_MESSAGE);
			} else if (payType == 1 && (payNum > player.getMedalNum() || null == pifs || payNum > pifs.getPLastNum())) {
				mark = true;
				throw new Exception(Common.ERRORKEY + ErrorMessages.TRATE_BADGEENOUGH_MESSAGE);
			}
			if (payType == 0) {
				ServiceManager.getManager().getPlayerService()
						.useTicket(player, payNum, TradeService.ORIGIN_EXCHANGEUPDATE, null, null, "兑换刷新");
			} else if (payType == 1) {
				player.setMedalNum(player.getMedalNum() - payNum);
				pifs.setPLastNum(pifs.getPLastNum() - payNum);
				ServiceManager.getManager().getPlayerItemsFromShopService().update(pifs);
				// 更新玩家拥有的物品
				ServiceManager.getManager().getPlayerItemsFromShopService().useItem(player, pifs);
				ServiceManager.getManager().getPlayerItemsFromShopService()
						.saveGetItemRecord(player.getPlayer().getId(), Common.BADGEID, -1, -1, -4, 1, null);
				ServiceManager.getManager().getPlayerItemsFromShopService().refreshExchange(player, new Date().getTime());
				ResponseRefresh responseRefresh = new ResponseRefresh(data.getSessionId(), data.getSerial());
				responseRefresh.setCode(0);
				responseRefresh.setMessage(TipMessages.REFRESHSUCCESS);
				session.write(responseRefresh);
			}
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (mark) {
				if (null != ex.getMessage())
					throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
			} else {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
		}
	}
}
