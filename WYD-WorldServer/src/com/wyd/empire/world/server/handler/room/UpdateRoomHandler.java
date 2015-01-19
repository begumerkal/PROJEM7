package com.wyd.empire.world.server.handler.room;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.room.UpdateRoom;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 更新房间信息
 * 
 * @author Administrator
 *
 */
public class UpdateRoomHandler implements IDataHandler {
	Logger log = Logger.getLogger(UpdateRoomHandler.class);

	public void handle(AbstractData data) throws Exception {
		UpdateRoom updateRoom = (UpdateRoom) data;
		try {
			Room room = ServiceManager.getManager().getRoomService().getRoom(updateRoom.getRoomId());
			if (null == room) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.ROOM_NOTFOUND_MESSAGE);
			}
			if (-1 != updateRoom.getBattleMode())
				room.setBattleMode(updateRoom.getBattleMode());
			// if(!updateRoom.getPassWord().equals(room.getPassWord()))
			// room.setPassWord(updateRoom.getPassWord());
			if (-1 != updateRoom.getWnersId())
				room.setWnersId(updateRoom.getWnersId());

			if (-1 != updateRoom.getMapId() && updateRoom.getMapId() != room.getMapId()) {
				if (null == ServiceManager.getManager().getMapsService().getMapById(updateRoom.getMapId())) {
					throw new Exception(Common.ERRORKEY + ErrorMessages.MAP_NFM_MESSAGE);
				}
				room.setMapId(updateRoom.getMapId());
			}

			boolean update = false;
			if (-1 != updateRoom.getPlayerNumMode() && updateRoom.getPlayerNumMode() != room.getPlayerNumMode()) {
				room.setPlayerNumMode(updateRoom.getPlayerNumMode());
				update = true;
			}
			if (-1 != updateRoom.getStartMode() && updateRoom.getStartMode() != room.getStartMode()) {
				room.setStartMode(updateRoom.getStartMode());
				update = true;
			}
			room.setServiceMode(updateRoom.getServiceMode());
			if (update) {
				ServiceManager.getManager().getRoomService().updateRoom(room);
			}
			// System.out.println("UpdateRoomHandler-------------" +
			// room.getPlayerNumMode());
			ServiceManager.getManager().getRoomService().SynRoomInfo(updateRoom.getRoomId());
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}
