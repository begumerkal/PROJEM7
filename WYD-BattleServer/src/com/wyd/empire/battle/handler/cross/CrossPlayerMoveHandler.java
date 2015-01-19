package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossOtherPlayerMove;
import com.wyd.empire.protocol.data.cross.CrossPlayerMove;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 角色移动
 * @author Administrator
 */
public class CrossPlayerMoveHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossPlayerMoveHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossPlayerMove playerMove = (CrossPlayerMove) data;
        try {
            int battleId = playerMove.getBattleId();
            int playerId = playerMove.getPlayerId();
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null == battleTeam) {
                return;
            }
            Combat cb = battleTeam.getCombatMap().get(playerId);
            if (null == cb) {
                return;
            }
            int movecount = playerMove.getMovecount();
            byte[] movestep = playerMove.getMovestep();
            int curPositionX = playerMove.getCurPositionX();
            int curPositionY = playerMove.getCurPositionY();
            float movecountFloat = playerMove.getMovecountFloat() / 100f;
            float pf = cb.getPf() - movecountFloat;
            if (pf >= 0) {
                cb.setPf(pf);
            } else {
                cb.setPf(0);
            }
            CrossOtherPlayerMove otherPlayerMove = new CrossOtherPlayerMove();
            otherPlayerMove.setBattleId(battleId);
            otherPlayerMove.setCurrentPlayerId(playerId);
            otherPlayerMove.setMovecount(movecount);
            otherPlayerMove.setMovestep(movestep);
            otherPlayerMove.setCurPositionX(curPositionX);
            otherPlayerMove.setCurPositionY(curPositionY);
            battleTeam.sendData(otherPlayerMove);
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}
