package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.UpdatePlayerLocationInfo;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 更新玩家的坐标信息
 * 
 * @author zguoqiu
 */
public class UpdatePlayerLocationInfoHandler implements IDataHandler {
	private Logger log;

	public UpdatePlayerLocationInfoHandler() {
		this.log = Logger.getLogger(UpdatePlayerLocationInfoHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		UpdatePlayerLocationInfo updatePlayerLocationInfo = (UpdatePlayerLocationInfo) data;
		if (null == player)
			return;
		try {
			ServiceManager.getManager().getNearbyService()
					.UpdatePlayerInfo(player, updatePlayerLocationInfo.getLongitude(), updatePlayerLocationInfo.getLatitude());
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ErrorMessages.COMMUNITY_NOSUCCESS_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
					data.getSubType());
		}
	}
}