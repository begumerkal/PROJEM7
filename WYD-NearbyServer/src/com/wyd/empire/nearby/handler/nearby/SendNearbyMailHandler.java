package com.wyd.empire.nearby.handler.nearby;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.nearby.SendNearbyMail;
import com.wyd.empire.protocol.data.nearby.SendNearbyMailOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 添加附近好友
 * @author zguoqiu
 */
public class SendNearbyMailHandler implements IDataHandler {
    private Logger log;

    public SendNearbyMailHandler() {
        this.log = Logger.getLogger(SendNearbyMailHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        SendNearbyMail sendNearbyMail = (SendNearbyMail) data;
        try {
            PlayerInfo myInfo = (PlayerInfo) ServiceManager.getManager().getPlayerInfoManager().getBean(PlayerInfo.class, sendNearbyMail.getSenderId());
            PlayerInfo friendInfo = (PlayerInfo) ServiceManager.getManager().getPlayerInfoManager().getBean(PlayerInfo.class, sendNearbyMail.getReceiverId());
            if (null != myInfo && null!= friendInfo) {
                ServiceManager.getManager().getMailManager().sendMail(myInfo.getId(), friendInfo.getId(), myInfo.getPlayerName(), friendInfo.getPlayerName(), sendNearbyMail.getTheme(), sendNearbyMail.getContent());
                SendNearbyMailOk sendNearbyMailOk = new SendNearbyMailOk();
                sendNearbyMailOk.setPlayerId(myInfo.getPlayerId());
                session.send(sendNearbyMailOk);
                ServiceManager.getManager().getMailManager().synMailCount(friendInfo);
            }
        } catch (Exception ex) {            
            log.error(ex, ex);
        }
    }
}