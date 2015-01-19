package com.wyd.empire.nearby.handler.nearby;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.bean.Mail;
import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.nearby.DeleteNearbyMail;
import com.wyd.empire.protocol.data.nearby.DeleteNearbyMailOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 删除好友邮件
 * 
 * @author zguoqiu
 */
public class DeleteNearbyMailHandler implements IDataHandler {
    private Logger log;

    public DeleteNearbyMailHandler() {
        this.log = Logger.getLogger(DeleteNearbyMailHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        DeleteNearbyMail deleteNearbyMail = (DeleteNearbyMail) data;
        try {
            PlayerInfo playerInfo = (PlayerInfo) ServiceManager.getManager().getPlayerInfoManager().getBean(PlayerInfo.class, deleteNearbyMail.getMyInfoId());
            if (null != playerInfo) {
                boolean syn = false;
                for(int mailId:deleteNearbyMail.getMailId()){
                    Mail mail = ServiceManager.getManager().getMailManager().deleteMail(deleteNearbyMail.getMyInfoId(), mailId);
                    if(!mail.getIsRead()){
                        syn = true;
                    }
                }
                DeleteNearbyMailOk deleteNearbyMailOk = new DeleteNearbyMailOk();
                deleteNearbyMailOk.setPlayerId(playerInfo.getPlayerId());
                session.send(deleteNearbyMailOk);
                if(syn){
                    ServiceManager.getManager().getMailManager().synMailCount(playerInfo, session);
                }
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}