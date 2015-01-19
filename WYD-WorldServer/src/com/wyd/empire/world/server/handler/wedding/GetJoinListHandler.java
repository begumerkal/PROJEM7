package com.wyd.empire.world.server.handler.wedding;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.wedding.GetJoinList;
import com.wyd.empire.protocol.data.wedding.SendJoinList;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WeddingRoom;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.impl.MarryService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获得来宾列表
 * 
 * @author Administrator
 */
public class GetJoinListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetJoinListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		GetJoinList getJoinList = (GetJoinList) data;
		try {
			String wedNum = getJoinList.getWedNum();
			WeddingRoom weddingRoom = MarryService.weddingMap.get(wedNum);
			if (null == weddingRoom) {
				return;
			}

			int[] playerId = new int[weddingRoom.getPlayerList().size()]; // 来宾Id
			String[] playerName = new String[weddingRoom.getPlayerList().size()]; // 来宾名称
			int[] level = new int[weddingRoom.getPlayerList().size()]; // 来宾等级
			boolean[] sex = new boolean[weddingRoom.getPlayerList().size()]; // 来宾性别，false是男，true是女
			boolean[] rebirth = new boolean[weddingRoom.getPlayerList().size()];

			int index = 0;
			for (WorldPlayer wp : weddingRoom.getPlayerList()) {
				playerId[index] = wp.getId();
				playerName[index] = wp.getName();
				level[index] = wp.getLevel();
				sex[index] = wp.getPlayer().getSex() == 0 ? true : false;
				rebirth[index] = wp.getPlayer().getZsLevel() > 0;
				index++;
			}

			SendJoinList sendJoinList = new SendJoinList(data.getSessionId(), data.getSerial());
			sendJoinList.setLevel(level);
			sendJoinList.setPlayerId(playerId);
			sendJoinList.setPlayerName(playerName);
			sendJoinList.setSex(sex);
			sendJoinList.setRebirth(rebirth);

			session.write(sendJoinList);

		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
