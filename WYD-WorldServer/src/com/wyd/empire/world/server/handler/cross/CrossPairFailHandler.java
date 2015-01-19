package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.cross.CrossPairFail;
import com.wyd.empire.protocol.data.room.MakePairFail;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 跨服对战配对失败
 * 
 * @author zguoqiu
 */
public class CrossPairFailHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossPairFailHandler.class);
	private MakePairFail makePairFail = new MakePairFail();

	public void handle(AbstractData data) throws Exception {
		CrossPairFail pairFail = (CrossPairFail) data;
		try {
			// System.out.println("pairFail:-------------" +
			// pairFail.getRoomId());
			Room room = ServiceManager.getManager().getRoomService().getRoom(pairFail.getRoomId());
			if (null != room) {
				// if (room.getBattleMode() == 6 ||
				// ServiceManager.getManager().getPlayerService().getLostPlayerCount(room.getRoomChannel())
				// < (room.getPlayerNumMode() * 2)) {
				room.setBattleStatus(0);
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer() && !seat.isRobot()) {
						seat.getPlayer().sendData(makePairFail);
					}
				}
				// } else {
				// int battleId =
				// ServiceManager.getManager().getBattleTeamService().createBattleTeam();
				// try {
				// List<Seat> seatList = room.getPlayerList();
				// Set<Integer> idMap = new HashSet<Integer>();
				// int count = 0;
				// int fighting = 0;
				// for (Seat seat : seatList) {
				// if (null != seat.getPlayer()) {
				// idMap.add(seat.getPlayer().getPlayer().getId());
				// fighting += seat.getPlayer().getFighting();
				// count++;
				// }
				// }
				// if (count < 1) {
				// ServiceManager.getManager().getRoomService().deletRoom(room);
				// return;
				// }
				// fighting = fighting / count;
				// for (Seat seat : seatList) {
				// if (null != seat.getPlayer()) {
				// ServiceManager.getManager().getBattleTeamService().enBattleTeam(battleId,
				// seat.getPlayer(), 0, seat.isRobot());
				// // 加入机器人<还需添加防止重复机器人的代码>
				// WorldPlayer worldPlayer;
				// if (room.isNewPlayerRoom()) {
				// worldPlayer =
				// ServiceManager.getManager().getPlayerService().getNewRobotPlayer();
				// } else {
				// worldPlayer =
				// ServiceManager.getManager().getPlayerService().getLostPlayer(idMap,
				// room.getRoomChannel(), fighting);
				// }
				// idMap.add(worldPlayer.getPlayer().getId());
				// ServiceManager.getManager().getBattleTeamService().enBattleTeam(battleId,
				// worldPlayer, 1, true);
				// }
				// }
				// idMap = null;
				// ServiceManager.getManager().getBattleTeamService().startBattle(battleId,
				// room, 1);
				// } catch (Exception e) {
				// BattleTeam battleTeam =
				// ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				// if (null != battleTeam) {
				// ServiceManager.getManager().getBattleTeamService().deleteBattleTeam(battleTeam);
				// battleTeam = null;
				// }
				// room.setBattleStatus(0);
				// for (Seat seat : room.getPlayerList()) {
				// if (null != seat.getPlayer() && !seat.isRobot()) {
				// seat.getPlayer().sendData(makePairFail);
				// }
				// }
				// }
				// }
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}