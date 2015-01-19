package com.wyd.empire.world.server.handler.battle;

import org.apache.log4j.Logger;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 被冰冻
 * 
 * @author Administrator
 */
public class BeFrozenHandler implements IDataHandler {
	Logger log = Logger.getLogger(BeFrozenHandler.class);

	public void handle(AbstractData data) throws Exception {
		// BeFrozen beFrozen = (BeFrozen) data;
		// int battleId = beFrozen.getBattleId();
		// int[] playerIds = beFrozen.getPlayerIds();
		// if (battleId > 0) {
		// try {
		// BattleTeam battleTeam =
		// ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
		// if (null != battleTeam) {
		// for (int playerId : playerIds) {
		// Combat cb = battleTeam.getCombatMap().get(playerId);
		// if (null != cb)
		// cb.setFrozenTimes(ServiceManager.getManager().getToolsService().getToolById(Common.FREEZEID).getParam1());
		// }
		// }
		// } catch (Exception ex) {
		// log.error(ex, ex);
		// }
		// } else {
		// ServiceManager.getManager().getCrossService().beFrozen(battleId,
		// playerIds);
		// }
	}
}
