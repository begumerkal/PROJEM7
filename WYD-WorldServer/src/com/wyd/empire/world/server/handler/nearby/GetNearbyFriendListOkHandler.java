package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.GetNearbyFriendListOk;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取附近好友列表成功
 * 
 * @author zguoqiu
 */
public class GetNearbyFriendListOkHandler implements IDataHandler {
	private Logger log;

	public GetNearbyFriendListOkHandler() {
		this.log = Logger.getLogger(GetNearbyFriendListOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		GetNearbyFriendListOk getNearbyFriendListOk = (GetNearbyFriendListOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(getNearbyFriendListOk.getPlayerId());
			if (null != player) {
				player.sendData(getNearbyFriendListOk);
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}