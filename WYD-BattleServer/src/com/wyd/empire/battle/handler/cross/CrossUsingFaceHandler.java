package com.wyd.empire.battle.handler.cross;

import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossOtherUsingFace;
import com.wyd.empire.protocol.data.cross.CrossUsingFace;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 发送使用的表情
 * @author zguoqiu
 */
public class CrossUsingFaceHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossUsingFaceHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossUsingFace usingFace = (CrossUsingFace) data;
        try {
            int battleId = usingFace.getBattleId();
            int playerId = usingFace.getPlayerId();
            int currentPlayerId= usingFace.getCurrentPlayerId();
            int faceId = usingFace.getFaceId();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null != battleTeam) {
                CrossOtherUsingFace otherUsingFace = new CrossOtherUsingFace();
                otherUsingFace.setBattleId(battleId);
                otherUsingFace.setPlayerId(playerId);
                otherUsingFace.setCurrentPlayerId(currentPlayerId);
                otherUsingFace.setFaceId(faceId);
                battleTeam.sendData(otherUsingFace);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
