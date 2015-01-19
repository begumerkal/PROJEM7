package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.PlayerReborn;
import com.wyd.empire.protocol.data.bossmapbattle.RebornPosition;
import com.wyd.empire.world.battle.BossBattleTeam;
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
		try {
			int battleId = rebornPosition.getBattleId();
			int playercount = rebornPosition.getPlayercount();
			int[] playerIds = rebornPosition.getPlayerIds();
			int[] postionX = rebornPosition.getPostionX();
			int[] postionY = rebornPosition.getPostionY();
			int guaicount = rebornPosition.getGuaicount();
			int[] guaiBattleIds = rebornPosition.getGuaiBattleIds();
			int[] guaipostionX = rebornPosition.getGuaipostionX();
			int[] guaipostionY = rebornPosition.getGuaipostionY();

			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			if (null == battleTeam) {
				return;
			}

			for (int pid : playerIds) {
				Combat combat = battleTeam.getCombatMap().get(pid);
				combat.setDead(false);
				combat.setHp(combat.getMaxHP());
			}

			for (int gid : guaiBattleIds) {
				Combat guaiVo = battleTeam.getGuaiMap().get(gid);
				guaiVo.setDead(false);
				guaiVo.setHp(guaiVo.getGuai().getMaxHP());
			}

			PlayerReborn playerReborn = new PlayerReborn();
			playerReborn.setBattleId(battleId);
			playerReborn.setPlayercount(playercount);
			playerReborn.setPlayerIds(playerIds);
			playerReborn.setPostionX(postionX);
			playerReborn.setPostionY(postionY);
			playerReborn.setGuaicount(guaicount);
			playerReborn.setGuaiBattleIds(guaiBattleIds);
			playerReborn.setGuaipostionX(guaipostionX);
			playerReborn.setGuaipostionY(guaipostionY);

			for (Combat cb : battleTeam.getCombatList()) {
				if (!cb.isLost() && !cb.isRobot() && null != cb.getPlayer()) {
					cb.getPlayer().sendData(playerReborn);
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
