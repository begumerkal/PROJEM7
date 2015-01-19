package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.PostionsForPlayers;
import com.wyd.empire.protocol.data.cross.CrossPostionsForPlayers;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 初始化玩家坐标
 * 
 * @author zguoqiu
 */
public class CrossPostionsForPlayersHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossPostionsForPlayersHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossPostionsForPlayers pfp = (CrossPostionsForPlayers) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService()
					.getOnlineWorldPlayer(ServiceManager.getManager().getCrossService().getPlayerId(pfp.getPlayerId()));
			if (null != player) {
				PostionsForPlayers postionsForPlayers = new PostionsForPlayers();
				postionsForPlayers.setBattleId(pfp.getBattleId());
				postionsForPlayers.setPlayerId(player.getId());
				postionsForPlayers.setIdcount(pfp.getIdcount());
				postionsForPlayers.setPlayerIds(pfp.getPlayerIds());
				postionsForPlayers.setPostionX(pfp.getPostionX());
				postionsForPlayers.setPostionY(pfp.getPostionY());
				player.sendData(postionsForPlayers);
			}
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}