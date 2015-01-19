package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.CanStartCurRound;
import com.wyd.empire.protocol.data.bossmapbattle.EndCurRound;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.battle.CombatChara;
import com.wyd.empire.world.common.util.ServiceUtils;
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
		try {
			int battleId = endCurRound.getBattleId();
			int playerOrGuai = endCurRound.getPlayerOrGuai();
			int currentId = endCurRound.getCurrentId();
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			if (null == battleTeam) {
				return;
			}
			if (0 == playerOrGuai) {
				ServiceManager.getManager().getBossBattleTeamService().playerRun(battleId, playerOrGuai, currentId);
			}
			if (battleTeam.isCurRound()) {
				try {
					if (ServiceManager.getManager().getBossBattleTeamService().gameOver(battleId)) {
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				CanStartCurRound canStartCurRound = new CanStartCurRound();
				canStartCurRound.setBattleId(battleId);
				canStartCurRound.setWind(battleTeam.getWind());
				CombatChara chara = ServiceManager.getManager().getBossBattleTeamService().getActionChara(battleId);
				if (chara == null) {
					if (battleTeam.getRunTimes() > 0 && battleTeam.getRound() >= battleTeam.getRunTimes()) {
						for (Combat combat : battleTeam.getCombatList()) {
							if (!combat.isLost() && !combat.isRobot()) {
								combat.setHp(0);
								combat.setDead(true);
							}
						}
						ServiceManager.getManager().getBossBattleTeamService().gameOver(battleId);
						return;
					}
					ServiceManager.getManager().getBossBattleTeamService().sendSort(battleId);
					chara = ServiceManager.getManager().getBossBattleTeamService().getActionChara(battleId);
				} else {
					battleTeam.setNewRun(false);// 不是新一轮
				}
				switch (chara.getType()) {
					case CombatChara.CHARA_TYPE_PLAYER :
						canStartCurRound.setPlayerOrGuai(0);
						canStartCurRound.setCurrentId(chara.getCombat().getId());
						break;
					case CombatChara.CHARA_TYPE_GUAI :
						canStartCurRound.setPlayerOrGuai(1);
						canStartCurRound.setCurrentId(chara.getCombatGuai().getBattleId());
						break;
					default :
						canStartCurRound.setPlayerOrGuai(1);
						canStartCurRound.setCurrentId(chara.getType());
						break;
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
				int isNewRound = battleTeam.isNewRun() ? 1 : 0;
				canStartCurRound.setIsNewRound(isNewRound);
				canStartCurRound.setRoundTimes(battleTeam.getRound());
				if (battleTeam.isNewRun()) {
					// 设置玩家本回合是否可以触发无敌
					battleTeam.setCanInvincible(battleRand[0] < 5000 && battleTeam.getRound() > 1);
				}
				for (Combat combat : battleTeam.getCombatList()) {
					combat.setCurRound(false);
					if (!combat.isLost() && !combat.isRobot()) {
						combat.getPlayer().sendData(canStartCurRound);
					}
				}
				battleTeam.setCurRound(false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
		}
	}
}
