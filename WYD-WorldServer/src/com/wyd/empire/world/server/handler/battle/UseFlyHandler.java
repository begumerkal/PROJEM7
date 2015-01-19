package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.OtherUseFly;
import com.wyd.empire.protocol.data.battle.UseFly;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.battle.Combat;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 使用飞行技能
 * 
 * @author Administrator
 */
public class UseFlyHandler implements IDataHandler {
	Logger log = Logger.getLogger(UseFlyHandler.class);

	public void handle(AbstractData data) throws Exception {
		ConnectSession session = (ConnectSession) data.getHandlerSource();
		WorldPlayer me = session.getPlayer(data.getSessionId());
		UseFly useFly = (UseFly) data;
		int battleId = useFly.getBattleId();
		int playerId = useFly.getPlayerId();
		int currentPlayerId = useFly.getCurrentPlayerId();
		if (battleId > 0) {
			try {
				OtherUseFly otherUseFly = new OtherUseFly();
				otherUseFly.setBattleId(battleId);
				otherUseFly.setCurrentPlayerId(currentPlayerId);
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				if (null == battleTeam) {
					throw new ProtocolException(ErrorMessages.BATTLE_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(),
							data.getType(), data.getSubType());
				}
				Combat cb = battleTeam.getCombatMap().get(currentPlayerId);
				cb.setPf(cb.getPf() - 50, battleTeam.getMapId(), battleTeam.getBattleMode(), me);
				for (Combat combat : battleTeam.getCombatList()) {
					if (!combat.isLost() && !combat.isRobot() && combat.getPlayer() != null && combat.getId() != playerId) {
						otherUseFly.setPlayerId(combat.getId());
						combat.getPlayer().sendData(otherUseFly);
					}
				}
			} catch (ProtocolException ex) {
				throw ex;
			} catch (Exception ex) {
				log.error(ex, ex);
				throw new ProtocolException(ErrorMessages.BATTLE_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
		} else {
			ServiceManager.getManager().getCrossService().useFly(battleId, playerId, currentPlayerId);
		}
	}
}
