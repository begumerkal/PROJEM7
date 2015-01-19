package com.wyd.empire.world.server.handler.system;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.system.BattleShakeHands;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 类 <code>ShakeHandsHandler</code>客户端网络握手协议。保持中间环节线路通畅<li>实现IDataHandler接口</li>
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author mazheng
 * 
 */
public class BattleShakeHandsHandler implements IDataHandler {
	Logger log = Logger.getLogger(BattleShakeHandsHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer player = session.getPlayer(data.getSessionId());
		if (null == player)
			return;
		BattleShakeHands shakehands = (BattleShakeHands) data;
		try {
			int battleId = shakehands.getBattleId();
			if (battleId < 0) {
				ServiceManager.getManager().getCrossService()
						.crossShakeHands(battleId, ServiceManager.getManager().getCrossService().getCrossPlayerId(player.getId()));
			} else {
				player.setActionTime(System.currentTimeMillis());
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}