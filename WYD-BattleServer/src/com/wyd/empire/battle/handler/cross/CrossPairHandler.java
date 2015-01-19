package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.RandomRoom;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossPair;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
public class CrossPairHandler implements IDataHandler {
    private static final Logger log = Logger.getLogger(CrossPairHandler.class);

    public void handle(AbstractData message) throws Exception {
        CrossPair crossPair = (CrossPair) message;
        AcceptSession session = (AcceptSession) message.getSource();
        try{
            RandomRoom randomRoom = new RandomRoom(session);
            randomRoom.setId(crossPair.getRoomId());
            randomRoom.setAverageFighting(crossPair.getAverageFighting());
            randomRoom.setBattleMode(crossPair.getBattleMode());
            randomRoom.setPlayerNum(crossPair.getPlayerNum());
            randomRoom.setRoomChannel(crossPair.getRoomChannel());
            randomRoom.setVersion(crossPair.getVersion());
            ServiceManager.getManager().getPairService().addRandomRoom(randomRoom);
        }catch(Exception ex){
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
