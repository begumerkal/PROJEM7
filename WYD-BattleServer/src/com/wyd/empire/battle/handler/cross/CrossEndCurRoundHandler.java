package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.bean.WorldPlayer;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.battle.util.ServiceUtils;
import com.wyd.empire.protocol.data.cross.CrossCanStartCurRound;
import com.wyd.empire.protocol.data.cross.CrossEndCurRound;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 战斗操作计时间到
 * 
 * @author Administrator
 */
public class CrossEndCurRoundHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossEndCurRoundHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossEndCurRound endCurRound = (CrossEndCurRound) data;
        try {
            int battleId = endCurRound.getBattleId();
            int playerId = endCurRound.getPlayerId();
            int currentPlayerId = endCurRound.getCurrentPlayerId();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null == battleTeam) {
                return;
            }
            ServiceManager.getManager().getBattleTeamService().playerRun(battleId, playerId);
            if (battleTeam.isCurRound()) {
                ServiceManager.getManager().getBattleTeamService().sendSort(battleTeam, currentPlayerId);
                CrossCanStartCurRound canStartCurRound = new CrossCanStartCurRound();
                canStartCurRound.setBattleId(battleId);
                canStartCurRound.setWind(battleTeam.getWind());
                if (battleTeam.allFrozen()) {
                    canStartCurRound.setCurrentPlayerId(-1);
                    battleTeam.setNewRun(true);
                } else {
                    WorldPlayer worldPlayer = ServiceManager.getManager().getBattleTeamService().getActionPlayer(battleId);
                    if (null == worldPlayer) {
                        if (ServiceManager.getManager().getBattleTeamService().gameOver(battleTeam, 0)) {
                            return;
                        } else {
                            canStartCurRound.setCurrentPlayerId(-1);
                        }
                    } else {
                        canStartCurRound.setCurrentPlayerId(worldPlayer.getId());
                        canStartCurRound.setIsCrit(worldPlayer.IsCriticalHit());
                    }
                }
                int[] attackRate = new int[10];
                for (int i = 0; i < 10; i++) {
                    attackRate[i] = ServiceUtils.getRandomNum(960, 1031);
                }
                canStartCurRound.setAttackRate(attackRate);
                int[] battleRand = new int[10];
                for (int i = 0; i < 10; i++) {
                    battleRand[i] = ServiceUtils.getRandomNum(0, 10001);
                }
                canStartCurRound.setBattleRand(battleRand);
                if (battleTeam.isNewRun()) {
                    canStartCurRound.setIsNewRound(1);
                } else {
                    canStartCurRound.setIsNewRound(0);
                }
                for (Combat combat : battleTeam.getCombatList()) {
                    combat.setCurRound(false);
                }
                battleTeam.setBattleRand(battleRand);
                battleTeam.setCritValue(canStartCurRound.getIsCrit());
                battleTeam.sendData(canStartCurRound);
                battleTeam.setCurRound(false);
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}
