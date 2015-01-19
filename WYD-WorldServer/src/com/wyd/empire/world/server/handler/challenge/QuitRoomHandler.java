package com.wyd.empire.world.server.handler.challenge;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.challenge.QuitRoom;
import com.wyd.empire.protocol.data.challenge.QuitRoomOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
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
			Room room = ServiceManager.getManager().getChallengeService().getRoom(player.getRoomId());
			if (null == room || room.getRoomId() != quitRoom.getBattleTeamId()) {
				player.sendData(new QuitRoomOk());
				return;
			}

			// if(null==ServiceManager.getManager().getChallengeService().getRoom(quitRoom.getBattleTeamId())){
			// player.setRoomId(0);
			// player.sendData(new QuitRoomOk());
			// return;
			// }
			ServiceManager.getManager().getChallengeService().exRoom(quitRoom.getBattleTeamId(), quitRoom.getOldSeat());
		} catch (Exception ex) {
			ex.printStackTrace();
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_QUIT_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
