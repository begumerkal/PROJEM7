package com.wyd.empire.battle.handler.cross;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.bean.Combat;
import com.wyd.empire.battle.bean.WorldPlayer;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossPositionsInMap;
import com.wyd.empire.protocol.data.cross.CrossPostionsForPlayers;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
/**
 * 提交地图可选择的出现位置给服务器
 * 
 * @author zguoqiu
 */
public class CrossPositionsInMapHandler implements IDataHandler {
    Logger log = Logger.getLogger(CrossPositionsInMapHandler.class);

    public void handle(AbstractData data) throws Exception {
        CrossPositionsInMap positionsInMap = (CrossPositionsInMap) data;
        try {
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(positionsInMap.getBattleId());
            if (null != battleTeam) {
                List<Integer> indexList = new ArrayList<Integer>();
                for (int i = 0; i < positionsInMap.getPostionCount(); i++) {
                    indexList.add(i);
                }
                Random random = new Random();
                CrossPostionsForPlayers postionsForPlayers = battleTeam.getPostionsForPlayers();
                if (null == postionsForPlayers) {
                    postionsForPlayers = new CrossPostionsForPlayers();
                    battleTeam.setPostionsForPlayers(postionsForPlayers);
                    int idcount = battleTeam.getCombatList().size();
                    int[] playerIds = new int[idcount];
                    int[] postionX = new int[idcount];
                    int[] postionY = new int[idcount];
                    Combat combat;
                    for (int i = 0; i < idcount; i++) {
                        combat = battleTeam.getCombatList().get(i);
                        int index = 0;
                        if (indexList.size() > 1) {
                            index = random.nextInt(indexList.size() - 1);
                        }
                        int pindex = indexList.get(index);
                        indexList.remove(index);
                        playerIds[i] = combat.getPlayer().getId();
                        postionX[i] = positionsInMap.getPostionX()[pindex];
                        postionY[i] = positionsInMap.getPostionY()[pindex];
                        combat.setX(postionX[i]);
                        combat.setY(postionY[i]);
                    }
                    postionsForPlayers.setBattleId(battleTeam.getBattleId());
                    postionsForPlayers.setIdcount(idcount);
                    postionsForPlayers.setPlayerIds(playerIds);
                    postionsForPlayers.setPostionX(postionX);
                    postionsForPlayers.setPostionY(postionY);
                }
                WorldPlayer player = battleTeam.getCombatMap().get(positionsInMap.getPlayerId()).getPlayer();
                postionsForPlayers.setPlayerId(player.getId());
                player.sendData(postionsForPlayers);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
