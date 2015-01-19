package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.GotoBattle;
import com.wyd.empire.protocol.data.cross.CrossGotoBattle;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 开始战斗
 * 
 * @author zguoqiu
 */
public class CrossGotoBattleHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossGotoBattleHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossGotoBattle cgb = (CrossGotoBattle) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(cgb.getRoomId());
			if (null != room) {
				GotoBattle gotoBattle = new GotoBattle();
				gotoBattle.setBattleId(cgb.getBattleId());
				gotoBattle.setWind(cgb.getWind());
				gotoBattle.setCurrentPlayerId(cgb.getCurrentPlayerId());
				gotoBattle.setAttackRate(cgb.getAttackRate());
				gotoBattle.setBattleRand(cgb.getBattleRand());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && !seat.isRobot()) {
						gotoBattle.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(gotoBattle);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}