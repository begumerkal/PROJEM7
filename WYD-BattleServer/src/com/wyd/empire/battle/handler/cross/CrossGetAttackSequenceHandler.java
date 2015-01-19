package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossAttackSequence;
import com.wyd.empire.protocol.data.cross.CrossGetAttackSequence;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 请求攻击序列
 * @author Administrator
 */
public class CrossGetAttackSequenceHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossGetAttackSequenceHandler.class);

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        CrossGetAttackSequence getAttackSequence = (CrossGetAttackSequence) data;
        try {
            int battleId = getAttackSequence.getBattleId();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null != battleTeam) {
                int[] playerIds = ServiceManager.getManager().getBattleTeamService().sort(battleTeam);
                int idcount = playerIds.length;
                int[] isCriticalHit = new int[idcount];
                int i = 0;
                for (int playerId : playerIds) {
                    Combat combat = battleTeam.getCombatMap().get(playerId);
                    if (null != combat.getPlayer()) {
                        isCriticalHit[i] = combat.getPlayer().IsCriticalHit();
                    } else {
                        isCriticalHit[i] = 0;
                    }
                    i++;
                }
                CrossAttackSequence attackSequence = new CrossAttackSequence();
                attackSequence.setBattleId(battleId);
                attackSequence.setPlayerId(getAttackSequence.getPlayerId());
                attackSequence.setIdcount(idcount);
                attackSequence.setPlayerIds(playerIds);
                attackSequence.setIsCriticalHit(isCriticalHit);
                session.send(attackSequence);
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}
