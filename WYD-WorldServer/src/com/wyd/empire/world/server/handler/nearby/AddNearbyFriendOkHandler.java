package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.AddNearbyFriendOk;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 添加附近好友成功
 * 
 * @author zguoqiu
 */
public class AddNearbyFriendOkHandler implements IDataHandler {
	private Logger log;

	public AddNearbyFriendOkHandler() {
		this.log = Logger.getLogger(AddNearbyFriendOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		AddNearbyFriendOk addNearbyFriendOk = (AddNearbyFriendOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(addNearbyFriendOk.getPlayerId());
			if (null != player) {
				player.sendData(addNearbyFriendOk);
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}