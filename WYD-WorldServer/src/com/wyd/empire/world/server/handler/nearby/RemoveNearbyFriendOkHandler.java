package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.RemoveNearbyFriendOk;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 删除附近好友成功
 * 
 * @author zguoqiu
 */
public class RemoveNearbyFriendOkHandler implements IDataHandler {
	private Logger log;

	public RemoveNearbyFriendOkHandler() {
		this.log = Logger.getLogger(RemoveNearbyFriendOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		RemoveNearbyFriendOk removeNearbyFriendOk = (RemoveNearbyFriendOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(removeNearbyFriendOk.getPlayerId());
			if (null != player) {
				player.sendData(removeNearbyFriendOk);
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}