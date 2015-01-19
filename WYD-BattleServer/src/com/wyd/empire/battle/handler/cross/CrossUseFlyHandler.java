package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossOtherUseFly;
import com.wyd.empire.protocol.data.cross.CrossUseFly;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 使用飞行技能
 * @author Administrator
 */
public class CrossUseFlyHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossUseFlyHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossUseFly useFly = (CrossUseFly) data;
        try {
            int battleId = useFly.getBattleId();
            int playerId = useFly.getPlayerId();
            int currentPlayerId = useFly.getCurrentPlayerId();
            CrossOtherUseFly otherUseFly = new CrossOtherUseFly();
            otherUseFly.setBattleId(battleId);
            otherUseFly.setPlayerId(playerId);
            otherUseFly.setCurrentPlayerId(currentPlayerId);
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null == battleTeam) {
                return;
            }
            Combat cb = battleTeam.getCombatMap().get(currentPlayerId);
            cb.setPf(cb.getPf() - 50);
            battleTeam.sendData(otherUseFly);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
