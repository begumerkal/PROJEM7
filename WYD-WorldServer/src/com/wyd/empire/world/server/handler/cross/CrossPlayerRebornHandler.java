package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.PlayerReborn;
import com.wyd.empire.protocol.data.cross.CrossPlayerReborn;
import com.wyd.empire.world.player.WorldPlayer;
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
public class CrossPlayerRebornHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossPlayerRebornHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossPlayerReborn cpr = (CrossPlayerReborn) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(cpr.getRoomId());
			if (null != room) {
				PlayerReborn playerReborn = new PlayerReborn();
				playerReborn.setBattleId(cpr.getBattleId());
				playerReborn.setPlayercount(cpr.getPlayercount());
				playerReborn.setPlayerIds(cpr.getPlayerIds());
				playerReborn.setPostionX(cpr.getPostionX());
				playerReborn.setPostionY(cpr.getPostionY());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && !seat.isRobot()) {
						playerReborn.setPlayerId(ServiceManager.getManager().getCrossService().getCrossPlayerId(seat.getPlayer().getId()));
						seat.getPlayer().sendData(playerReborn);
					}
				}
				for (int i = 0; i < cpr.getPlayerIds().length; i++) {
					if (!cpr.getRobot()[i]) {
						WorldPlayer player = ServiceManager.getManager().getPlayerService()
								.getOnlineWorldPlayer(ServiceManager.getManager().getCrossService().getPlayerId(cpr.getPlayerIds()[i]));
						if (null != player) {
							ServiceManager.getManager().getTitleService().fuHuo(player);
						}
					}
				}
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}