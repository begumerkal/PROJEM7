package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.SendNearbyMailOk;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送附近好友邮件成功
 * 
 * @author zguoqiu
 */
public class SendNearbyMailOkHandler implements IDataHandler {
	private Logger log;

	public SendNearbyMailOkHandler() {
		this.log = Logger.getLogger(SendNearbyMailOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		SendNearbyMailOk sendNearbyMailOk = (SendNearbyMailOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(sendNearbyMailOk.getPlayerId());
			if (null != player) {
				player.sendData(sendNearbyMailOk);
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}