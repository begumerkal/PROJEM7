package com.wyd.empire.world.server.handler.pet;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.pet.OpenBarInfo;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.base.IPlayerPetService;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 请求开启宠物槽
 * 
 * @author zengxc
 *
 */
public class RequestOpenBarHandler implements IDataHandler {
	Logger log = Logger.getLogger(RequestOpenBarHandler.class);
	IPlayerPetService playerPetService = null;
	ServiceManager manager = ServiceManager.getManager();

	@Override
	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		playerPetService = manager.getPlayerPetService();
		try {
			int openNum = playerPetService.openBarNum(player.getId());
			int diamond = playerPetService.getOpenBarDiamond(openNum + 1);
			OpenBarInfo info = new OpenBarInfo(data.getSessionId(), data.getSerial());
			info.setOpenNeedDiamond(diamond);
			session.write(info);
		} catch (Exception ex) {
			log.error(ex, ex);
			throw new ProtocolException(ex.getMessage(), data.getSerial(), data.getSessionId(), data.getType(), data.getSubType());
		}
	}

}
