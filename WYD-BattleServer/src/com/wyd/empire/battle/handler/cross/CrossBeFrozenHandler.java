package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.battle.util.Common;
import com.wyd.empire.protocol.data.cross.CrossBeFrozen;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 被冰冻
 * @author Administrator
 */
public class CrossBeFrozenHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossBeFrozenHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossBeFrozen beFrozen = (CrossBeFrozen) data;
        try {
            int battleId = beFrozen.getBattleId();
            int[] playerIds = beFrozen.getPlayerIds();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null != battleTeam) {
                for (int playerId : playerIds) {
                    Combat cb = battleTeam.getCombatMap().get(playerId);
                    if (null != cb) cb.setFrozenTimes(Common.FROZENTIMES);
                }
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}
