package com.wyd.empire.world.server.handler.singlemap;

import org.apache.log4j.Logger;

import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 挑战失败
 * 
 * @author zengxc
 * 
 */

public class ChallengeFailHandler implements IDataHandler {
	Logger log = Logger.getLogger(ChallengeFailHandler.class);

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		session.getPlayer(data.getSessionId()).outSingleMap();

	}

}
