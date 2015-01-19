package com.wyd.empire.world.server.handler.task;

import org.apache.log4j.Logger;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 完成新手指引
 * 
 * @author Administrator
 */
public class TiroFlishHandler implements IDataHandler {
	Logger log = Logger.getLogger(TiroFlishHandler.class);

	public void handle(AbstractData data) throws Exception {
		// ConnectSession session = (ConnectSession) data.getHandlerSource();
		// WorldPlayer player = session.getPlayer(data.getSessionId());
		// TiroFlish tiroFlish = (TiroFlish) data;
		try {
			// PlayerGuide playerGuide = (PlayerGuide)
			// ServiceManager.getManager().getGuideService().get(PlayerGuide.class,
			// tiroFlish.getId());
			// playerGuide.setStatus(1);
			// ServiceManager.getManager().getGuideService().update(playerGuide);
			// if (1 == playerGuide.getGuide().getId().intValue()) {
			// ServiceManager.getManager().getPushService().addPushInfo(player);
			// }
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
