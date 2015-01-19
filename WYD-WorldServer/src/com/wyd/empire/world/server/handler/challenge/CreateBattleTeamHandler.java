package com.wyd.empire.world.server.handler.challenge;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.challenge.CreateBattleTeam;
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
public class CreateBattleTeamHandler implements IDataHandler {
	Logger log = Logger.getLogger(CreateBattleTeamHandler.class);

	// 创建房间
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		CreateBattleTeam createBattleTeam = (CreateBattleTeam) data;
		try {
			if (!ServiceManager.getManager().getChallengeSerService().isInTime()) {
				throw new ProtocolException(ErrorMessages.CHALLENGE_NOT_INTIME, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (player.isInSingleMap() || player.getBattleId() != 0 || player.getRoomId() != 0 || player.getBossmapRoomId() != 0
					|| player.getBossmapBattleId() != 0) {
				throw new ProtocolException(ErrorMessages.PLAYERINROOM, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			Integer challengeNum = ServiceManager.getManager().getVersionService().getSpecialMarkByKey("challengeNum");
			if (null == challengeNum) {
				challengeNum = 3;
			}
			int roomId = ServiceManager.getManager().getChallengeService()
					.createRoom(player, createBattleTeam.getTeamName(), 6, challengeNum, "-1", 1, Room.BATTLE_TYPE_NORMAL);
			// System.out.println("CreateRoom------------"+roomId);
			// 进入战队（房间）
			ServiceManager.getManager().getChallengeService().enRoom(roomId, player, false);
			ServiceManager.getManager().getChallengeService().SynRoomInfo(roomId);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_CREATE_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
