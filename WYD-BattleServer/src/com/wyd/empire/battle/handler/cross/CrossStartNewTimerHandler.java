package com.wyd.empire.battle.handler.cross;

import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossStartNewTimer;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 开始新的战斗操作计时
 * @author Administrator
 */
public class CrossStartNewTimerHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossStartNewTimerHandler.class);
	public void handle(AbstractData data) throws Exception {
        CrossStartNewTimer startNewTimer = (CrossStartNewTimer) data;
        try {
            int battleId = startNewTimer.getBattleId();
            int playerId = startNewTimer.getPlayerId();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null == battleTeam) {
                return;
            }
            Combat combat = battleTeam.getCombatMap().get(playerId);
            if (null == combat) {
                return;
            }
            combat.setState(3);
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}
