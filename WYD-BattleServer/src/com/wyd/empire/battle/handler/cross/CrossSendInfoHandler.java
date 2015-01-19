package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossSendInfo;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
public class CrossSendInfoHandler implements IDataHandler {
    private static final Logger log = Logger.getLogger(CrossSendInfoHandler.class);

    public void handle(AbstractData message) throws Exception {
        CrossSendInfo csi = (CrossSendInfo) message;
        try {
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(csi.getBattleId());
            if (null != battleTeam) {
                Combat combat = battleTeam.getCombatMap().get(csi.getPlayerId());
                if (null != combat) {
                    combat.getPlayer().sendData(csi);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
