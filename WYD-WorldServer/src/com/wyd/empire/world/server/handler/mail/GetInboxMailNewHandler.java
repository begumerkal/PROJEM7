package com.wyd.empire.world.server.handler.mail;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.mail.GetInboxMailNew;
import com.wyd.empire.protocol.data.nearby.GetNearbyReceivedMailList;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

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
			int nearbyId = player.getPlayerInfo().getNearbyId();
 
				ServiceManager.getManager().getMailService().receivedMailList(player, getInboxMailNew.getPageNumber(), null);
		} catch (Exception ex) {
			this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.MAIL_LIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
		return null;
	}
}