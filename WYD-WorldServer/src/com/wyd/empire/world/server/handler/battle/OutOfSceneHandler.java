package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OutOfScene;
import com.wyd.empire.protocol.data.battle.SomeOneDead;
import com.wyd.empire.protocol.data.battle.UpdateHP;
import com.wyd.empire.protocol.data.battle.UpdateMedal;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
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
		int battleId = outOfScene.getBattleId();
		int playerId = outOfScene.getPlayerId();
		int currentPlayerId = outOfScene.getCurrentPlayerId();
		if (battleId > 0) {
			try {
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null == battleTeam) {
					return;
				}
				Combat hurtPlayer = battleTeam.getCombatMap().get(playerId);
				if (hurtPlayer.isDead()) {
					return;
				}
				boolean firstBlood = false;
				hurtPlayer.setHp(0);
				hurtPlayer.setDead(true);
				hurtPlayer.setBeKilledCount(hurtPlayer.getBeKilledCount() + 1);
				hurtPlayer.setBeKillRound(battleTeam.getRound());
				Combat shootPlayer = battleTeam.getCombatMap().get(currentPlayerId);
				// 判断玩家杀死的是否是队友
				if (shootPlayer.getCamp() != hurtPlayer.getCamp()) {
					shootPlayer.setKillCount(shootPlayer.getKillCount() + 1);
					if (0 == battleTeam.getFirstHurtPlayerId()) {// 首杀
						battleTeam.setFirstHurtPlayerId(playerId);
						firstBlood = true;
					}
				}
				if (0 == hurtPlayer.getCamp()) {
					battleTeam.setCamp0BeKillCount(battleTeam.getCamp0BeKillCount() + 1);
				} else {
					battleTeam.setCamp1BeKillCount(battleTeam.getCamp1BeKillCount() + 1);
				}
				// 玩家自杀
				if (currentPlayerId == playerId) {
					hurtPlayer.setSuicide(true);
				} else {
					ServiceManager.getManager().getTaskService().battleBeatTask(shootPlayer, hurtPlayer);
				}
				ServiceManager.getManager().getTitleService().battleBeatTask(shootPlayer, hurtPlayer, 1);
				UpdateHP updateHP = new UpdateHP();
				updateHP.setBattleId(battleId);
				updateHP.setHurtcount(1);
				updateHP.setPlayerIds(new int[]{playerId});
				updateHP.setHp(new int[]{0});
				updateHP.setAttackType(0);
				SomeOneDead someOneDead = new SomeOneDead();
				someOneDead.setBattleId(battleId);
				someOneDead.setDeadPlayerCount(1);
				someOneDead.setPlayerIds(new int[]{playerId});
				someOneDead.setFirstBlood(firstBlood);
				someOneDead.setPlayerId(currentPlayerId);
				UpdateMedal updateMedal = new UpdateMedal();
				updateMedal.setBattleId(battleId);
				updateMedal.setCampCount(2);
				updateMedal.setCampId(new int[]{0, 1});
				updateMedal.setCampMedalNum(new int[]{battleTeam.getCamp1BeKillCount(), battleTeam.getCamp0BeKillCount()});
				for (Combat cb : battleTeam.getCombatList()) {
					if (!cb.isLost() && !cb.isRobot()) {
						cb.getPlayer().sendData(updateHP);
						cb.getPlayer().sendData(someOneDead);
						updateMedal.setPlayerId(cb.getId());
						cb.getPlayer().sendData(updateMedal);
					}
				}
				ServiceManager.getManager().getBattleTeamService().gameOver(battleTeam, shootPlayer.getCamp());
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		} else {
			ServiceManager.getManager().getCrossService().outOfScene(battleId, playerId, currentPlayerId);
		}
	}
}
