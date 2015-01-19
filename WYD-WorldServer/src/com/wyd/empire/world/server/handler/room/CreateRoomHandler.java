package com.wyd.empire.world.server.handler.room;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.room.CreateRoom;
import com.wyd.empire.protocol.data.room.EnterRoom;
import com.wyd.empire.protocol.data.room.SoundRoom;
import com.wyd.empire.world.Server;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * @author Administrator
 */
public class CreateRoomHandler implements IDataHandler {
	Logger log = Logger.getLogger(CreateRoomHandler.class);

	// 创建房间
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		CreateRoom createRoom = (CreateRoom) data;
		try {
			if (createRoom.getBattleMode() == 5 && player.getGuildId() == 0) {
				throw new ProtocolException(ErrorMessages.CANNOT_BUILD_CUMMUNITYROOM, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
			// 公会战限制人数（客服测试使用）
			if (createRoom.getBattleMode() == 5) {
				Map<String, Integer> specialMarkMap = ServiceManager.getManager().getOperationConfigService().getSpecialMark();
				Integer guildStartMode = specialMarkMap.get("guildStartMode");
				if (guildStartMode != null && guildStartMode > 0) {
					createRoom.setStartMode(guildStartMode);
				}
			}

			int roomId = ServiceManager
					.getManager()
					.getRoomService()
					.createRoom(player, createRoom.getRoomName(), createRoom.getBattleMode(), createRoom.getPlayerNumMode(),
							createRoom.getPassWord(), createRoom.getStartMode(), Room.BATTLE_TYPE_NORMAL, createRoom.getServiceMode(),
							false, createRoom.getEventMode());
			// System.out.println("CreateRoom------------"+roomId);
			// 发送语音房间相关协议
			SoundRoom soundRoom = new SoundRoom();
			soundRoom.setSeverId(Server.config.getServerId());
			soundRoom.setRoomId(roomId);
			soundRoom.setLocation(0);
			soundRoom.setMark(0);
			player.sendData(soundRoom);

			// 进入房间
			EnterRoom enterRoom = new EnterRoom(data.getSessionId(), data.getSerial());
			enterRoom.setHandlerSource(data.getHandlerSource());
			enterRoom.setSource(data.getSource());
			enterRoom.setRoomId(roomId);
			EnterRoomHandler enterRoomHandler = new EnterRoomHandler();
			enterRoomHandler.handle(enterRoom);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_CREATE_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
