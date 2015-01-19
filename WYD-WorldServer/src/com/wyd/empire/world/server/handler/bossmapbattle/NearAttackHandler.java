package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.NearAttack;
import com.wyd.empire.protocol.data.bossmapbattle.OtherNearAttack;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 使用近距离攻击
 * 
 * @author Administrator
 */
public class NearAttackHandler implements IDataHandler {
	Logger log = Logger.getLogger(NearAttackHandler.class);

	public void handle(AbstractData data) throws Exception {
		NearAttack nearAttack = (NearAttack) data;
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int battleId = nearAttack.getBattleId();
			int playerOrGuai = nearAttack.getPlayerOrGuai();
			int currentId = nearAttack.getCurrentId();
			int leftRight = nearAttack.getLeftRight();

			OtherNearAttack otherNearAttack = new OtherNearAttack();
			otherNearAttack.setBattleId(battleId);
			otherNearAttack.setPlayerOrGuai(playerOrGuai);
			otherNearAttack.setCurrentId(currentId);
			otherNearAttack.setLeftRight(leftRight);

			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);

			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isLost() && !combat.isRobot() && combat.getId() != player.getId()) {
					combat.getPlayer().sendData(otherNearAttack);
				}
			}

		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
