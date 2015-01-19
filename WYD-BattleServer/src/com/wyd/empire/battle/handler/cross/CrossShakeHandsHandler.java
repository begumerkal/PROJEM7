package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossShakeHands;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
public class CrossShakeHandsHandler implements IDataHandler {
    private static final Logger log = Logger.getLogger(CrossShakeHandsHandler.class);

    public void handle(AbstractData message) throws Exception {
        CrossShakeHands shakeHands = (CrossShakeHands) message;
        try {
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(shakeHands.getBattleId());
            if (null == battleTeam) {
                return;
            }
            Combat combat = battleTeam.getCombatMap().get(shakeHands.getPlayerId());
            if (null == combat) {
                return;
            }
            combat.getPlayer().setActionTime(System.currentTimeMillis());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
