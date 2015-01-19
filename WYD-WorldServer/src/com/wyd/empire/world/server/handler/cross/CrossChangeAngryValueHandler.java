package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.ChangeAngryValue;
import com.wyd.empire.protocol.data.cross.CrossChangeAngryValue;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 更新玩家怒气值
 * 
 * @author zguoqiu
 */
public class CrossChangeAngryValueHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossChangeAngryValueHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossChangeAngryValue ccav = (CrossChangeAngryValue) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(ccav.getRoomId());
			if (null != room) {
				ChangeAngryValue cav = new ChangeAngryValue();
				cav.setBattleId(ccav.getBattleId());
				cav.setPlayerId(ccav.getPlayerId());
				cav.setAngryValue(ccav.getAngryValue());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && !seat.isRobot()) {
						seat.getPlayer().sendData(cav);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}