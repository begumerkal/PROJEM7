package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherFly;
import com.wyd.empire.protocol.data.cross.CrossOtherFly;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家飞行
 * 
 * @author zguoqiu
 */
public class CrossOtherFlyHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossOtherFlyHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossOtherFly cof = (CrossOtherFly) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(cof.getRoomId());
			if (null != room) {
				OtherFly otherFly = new OtherFly();
				otherFly.setBattleId(cof.getBattleId());
				otherFly.setCurrentPlayerId(cof.getCurrentPlayerId());
				otherFly.setSpeedx(cof.getSpeedx());
				otherFly.setSpeedy(cof.getSpeedy());
				otherFly.setLeftRight(cof.getLeftRight());
				otherFly.setIsEquip(cof.getIsEquip());
				otherFly.setStartx(cof.getStartx());
				otherFly.setStarty(cof.getStarty());
				otherFly.setPlayerCount(cof.getPlayerCount());
				otherFly.setPlayerIds(cof.getPlayerIds());
				otherFly.setCurPositionX(cof.getCurPositionX());
				otherFly.setCurPositionY(cof.getCurPositionY());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()
							&& !seat.isRobot()
							&& ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()) != cof
									.getCurrentPlayerId()) {
						otherFly.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(otherFly);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}