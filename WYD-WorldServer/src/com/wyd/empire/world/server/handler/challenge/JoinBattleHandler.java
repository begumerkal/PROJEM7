package com.wyd.empire.world.server.handler.challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.challenge.GetBattleTeamList;
import com.wyd.empire.protocol.data.challenge.JoinBattle;
import com.wyd.empire.world.battle.BattleTeamComparator;
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
 * 获得战队列表
 * 
 * @author Administrator
 *
 */
public class JoinBattleHandler implements IDataHandler {
	Logger log = Logger.getLogger(JoinBattleHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		JoinBattle joinBattle = (JoinBattle) data;
		try {
			// if(!ServiceManager.getManager().getChallengeSerService().isInTime()){
			// throw new Exception(Common.ERRORKEY +
			// ErrorMessages.CHALLENGE_NOT_INTIME);
			// }
			WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(joinBattle.getPlayerId());
			Room r = ServiceManager.getManager().getChallengeService().getRoom(worldPlayer.getRoomId());

			if (null != r) {
				ServiceManager.getManager().getChallengeService().SynRoomInfo(r.getRoomId());
			} else {
				if (!ServiceManager.getManager().getChallengeSerService().isInTime()) {
					throw new Exception(Common.ERRORKEY + ErrorMessages.CHALLENGE_NOT_INTIME);
				}
				List<Room> roomList = ServiceManager.getManager().getChallengeService().getListRoom();
				List<Room> battleTeamList = new ArrayList<Room>();
				for (Room room : roomList) {
					if (!room.isFull() && room.getBattleStatus() != 1) {
						battleTeamList.add(room);
					}
				}

				Comparator<Room> descComparator = new BattleTeamComparator();
				Collections.sort(battleTeamList, descComparator);

				int[] battleTeamId = new int[battleTeamList.size()];
				int[] hasNum = new int[battleTeamList.size()];
				int[] totalNum = new int[battleTeamList.size()];
				String[] teamName = new String[battleTeamList.size()];
				String[] teamLeader = new String[battleTeamList.size()];
				int[] avgIntegral = new int[battleTeamList.size()];

				int inx = 0;
				WorldPlayer wp;
				for (Room room : battleTeamList) {
					battleTeamId[inx] = room.getRoomId();
					hasNum[inx] = room.getPlayerNum();
					totalNum[inx] = room.getPlayerNumMode();
					teamName[inx] = room.getRoomName();
					wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(room.getWnersId());
					teamLeader[inx] = wp.getName();
					avgIntegral[inx] = room.getAvgFighting();
					inx++;
				}

				GetBattleTeamList getBattleTeamList = new GetBattleTeamList(data.getSessionId(), data.getSerial());
				getBattleTeamList.setAvgIntegral(avgIntegral);
				getBattleTeamList.setBattleTeamId(battleTeamId);
				getBattleTeamList.setHasNum(hasNum);
				getBattleTeamList.setTeamLeader(teamLeader);
				getBattleTeamList.setTeamName(teamName);
				getBattleTeamList.setTotalNum(totalNum);
				session.write(getBattleTeamList);
			}
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			if (null != ex.getMessage())
				throw new ProtocolException(ex.getMessage().replace(Common.ERRORKEY, ""), data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
		}
	}
}
