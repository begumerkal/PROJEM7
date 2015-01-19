package com.wyd.empire.world.server.handler.bossmaproom;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmaproom.GetRoomListOk;
import com.wyd.empire.world.bossmaproom.BossRoom;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取服务器房间列表
 * 
 * @author Administrator
 * 
 */
public class GetRoomListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetRoomListHandler.class);

	// 读取房间列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		try {
			Vector<BossRoom> roomlist = ServiceManager.getManager().getBossRoomService().getListRoom();
			int roomCount = roomlist.size();
			int returnSize = 10;
			int[] roomId = new int[returnSize];
			String[] roomName = new String[returnSize];
			int[] battleStatus = new int[returnSize];
			int[] playerNumMode = new int[returnSize];
			String[] passWord = new String[returnSize];
			int[] playerNum = new int[returnSize];
			boolean[] roomStaus = new boolean[returnSize];
			int[] mapId = new int[returnSize];
			int[] roomStar = new int[returnSize];
			BossRoom room;
			int roomIndex = 0;
			for (int i = roomCount - 1; i >= 0 && roomIndex < returnSize; i--) {
				room = roomlist.get(i);
				if (room.getPlayerNum() > 0) {
					roomId[roomIndex] = room.getRoomId();
					roomName[roomIndex] = room.getRoomShortName();
					battleStatus[roomIndex] = room.getBattleStatus();
					playerNumMode[roomIndex] = room.getPlayerNumMode();
					passWord[roomIndex] = room.getPassWord();
					playerNum[roomIndex] = room.getPlayerNum();
					roomStaus[roomIndex] = room.isFull(room.getPlayerNumMode());
					mapId[roomIndex] = room.getMapId();
					roomStar[roomIndex] = room.getDifficulty();
					roomIndex++;
				} else {
					ServiceManager.getManager().getBossRoomService().deletRoom(room);
				}
			}

			List<BossRoom> leaveRoomlist = ServiceManager.getManager().getBossRoomService().getLeaveRoom();
			for (int i = roomIndex; i < returnSize; i++) {
				room = leaveRoomlist.get(i);
				roomId[i] = room.getRoomId();
				roomName[i] = room.getRoomShortName();
				battleStatus[i] = room.getBattleStatus();
				playerNumMode[i] = room.getPlayerNumMode();
				passWord[i] = room.getPassWord();
				playerNum[i] = room.getPlayerNumMode();
				mapId[i] = room.getMapId();
				roomStar[i] = room.getDifficulty();
				roomStaus[i] = true;
			}

			GetRoomListOk sendRoomList = new GetRoomListOk(data.getSessionId(), data.getSerial());
			sendRoomList.setRoomCount(returnSize);
			sendRoomList.setRoomId(roomId);
			sendRoomList.setRoomName(roomName);
			sendRoomList.setBattleStatus(battleStatus);
			sendRoomList.setPlayerCountNum(playerNumMode);
			sendRoomList.setPassWord(passWord);
			sendRoomList.setPlayerNum(playerNum);
			sendRoomList.setRoomStaus(roomStaus);
			sendRoomList.setMapId(mapId);
			sendRoomList.setRoomStar(roomStar);
			session.write(sendRoomList);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_GRLF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
