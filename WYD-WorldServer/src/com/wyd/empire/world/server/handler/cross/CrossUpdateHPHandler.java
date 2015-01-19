package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.UpdateHP;
import com.wyd.empire.protocol.data.cross.CrossUpdateHP;
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
public class CrossUpdateHPHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossUpdateHPHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossUpdateHP cuhp = (CrossUpdateHP) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(cuhp.getRoomId());
			if (null != room) {
				UpdateHP updateHP = new UpdateHP();
				updateHP.setBattleId(cuhp.getBattleId());
				updateHP.setHurtcount(cuhp.getHurtcount());
				updateHP.setPlayerIds(cuhp.getPlayerIds());
				updateHP.setHp(cuhp.getHp());
				updateHP.setAttackType(cuhp.getAttackType());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && !seat.isRobot()) {
						seat.getPlayer().sendData(updateHP);
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}