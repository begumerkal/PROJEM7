package com.app.empire.world.server.handler.mail;

import org.apache.log4j.Logger;

import com.app.empire.protocol.data.mail.GetInboxMailNew;
import com.app.empire.world.exception.ErrorMessages;
import com.app.empire.world.model.player.WorldPlayer;
import com.app.empire.world.service.factory.ServiceManager;
import com.app.empire.world.session.ConnectSession;
import com.app.protocol.data.AbstractData;
import com.app.protocol.exception.ProtocolException;
import com.app.protocol.handler.IDataHandler;

/**
 * 类 <code> GetInboxMailHandler</code>Protocol.MAIL_GetInboxMail获得收件箱列表协议处理
 * 
 * @since JDK 1.6
 */
public class GetInboxMailNewHandler implements IDataHandler {
	private Logger log;

	public GetInboxMailNewHandler() {
		this.log = Logger.getLogger(GetInboxMailNewHandler.class);
	}

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return null;
		GetInboxMailNew getInboxMailNew = (GetInboxMailNew) data;
		try {
//			int nearbyId = player.getPlayerInfo().getNearbyId();
 
//				ServiceManager.getManager().getMailService().receivedMailList(player, getInboxMailNew.getPageNumber(), null);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.MAIL_LIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
		return null;
	}
}