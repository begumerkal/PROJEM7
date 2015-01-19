package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.nearby.SendNearbyMailCount;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送附近好友未读邮件数量
 * 
 * @author zguoqiu
 */
public class SendNearbyMailCountHandler implements IDataHandler {
	private Logger log;

	public SendNearbyMailCountHandler() {
		this.log = Logger.getLogger(SendNearbyMailCountHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		SendNearbyMailCount snmc = (SendNearbyMailCount) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(snmc.getPlayerId());
			if (null != player && player.getPlayerInfo().getNearbyId() == snmc.getInfoId()) {
				ServiceManager.getManager().getMailService().synMailCount(player, snmc.getMailCount());
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}