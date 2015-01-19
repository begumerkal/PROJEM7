package com.wyd.empire.battle.handler.cross;

import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossOtherPass;
import com.wyd.empire.protocol.data.cross.CrossPass;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 跳过本轮操作
 * @author Administrator
 */
public class CrossPassHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossPassHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossPass pass = (CrossPass) data;
        try {
            int battleId = pass.getBattleId();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null == battleTeam) {
                return;
            }
            int playerId = pass.getPlayerId();
            CrossOtherPass otherPass = new CrossOtherPass();
            otherPass.setBattleId(battleId);
            otherPass.setCurrentPlayerId(playerId);
            battleTeam.sendData(otherPass);
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}
