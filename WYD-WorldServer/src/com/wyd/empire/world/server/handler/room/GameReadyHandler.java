package com.wyd.empire.world.server.handler.room;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.room.GameReady;
import com.wyd.empire.protocol.data.room.MakePair;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.room.Seat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家准备游戏
 * 
 * @author Administrator
 */
public class GameReadyHandler implements IDataHandler {
	Logger log = Logger.getLogger(GameReadyHandler.class);

	// 读取房间列表
	public void handle(AbstractData data) throws Exception {
		GameReady gameReady = (GameReady) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(gameReady.getRoomId());
			if (null == room) {
				throw new ProtocolException(ErrorMessages.ROOM_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (1 == room.getBattleStatus()) {
				throw new ProtocolException(ErrorMessages.ROOM_UPDATE_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			room.getPlayerList().get(gameReady.getOldSeat()).setReady(gameReady.getReady());
			ServiceManager.getManager().getRoomService().SynRoomInfo(gameReady.getRoomId());
			if (Room.BATTLE_TYPE_MACHINE == room.getRoomType()) {
				Seat seat = room.getPlayerList().get(
						ServiceManager.getManager().getRoomService().getPlayerSeat(room.getRoomId(), room.getWnersId()));
				if (seat.isRobot()) {
					int s0 = 0;
					int s1 = 0;
					if (2 == room.getStartMode()) {
						for (Seat seats : room.getPlayerList()) {
							if (null == seats.getPlayer()) {
								continue;
							}
							if (0 == seats.getCamp()) {
								s0++;
							} else {
								s1++;
							}
						}
					}
					if (s0 == s1 || 3 == room.getBattleMode()) {
						MakePair makePair = new MakePair(data.getSessionId(), data.getSerial());
						makePair.setHandlerSource(data.getHandlerSource());
						makePair.setRoomId(room.getRoomId());
						MakePairHandler makePairHandler = new MakePairHandler();
						makePairHandler.handle(makePair);
					}
				}
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
