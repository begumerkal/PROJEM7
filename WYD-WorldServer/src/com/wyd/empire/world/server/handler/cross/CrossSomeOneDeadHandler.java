package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.SomeOneDead;
import com.wyd.empire.protocol.data.cross.CrossSomeOneDead;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家死亡
 * 
 * @author zguoqiu
 */
public class CrossSomeOneDeadHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossSomeOneDeadHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossSomeOneDead csod = (CrossSomeOneDead) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(csod.getRoomId());
			if (null != room) {
				WorldPlayer player = null;
				SomeOneDead someOneDead = new SomeOneDead();
				someOneDead.setBattleId(csod.getBattleId());
				someOneDead.setDeadPlayerCount(csod.getDeadPlayerCount());
				someOneDead.setPlayerIds(csod.getPlayerIds());
				someOneDead.setFirstBlood(csod.getFirstBlood());
				someOneDead.setPlayerId(csod.getPlayerId());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && !seat.isRobot()) {
						seat.getPlayer().sendData(someOneDead);
						if (null == player
								&& ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()) == csod
										.getPlayerId()) {
							player = seat.getPlayer();
						}
					}
				}
				if (null != player) {
					ServiceManager.getManager().getTaskService()
							.battleBeatTask(player, csod.getShootSex(), csod.getShootCamp(), csod.getDeadSex(), csod.getDeadCamp());
					ServiceManager
							.getManager()
							.getTitleService()
							.battleBeatTask(player, csod.getShootSex(), csod.getShootCamp(), csod.getPlayerIds(), csod.getDeadSex(),
									csod.getDeadCamp(), csod.getKillType());
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}