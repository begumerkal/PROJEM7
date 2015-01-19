package com.wyd.empire.battle.handler.cross;

import org.apache.log4j.Logger;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossPlayerLose;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 跳过本轮操作
 * @author Administrator
 */
public class CrossPlayerLoseHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossPlayerLoseHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossPlayerLose pass = (CrossPlayerLose) data;
        try {
            ServiceManager.getManager().getBattleTeamService().playerLose(pass.getBattleId(), pass.getCurrentPlayerId());
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}
