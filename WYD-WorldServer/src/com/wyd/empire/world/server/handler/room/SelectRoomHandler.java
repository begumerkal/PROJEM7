package com.wyd.empire.world.server.handler.room;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.room.EnterRoom;
import com.wyd.empire.protocol.data.room.SelectRoom;
import com.wyd.empire.protocol.data.room.SelectRoomOk;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 查找服务器房间
 * 
 * @author Administrator
 * 
 */
public class SelectRoomHandler implements IDataHandler {
	Logger log = Logger.getLogger(SelectRoomHandler.class);

	// 读取房间列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SelectRoom selectRoom = (SelectRoom) data;
		try {
			GameLogService.findRoom(player.getId(), player.getLevel());
			Room room = ServiceManager.getManager().getRoomService().getRoom(selectRoom.getRoomId());
			if (null == room) {
				throw new ProtocolException(ErrorMessages.ROOM_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			if (!room.isLeaveRoom() && room.getRoomChannel() != player.getBattleChannel()) {
				throw new ProtocolException(ErrorMessages.ROOM_ERF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			if (!"-1".equals(room.getPassWord())) {
				SelectRoomOk selectRoomOk = new SelectRoomOk(data.getSessionId(), data.getSerial());
				selectRoomOk.setRoomId(room.getRoomId());
				selectRoomOk.setPassWord(room.getPassWord());
				session.write(selectRoomOk);
			} else {
				// 进入房间
				EnterRoom enterRoom = new EnterRoom(data.getSessionId(), data.getSerial());
				enterRoom.setHandlerSource(selectRoom.getHandlerSource());
				enterRoom.setSource(selectRoom.getSource());
				enterRoom.setRoomId(selectRoom.getRoomId());
				EnterRoomHandler enterRoomHandler = new EnterRoomHandler();
				enterRoomHandler.handle(enterRoom);
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
