package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherPass;
import com.wyd.empire.protocol.data.cross.CrossOtherPass;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 跳过本轮操作
 * 
 * @author zguoqiu
 */
public class CrossOtherPassHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossOtherPassHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossOtherPass cop = (CrossOtherPass) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(cop.getRoomId());
			if (null != room) {
				OtherPass otherPass = new OtherPass();
				otherPass.setBattleId(cop.getBattleId());
				otherPass.setCurrentPlayerId(cop.getCurrentPlayerId());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()
							&& !seat.isRobot()
							&& ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()) != cop
									.getCurrentPlayerId()) {
						otherPass.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(otherPass);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}