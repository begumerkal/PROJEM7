package com.wyd.empire.world.server.handler.bossmaproom;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.bossmaproom.BeInvite;
import com.wyd.empire.protocol.data.bossmaproom.Invite;
import com.wyd.empire.world.bossmaproom.BossRoom;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
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
			int inviteTimes = player.getInviteTimes(invite.getPlayerId());
			if (inviteTimes > 2) {
				throw new ProtocolException(ErrorMessages.ROOM_IPO_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			player.invitePlayer(invite.getPlayerId());
			WorldPlayer wp = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(invite.getPlayerId());
			if (null != wp) {
				BossRoom room = ServiceManager.getManager().getBossRoomService().getRoom(invite.getRoomId());
				if (null != room) {
					BeInvite beInvite = new BeInvite();
					beInvite.setRoomId(room.getRoomId());
					beInvite.setPlayerName(player.getName());
					wp.sendData(beInvite);
				}
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.ROOM_IPF_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}
