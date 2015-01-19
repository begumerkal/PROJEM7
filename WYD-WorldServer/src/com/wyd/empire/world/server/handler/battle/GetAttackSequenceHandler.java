package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.battle.AttackSequence;
import com.wyd.empire.protocol.data.battle.GetAttackSequence;
import com.wyd.empire.world.battle.BattleTeam;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 请求攻击序列
 * 
 * @author Administrator
 */
public class GetAttackSequenceHandler implements IDataHandler {
	Logger log = Logger.getLogger(GetAttackSequenceHandler.class);

	public void handle(AbstractData data) throws Exception {
		GetAttackSequence getAttackSequence = (GetAttackSequence) data;
		int battleId = getAttackSequence.getBattleId();
		if (battleId > 0) {
			ConnectSession session = (ConnectSession) data.getHandlerSource();
			try {
				BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
				int[] playerIds = ServiceManager.getManager().getBattleTeamService().sort(battleTeam);
				int idcount = playerIds.length;
				AttackSequence attackSequence = new AttackSequence(data.getSessionId(), data.getSerial());
				attackSequence.setBattleId(battleId);
				attackSequence.setPlayerId(getAttackSequence.getPlayerId());
				attackSequence.setIdcount(idcount);
				attackSequence.setPlayerIds(playerIds);
				session.write(attackSequence);
			} catch (Exception ex) {
				log.error(ex, ex);
			}
		} else {
			ServiceManager.getManager().getCrossService().requestAttackSequence(battleId, getAttackSequence.getPlayerId());
		}
	}
}
