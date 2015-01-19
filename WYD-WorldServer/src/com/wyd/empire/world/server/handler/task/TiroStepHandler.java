package com.wyd.empire.world.server.handler.task;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.task.TiroStep;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 新手指引进行到的步骤
 * 
 * @author zguoqiu
 */
public class TiroStepHandler implements IDataHandler {
	Logger log = Logger.getLogger(TiroStepHandler.class);

	public void handle(AbstractData data) throws Exception {
		TiroStep tiroStep = (TiroStep) data;
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		try {
			ServiceManager.getManager().getTaskService().getService().setPlayerGuide(player.getId(), tiroStep.getId(), tiroStep.getStep());
		} catch (Exception ex) {
			log.error(ex, ex);
			System.out.println(ex.getMessage());
		}
	}
}
