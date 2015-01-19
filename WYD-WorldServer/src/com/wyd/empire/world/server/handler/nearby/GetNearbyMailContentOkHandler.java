package com.wyd.empire.world.server.handler.nearby;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.nearby.GetNearbyMailContentOk;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 查看附件好友邮件内容成功
 * 
 * @author zguoqiu
 */
public class GetNearbyMailContentOkHandler implements IDataHandler {
	private Logger log;

	public GetNearbyMailContentOkHandler() {
		this.log = Logger.getLogger(GetNearbyMailContentOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		GetNearbyMailContentOk getNearbyMailContentOk = (GetNearbyMailContentOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(getNearbyMailContentOk.getPlayerId());
			if (null != player) {
				player.sendData(getNearbyMailContentOk);
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}