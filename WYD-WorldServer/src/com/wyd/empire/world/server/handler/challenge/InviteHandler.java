package com.wyd.empire.world.server.handler.challenge;

import java.util.Map;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.challenge.BeInvite;
import com.wyd.empire.protocol.data.challenge.Invite;
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
 * 邀请玩家对战
 * 
 * @author Administrator
 */
public class InviteHandler implements IDataHandler {
	Logger log = Logger.getLogger(InviteHandler.class);

	// 邀请玩家对战
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		Invite invite = (Invite) data;
		try {
			WorldPlayer wp = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(invite.getPlayerId());
			if (null == wp) {
				return;
			}

			if (wp.getBattleId() != 0 || wp.getRoomId() != 0 || wp.getBossmapBattleId() != 0 || wp.getBossmapRoomId() != 0
					|| wp.isInSingleMap()) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.PLAYERINROOM);
			}

			// 获得特殊标示
			Map<String, Integer> map = ServiceManager.getManager().getVersionService().getSpecialMark();
			// 判断等级
			if (((null == map.get("challengeLevel") && wp.getLevel() < 20) || wp.getLevel() < map.get("challengeLevel"))) {
				throw new Exception(Common.ERRORKEY + ErrorMessages.CHALLENGE_LEVEL_LOW);
			}

			Room room = ServiceManager.getManager().getChallengeService().getRoom(invite.getBattleTeamId());
			// if(room.getRoomChannel()!=wp.getBattleChannel()){
			// throw new Exception(Common.ERRORKEY +
			// ErrorMessages.ROOM_IRF_MESSAGE);
			// }
			if (null != wp && null != room) {
				BeInvite beInvite = new BeInvite();
				beInvite.setBattleTeamId(room.getRoomId());
				beInvite.setTeamName(room.getRoomName());
				beInvite.setPlayerName(player.getName());
				beInvite.setInvitePlayerId(player.getId());
				wp.sendData(beInvite);
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
