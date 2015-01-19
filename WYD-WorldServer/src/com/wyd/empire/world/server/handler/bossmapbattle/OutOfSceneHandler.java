package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.OutOfScene;
import com.wyd.empire.protocol.data.bossmapbattle.SomeOneDead;
import com.wyd.empire.protocol.data.bossmapbattle.UpdateHP;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.battle.CombatChara;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 角色掉出地图外
 * 
 * @author Administrator
 */
public class OutOfSceneHandler implements IDataHandler {
	Logger log = Logger.getLogger(OutOfSceneHandler.class);

	public void handle(AbstractData data) throws Exception {
		OutOfScene outOfScene = (OutOfScene) data;
		try {
			int battleId = outOfScene.getBattleId();
			int playerOrGuai1 = outOfScene.getPlayerOrGuai1();
			int playerId = outOfScene.getPlayerId();// 操作人
			int playerOrGuai2 = outOfScene.getPlayerOrGuai2();
			int currentId = outOfScene.getCurrentId();// 受害人

			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			if (null == battleTeam) {
				return;
			}

			if (playerOrGuai2 == 0) {// 受害人是玩家
				Combat hurtPlayer = battleTeam.getCombatMap().get(currentId);// 受害人对象
				if (hurtPlayer.isDead()) {
					return;
				}

				hurtPlayer.setHp(0);
				hurtPlayer.setDead(true);
				hurtPlayer.setBeKilledCount(hurtPlayer.getBeKilledCount() + 1);
				hurtPlayer.setBeKillRound(battleTeam.getRound());

				if (playerOrGuai1 == CombatChara.CHARA_TYPE_GUAI) {
					Combat shootPlayer = battleTeam.getGuaiMap().get(playerId);
					if (shootPlayer.getCamp() != hurtPlayer.getCamp()) {
						shootPlayer.setKillCount(shootPlayer.getKillCount() + 1);
					}
				} else {
					Combat shootPlayer = battleTeam.getCombatMap().get(playerId);
					if (shootPlayer.getCamp() != hurtPlayer.getCamp()) {
						shootPlayer.setKillCount(shootPlayer.getKillCount() + 1);
					}
				}

				if (0 == hurtPlayer.getCamp()) {
					battleTeam.setCamp0BeKillCount(battleTeam.getCamp0BeKillCount() + 1);
				} else {
					battleTeam.setCamp1BeKillCount(battleTeam.getCamp1BeKillCount() + 1);
				}

				// 自杀
				if (currentId == playerId) {
					hurtPlayer.setSuicide(true);
				}
			}

			else if (playerOrGuai2 == 1) {// 受害人是怪物
				Combat hurtPlayer = battleTeam.getGuaiMap().get(currentId);// 受害人对象
				if (hurtPlayer.isDead()) {
					return;
				}

				hurtPlayer.setHp(0);
				hurtPlayer.setDead(true);
				hurtPlayer.setBeKilledCount(hurtPlayer.getBeKilledCount() + 1);
				hurtPlayer.setBeKillRound(battleTeam.getRound());

				if (playerOrGuai1 == CombatChara.CHARA_TYPE_PLAYER) {
					Combat shootPlayer = battleTeam.getCombatMap().get(playerId);
					if (shootPlayer.getCamp() != hurtPlayer.getCamp()) {
						shootPlayer.setKillCount(shootPlayer.getKillCount() + 1);
					}

				} else {
					Combat shootPlayer = battleTeam.getGuaiMap().get(playerId);
					if (shootPlayer.getCamp() != hurtPlayer.getCamp()) {
						shootPlayer.setKillCount(shootPlayer.getKillCount() + 1);
					}
				}

				if (0 == hurtPlayer.getCamp()) {
					battleTeam.setCamp0BeKillCount(battleTeam.getCamp0BeKillCount() + 1);
				} else {
					battleTeam.setCamp1BeKillCount(battleTeam.getCamp1BeKillCount() + 1);
				}

				// 自杀
				if (currentId == playerId) {
					hurtPlayer.setSuicide(true);
				}
			}

			UpdateHP updateHP = new UpdateHP();
			updateHP.setBattleId(battleId);
			updateHP.setAttackType(0);
			if (playerOrGuai2 == 0) {
				updateHP.setPlayercount(1);
				updateHP.setPlayerIds(new int[]{currentId});
				updateHP.setHp(new int[]{0});
				updateHP.setGuaicount(0);
				updateHP.setGuaiBattleIds(new int[]{});
				updateHP.setGuaiHp(new int[]{});
			} else {
				updateHP.setPlayercount(0);
				updateHP.setPlayerIds(new int[]{});
				updateHP.setHp(new int[]{});
				updateHP.setGuaicount(1);
				updateHP.setGuaiBattleIds(new int[]{currentId});
				updateHP.setGuaiHp(new int[]{0});
			}

			SomeOneDead someOneDead = new SomeOneDead();
			someOneDead.setBattleId(battleId);
			if (playerOrGuai2 == 0) {
				someOneDead.setDeadPlayerCount(1);
				someOneDead.setPlayerIds(new int[]{currentId});
				someOneDead.setDeadGuaiCount(0);
				someOneDead.setGuaiBattleIds(new int[]{});
			} else {
				someOneDead.setDeadPlayerCount(0);
				someOneDead.setPlayerIds(new int[]{});
				someOneDead.setDeadGuaiCount(1);
				someOneDead.setGuaiBattleIds(new int[]{currentId});
			}

			// UpdateMedal updateMedal = new UpdateMedal();
			// updateMedal.setBattleId(battleId);
			// updateMedal.setCampCount(2);
			// updateMedal.setCampId(new int[]{0,1});
			// updateMedal.setCampMedalNum(new
			// int[]{battleTeam.getCamp1BeKillCount(),battleTeam.getCamp0BeKillCount()});
			//
			for (Combat cb : battleTeam.getCombatList()) {
				if (!cb.isLost() && !cb.isRobot()) {
					cb.getPlayer().sendData(updateHP);
					cb.getPlayer().sendData(someOneDead);
					// updateMedal.setPlayerId(cb.getPlayer().getId());
					// cb.getPlayer().sendData(updateMedal);
				}
			}

			// ServiceManager.getManager().getBattleTeamService().gameOver(battleId,
			// shootPlayer.getCamp());
			ServiceManager.getManager().getBossBattleTeamService().gameOver(battleId);
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
