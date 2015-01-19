package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.cross.CrossKillLine;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 作弊提下线
 * 
 * @author zguoqiu
 */
public class CrossKillLineHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossKillLineHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossKillLine ckl = (CrossKillLine) data;
		try {
			int playerId = ServiceManager.getManager().getCrossService().getPlayerId(ckl.getPlayerId());
			System.out.println("作弊踢下线 id1:" + ckl.getPlayerId() + "   id2:" + playerId);
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(playerId);
			if (null != player) {
				player.writeLog("作弊踢下线");
				ServiceManager.getManager().getPlayerService().killLine(player.getId());
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}