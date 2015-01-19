package com.wyd.empire.world.server.handler.room;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.room.QuitRoom;
import com.wyd.empire.protocol.data.room.QuitRoomOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 退出房间
 * 
 * @author Administrator
 *
 */
public class QuitRoomHandler implements IDataHandler {
	Logger log = Logger.getLogger(QuitRoomHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		QuitRoom quitRoom = (QuitRoom) data;
		try {
			if (null == ServiceManager.getManager().getRoomService().getRoom(quitRoom.getRoomId())) {
				player.setRoomId(0);
				player.sendData(new QuitRoomOk());
				return;
			}
			ServiceManager.getManager().getRoomService().exRoom(quitRoom.getRoomId(), quitRoom.getOldSeat(), player.getId());
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_QUIT_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
