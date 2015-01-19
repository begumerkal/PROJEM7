package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.UpdateMedal;
import com.wyd.empire.protocol.data.cross.CrossUpdateMedal;
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
public class CrossUpdateMedalHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossUpdateMedalHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossUpdateMedal cum = (CrossUpdateMedal) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(cum.getRoomId());
			if (null != room) {
				UpdateMedal updateMedal = new UpdateMedal();
				updateMedal.setBattleId(cum.getBattleId());
				updateMedal.setCampCount(cum.getCampCount());
				updateMedal.setCampId(cum.getCampId());
				updateMedal.setCampMedalNum(cum.getCampMedalNum());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && !seat.isRobot()) {
						updateMedal.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(updateMedal);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}