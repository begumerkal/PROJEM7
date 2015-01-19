package com.wyd.empire.world.server.handler.bossmapbattle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.bossmapbattle.OtherPass;
import com.wyd.empire.protocol.data.bossmapbattle.Pass;
import com.wyd.empire.world.battle.BossBattleTeam;
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
		try {
			int battleId = pass.getBattleId();
			// System.out.println("Pass--------------"+battleId);
			BossBattleTeam battleTeam = ServiceManager.getManager().getBossBattleTeamService().getBattleTeam(battleId);
			if (null == battleTeam) {
				throw new ProtocolException(ErrorMessages.BATTLE_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			Combat currentCombat = battleTeam.getCombatMap().get(pass.getCurrentId());
			if (null == currentCombat) {
				throw new ProtocolException(ErrorMessages.BATTLE_NOTFOUND_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
						data.getSubType());
			}
			int playerOrGuai = pass.getPlayerOrGuai();
			int currentId = pass.getCurrentId();

			OtherPass otherPass = new OtherPass();
			otherPass.setBattleId(battleId);
			otherPass.setPlayerOrGuai(playerOrGuai);
			otherPass.setCurrentId(currentId);

			for (Combat combat : battleTeam.getCombatList()) {
				if (!combat.isLost() && !combat.isRobot() && combat.getId() != currentCombat.getId()) {
					combat.getPlayer().sendData(otherPass);
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
