package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherPlayerMove;
import com.wyd.empire.protocol.data.cross.CrossOtherPlayerMove;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家移动
 * 
 * @author zguoqiu
 */
public class CrossOtherPlayerMoveHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossOtherPlayerMoveHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossOtherPlayerMove copm = (CrossOtherPlayerMove) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(copm.getRoomId());
			if (null != room) {
				OtherPlayerMove opm = new OtherPlayerMove();
				opm.setBattleId(copm.getBattleId());
				opm.setCurrentPlayerId(copm.getCurrentPlayerId());
				opm.setMovecount(copm.getMovecount());
				opm.setMovestep(copm.getMovestep());
				opm.setCurPositionX(copm.getCurPositionX());
				opm.setCurPositionY(copm.getCurPositionY());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()
							&& !seat.isRobot()
							&& ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()) != copm
									.getCurrentPlayerId()) {
						opm.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(opm);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}