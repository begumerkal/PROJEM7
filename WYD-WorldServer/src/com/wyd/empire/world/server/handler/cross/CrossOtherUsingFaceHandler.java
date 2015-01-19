package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherUsingFace;
import com.wyd.empire.protocol.data.cross.CrossOtherUsingFace;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送使用的表情
 * 
 * @author zguoqiu
 */
public class CrossOtherUsingFaceHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossOtherUsingFaceHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossOtherUsingFace couf = (CrossOtherUsingFace) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(couf.getRoomId());
			if (null != room) {
				OtherUsingFace otherUsingFace = new OtherUsingFace();
				otherUsingFace.setBattleId(couf.getBattleId());
				otherUsingFace.setCurrentPlayerId(couf.getCurrentPlayerId());
				otherUsingFace.setFaceId(couf.getFaceId());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()
							&& !seat.isRobot()
							&& ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()) != couf
									.getPlayerId()) {
						otherUsingFace
								.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(otherUsingFace);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}