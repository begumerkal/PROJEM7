package com.wyd.empire.battle.handler.cross;

import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossOtherShoot;
import com.wyd.empire.protocol.data.cross.CrossShoot;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发射
 * @author Administrator
 */
public class CrossShootHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossShootHandler.class);
	public void handle(AbstractData data) throws Exception {
	    CrossShoot shoot = (CrossShoot) data;
		try {
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
			int[] attackerRandom = shoot.getAttackerRandom();
			BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
			if(null!=battleTeam){
			    CrossOtherShoot otherShoot = new CrossOtherShoot();
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
				battleTeam.sendData(otherShoot);
				Combat combat = battleTeam.getCombatMap().get(playerId);
				combat.checkAttackerSkill(attackerRandom, battleTeam.getBattleRand());
				combat.setShootTimes(combat.getShootTimes()+1);
				combat.setActionTimes(combat.getActionTimes()+1);
				if(!battleTeam.isUseKill()){
					combat.setTiredValue(combat.getTiredValue()+5);
				}else{
					battleTeam.setUseKill(false);
				}
				
				if(!battleTeam.isUseBigKill()){
					ServiceManager.getManager().getBattleTeamService().updateAngryValue(battleId, playerId, 17, false);
				}else{
					battleTeam.setUseBigKill(false);
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
			ex.printStackTrace();
		}
	}
}
