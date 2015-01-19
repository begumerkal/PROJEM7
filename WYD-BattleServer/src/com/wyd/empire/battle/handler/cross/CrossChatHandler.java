package com.wyd.empire.battle.handler.cross;
import org.apache.log4j.Logger;
import com.wyd.empire.battle.bean.BattleTeam;
import com.wyd.empire.battle.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.cross.CrossChat;
import com.wyd.empire.protocol.data.cross.CrossSendMessage;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
public class CrossChatHandler implements IDataHandler {
    private static final Logger log = Logger.getLogger(CrossChatHandler.class);

    public void handle(AbstractData message) throws Exception {
        CrossChat crossChat = (CrossChat) message;
        try{
            BattleTeam battleTeam = ServiceManager.getManager().getBattleTeamService().getBattleTeam(crossChat.getBattleId());
            if (null != battleTeam) {
                CrossSendMessage crossSendMessage = new CrossSendMessage();
                crossSendMessage.setChannel(crossChat.getChannel());
                crossSendMessage.setSendId(crossChat.getSendId());
                crossSendMessage.setSendName(crossChat.getSendName());
                crossSendMessage.setReceiveId(crossChat.getReceiveId());
                crossSendMessage.setReceiveName(crossChat.getReceiveName());
                crossSendMessage.setMessage(crossChat.getMessage());
                battleTeam.sendData(crossSendMessage);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}
