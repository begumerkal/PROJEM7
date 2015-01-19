package com.wyd.empire.world.server.handler.admin;

import java.util.Date;

import com.wyd.empire.protocol.data.admin.Gag;
import com.wyd.empire.protocol.data.admin.GagResult;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家禁言
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author mazheng
 */
public class GagHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		GagResult gagResult = new GagResult(data.getSessionId(), data.getSerial());
		try {
			Gag gag = (Gag) data;
			int playerId = gag.getPlayerId();
			int status = gag.getStatus();
			long eTime = (long) gag.getEndTime() * 60 * 1000;
			WorldPlayer worldPlayer = ServiceManager.getManager().getPlayerService().getWorldPlayerById(playerId);
			if (null != worldPlayer) {
				Player player = worldPlayer.getPlayer();
				player.setChatStatus(status);
				player.setProhibitTime(new Date(eTime));
				ServiceManager.getManager().getPlayerService().savePlayerData(player);
				gagResult.setSuccess(true);
			} else {
				gagResult.setSuccess(false);
			}
		} catch (Exception e) {
			gagResult.setSuccess(false);
		}
		session.write(gagResult);
	}
}