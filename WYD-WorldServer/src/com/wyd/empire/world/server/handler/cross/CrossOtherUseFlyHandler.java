package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherUseFly;
import com.wyd.empire.protocol.data.cross.CrossOtherUseFly;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 使用飞行技能
 * 
 * @author zguoqiu
 */
public class CrossOtherUseFlyHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossOtherUseFlyHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossOtherUseFly couf = (CrossOtherUseFly) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(couf.getRoomId());
			if (null != room) {
				OtherUseFly otherUseFly = new OtherUseFly();
				otherUseFly.setBattleId(couf.getBattleId());
				otherUseFly.setCurrentPlayerId(couf.getCurrentPlayerId());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()
							&& !seat.isRobot()
							&& ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()) != couf
									.getPlayerId()) {
						otherUseFly.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(otherUseFly);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}