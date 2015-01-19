package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.DeleteNearbyMailOk;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 删除好友邮件成功
 * 
 * @author zguoqiu
 */
public class DeleteNearbyMailOkHandler implements IDataHandler {
	private Logger log;

	public DeleteNearbyMailOkHandler() {
		this.log = Logger.getLogger(DeleteNearbyMailOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		DeleteNearbyMailOk deleteNearbyMailOk = (DeleteNearbyMailOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(deleteNearbyMailOk.getPlayerId());
			if (null != player) {
				player.sendData(deleteNearbyMailOk);
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}