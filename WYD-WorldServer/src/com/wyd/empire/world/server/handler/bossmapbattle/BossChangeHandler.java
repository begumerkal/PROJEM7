package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.BossChange;
import com.wyd.empire.protocol.data.bossmapbattle.OtherChange;
import com.wyd.empire.world.battle.BossBattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.bossmaproom.GuaiPlayer;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * boss变身
 * 
 * @author Administrator
 */
public class BossChangeHandler implements IDataHandler {
	Logger log = Logger.getLogger(BossChangeHandler.class);

	public void handle(AbstractData data) throws Exception {
		BossChange bossChange = (BossChange) data;
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int battleId = bossChange.getBattleId();
			int guaiBattleId = bossChange.getGuaiBattleId();
			int guaiOldId = bossChange.getGuaiOldId();
			int guaiNewId = bossChange.getGuaiNewId();
			BossBattleTeam bbt = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			if (null != bbt) {
				Combat combatGuai = bbt.getGuaiMap().get(guaiBattleId);
				if (null != combatGuai) {
					GuaiPlayer guai = ServiceManager.getManager().getGuaiService().getGuaiById(bbt.getDifficulty(), guaiNewId);
					combatGuai.setGuai(guai);
					combatGuai.setPf(guai.getMaxPF(), bbt.getMapId(), 6, player);
					OtherChange otherChange = new OtherChange();
					otherChange.setBattleId(battleId);
					otherChange.setGuaiBattleId(guaiBattleId);
					otherChange.setGuaiOldId(guaiOldId);
					otherChange.setGuaiNewId(guaiNewId);
					for (Combat combat : bbt.getTrueCombatList()) {
						if (!combat.isLost() && !combat.isRobot() && combat.getId() != player.getId()) {
							combat.getPlayer().sendData(otherChange);
						}
					}
				}
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}
