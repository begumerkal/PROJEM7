package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.PlayerLose;
import com.wyd.empire.protocol.data.cross.CrossPlayerLose;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家掉线
 * 
 * @author zguoqiu
 */
public class CrossPlayerLoseHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossPlayerLoseHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossPlayerLose cpr = (CrossPlayerLose) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(cpr.getRoomId());
			if (null != room) {
				PlayerLose playerLose = new PlayerLose();
				playerLose.setBattleId(cpr.getBattleId());
				playerLose.setCurrentPlayerId(cpr.getCurrentPlayerId());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && !seat.isRobot()) {
						playerLose.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(playerLose);
						if (ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()) == cpr
								.getCurrentPlayerId()) {
							ServiceManager.getManager().getPlayerService().clearPlayer(seat.getPlayer());
						}
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}