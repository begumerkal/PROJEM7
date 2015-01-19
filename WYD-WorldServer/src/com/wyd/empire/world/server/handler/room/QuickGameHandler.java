package com.wyd.empire.world.server.handler.room;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.room.EnterRoom;
import com.wyd.empire.protocol.data.room.QuickGame;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 快速游戏
 * 
 * @author Administrator
 */
public class QuickGameHandler implements IDataHandler {
	Logger log = Logger.getLogger(QuickGameHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		QuickGame quickGame = (QuickGame) data;
		try {
			GameLogService.fastGame(player.getId(), player.getLevel());
			if (!player.isNewPlayer()) {
				List<Room> roomList = ServiceManager.getManager().getRoomService().getListRoom();
				List<Integer> randomList = new ArrayList<Integer>();
				for (Room room : roomList) {
					if ("-1".equals(room.getPassWord()) && !room.isFull() && 0 == room.getBattleStatus()
							&& room.getRoomChannel() == player.getBattleChannel() && player.checkPlayerRoom(room.getRoomId())) {
						randomList.add(room.getRoomId());
					}
				}
				Room room = null;
				int roomId = 0;
				if (randomList.size() > 0) {
					room = ServiceManager.getManager().getRoomService()
							.getRoom(randomList.get(ServiceUtils.getRandomNum(0, randomList.size())));
					player.enterRoom(room.getRoomId());
					roomId = room.getRoomId();
				} else {
					int playerNum = 0;
					int battleMode = 0;
					int playerNumMode = 0;
					int startMode = 0;
					while (playerNum < 1) {
						battleMode = ServiceUtils.getRandomBattleMode(player.getLevel());
						if (3 == battleMode) {
							playerNumMode = 3;
							startMode = 2;
						} else {
							playerNumMode = ServiceUtils.getPlayerNumMode(player.getLevel());
							startMode = ServiceUtils.getRandomNum(1, 3);
						}
						if (1 == startMode) {
							playerNum = playerNumMode;
						} else {
							playerNum = playerNumMode * 2;
						}
						playerNum--;
					}
					if (ServiceManager.getManager().getRobotService().getRobotCount(player.getBattleChannel()) < (playerNumMode * 2)) {
						throw new ProtocolException(ErrorMessages.ROOM_RNF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
								data.getSubType());
					}
					Set<String> idMap = new HashSet<String>();
					for (int i = 0; i < playerNum; i++) {
						WorldPlayer worldPlayer = ServiceManager.getManager().getRobotService().getRobot(player, idMap);
						idMap.add(worldPlayer.getPlayer().getId().toString());
						if (null == room) {
							roomId = ServiceManager
									.getManager()
									.getRoomService()
									.createRoom(worldPlayer, TipMessages.ROOMNAME1, battleMode, playerNumMode, "-1", startMode,
											Room.BATTLE_TYPE_MACHINE, 0, false, true);
						}
						int result = ServiceManager.getManager().getRoomService().enRoom(roomId, worldPlayer, true);
						switch (result) {
							case -1 :
								throw new ProtocolException(ErrorMessages.ROOM_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(),
										data.getType(), data.getSubType());
							case -2 :
								throw new ProtocolException(ErrorMessages.ROOM_BHS_MESSAGE, data.getSerial(), data.getSessionId(),
										data.getType(), data.getSubType());
							case -3 :
								throw new ProtocolException(ErrorMessages.ROOM_FULL_MESSAGE, data.getSerial(), data.getSessionId(),
										data.getType(), data.getSubType());
							case -4 :
								throw new ProtocolException(ErrorMessages.CANNOT_START_CONBATTLE, data.getSerial(), data.getSessionId(),
										data.getType(), data.getSubType());
						}
					}
					idMap = null;
				}
				randomList = null;
				// 进入房间
				EnterRoom enterRoom = new EnterRoom(data.getSessionId(), data.getSerial());
				enterRoom.setHandlerSource(quickGame.getHandlerSource());
				enterRoom.setSource(quickGame.getSource());
				enterRoom.setRoomId(roomId);
				EnterRoomHandler enterRoomHandler = new EnterRoomHandler();
				enterRoomHandler.handle(enterRoom);
			} else {
				int battleMode = 1;
				int playerNumMode = 1;
				int startMode = 2;
				WorldPlayer worldPlayer = ServiceManager.getManager().getRobotService().getRobot(player, new HashSet<String>());
				int roomId = ServiceManager
						.getManager()
						.getRoomService()
						.createRoom(worldPlayer, TipMessages.ROOMNAME1, battleMode, playerNumMode, "-1", startMode,
								Room.BATTLE_TYPE_MACHINE, 0, true, true);
				int result = ServiceManager.getManager().getRoomService().enRoom(roomId, worldPlayer, true);
				switch (result) {
					case -1 :
						throw new ProtocolException(ErrorMessages.ROOM_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(),
								data.getType(), data.getSubType());
					case -2 :
						throw new ProtocolException(ErrorMessages.ROOM_BHS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
								data.getSubType());
					case -3 :
						throw new ProtocolException(ErrorMessages.ROOM_FULL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
								data.getSubType());
					case -4 :
						throw new ProtocolException(ErrorMessages.CANNOT_START_CONBATTLE, data.getSerial(), data.getSessionId(),
								data.getType(), data.getSubType());
				}
				// 进入房间
				EnterRoom enterRoom = new EnterRoom(data.getSessionId(), data.getSerial());
				enterRoom.setHandlerSource(quickGame.getHandlerSource());
				enterRoom.setSource(quickGame.getSource());
				enterRoom.setRoomId(roomId);
				EnterRoomHandler enterRoomHandler = new EnterRoomHandler();
				enterRoomHandler.handle(enterRoom);
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_RNF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
