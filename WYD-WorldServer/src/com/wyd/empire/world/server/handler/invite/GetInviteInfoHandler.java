package com.wyd.empire.world.server.handler.invite;

import org.apache.log4j.Logger;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.server.service.impl.InviteService;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取邀请码信息
 * 
 * @author zguoqiu
 */
public class GetInviteInfoHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetInviteInfoHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer worldPlayer = session.getPlayer(data.getSessionId());
		try {
			ServiceManager.getManager().getInviteService().addInviteInfo(worldPlayer, InviteService.INVITE_NUM, 0, 0);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}
}
