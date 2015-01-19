package com.wyd.empire.battle.handler.cross;

import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.bean.WorldPlayer;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossQuitBattle;
import com.wyd.empire.protocol.data.cross.CrossQuitBattleOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 玩家强退游戏
 * @author Administrator
 */
public class CrossQuitBattleHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossQuitBattleHandler.class);
	public void handle(AbstractData data) throws Exception {
        CrossQuitBattle quitBattle = (CrossQuitBattle) data;
        try {
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(quitBattle.getBattleId());
            if (null == battleTeam) return;
            Combat combat = battleTeam.getCombatMap().get(quitBattle.getPlayerId());
            if (null == combat) return;
            WorldPlayer player = combat.getPlayer();
            if (null == player) return;
            if (!combat.isDead()) combat.setEnforceQuit(true);// 死亡不算强退
            ServiceManager.getManager().getBattleTeamService().playerLose(battleTeam.getBattleId(), player.getId());
            CrossQuitBattleOk quitBattleOk = new CrossQuitBattleOk();
            quitBattleOk.setBattleId(battleTeam.getBattleId());
            quitBattleOk.setPlayerId(player.getId());
            player.sendData(quitBattleOk);
            StringBuffer sbf = new StringBuffer();
            sbf.append("玩家主动退出对战---战斗组:");
            sbf.append(battleTeam.getBattleId());
            if (null != battleTeam) {
                for (Combat pclost : battleTeam.getCombatList()) {
                    if (!pclost.isRobot() && !pclost.isLost() && null != pclost.getPlayer()) {
                        sbf.append("---同组玩家:id=");
                        sbf.append(pclost.getPlayer().getId());
                        sbf.append("---name=");
                        sbf.append(pclost.getPlayer().getName());
                    }
                }
            }
            log.info(sbf);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
