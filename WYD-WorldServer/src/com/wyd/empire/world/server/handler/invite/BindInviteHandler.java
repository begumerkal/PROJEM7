package com.wyd.empire.world.server.handler.invite;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.invite.BindInvite;
import com.wyd.empire.protocol.data.invite.BindInviteResult;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取玩家邀请码
 * 
 * @since JDK 1.6
 */
public class BindInviteHandler implements IDataHandler {
	private Logger log;

	public BindInviteHandler() {
		this.log = Logger.getLogger(BindInviteHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		BindInvite bindInvite = (BindInvite) data;
		try {
			player.getPlayer().setBindInviteCode(bindInvite.getInviteCode().toUpperCase());
			ServiceManager.getManager().getPlayerService().savePlayerData(player.getPlayer());
			BindInviteResult bindInviteResult = new BindInviteResult(data.getSessionId(), data.getSerial());
			session.write(bindInviteResult);
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}