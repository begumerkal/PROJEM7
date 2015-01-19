package com.wyd.empire.world.server.handler.cross;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.QuitBattleOk;
import com.wyd.empire.protocol.data.cross.CrossQuitBattleOk;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家强退
 * 
 * @author zguoqiu
 */
public class CrossQuitBattleOkHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossQuitBattleOkHandler.class);

	public void handle(AbstractData data) throws Exception {
		CrossQuitBattleOk cqbo = (CrossQuitBattleOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService()
					.getOnlineWorldPlayer(ServiceManager.getManager().getCrossService().getPlayerId(cqbo.getPlayerId()));
			if (null == player)
				return;
			player.setBattleId(0);
			QuitBattleOk quitBattleOk = new QuitBattleOk();
			quitBattleOk.setBattleId(cqbo.getBattleId());
			quitBattleOk.setPlayerId(cqbo.getPlayerId());
			player.sendData(quitBattleOk);
		} catch (Exception e) {
			log.info(e, e);
		}
	}
}