package com.wyd.empire.world.server.handler.mail;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.mail.GetOutboxMailNew;
import com.wyd.empire.protocol.data.nearby.GetNearbySendMailList;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> GetOutboxMailHandler</code>Protocol.MAIL_GetInboxMail获得发件箱列表协议处理
 * 
 * @since JDK 1.6
 */
public class GetOutboxMailNewHandler implements IDataHandler {
	private Logger log;

	public GetOutboxMailNewHandler() {
		this.log = Logger.getLogger(GetOutboxMailNewHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		GetOutboxMailNew getOutboxMailNew = (GetOutboxMailNew) data;
		try {
			int nearbyId = player.getPlayerInfo().getNearbyId();
			if (nearbyId > 0 && ServiceManager.getManager().getNearbyService().isNearbyServiceOpen()) {
				GetNearbySendMailList gnsml = new GetNearbySendMailList();
				gnsml.setMyInfoId(nearbyId);
				gnsml.setPageNum(getOutboxMailNew.getPageNumber());
				ServiceManager.getManager().getNearbyService().sendData(gnsml);
			} else {
				ServiceManager.getManager().getMailService().sendMailList(player, getOutboxMailNew.getPageNumber(), null);
			}
		} catch (Exception ex) {
			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
				this.log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.MAIL_LIST_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}