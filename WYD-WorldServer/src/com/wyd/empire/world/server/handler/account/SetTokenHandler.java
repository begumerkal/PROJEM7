package com.wyd.empire.world.server.handler.account;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.account.SetToken;
import com.wyd.empire.world.model.player.WorldPlayer;
import com.wyd.empire.world.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code> SetTokenHandler</code>
 * 
 * @since JDK 1.6
 */
public class SetTokenHandler implements IDataHandler {
	private static Logger log = Logger.getLogger(SetTokenHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		SetToken setToken = (SetToken) data;
		try {
			player.getPlayer().setToken(setToken.getToken());
			player.getPlayer().setUdid(setToken.getUdid());
			ServiceManager.getManager().getPlayerService().savePlayerData(player.getPlayer());
		} catch (Exception ex) {
			log.error(ex, ex);
			ex.printStackTrace();
		}
		return null;
	}
}