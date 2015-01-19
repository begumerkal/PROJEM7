package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.CanStartCurRound;
import com.wyd.empire.protocol.data.battle.EndCurRound;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.common.util.ServiceUtils;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 战斗操作计时间到
 * 
 * @author Administrator
 */
public class EndCurRoundHandler implements IDataHandler {
	Logger log = Logger.getLogger(EndCurRoundHandler.class);

	public void handle(AbstractData data) throws Exception {
		EndCurRound endCurRound = (EndCurRound) data;
		int battleId = endCurRound.getBattleId();
		int playerId = endCurRound.getPlayerId();
		int currentPlayerId = endCurRound.getCurrentPlayerId();
		if (battleId > 0) {
			try {
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null == battleTeam) {
					return;
				}
				ServiceManager.getManager().getBattleTeamService().playerRun(battleId, playerId);
				if (battleTeam.isCurRound()) {
					ServiceManager.getManager().getBattleTeamService().sendSort(battleTeam, currentPlayerId);
					battleTeam.createWind();
					// 龙卷风事件必须要有一个风力
					if (battleTeam.getEventId() == 1) {
						while (battleTeam.getWind() == 0) {
							battleTeam.createWind();
						}
					}
					CanStartCurRound canStartCurRound = new CanStartCurRound();
					canStartCurRound.setBattleId(battleId);
					canStartCurRound.setWind(battleTeam.getWind());
					if (battleTeam.allFrozen()) {
						// System.out.println("ActionPlayerId:-1");
						canStartCurRound.setCurrentPlayerId(-1);
						battleTeam.setNewRun(true);
					} else {
						WorldPlayer worldPlayer = ServiceManager.getManager().getBattleTeamService().getActionPlayer(battleId);
						if (null == worldPlayer) {
							if (ServiceManager.getManager().getBattleTeamService().gameOver(battleTeam, 0)) {
								return;
							} else {
								// System.out.println("ActionPlayerId:-1");
								canStartCurRound.setCurrentPlayerId(-1);
							}
						} else {
							// System.out.println("ActionPlayerId:" +
							// worldPlayer.getId());
							canStartCurRound.setCurrentPlayerId(worldPlayer.getId());
						}
					}
					int[] attackRate = new int[10];
					for (int i = 0; i < 10; i++) {
						attackRate[i] = ServiceUtils.getRandomNum(960, 1031);
					}
					canStartCurRound.setAttackRate(attackRate);
					int[] battleRand = new int[10];
					for (int i = 0; i < 10; i++) {
						battleRand[i] = ServiceUtils.getRandomNum(0, 10001);
					}
					canStartCurRound.setBattleRand(battleRand);
					battleTeam.setBattleRand(battleRand);
					if (battleTeam.isNewRun()) {
						canStartCurRound.setIsNewRound(1);
						battleTeam.createEventId();
					} else {
						canStartCurRound.setIsNewRound(0);
					}
					for (Combat combat : battleTeam.getCombatList()) {
						combat.setCurRound(false);
						if (!combat.isLost() && !combat.isRobot()) {
							canStartCurRound.setPlayerId(combat.getId());
							combat.getPlayer().sendData(canStartCurRound);
						}
					}
					battleTeam.setCurRound(false);

				}
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		} else {
			ServiceManager.getManager().getCrossService().endCurRound(battleId, playerId, currentPlayerId);
		}
	}
}
