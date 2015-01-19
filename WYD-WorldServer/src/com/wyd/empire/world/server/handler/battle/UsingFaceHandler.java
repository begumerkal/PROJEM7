package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherUsingFace;
import com.wyd.empire.protocol.data.battle.UsingFace;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送使用的表情
 * 
 * @author Administrator
 */
public class UsingFaceHandler implements IDataHandler {
	Logger log = Logger.getLogger(UsingFaceHandler.class);

	public void handle(AbstractData data) throws Exception {
		UsingFace usingFace = (UsingFace) data;
		int battleId = usingFace.getBattleId();
		int playerId = usingFace.getPlayerId();
		int currentPlayerId = usingFace.getCurrentPlayerId();
		int faceId = usingFace.getFaceId();
		if (battleId > 0) {
			try {
				OtherUsingFace otherUsingFace = new OtherUsingFace();
				otherUsingFace.setBattleId(battleId);
				otherUsingFace.setCurrentPlayerId(playerId);
				otherUsingFace.setFaceId(faceId);
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null != battleTeam) {
					for (Combat combat : battleTeam.getCombatList()) {
						if (!combat.isLost() && !combat.isRobot() && combat.getId() != playerId) {
							otherUsingFace.setPlayerId(combat.getId());
							combat.getPlayer().sendData(otherUsingFace);
						}
					}
				}
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		} else {
			ServiceManager.getManager().getCrossService().usingFace(battleId, playerId, currentPlayerId, faceId);
		}
	}
}
