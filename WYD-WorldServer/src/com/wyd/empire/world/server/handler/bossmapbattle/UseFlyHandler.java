package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.OtherUseFly;
import com.wyd.empire.protocol.data.bossmapbattle.UseFly;
import com.wyd.empire.world.battle.BossBattleTeam;
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
		UseFly useFly = (UseFly) data;
		try {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			WorldPlayer player = session.getPlayer(data.getSessionId());
			int battleId = useFly.getBattleId();
			int playerOrGuai = useFly.getPlayerOrGuai();
			int currentId = useFly.getCurrentId();
			OtherUseFly otherUseFly = new OtherUseFly();
			otherUseFly.setBattleId(battleId);
			otherUseFly.setPlayerOrGuai(playerOrGuai);
			otherUseFly.setCurrentId(currentId);
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			if (null == battleTeam) {
				throw new ProtocolException(ErrorMessages.BATTLE_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			if (playerOrGuai == 0) {
				Combat cb = battleTeam.getCombatMap().get(currentId);
				cb.setPf(cb.getPf() - 50, battleTeam.getMapId(), 6, player);
			} else {
				Combat cG = battleTeam.getGuaiMap().get(currentId);
				cG.setPf(cG.getPf() - 50, battleTeam.getMapId(), 6, player);
			}
			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isLost() && !combat.isRobot() && combat.getPlayer() != null && combat.getId() != player.getId()) {
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
	}
}
