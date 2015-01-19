package com.wyd.empire.world.server.handler.bossmaproom;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmaproom.UpdateRoom;
import com.wyd.empire.world.bossmaproom.BossRoom;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 更新房间信息
 * 
 * @author Administrator
 */
public class UpdateRoomHandler implements IDataHandler {
	Logger log = Logger.getLogger(UpdateRoomHandler.class);

	public void handle(AbstractData data) throws Exception {
		UpdateRoom updateRoom = (UpdateRoom) data;
		try {
			BossRoom room = ServiceManager.getManager().getBossRoomService().getRoom(updateRoom.getRoomId());
			if (null == room) {
				throw new ProtocolException(ErrorMessages.ROOM_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (!updateRoom.getPassWord().equals(room.getPassWord()))
				room.setPassWord(updateRoom.getPassWord());
			if (-1 != updateRoom.getWnersId())
				room.setWnersId(updateRoom.getWnersId());
			if (-1 != updateRoom.getMapId() && updateRoom.getMapId() != room.getMapId()) {
				if (null == ServiceManager.getManager().getMapsService().getBossMapById(updateRoom.getMapId())) {
					throw new ProtocolException(ErrorMessages.MAP_NFM_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
				room.setMapId(updateRoom.getMapId());
			}
			boolean update = false;
			if (-1 != updateRoom.getPlayerNumMode() && updateRoom.getPlayerNumMode() != room.getPlayerNumMode()) {
				room.setPlayerNumMode(updateRoom.getPlayerNumMode());
				update = true;
			}
			if (-1 != updateRoom.getDifficulty()) {
				room.setDifficulty(updateRoom.getDifficulty());
			}
			if (update) {
				ServiceManager.getManager().getBossRoomService().updateRoom(room);
			}
			ServiceManager.getManager().getBossRoomService().SynRoomInfo(updateRoom.getRoomId());
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
