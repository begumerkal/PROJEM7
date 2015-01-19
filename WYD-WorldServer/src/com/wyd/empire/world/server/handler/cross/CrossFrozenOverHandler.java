package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.FrozenOver;
import com.wyd.empire.protocol.data.cross.CrossFrozenOver;
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
public class CrossFrozenOverHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossFrozenOverHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossFrozenOver cfo = (CrossFrozenOver) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(cfo.getRoomId());
			if (null != room) {
				FrozenOver frozenOver = new FrozenOver();
				frozenOver.setPlayerIds(cfo.getPlayerIds());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && !seat.isRobot()) {
						seat.getPlayer().sendData(frozenOver);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}