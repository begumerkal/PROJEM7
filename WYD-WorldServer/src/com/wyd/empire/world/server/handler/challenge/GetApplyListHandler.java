package com.wyd.empire.world.server.handler.challenge;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.challenge.GetApplyList;
import com.wyd.empire.protocol.data.challenge.SendApplyList;
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
 * 获得申请列表
 * 
 * @author Administrator
 */
public class GetApplyListHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetApplyListHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		// WorldPlayer player = session.getPlayer(data.getSessionId());
		GetApplyList getApplyList = (GetApplyList) data;
		try {
			Room room = ServiceManager.getManager().getChallengeService().getRoom(getApplyList.getBattleTeamId());
			if (null == room)
				return;
			List<WorldPlayer> list = new ArrayList<WorldPlayer>();
			for (WorldPlayer wp : room.getApplyList()) {
				if (wp.getBattleId() != 0 || wp.getRoomId() != 0 || wp.getBossmapBattleId() != 0 || wp.getBossmapRoomId() != 0
						|| !wp.isPlayerInChallenge()) {
					list.add(wp);
				}
			}
			room.getApplyList().removeAll(list);
			int count = room.getApplyList().size();
			int[] playerId = new int[count];
			String[] playerName = new String[count];
			int[] playerLevel = new int[count];
			int[] playerBattle = new int[count];
			int idx = 0;
			for (WorldPlayer wp : room.getApplyList()) {
				playerId[idx] = wp.getId();
				playerName[idx] = wp.getName();
				playerLevel[idx] = wp.getLevel();
				playerBattle[idx] = wp.getFighting();
				idx++;
			}
			SendApplyList sendApplyList = new SendApplyList(data.getSessionId(), data.getSerial());
			sendApplyList.setPlayerBattle(playerBattle);
			sendApplyList.setPlayerId(playerId);
			sendApplyList.setPlayerLevel(playerLevel);
			sendApplyList.setPlayerName(playerName);
			session.write(sendApplyList);
		} catch (Exception ex) {
			ex.printStackTrace();
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
