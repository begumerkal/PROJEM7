package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.CanStartCurRound;
import com.wyd.empire.protocol.data.cross.CrossCanStartCurRound;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 可以开始下一个玩家操作
 * 
 * @author zguoqiu
 */
public class CrossCanStartCurRoundHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossCanStartCurRoundHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossCanStartCurRound ccscr = (CrossCanStartCurRound) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(ccscr.getRoomId());
			if (null != room) {
				CanStartCurRound cscr = new CanStartCurRound();
				cscr.setBattleId(ccscr.getBattleId());
				cscr.setWind(ccscr.getWind());
				cscr.setCurrentPlayerId(ccscr.getCurrentPlayerId());
				cscr.setAttackRate(ccscr.getAttackRate());
				cscr.setIsNewRound(ccscr.getIsNewRound());
				cscr.setBattleRand(ccscr.getBattleRand());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && !seat.isRobot()) {
						cscr.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(cscr);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}