package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.OtherUsingFace;
import com.wyd.empire.protocol.data.bossmapbattle.UsingFace;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
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
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			int battleId = usingFace.getBattleId();
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			WorldPlayer player = session.getPlayer(data.getSessionId());
			if (null == battleTeam || null == player)
				return;
			int playerOrGuai = usingFace.getPlayerOrGuai();
			int currentId = usingFace.getCurrentId();
			int faceId = usingFace.getFaceId();
			OtherUsingFace otherUsingFace = new OtherUsingFace();
			otherUsingFace.setBattleId(battleId);
			otherUsingFace.setPlayerOrGuai(playerOrGuai);
			otherUsingFace.setCurrentId(currentId);
			otherUsingFace.setFaceId(faceId);
			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isLost() && !combat.isRobot() && combat.getId() != player.getId()) {
					combat.getPlayer().sendData(otherUsingFace);
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
