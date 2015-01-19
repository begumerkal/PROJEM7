package com.wyd.empire.world.server.handler.qualifying;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.qualifying.Start;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.RandomRoom;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.server.handler.task.GetEverydayRewardListHandler;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 开始排位赛
 * 
 * @author Administrator
 * 
 */
public class StartHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetEverydayRewardListHandler.class);

	// 获取玩家显示的任务列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		Start start = (Start) data;
		try {
			int playerScore = start.getPlayerScore();
			int roomId = ServiceManager.getManager().getRoomService()
					.createRoom(player, "", 4, 1, "-1", 1, Room.BATTLE_TYPE_NORMAL, 0, false, false);
			Room room = ServiceManager.getManager().getRoomService().getRoom(roomId);
			room.setRankScore(playerScore);
			room.setGap((int) (120 * Math.pow(player.getPlayer().getHonorLevel(), 3) - 120 * Math.pow(
					player.getPlayer().getHonorLevel() - 1, 3)));
			// 进入房间
			if (0 != player.getBossmapRoomId()
					&& null != ServiceManager.getManager().getBossRoomService().getRoom(player.getBossmapRoomId())) {
				int index = ServiceManager.getManager().getBossRoomService().getPlayerSeat(player.getBossmapRoomId(), player.getId());
				ServiceManager.getManager().getBossRoomService().extRoom(player.getBossmapRoomId(), index, 0);
			}
			if (0 != player.getRoomId() && null != ServiceManager.getManager().getRoomService().getRoom(player.getRoomId())) {
				int index = ServiceManager.getManager().getRoomService().getPlayerSeat(player.getRoomId(), player.getId());
				ServiceManager.getManager().getRoomService().exRoom(player.getRoomId(), index, 0);
			}
			int result = ServiceManager.getManager().getRoomService().enRoom(roomId, player, false);
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
			// 随机配对
			RandomRoom randomRoom = new RandomRoom();
			randomRoom.setRoom(room);
			ServiceManager.getManager().getRankPairService().addRandomRoom(randomRoom);

		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_GRLF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());

		}
	}
}
