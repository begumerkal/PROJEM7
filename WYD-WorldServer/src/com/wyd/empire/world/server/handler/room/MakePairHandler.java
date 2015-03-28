package com.wyd.empire.world.server.handler.room;

import java.util.HashSet;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.room.MakePair;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.RandomRoom;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 生成战斗组
 * 
 * @author zguoqiu
 */
public class MakePairHandler implements IDataHandler {
	Logger log = Logger.getLogger(MakePairHandler.class);

	// 生成战斗组
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer worldPlayer = session.getPlayer(data.getSessionId());
		MakePair makePair = (MakePair) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(makePair.getRoomId());
			if (null == worldPlayer || null == room || 1 == room.getBattleStatus()) {
				return;
			}
			if (3 == room.getBattleMode()) {
				if (room.getPlayerNum() < 2) {
					throw new ProtocolException(ErrorMessages.ROOM_PNR_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				for (Seat seat : room.getPlayerList()) {
					if (seat.isUsed() && null == seat.getPlayer())
						seat.setReady(true);
				}
			} else {
				if (1 == room.getStartMode()) {
					for (Seat seat : room.getPlayerList()) {
						if (seat.isUsed() && null == seat.getPlayer())
							seat.setReady(true);
					}
				} else {
					int camp0 = 0;
					int camp1 = 0;
					for (Seat seat : room.getPlayerList()) {
						if (null != seat.getPlayer()) {
							if (0 == seat.getCamp()) {
								camp0++;
							} else {
								camp1++;
							}
						}
					}
					if (camp0 == camp1 && camp0 != room.getPlayerNumMode()) {
						for (Seat seat : room.getPlayerList()) {
							if (seat.isUsed() && null == seat.getPlayer())
								seat.setReady(true);
						}
					}
				}
			}
			if (!ServiceManager.getManager().getRoomService().isAllReady(makePair.getRoomId())) {
				throw new ProtocolException(ErrorMessages.ROOM_PNR_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (5 == room.getBattleMode() && room.getPlayerNum() < 2) {
				throw new ProtocolException(ErrorMessages.COMMUNITY_CANNOT_START, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			room.setBattleStatus(1);
			List<WorldPlayer> playerList = ServiceManager.getManager().getRoomService().getPlayerList(makePair.getRoomId());
			for (WorldPlayer player : playerList) {
				if (null != player && player.getId() != room.getWnersId()) {
					player.sendData(makePair);
				}
			}
			if (1 == room.getStartMode() && !room.isNewPlayerRoom()) {
				RandomRoom randomRoom = new RandomRoom();
				randomRoom.setRoom(room);
				if (1 == room.getServiceMode() && WorldServer.config.isCross()) {
					ServiceManager.getManager().getCrossService().sendPairInfo(room);
				} else {
					ServiceManager.getManager().getPairService().addRandomRoom(randomRoom);
				}
			} else {
				int battleId = ServiceManager.getManager().getBattleTeamService().createBattleTeam(room.getStartMode());
				for (Seat seat : room.getPlayerList()) {
					if (null != seat.getPlayer()) {
						ServiceManager.getManager().getBattleTeamService()
								.enBattleTeam(battleId, seat.getPlayer(), seat.getCamp(), seat.isRobot());
						if (room.isNewPlayerRoom()) {
							WorldPlayer robot = ServiceManager.getManager().getRobotService()
									.getRobot(room.getAvgLevel(), room.getAvgFighting(), new HashSet<String>(), room.getRoomChannel());
							ServiceManager.getManager().getBattleTeamService().enBattleTeam(battleId, robot, 1, true);
						}
					}
				}
				ServiceManager.getManager().getBattleTeamService().startBattle(battleId, room, room.getRoomType());
			}
			GameLogService.startGame(worldPlayer.getId(), worldPlayer.getLevel());
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_PNR_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
