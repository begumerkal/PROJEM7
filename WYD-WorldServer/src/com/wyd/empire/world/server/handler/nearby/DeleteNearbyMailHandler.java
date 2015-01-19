package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.DeleteNearbyMail;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 删除好友邮件
 * 
 * @author zguoqiu
 */
public class DeleteNearbyMailHandler implements IDataHandler {
	private Logger log;

	public DeleteNearbyMailHandler() {
		this.log = Logger.getLogger(DeleteNearbyMailHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		DeleteNearbyMail deleteNearbyMail = (DeleteNearbyMail) data;
		try {
			int nearbyId = player.getPlayerInfo().getNearbyId();
			if (nearbyId > 0) {
				deleteNearbyMail.setMyInfoId(nearbyId);
				ServiceManager.getManager().getNearbyService().sendData(deleteNearbyMail);
			} else {
				throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(),
						data.getType(), data.getSubType());
			}
		} catch (ProtocolException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}