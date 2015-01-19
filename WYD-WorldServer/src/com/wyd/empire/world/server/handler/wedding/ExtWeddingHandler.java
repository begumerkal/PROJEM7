package com.wyd.empire.world.server.handler.wedding;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.ExtWedding;
import com.wyd.empire.protocol.data.wedding.ExtWeddingOk;
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
 * 退出婚礼现场
 * 
 * @author Administrator
 */
public class ExtWeddingHandler implements IDataHandler {
	Logger log = Logger.getLogger(ExtWeddingHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		ExtWedding extWedding = (ExtWedding) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			String wedNum = extWedding.getWedNum();
			WeddingRoom weddingRoom = MarryService.weddingMap.get(wedNum);
			if (null == weddingRoom) {
				return;
			}
			ExtWeddingOk extWeddingOk = new ExtWeddingOk(data.getSessionId(), data.getSerial());
			extWeddingOk.setPlayerId(player.getId());
			// 宾客退出则给现场每个人都刷新
			if (player.getId() != weddingRoom.getWedHall().getManId() && player.getId() != weddingRoom.getWedHall().getWomanId()) {
				// 给宾客发送
				for (WorldPlayer wp : weddingRoom.getPlayerList()) {
					wp.sendData(extWeddingOk);
				}
				// 给新郎发送
				WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService()
						.getWorldPlayerById(weddingRoom.getWedHall().getManId());
				worldPlayer.sendData(extWeddingOk);
				// 给新娘发送
				worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(weddingRoom.getWedHall().getWomanId());
				worldPlayer.sendData(extWeddingOk);
			} else {
				// 新人退出，就只发给自己显示操作成功
				session.write(extWeddingOk);
				if (player.getId() == weddingRoom.getWedHall().getManId()) {
					// 记录新郎累计时间
					weddingRoom.setBgTime(System.currentTimeMillis() - weddingRoom.getBgInTime());
				} else if (player.getId() == weddingRoom.getWedHall().getWomanId()) {
					// 记录新娘累计时间
					weddingRoom.setbInTime(System.currentTimeMillis());
					weddingRoom.setbTime(System.currentTimeMillis() - weddingRoom.getbInTime());
				}
			}
			weddingRoom.getPlayerList().remove(player);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
