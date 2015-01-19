package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.BigSkill;
import com.wyd.empire.protocol.data.bossmapbattle.OtherBigSkill;
import com.wyd.empire.world.battle.BossBattleTeam;
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
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int battleId = bigSkill.getBattleId();
			int playerOrGuai = bigSkill.getPlayerOrGuai();
			int currentId = bigSkill.getCurrentId();
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			if (null == battleTeam) {
				return;
			}
			Combat shootPlayer = battleTeam.getCombatMap().get(player.getId());
			if (!shootPlayer.isRobot() && !shootPlayer.isGuaiPlayer() && !shootPlayer.isLost() && shootPlayer.getAngryValue() < 100) {
				shootPlayer.killLine(player.getId());
				GameLogService.battleCheat(player.getId(), player.getLevel(), battleTeam.getMapId(), 6, 0, 4, shootPlayer.getAngryValue()
						+ "");
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
			bigSkillAvoid.setPlayerOrGuai(playerOrGuai);
			bigSkillAvoid.setCurrentId(currentId);
			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isLost() && !combat.isRobot() && combat.getId() != player.getId()) {
					combat.getPlayer().sendData(bigSkillAvoid);
				}
			}
			// 重置怒气值
			ServiceManager.getManager().getBossBattleTeamService().updateAngryValue(battleId, playerOrGuai, currentId, 0, true);
			// TODO 更新任务,不知道是不是完全正确
			if (playerOrGuai == 0) {// 更新任务
				Combat combat = battleTeam.getCombatMap().get(currentId);
				if (!combat.isRobot()) {
					ServiceManager.getManager().getTaskService().useBigSkill(combat.getPlayer());
					ServiceManager.getManager().getTitleService().useBigSkill(player);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex, ex);
		}
	}
}
