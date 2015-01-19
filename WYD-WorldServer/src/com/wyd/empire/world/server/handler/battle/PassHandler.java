package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherPass;
import com.wyd.empire.protocol.data.battle.Pass;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 跳过本轮操作
 * 
 * @author Administrator
 */
public class PassHandler implements IDataHandler {
	Logger log = Logger.getLogger(PassHandler.class);

	public void handle(AbstractData data) throws Exception {
		Pass pass = (Pass) data;
		int battleId = pass.getBattleId();
		int playerId = pass.getPlayerId();
		if (battleId > 0) {
			try {
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null == battleTeam) {
					return;
				}
				OtherPass otherPass = new OtherPass();
				otherPass.setBattleId(battleId);
				otherPass.setCurrentPlayerId(playerId);
				for (Combat combat : battleTeam.getCombatList()) {
					if (!combat.isLost() && !combat.isRobot() && combat.getId() != playerId) {
						otherPass.setPlayerId(combat.getId());
						combat.getPlayer().sendData(otherPass);
					}
				}
			} catch (Exception ex) {
				log.error(ex, ex);
				throw new ProtocolException(ErrorMessages.BATTLE_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
		} else {
			ServiceManager.getManager().getCrossService().pass(battleId, playerId);
		}
	}
}
