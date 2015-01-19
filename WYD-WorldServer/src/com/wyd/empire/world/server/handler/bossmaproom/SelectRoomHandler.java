package com.wyd.empire.world.server.handler.bossmaproom;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmaproom.EnterRoom;
import com.wyd.empire.protocol.data.bossmaproom.SelectRoom;
import com.wyd.empire.protocol.data.bossmaproom.SelectRoomOk;
import com.wyd.empire.world.bossmaproom.BossRoom;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
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
		SelectRoom selectRoom = (SelectRoom) data;
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			BossRoom room = ServiceManager.getManager().getBossRoomService().getRoom(selectRoom.getRoomId());
			if (null == room) {
				throw new ProtocolException(ErrorMessages.ROOM_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			if (1 == room.getBattleStatus()) {
				throw new ProtocolException(ErrorMessages.ROOM_BHS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			if (room.isFull(room.getPlayerNumMode()) || room.isLeaveRoom()) {
				throw new ProtocolException(ErrorMessages.ROOM_FULL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			if (room.getProgress() > player.getBossmap_progress()) {
				throw new ProtocolException(ErrorMessages.ROOM_OPEN_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
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
