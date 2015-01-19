package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossPlayerInfo;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
public class CrossPlayerInfoHandler implements IDataHandler {
    private static final Logger log = Logger.getLogger(CrossPlayerInfoHandler.class);

    public void handle(AbstractData message) throws Exception {
    	CrossPlayerInfo cce = (CrossPlayerInfo) message;
        try {
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(cce.getBattleId());
            if (null != battleTeam) {
                Combat combat = battleTeam.getCombatMap().get(cce.getFriendId());
                if (null != combat) {
                    combat.getPlayer().sendData(cce);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
