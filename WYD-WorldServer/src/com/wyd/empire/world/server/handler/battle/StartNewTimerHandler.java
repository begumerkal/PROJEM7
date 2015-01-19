package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.StartNewTimer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 开始新的战斗操作计时
 * 
 * @author Administrator
 */
public class StartNewTimerHandler implements IDataHandler {
	Logger log = Logger.getLogger(StartNewTimerHandler.class);

	public void handle(AbstractData data) throws Exception {
		StartNewTimer startNewTimer = (StartNewTimer) data;
		int battleId = startNewTimer.getBattleId();
		int playerId = startNewTimer.getPlayerId();
		if (battleId > 0) {
			try {
				ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId).getCombatMap().get(playerId).setState(3);
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		} else {
			ServiceManager.getManager().getCrossService().startNewTimer(battleId, playerId);
		}
	}
}
