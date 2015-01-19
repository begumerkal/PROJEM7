package com.wyd.empire.world.server.handler.bossmaproom;

import java.util.HashSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmaproom.EnterRoom;
import com.wyd.empire.protocol.data.bossmaproom.QuickGame;
import com.wyd.empire.world.bossmaproom.BossRoom;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
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
		QuickGame quickGame = (QuickGame) data;
		try {
			HashSet<Integer> mapId = new HashSet<Integer>();
			Vector<BossRoom> roomList = ServiceManager.getManager().getBossRoomService().getListRoom();
			BossRoom room = null;
			for (BossRoom r : roomList) {
				if (mapId.contains(r.getMapId()))
					continue;
				if ("-1".equals(r.getPassWord()) && !r.isFull(r.getPlayerNumMode()) && 0 == r.getBattleStatus()
						&& !(player.getBossmap_progress() < r.getProgress())) {
					room = r;
					break;
				}
			}
			mapId = null;
			if (null == room) {
				throw new ProtocolException(ErrorMessages.ROOM_RNF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			// 进入房间
			EnterRoom enterRoom = new EnterRoom(data.getSessionId(), data.getSerial());
			enterRoom.setHandlerSource(quickGame.getHandlerSource());
			enterRoom.setSource(quickGame.getSource());
			enterRoom.setRoomId(room.getRoomId());
			EnterRoomHandler enterRoomHandler = new EnterRoomHandler();
			enterRoomHandler.handle(enterRoom);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
