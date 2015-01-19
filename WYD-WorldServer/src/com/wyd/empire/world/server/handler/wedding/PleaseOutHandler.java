package com.wyd.empire.world.server.handler.wedding;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.wedding.ExtWeddingOk;
import com.wyd.empire.protocol.data.wedding.PleaseOut;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WeddingRoom;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.impl.MarryService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 踢出玩家
 * 
 * @author Administrator
 */
public class PleaseOutHandler implements IDataHandler {
	Logger log = Logger.getLogger(PleaseOutHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		PleaseOut pleaseOut = (PleaseOut) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			String wedNum = pleaseOut.getWedNum();
			int playerId = pleaseOut.getPlayerId();

			WeddingRoom weddingRoom = MarryService.weddingMap.get(wedNum);
			if (null == weddingRoom) {
				return;
			}

			// 只有新郎新娘才能操作
			if (player.getId() == weddingRoom.getWedHall().getManId() || player.getId() == weddingRoom.getWedHall().getWomanId()) {
				WorldPlayer wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
				weddingRoom.getPlayerList().remove(wp);
				// PleaseOutOk pleaseOutOk = new
				// PleaseOutOk(data.getSessionId(),data.getSerial());
				ExtWeddingOk pleaseOutOk = new ExtWeddingOk(data.getSessionId(), data.getSerial());
				pleaseOutOk.setPlayerId(playerId);
				wp.sendData(pleaseOutOk);
				// 给新郎新娘刷新
				wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getManId());
				wp.sendData(pleaseOutOk);
				wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getWomanId());
				wp.sendData(pleaseOutOk);

				// 给宾客刷新
				for (WorldPlayer worldPlayer : weddingRoom.getPlayerList()) {
					worldPlayer.sendData(pleaseOutOk);
				}

				// 给操作人刷新宾客列表
				// GetJoinList getJoinList = new
				// GetJoinList(data.getSessionId(), data.getSerial());
				// getJoinList.setHandlerSource(data.getHandlerSource());
				// getJoinList.setSource(data.getSource());
				// getJoinList.setWedNum(wedNum);
				//
				// GetJoinListHandler getJoinListHandler = new
				// GetJoinListHandler();
				// getJoinListHandler.handle(getJoinList);
			}

		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
