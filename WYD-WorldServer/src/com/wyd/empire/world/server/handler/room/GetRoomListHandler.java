package com.wyd.empire.world.server.handler.room;

import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.room.GetRoomListOk;
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
 * 获取服务器房间列表
 * 
 * @author Administrator
 */
public class GetRoomListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetRoomListHandler.class);

	// 读取房间列表
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		try {
			List<Room> roomlist = ServiceManager.getManager().getRoomService().getListRoom();
			int roomCount = roomlist.size();
			int returnSize = 10;
			int[] roomId = new int[returnSize];
			String[] roomName = new String[returnSize];
			int[] battleStatus = new int[returnSize];
			int[] battleMode = new int[returnSize];
			int[] playerNumMode = new int[returnSize];
			String[] passWord = new String[returnSize];
			int[] playerNum = new int[returnSize];
			int[] startMode = new int[returnSize];
			boolean[] roomStaus = new boolean[returnSize];
			Room room;
			int roomIndex = 0;
			int roomChannel = player.getBattleChannel();
			for (int i = roomCount - 1; i >= 0 && roomIndex < returnSize; i--) {
				room = roomlist.get(i);
				if (room.getRoomChannel() == roomChannel && room.getBattleMode() != 4) {
					roomId[roomIndex] = room.getRoomId();
					roomName[roomIndex] = room.getRoomName();
					battleStatus[roomIndex] = room.getBattleStatus();
					battleMode[roomIndex] = room.getBattleMode();
					playerNumMode[roomIndex] = room.getPlayerNumMode();
					passWord[roomIndex] = room.getPassWord();
					playerNum[roomIndex] = room.getPlayerNum();
					startMode[roomIndex] = room.getStartMode();
					roomStaus[roomIndex] = room.isFull();
					roomIndex++;
				}
			}
			List<Room> leaveRoomlist = ServiceManager.getManager().getRoomService().getLeaveRoom();
			for (int i = roomIndex; i < returnSize; i++) {
				room = leaveRoomlist.get(i);
				roomId[i] = room.getRoomId();
				roomName[i] = room.getRoomName();
				battleStatus[i] = room.getBattleStatus();
				battleMode[i] = room.getBattleMode();
				playerNumMode[i] = room.getPlayerNumMode();
				passWord[i] = room.getPassWord();
				playerNum[i] = room.getPlayerNum();
				startMode[i] = room.getStartMode();
				roomStaus[i] = true;
			}
			// for(int i=0;i<10;i++){
			// if(null==roomName[i])
			// System.out.println(i+"==="+roomId[i]+"---------------------------name"+roomName[i]);
			// }
			GetRoomListOk sendRoomList = new GetRoomListOk(data.getSessionId(), data.getSerial());
			sendRoomList.setRoomCount(returnSize);
			sendRoomList.setRoomId(roomId);
			sendRoomList.setRoomName(roomName);
			sendRoomList.setBattleStatus(battleStatus);
			sendRoomList.setBattleMode(battleMode);
			sendRoomList.setPlayerNumMode(playerNumMode);
			sendRoomList.setPassWord(passWord);
			sendRoomList.setPlayerNum(playerNum);
			sendRoomList.setStartMode(startMode);
			sendRoomList.setRoomStaus(roomStaus);
			session.write(sendRoomList);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_GRLF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
