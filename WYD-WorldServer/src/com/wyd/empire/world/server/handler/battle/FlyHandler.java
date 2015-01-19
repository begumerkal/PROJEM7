package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.Fly;
import com.wyd.empire.protocol.data.battle.OtherFly;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 飞行
 * 
 * @author Administrator
 */
public class FlyHandler implements IDataHandler {
	Logger log = Logger.getLogger(FlyHandler.class);

	public void handle(AbstractData data) throws Exception {
		Fly fly = (Fly) data;
		int battleId = fly.getBattleId();
		int playerId = fly.getPlayerId();
		int speedx = fly.getSpeedx();
		int speedy = fly.getSpeedy();
		byte leftRight = fly.getLeftRight();
		int isEquip = fly.getIsEquip();
		int startx = fly.getStartx();
		int starty = fly.getStarty();
		int playerCount = fly.getPlayerCount();
		int[] playerIds = fly.getPlayerIds();
		int[] curPositionX = fly.getCurPositionX();
		int[] curPositionY = fly.getCurPositionY();
		if (battleId > 0) {
			try {
				OtherFly otherFly = new OtherFly();
				otherFly.setBattleId(battleId);
				otherFly.setCurrentPlayerId(playerId);
				otherFly.setSpeedx(speedx);
				otherFly.setSpeedy(speedy);
				otherFly.setLeftRight(leftRight);
				otherFly.setIsEquip(isEquip);
				otherFly.setStartx(startx);
				otherFly.setStarty(starty);
				otherFly.setPlayerCount(playerCount);
				otherFly.setPlayerIds(playerIds);
				otherFly.setCurPositionX(curPositionX);
				otherFly.setCurPositionY(curPositionY);

				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null == battleTeam) {
					return;
				}

				for (Combat combat : battleTeam.getCombatList()) {
					if (!combat.isLost() && !combat.isRobot() && combat.getId() != playerId) {
						otherFly.setPlayerId(combat.getId());
						combat.getPlayer().sendData(otherFly);
					}
				}

				Combat combat = battleTeam.getCombatMap().get(playerId);
				if (!combat.isRobot()) {
					ServiceManager.getManager().getTaskService().flySkill(combat.getPlayer());
					ServiceManager.getManager().getTitleService().flySkill(combat.getPlayer());
				}
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		} else {
			ServiceManager
					.getManager()
					.getCrossService()
					.fly(battleId, playerId, speedx, speedy, leftRight, isEquip, startx, starty, playerCount, playerIds, curPositionX,
							curPositionY);
		}
	}
}
