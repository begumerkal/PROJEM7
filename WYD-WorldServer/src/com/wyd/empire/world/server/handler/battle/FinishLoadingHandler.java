package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.FinishLoading;
import com.wyd.empire.protocol.data.battle.GotoBattle;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 通知已经完成加载
 * 
 * @author Administrator
 */
public class FinishLoadingHandler implements IDataHandler {
	Logger log = Logger.getLogger(FinishLoadingHandler.class);

	public void handle(AbstractData data) throws Exception {
		FinishLoading finishLoading = (FinishLoading) data;
		int battleId = finishLoading.getBattleId();
		int playerId = finishLoading.getPlayerId();
		if (battleId > 0) {
			try {
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null == battleTeam) {
					return;
				}
				ServiceManager.getManager().getBattleTeamService().ready(battleId, playerId);
				if (ServiceManager.getManager().getBattleTeamService().isReady(battleId)) {
					battleTeam.setStat(1);
					battleTeam.createWind();
					battleTeam.createEventId();
					GotoBattle gotoBattle = new GotoBattle();
					gotoBattle.setBattleId(battleId);
					gotoBattle.setWind(battleTeam.getWind());
					WorldPlayer worldPlayer = ServiceManager.getManager().getBattleTeamService().getActionPlayer(battleId);
					if (null == worldPlayer) {
						ServiceManager.getManager().getBattleTeamService().gameOver(battleTeam, 0);
						return;
					}
					gotoBattle.setCurrentPlayerId(worldPlayer.getId());
					int[] attackRate = new int[10];
					for (int i = 0; i < 10; i++) {
						attackRate[i] = ServiceUtils.getRandomNum(960, 1031);
					}
					gotoBattle.setAttackRate(attackRate);
					int[] battleRand = new int[10];
					for (int i = 0; i < 10; i++) {
						battleRand[i] = ServiceUtils.getRandomNum(0, 10001);
					}
					gotoBattle.setBattleRand(battleRand);
					battleTeam.setBattleRand(battleRand);

					for (Combat combat : ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId).getCombatList()) {
						if (!combat.isLost() && !combat.isRobot()) {
							gotoBattle.setPlayerId(combat.getId());
							combat.getPlayer().sendData(gotoBattle);
						}
					}
					ServiceManager.getManager().getBattleTeamService().gameOver(battleTeam, 0);
				}
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		} else {
			ServiceManager.getManager().getCrossService().finishLoading(battleId, playerId);
		}
	}
}
