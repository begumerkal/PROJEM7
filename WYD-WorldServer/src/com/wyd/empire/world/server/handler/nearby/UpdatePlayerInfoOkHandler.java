package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.UpdatePlayerInfoOk;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 更新玩家的坐标信息成功
 * 
 * @author zguoqiu
 */
public class UpdatePlayerInfoOkHandler implements IDataHandler {
	private Logger log;

	public UpdatePlayerInfoOkHandler() {
		this.log = Logger.getLogger(UpdatePlayerInfoOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		UpdatePlayerInfoOk updatePlayerInfoOk = (UpdatePlayerInfoOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(updatePlayerInfoOk.getPlayerId());
			if (null != player) {
				player.sendData(updatePlayerInfoOk);
				if (player.getPlayerInfo().getNearbyId() != updatePlayerInfoOk.getMyInfoId()) {
					player.getPlayerInfo().setNearbyId(updatePlayerInfoOk.getMyInfoId());
					player.updatePlayerInfo();
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}