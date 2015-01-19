package com.wyd.empire.world.server.handler.player;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.player.NoviceSteps;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 设置玩家新手教程完成步骤
 * 
 * @author Administrator
 */
public class NoviceStepsHandler implements IDataHandler {
	Logger log = Logger.getLogger(NoviceStepsHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		NoviceSteps noviceSteps = (NoviceSteps) data;
		try {
			player.getPlayer().setSteps(noviceSteps.getSteps());
		} catch (Exception ex) {
			this.log.error(ex, ex);
		}
	}
}
