package com.wyd.empire.world.server.handler.room;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.room.EnterRoom;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 进入房间列
 * 
 * @author Administrator
 *
 */
public class EnterRoomHandler implements IDataHandler {
	Logger log = Logger.getLogger(EnterRoomHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		EnterRoom enterRoom = (EnterRoom) data;
		try {
			if (0 != player.getBossmapRoomId()
					&& null != ServiceManager.getManager().getBossRoomService().getRoom(player.getBossmapRoomId())) {
				int index = ServiceManager.getManager().getBossRoomService().getPlayerSeat(player.getBossmapRoomId(), player.getId());
				ServiceManager.getManager().getBossRoomService().extRoom(player.getBossmapRoomId(), index, 0);
			}
			if (0 != player.getRoomId() && null != ServiceManager.getManager().getRoomService().getRoom(player.getRoomId())) {
				int index = ServiceManager.getManager().getRoomService().getPlayerSeat(player.getRoomId(), player.getId());
				ServiceManager.getManager().getRoomService().exRoom(player.getRoomId(), index, 0);
			}
			int result = ServiceManager.getManager().getRoomService().enRoom(enterRoom.getRoomId(), player, false);
			switch (result) {
				case -1 :
					throw new ProtocolException(ErrorMessages.ROOM_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
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
			ServiceManager.getManager().getRoomService().SynRoomInfo(enterRoom.getRoomId());
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
					data.getType(), data.getSubType());
		}
	}
}
