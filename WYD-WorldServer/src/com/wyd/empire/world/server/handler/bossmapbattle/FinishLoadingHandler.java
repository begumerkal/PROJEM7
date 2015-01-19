package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.FinishLoading;
import com.wyd.empire.protocol.data.bossmapbattle.GotoBattle;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.battle.CombatChara;
import com.wyd.empire.world.common.util.ServiceUtils;
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
		try {
			int battleId = finishLoading.getBattleId();
			int playerId = finishLoading.getPlayerId();
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			ServiceManager.getManager().getBossBattleTeamService().ready(battleId, playerId);
			if (ServiceManager.getManager().getBossBattleTeamService().isReady(battleId)) {

				battleTeam.setStat(1);
				GotoBattle gotoBattle = new GotoBattle();
				gotoBattle.setBattleId(battleId);
				gotoBattle.setWind(battleTeam.getWind());
				CombatChara chara = ServiceManager.getManager().getBossBattleTeamService().getActionChara(battleId);
				switch (chara.getType()) {
					case CombatChara.CHARA_TYPE_PLAYER :
						gotoBattle.setPlayerOrGuai(0);
						gotoBattle.setCurrentId(chara.getCombat().getId());
						break;
					case CombatChara.CHARA_TYPE_GUAI :
						gotoBattle.setPlayerOrGuai(1);
						gotoBattle.setCurrentId(chara.getCombatGuai().getBattleId());
						break;
					default :
						gotoBattle.setPlayerOrGuai(1);
						gotoBattle.setCurrentId(chara.getType());
						break;
				}
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
				gotoBattle.setRunTimes(battleTeam.getRunTimes());
				battleTeam.setBattleRand(battleRand);
				for (Combat combat : battleTeam.getCombatList()) {
					combat.setCurRound(false);
					if (!combat.isLost() && !combat.isRobot()) {
						combat.getPlayer().sendData(gotoBattle);
					}
				}
				battleTeam.setCurRound(false);
				ServiceManager.getManager().getBossBattleTeamService().gameOver(battleId);
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
