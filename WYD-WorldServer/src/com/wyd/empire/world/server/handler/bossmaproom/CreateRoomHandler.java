package com.wyd.empire.world.server.handler.bossmaproom;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmaproom.CreateRoom;
import com.wyd.empire.protocol.data.bossmaproom.EnterRoom;
import com.wyd.empire.protocol.data.room.SoundRoom;
import com.wyd.empire.world.WorldServer;
import com.wyd.empire.world.bean.Map;
import com.wyd.empire.world.bean.PlayerBossmap;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.player.WorldPlayer;
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
			Map map = ServiceManager.getManager().getMapsService().getBossMapById(createRoom.getBossMapId());
			int useVigor = map.getVitalityExpend();
			int vigor = player.getVigor();
			int canPassTimes = map.getPassTimes();
			boolean unlimited = map.getPassTimes() == -1;
			PlayerBossmap playerMap = ServiceManager.getManager().getPlayerBossmapService().loadPlayerBossMap(player.getId(), map.getId());
			// 是否足够活力
			if ((vigor - useVigor) < 0) {
				throw new ProtocolException(TipMessages.VIGOR_LOW, data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
			}
			// 次数上限
			if (!unlimited) {
				if (playerMap != null && playerMap.getPassTimes() >= canPassTimes) {
					throw new ProtocolException(TipMessages.SINGLEMAP_PASS_LIMIT, data.getSerial(), data.getSessionId(), data.getType(),
							data.getSubType());
				}
			}
			// 校验等级
			if (map.getLevel() > player.getLevel()) {
				throw new ProtocolException(ErrorMessages.ROOM_LEVEL_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			int playerNum = 3;// 所有副本人数均为3人
			int roomId = ServiceManager.getManager().getBossRoomService()
					.createRoom(player, map, playerNum, createRoom.getPassWord(), map.getBossmapSerial(), createRoom.getDifficulty());

			// 发送语音房间相关协议
			SoundRoom soundRoom = new SoundRoom();
			soundRoom.setSeverId(WorldServer.config.getServerId());
			soundRoom.setRoomId(roomId);
			soundRoom.setLocation(0);
			soundRoom.setMark(1);
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
