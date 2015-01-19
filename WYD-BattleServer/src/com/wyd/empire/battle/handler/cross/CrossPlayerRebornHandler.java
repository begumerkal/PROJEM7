package com.wyd.empire.battle.handler.cross;

import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.battle.PlayerReborn;
import com.wyd.empire.protocol.data.battle.RebornPosition;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 角色复活
 * @author Administrator
 */
public class CrossPlayerRebornHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossPlayerRebornHandler.class);
	public void handle(AbstractData data) throws Exception {
		RebornPosition rebornPosition = (RebornPosition) data;
		try {
			int battleId = rebornPosition.getBattleId();
//			int playerId = rebornPosition.getPlayerId();
			int playercount = rebornPosition.getPlayercount();
			int[] playerIds	= rebornPosition.getPlayerIds();
			int[] postionX = rebornPosition.getPostionX();
			int[] postionY = rebornPosition.getPostionY();
			
			BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
			if(null==battleTeam){
				return;
			}
			
			for(int pid:playerIds){
				Combat combat = battleTeam.getCombatMap().get(pid);
				combat.setDead(false);
				combat.setHp(combat.getPlayer().getMaxHP());
			}
			
			PlayerReborn playerReborn = new PlayerReborn();
			playerReborn.setBattleId(battleId);
			playerReborn.setPlayercount(playercount);
			playerReborn.setPlayerIds(playerIds);
			playerReborn.setPostionX(postionX);
			playerReborn.setPostionY(postionY);

			for(Combat cb:battleTeam.getCombatList()){
				if(!cb.isLost() && !cb.isRobot() && null!=cb.getPlayer()){
		            playerReborn.setPlayerId(cb.getPlayer().getId());
					cb.getPlayer().sendData(playerReborn);
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
