package com.wyd.empire.battle.handler.cross;

import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossLoadingPercent;
import com.wyd.empire.protocol.data.cross.CrossOtherLoadingPercent;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送加载百份比
 * @author Administrator
 */
public class CrossLoadingPercentHandler implements IDataHandler {
	Logger log = Logger.getLogger(CrossLoadingPercentHandler.class);
	public void handle(AbstractData data) throws Exception {
        CrossLoadingPercent loadingPercent = (CrossLoadingPercent) data;
        try {
            int battleId = loadingPercent.getBattleId();
            int playerId = loadingPercent.getCurrentPlayerId();
            int percent = loadingPercent.getPercent();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null != battleTeam) {
                CrossOtherLoadingPercent otherLoadingPercent = new CrossOtherLoadingPercent();
                otherLoadingPercent.setBattleId(battleId);
                otherLoadingPercent.setCurrentPlayerId(playerId);
                otherLoadingPercent.setPercent(percent);
                battleTeam.sendData(otherLoadingPercent);
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}
