package com.wyd.empire.world.server.handler.admin;

import java.util.Date;

import com.wyd.empire.protocol.data.admin.Banned;
import com.wyd.empire.protocol.data.admin.BannedResult;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 帐号封禁
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author mazheng
 */
public class BannedHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		BannedResult bannedResult = new BannedResult(data.getSessionId(), data.getSerial());
		try {
			Banned banned = (Banned) data;
			int playerId = banned.getPlayerId();
			int status = banned.getStatus();
			long sTime = (long) banned.getStartTime() * 60 * 1000;
			long eTime = (long) banned.getEndTime() * 60 * 1000;
			WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
			Player player = worldPlayer.getPlayer();
			player.setStatus((byte) status);
			player.setBsTime(new Date(sTime));
			player.setBeTime(new Date(eTime));
			ServiceManager.getManager().getPlayerService().savePlayerData(player);
			bannedResult.setSuccess(true);
			ServiceManager.getManager().getPlayerService().killLine(playerId);
		} catch (Exception e) {
			bannedResult.setSuccess(false);
		}
		session.write(bannedResult);
	}
}