package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.GetNearbyPlayerListOk;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取附近玩家列表成功
 * 
 * @author zguoqiu
 */
public class GetNearbyPlayerListOkHandler implements IDataHandler {
	private Logger log;

	public GetNearbyPlayerListOkHandler() {
		this.log = Logger.getLogger(GetNearbyPlayerListOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		GetNearbyPlayerListOk getNearbyPlayerListOk = (GetNearbyPlayerListOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(getNearbyPlayerListOk.getPlayerId());
			if (null != player) {
				player.sendData(getNearbyPlayerListOk);
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}