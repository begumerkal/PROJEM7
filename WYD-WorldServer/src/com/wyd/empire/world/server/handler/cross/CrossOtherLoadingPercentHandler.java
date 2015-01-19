package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherLoadingPercent;
import com.wyd.empire.protocol.data.cross.CrossOtherLoadingPercent;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送加载百份比
 * 
 * @author zguoqiu
 */
public class CrossOtherLoadingPercentHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossOtherLoadingPercentHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossOtherLoadingPercent colp = (CrossOtherLoadingPercent) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(colp.getRoomId());
			if (null != room) {
				OtherLoadingPercent olp = new OtherLoadingPercent();
				olp.setBattleId(colp.getBattleId());
				olp.setCurrentPlayerId(colp.getCurrentPlayerId());
				olp.setPercent(colp.getPercent());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()
							&& !seat.isRobot()
							&& ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()) != colp
									.getCurrentPlayerId()) {
						olp.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(olp);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}