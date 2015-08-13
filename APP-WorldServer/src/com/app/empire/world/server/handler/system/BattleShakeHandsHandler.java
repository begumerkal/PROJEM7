package com.app.empire.world.server.handler.system;

import org.apache.log4j.Logger;

import com.app.empire.protocol.data.system.BattleShakeHands;
import com.app.empire.world.model.player.WorldPlayer;
import com.app.empire.world.service.factory.ServiceManager;
import com.app.empire.world.session.ConnectSession;
import com.app.protocol.data.AbstractData;
import com.app.protocol.handler.IDataHandler;

/**
 * 客户端网络握手协议。保持中间环节线路通畅
 * 
 * @author doter
 */
public class BattleShakeHandsHandler implements IDataHandler {
	Logger log = Logger.getLogger(BattleShakeHandsHandler.class);

	public AbstractData handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer worldPlayer = session.getPlayer(data.getSessionId());
		if (null == worldPlayer)
			return null;
		BattleShakeHands shakehands = (BattleShakeHands) data;
		try {
			int battleId = shakehands.getBattleId();
			if (battleId < 0) {
				ServiceManager
						.getManager()
						.getCrossService()
						.crossShakeHands(battleId,
								ServiceManager.getManager().getCrossService().getCrossPlayerId(worldPlayer.getPlayer().getId()));
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
		return null;
	}
}