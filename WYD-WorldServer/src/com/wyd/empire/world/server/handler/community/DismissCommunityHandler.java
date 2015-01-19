package com.wyd.empire.world.server.handler.community;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.community.DismissCommunityOk;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> ExitCommunityHandler</code>Protocol.COMMUNITY _ExitCommunity解散公会协议处理
 * 
 * @since JDK 1.6
 */
public class DismissCommunityHandler implements IDataHandler {
	private Logger log;

	public DismissCommunityHandler() {
		this.log = Logger.getLogger(ExitCommunityHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		try {
			int consortiaId = player.getGuildId();
			DismissCommunityOk dismissCommunityOk = new DismissCommunityOk(data.getSessionId(), data.getSerial());
			// 解散公会操作
			ServiceManager.getManager().getConsortiaService().exitCommunity(player.getId(), player);
			// 加入日志
			log.info("公会进出会记录：玩家Id-" + player.getId() + "-公会Id-" + consortiaId + "-操作-解散工会");
			session.write(dismissCommunityOk);
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_DISMISS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}