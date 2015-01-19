package com.wyd.empire.battle.service.impl;
import java.util.List;
import java.util.Vector;
import com.wyd.empire.battle.bean.RandomRoom;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossPairFail;
import com.wyd.empire.protocol.data.cross.CrossPairOk;
/**
 * 随机配对服务
 * 
 * @author Administrator
 */
public class PairService implements Runnable {
    private List<RandomRoom> randomRoomList;
    private CrossPairFail    pairFail;
    private CrossPairOk      pairOk;

    public PairService() {
        randomRoomList = new Vector<RandomRoom>();
        pairFail = new CrossPairFail();
        pairOk = new CrossPairOk();
    }

    public void start() {
        Thread t = new Thread(this);
        t.setName("PairService-Thread");
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                RandomRoom randomRoomX;
                RandomRoom randomRoomY;
                for (int i = 0; i < randomRoomList.size(); i++) {
                    randomRoomX = randomRoomList.get(i);
                    randomRoomX.setCount(randomRoomX.getCount() + 1);
                    if (!randomRoomX.isReady()) {
                        for (int y = i + 1; y < randomRoomList.size(); y++) {
                            randomRoomY = randomRoomList.get(y);
                            float fightingX = randomRoomX.getAverageFighting();
                            float fightingY = randomRoomY.getAverageFighting();
                            int rate = 100;
                            if (fightingX > 0) rate = Math.abs((int) (((fightingX - fightingY) / fightingX) * 100f));
                            int battleMode = randomRoomX.getBattleMode();
                            int playerNum = randomRoomX.getPlayerNum();
                            if (!randomRoomY.isReady() && rate <= 30) {
                                if (battleMode == randomRoomY.getBattleMode() && playerNum == randomRoomY.getPlayerNum() && randomRoomX.getRoomChannel() == randomRoomY.getRoomChannel() && randomRoomX.getVersion().equals(randomRoomY.getVersion()) && !randomRoomX.getServerId().equals(randomRoomY.getServerId())) {
                                    randomRoomX.setReady(true);
                                    randomRoomY.setReady(true);
                                    int battleId = ServiceManager.getManager().getBattleTeamService().createBattleTeam(battleMode, playerNum);
                                    pairOk.setBattleId(battleId);
                                    pairOk.setRoomId(randomRoomX.getId());
                                    randomRoomX.sendData(pairOk);
                                    pairOk.setRoomId(randomRoomY.getId());
                                    randomRoomY.sendData(pairOk);
                                    break;
                                }
                            }
                        }
                    }
                    if (!randomRoomX.isReady() && randomRoomX.getBattleMode() == 6) {
                        for (int y = i + 1; y < randomRoomList.size(); y++) {
                            randomRoomY = randomRoomList.get(y);
                            int battleMode = randomRoomX.getBattleMode();
                            int playerNum = randomRoomX.getPlayerNum();
                            if (!randomRoomY.isReady() && randomRoomX.getBattleMode() == randomRoomY.getBattleMode()) {
                                randomRoomX.setReady(true);
                                randomRoomY.setReady(true);
                                int battleId = ServiceManager.getManager().getBattleTeamService().createBattleTeam(battleMode, playerNum);
                                pairOk.setBattleId(battleId);
                                pairOk.setRoomId(randomRoomX.getId());
                                randomRoomX.sendData(pairOk);
                                pairOk.setRoomId(randomRoomY.getId());
                                randomRoomY.sendData(pairOk);
                                break;
                            } 
                        }
                    }
                }
                RandomRoom randomRoom;
                for (int i = randomRoomList.size() - 1; i >= 0; i--) {
                    randomRoom = randomRoomList.get(i);
                    if (randomRoom.isReady()) {
                        randomRoomList.remove(i);
                        continue;
                    }
                    if (randomRoom.getCount() > 1) {
                        randomRoomList.remove(i);
                        pairFail.setRoomId(randomRoom.getId());
                        // System.out.println("removeRoom:-------------------"+randomRoom.getId());
                        randomRoom.sendData(pairFail);
                    }
                }
                Thread.sleep(5000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加入随机房间
     * 
     * @param randomRoom
     */
    public void addRandomRoom(RandomRoom randomRoom) {
//         System.out.println("randomRoom:-------------------"+randomRoom.getId());
        randomRoomList.add(randomRoom);
    }
}