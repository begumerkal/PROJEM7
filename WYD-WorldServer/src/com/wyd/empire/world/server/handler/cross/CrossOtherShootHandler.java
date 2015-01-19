package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherShoot;
import com.wyd.empire.protocol.data.cross.CrossOtherShoot;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家使用大招
 * 
 * @author zguoqiu
 */
public class CrossOtherShootHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossOtherShootHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossOtherShoot cos = (CrossOtherShoot) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(cos.getRoomId());
			if (null != room) {
				OtherShoot otherShoot = new OtherShoot();
				otherShoot.setBattleId(cos.getBattleId());
				otherShoot.setCurrentPlayerId(cos.getCurrentPlayerId());
				otherShoot.setSpeedx(cos.getSpeedx());
				otherShoot.setSpeedy(cos.getSpeedy());
				otherShoot.setLeftRight(cos.getLeftRight());
				otherShoot.setStartx(cos.getStartx());
				otherShoot.setStarty(cos.getStarty());
				otherShoot.setPlayerCount(cos.getPlayerCount());
				otherShoot.setPlayerIds(cos.getPlayerIds());
				otherShoot.setCurPositionX(cos.getCurPositionX());
				otherShoot.setCurPositionY(cos.getCurPositionY());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()
							&& !seat.isRobot()
							&& ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()) != otherShoot
									.getCurrentPlayerId()) {
						otherShoot.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(otherShoot);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}