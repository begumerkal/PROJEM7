package com.wyd.empire.battle.handler.cross;

import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossFly;
import com.wyd.empire.protocol.data.cross.CrossOtherFly;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 飞行
 * @author Administrator
 */
public class CrossFlyHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossFlyHandler.class);
	public void handle(AbstractData data) throws Exception {
	    CrossFly fly = (CrossFly) data;
		try {
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
            CrossOtherFly otherFly = new CrossOtherFly();
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
            battleTeam.sendData(otherFly);
        } catch (Exception ex) {
            log.error(ex, ex);
        }
	}
}
