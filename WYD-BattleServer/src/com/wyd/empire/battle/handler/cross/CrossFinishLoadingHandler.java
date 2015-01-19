package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.WorldPlayer;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.battle.util.ServiceUtils;
import com.wyd.empire.protocol.data.cross.CrossFinishLoading;
import com.wyd.empire.protocol.data.cross.CrossGotoBattle;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 通知已经完成加载
 * 
 * @author Administrator
 */
public class CrossFinishLoadingHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossFinishLoadingHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossFinishLoading finishLoading = (CrossFinishLoading) data;
        try {
            int battleId = finishLoading.getBattleId();
            int playerId = finishLoading.getPlayerId();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null == battleTeam) {
                return;
            }
            ServiceManager.getManager().getBattleTeamService().ready(battleId, playerId);
            if (ServiceManager.getManager().getBattleTeamService().isReady(battleId)) {
                battleTeam.setStat(1);
                CrossGotoBattle gotoBattle = new CrossGotoBattle();
                gotoBattle.setBattleId(battleId);
                gotoBattle.setWind(battleTeam.getWind());
                WorldPlayer worldPlayer = ServiceManager.getManager().getBattleTeamService().getActionPlayer(battleId);
                if (null == worldPlayer) {
                    ServiceManager.getManager().getBattleTeamService().gameOver(battleTeam, 0);
                    return;
                }
                gotoBattle.setCurrentPlayerId(worldPlayer.getId());
                gotoBattle.setIsCriticalHit(worldPlayer.IsCriticalHit());
                int[] attackRate = new int[10];
                for (int i = 0; i < 10; i++) {
                    attackRate[i] = ServiceUtils.getRandomNum(960, 1031);
                }
                gotoBattle.setAttackRate(attackRate);
                int[] battleRand = new int[10];
                for (int i = 0; i < 10; i++) {
                    battleRand[i] = ServiceUtils.getRandomNum(0, 10001);
                }
                gotoBattle.setBattleRand(battleRand);
                battleTeam.setBattleRand(battleRand);
                battleTeam.setCritValue(gotoBattle.getIsCriticalHit());
                battleTeam.sendData(gotoBattle);
                ServiceManager.getManager().getBattleTeamService().gameOver(battleTeam, 0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
