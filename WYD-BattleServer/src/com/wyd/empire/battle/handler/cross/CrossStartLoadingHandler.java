package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossStartLoading;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 类<code>StartLoadingHandler</code> 通知服务器已经开始加载
 * 
 * @author Administrator
 */
public class CrossStartLoadingHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossStartLoadingHandler.class);

    /**
     * 读取房间列表
     */
    public void handle(AbstractData data) throws Exception {
        CrossStartLoading startLoading = (CrossStartLoading) data;
        try {
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(startLoading.getBattleId());
            if (null == battleTeam) {
               return;
            }
            Combat combat = battleTeam.getCombatMap().get(startLoading.getPlayerId());
            combat.setState(1);
            ServiceManager.getManager().getBattleTeamService().sendAIControlCommon(startLoading.getBattleId());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
