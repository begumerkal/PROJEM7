package com.wyd.empire.world.server.handler.challenge;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.challenge.AuditPlayer;
import com.wyd.empire.protocol.data.challenge.AuditPlayerOK;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 审核
 * 
 * @author Administrator
 */
public class AuditPlayerHandler implements IDataHandler {
	Logger log = Logger.getLogger(AuditPlayerHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		AuditPlayer auditPlayer = (AuditPlayer) data;
		try {
			int playerId = auditPlayer.getPlayerId();
			int battleTeamId = auditPlayer.getBattleTeamId();
			int status = auditPlayer.getStatus();

			WorldPlayer wp = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
			if (wp.getBattleId() != 0 || wp.getRoomId() != 0 || wp.getBossmapRoomId() != 0 || wp.getBossmapBattleId() != 0
					|| wp.isInSingleMap()) {
				throw new ProtocolException(ErrorMessages.PLAYERINROOM, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}

			Room room = ServiceManager.getManager().getChallengeService().getRoom(battleTeamId);
			if (room.isFull()) {
				room.getApplyList().clear();
				throw new ProtocolException(ErrorMessages.BATTLETEAM_FULL, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			room.getApplyList().remove(wp);
			if (status == 1) {// 同意则进入房间
				ServiceManager.getManager().getChallengeService().enRoom(battleTeamId, wp, false);
				ServiceManager.getManager().getChallengeService().SynRoomInfo(room.getRoomId());
			}

			AuditPlayerOK auditPlayerOK = new AuditPlayerOK();
			session.write(auditPlayerOK);

			// WorldPlayer leader =
			// ServiceManager.getManager().getPlayerService().getWorldPlayerById(room.getWnersId());
			// NoticeLeader noticeLeader = new NoticeLeader();
			// ServiceManager.getManager().getChallengeService().checkApplyList(room);
			// noticeLeader.setNum(room.getApplyList().size());
			// leader.sendData(noticeLeader);

		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.PLAYERINROOM, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
