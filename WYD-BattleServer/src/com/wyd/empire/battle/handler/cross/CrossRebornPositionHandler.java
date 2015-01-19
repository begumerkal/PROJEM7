package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossPlayerReborn;
import com.wyd.empire.protocol.data.cross.CrossRebornPosition;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 角色复活
 * @author Administrator
 */
public class CrossRebornPositionHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossRebornPositionHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossRebornPosition rebornPosition = (CrossRebornPosition) data;
        int battleId = rebornPosition.getBattleId();
        int playercount = rebornPosition.getPlayercount();
        int[] playerIds = rebornPosition.getPlayerIds();
        int[] postionX = rebornPosition.getPostionX();
        int[] postionY = rebornPosition.getPostionY();
        boolean[] robot = new boolean[playerIds.length];
        try {
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(battleId);
            if (null == battleTeam) {
                return;
            }
            int i = 0;
            for (int pid : playerIds) {
                Combat combat = battleTeam.getCombatMap().get(pid);
                combat.setDead(false);
                combat.setHp(combat.getPlayer().getMaxHP());
                robot[i] = combat.isRobot();
                i++;
            }
            CrossPlayerReborn playerReborn = new CrossPlayerReborn();
            playerReborn.setBattleId(battleId);
            playerReborn.setPlayercount(playercount);
            playerReborn.setPlayerIds(playerIds);
            playerReborn.setPostionX(postionX);
            playerReborn.setPostionY(postionY);
            playerReborn.setRobot(robot);
            battleTeam.sendData(playerReborn);
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}
