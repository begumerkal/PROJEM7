package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherBigSkill;
import com.wyd.empire.protocol.data.cross.CrossOtherBigSkill;
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
public class CrossOtherBigSkillHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossOtherBigSkillHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossOtherBigSkill cobs = (CrossOtherBigSkill) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(cobs.getRoomId());
			if (null != room) {
				OtherBigSkill obs = new OtherBigSkill();
				obs.setBattleId(cobs.getBattleId());
				obs.setCurrentPlayerId(cobs.getCurrentPlayerId());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()
							&& !seat.isRobot()
							&& ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()) != obs
									.getCurrentPlayerId()) {
						obs.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(obs);
					}
				}
				Seat playerSeat = ServiceManager.getManager().getRoomService()
						.getSeat(cobs.getRoomId(), ServiceManager.getManager().getCrossService().getPlayerId(obs.getCurrentPlayerId()));
				if (null == playerSeat || null == playerSeat.getPlayer()) {
					return;
				}
				// 更新任务
				if (!playerSeat.isRobot()) {
					ServiceManager.getManager().getTaskService().useBigSkill(playerSeat.getPlayer());
					ServiceManager.getManager().getTitleService().useBigSkill(playerSeat.getPlayer());
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}