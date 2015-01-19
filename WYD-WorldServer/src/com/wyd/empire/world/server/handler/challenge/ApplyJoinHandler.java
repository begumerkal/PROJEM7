package com.wyd.empire.world.server.handler.challenge;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.challenge.ApplyJoin;
import com.wyd.empire.protocol.data.challenge.ApplyJoinOk;
import com.wyd.empire.protocol.data.challenge.NoticeLeader;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.room.Room;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 申请加入
 * 
 * @author Administrator
 */
public class ApplyJoinHandler implements IDataHandler {
	Logger log = Logger.getLogger(ApplyJoinHandler.class);

	// 申请加入
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		ApplyJoin applyJoin = (ApplyJoin) data;
		try {
			Room room = ServiceManager.getManager().getChallengeService().getRoom(applyJoin.getBattleTeamId());
			if (null == room) {
				throw new ProtocolException(ErrorMessages.ROOM_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (1 == room.getBattleStatus()) {
				throw new ProtocolException(ErrorMessages.ROOM_BHS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (room.isFull()) {
				throw new ProtocolException(ErrorMessages.BATTLETEAM_FULL, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (room.getApplyList().indexOf(player) == -1) {
				room.getApplyList().add(player);
				if (player.getApplyList().indexOf(room.getRoomId()) == -1) {
					player.getApplyList().add(room.getRoomId());
				}
			}
			ApplyJoinOk applyJoinOk = new ApplyJoinOk(data.getSessionId(), data.getSerial());
			session.write(applyJoinOk);
			WorldPlayer leader = ServiceManager.getManager().getPlayerService().getWorldPlayerById(room.getWnersId());
			NoticeLeader noticeLeader = new NoticeLeader();
			ServiceManager.getManager().getChallengeService().checkApplyList(room);
			noticeLeader.setNum(room.getApplyList().size());
			leader.sendData(noticeLeader);
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
