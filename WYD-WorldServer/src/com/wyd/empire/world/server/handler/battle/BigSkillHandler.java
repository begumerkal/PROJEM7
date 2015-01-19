package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.BigSkill;
import com.wyd.empire.protocol.data.battle.OtherBigSkill;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 使用大招
 * 
 * @author Administrator
 */
public class BigSkillHandler implements IDataHandler {
	Logger log = Logger.getLogger(BigSkillHandler.class);

	public void handle(AbstractData data) throws Exception {
		BigSkill bigSkill = (BigSkill) data;
		int battleId = bigSkill.getBattleId();
		int playerId = bigSkill.getPlayerId();
		if (battleId > 0) {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			try {
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null == battleTeam) {
					return;
				}
				Combat shootPlayer = battleTeam.getCombatMap().get(playerId);
				if (!shootPlayer.isRobot() && !shootPlayer.isGuaiPlayer() && !shootPlayer.isLost() && shootPlayer.getAngryValue() < 100) {
					shootPlayer.killLine(playerId);
					GameLogService.battleCheat(player.getId(), player.getLevel(), battleTeam.getMapId(), battleTeam.getBattleMode(), 0, 4,
							shootPlayer.getAngryValue() + "");
				}
				float attPercent = 0;
				switch (shootPlayer.getBigSkillType()) {
					case 0 :
						attPercent = 0.3f;
						shootPlayer.setPlayerAttackTimes(7);
						break;
					case 1 :
						attPercent = 2.5f;
						shootPlayer.setPlayerAttackTimes(1);
						break;
					case 2 :
						attPercent = 0.4f;
						shootPlayer.setPlayerAttackTimes(7);
						break;
				}
				shootPlayer.setHurtRate(attPercent);
				shootPlayer.setWillFrozen(0);
				battleTeam.setUseBigKill(true);
				OtherBigSkill bigSkillAvoid = new OtherBigSkill();
				bigSkillAvoid.setBattleId(battleId);
				bigSkillAvoid.setCurrentPlayerId(playerId);
				for (Combat combat : battleTeam.getCombatList()) {
					if (!combat.isLost() && !combat.isRobot() && combat.getId() != playerId) {
						bigSkillAvoid.setPlayerId(combat.getId());
						combat.getPlayer().sendData(bigSkillAvoid);
					}
				}
				// 重置怒气值
				ServiceManager.getManager().getBattleTeamService().updateAngryValue(battleId, playerId, 0, true);
				// 更新任务
				if (!ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId).getCombatMap().get(playerId).isRobot()) {
					ServiceManager.getManager().getTaskService().useBigSkill(player);
					ServiceManager.getManager().getTitleService().useBigSkill(player);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				log.error(ex, ex);
			}
		} else {
			ServiceManager.getManager().getCrossService().bigSkill(battleId, playerId);
		}
	}
}
