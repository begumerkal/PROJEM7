package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherShoot;
import com.wyd.empire.protocol.data.battle.Shoot;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发射
 * 
 * @author Administrator
 */
public class ShootHandler implements IDataHandler {
	Logger log = Logger.getLogger(ShootHandler.class);

	public void handle(AbstractData data) throws Exception {
		Shoot shoot = (Shoot) data;
		if (shoot.getBattleId() > 0) {
			int battleId = shoot.getBattleId();
			int playerId = shoot.getPlayerId();
			int speedx = shoot.getSpeedx();
			int speedy = shoot.getSpeedy();
			byte leftRight = shoot.getLeftRight();
			int startx = shoot.getStartx();
			int starty = shoot.getStarty();
			int playerCount = shoot.getPlayerCount();
			int[] playerIds = shoot.getPlayerIds();
			int[] curPositionX = shoot.getCurPositionX();
			int[] curPositionY = shoot.getCurPositionY();
			int[] attackerRandom = shoot.getAttackerRandom(); // 攻击者武器技能触发使用随机数下标
			try {
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null != battleTeam) {
					OtherShoot otherShoot = new OtherShoot();
					otherShoot.setBattleId(battleId);
					otherShoot.setCurrentPlayerId(playerId);
					otherShoot.setSpeedx(speedx);
					otherShoot.setSpeedy(speedy);
					otherShoot.setLeftRight(leftRight);
					otherShoot.setStartx(startx);
					otherShoot.setStarty(starty);
					otherShoot.setPlayerCount(playerCount);
					otherShoot.setPlayerIds(playerIds);
					otherShoot.setCurPositionX(curPositionX);
					otherShoot.setCurPositionY(curPositionY);
					for (Combat combat : battleTeam.getCombatList()) {
						if (!combat.isLost() && !combat.isRobot() && combat.getId() != playerId) {
							otherShoot.setPlayerId(combat.getId());
							combat.getPlayer().sendData(otherShoot);
						}
					}
					Combat combat = battleTeam.getCombatMap().get(playerId);
					combat.checkAttackerSkill(attackerRandom, battleTeam.getBattleRand(), true);
					combat.setShootTimes(combat.getShootTimes() + 1);
					combat.setActionTimes(combat.getActionTimes() + 1);
					if (!battleTeam.isUseKill()) {
						combat.setTiredValue(combat.getTiredValue() + 5);
					} else {
						battleTeam.setUseKill(false);
					}
					if (!battleTeam.isUseBigKill()) {
						ServiceManager.getManager().getBattleTeamService().updateAngryValue(battleId, playerId, 17, false);
					} else {
						battleTeam.setUseBigKill(false);
					}
				}
			} catch (Exception ex) {
				log.error(ex, ex);
				ex.printStackTrace();
			}
		} else {
			ServiceManager.getManager().getCrossService().shoot(shoot);
		}
	}
}
