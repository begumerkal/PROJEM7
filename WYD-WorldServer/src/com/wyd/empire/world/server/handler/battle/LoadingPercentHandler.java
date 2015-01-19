package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.LoadingPercent;
import com.wyd.empire.protocol.data.battle.OtherLoadingPercent;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送加载百份比
 * 
 * @author Administrator
 */
public class LoadingPercentHandler implements IDataHandler {
	Logger log = Logger.getLogger(LoadingPercentHandler.class);

	public void handle(AbstractData data) throws Exception {
		LoadingPercent loadingPercent = (LoadingPercent) data;
		int battleId = loadingPercent.getBattleId();
		int playerId = loadingPercent.getCurrentPlayerId();
		int percent = loadingPercent.getPercent();
		if (battleId > 0) {
			try {
				OtherLoadingPercent otherLoadingPercent = new OtherLoadingPercent();
				otherLoadingPercent.setBattleId(battleId);
				otherLoadingPercent.setCurrentPlayerId(playerId);
				otherLoadingPercent.setPercent(percent);
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null != battleTeam) {
					for (Combat combat : battleTeam.getCombatList()) {
						if (!combat.isLost() && !combat.isRobot() && combat.getId() != playerId) {
							otherLoadingPercent.setPlayerId(combat.getId());
							combat.getPlayer().sendData(otherLoadingPercent);
						}
					}
				}
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		} else {
			ServiceManager.getManager().getCrossService().loadingPercent(battleId, playerId, percent);
		}
	}
}
