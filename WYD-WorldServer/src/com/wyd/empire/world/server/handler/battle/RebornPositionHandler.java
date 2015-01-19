package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.PlayerReborn;
import com.wyd.empire.protocol.data.battle.RebornPosition;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 角色复活
 * 
 * @author Administrator
 */
public class RebornPositionHandler implements IDataHandler {
	Logger log = Logger.getLogger(RebornPositionHandler.class);

	public void handle(AbstractData data) throws Exception {
		RebornPosition rebornPosition = (RebornPosition) data;
		int battleId = rebornPosition.getBattleId();
		int playerId = rebornPosition.getPlayerId();
		int playercount = rebornPosition.getPlayercount();
		int[] playerIds = rebornPosition.getPlayerIds();
		int[] postionX = rebornPosition.getPostionX();
		int[] postionY = rebornPosition.getPostionY();
		if (battleId > 0) {
			try {
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null == battleTeam) {
					return;
				}
				for (int pid : playerIds) {
					Combat combat = battleTeam.getCombatMap().get(pid);
					combat.setDead(false);
					combat.setHp(combat.getMaxHP());
					if (!combat.isRobot()) {
						ServiceManager.getManager().getTitleService().fuHuo(combat.getPlayer());
					}
				}
				PlayerReborn playerReborn = new PlayerReborn();
				playerReborn.setBattleId(battleId);
				playerReborn.setPlayerId(playerId);
				playerReborn.setPlayercount(playercount);
				playerReborn.setPlayerIds(playerIds);
				playerReborn.setPostionX(postionX);
				playerReborn.setPostionY(postionY);
				for (Combat cb : battleTeam.getCombatList()) {
					if (!cb.isLost() && !cb.isRobot() && null != cb.getPlayer()) {
						cb.getPlayer().sendData(playerReborn);
					}
				}
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		} else {
			ServiceManager.getManager().getCrossService().rebornPosition(battleId, playerId, playercount, playerIds, postionX, postionY);
		}
	}
}
