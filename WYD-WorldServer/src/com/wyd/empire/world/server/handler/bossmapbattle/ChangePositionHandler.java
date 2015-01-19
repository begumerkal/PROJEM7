package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.ChangePosition;
import com.wyd.empire.protocol.data.bossmapbattle.OtherChangePosition;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 飞行
 * 
 * @author Administrator
 */
public class ChangePositionHandler implements IDataHandler {
	Logger log = Logger.getLogger(ChangePositionHandler.class);

	public void handle(AbstractData data) throws Exception {
		ChangePosition changePosition = (ChangePosition) data;
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			OtherChangePosition otherChangePosition = new OtherChangePosition();
			otherChangePosition.setBattleId(changePosition.getBattleId());
			otherChangePosition.setPlayerOrGuai(changePosition.getPlayerOrGuai());
			otherChangePosition.setCurrentId(changePosition.getCurrentId());
			otherChangePosition.setPlayerCount(changePosition.getPlayerCount());
			otherChangePosition.setPlayerIds(changePosition.getPlayerIds());
			otherChangePosition.setCurPositionX(changePosition.getCurPositionX());
			otherChangePosition.setCurPositionY(changePosition.getCurPositionY());
			otherChangePosition.setGuaiCount(changePosition.getGuaiCount());
			otherChangePosition.setGuaiBattleIds(changePosition.getGuaiBattleIds());
			otherChangePosition.setGuaiCurPositionX(changePosition.getGuaiCurPositionX());
			otherChangePosition.setGuaiCurPositionY(changePosition.getGuaiCurPositionY());
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(changePosition.getBattleId());
			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isLost() && !combat.isRobot() && combat.getId() != player.getId()) {
					combat.getPlayer().sendData(otherChangePosition);
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
