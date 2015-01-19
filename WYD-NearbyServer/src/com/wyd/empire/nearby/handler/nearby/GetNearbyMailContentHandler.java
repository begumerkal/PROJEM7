package com.wyd.empire.nearby.handler.nearby;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.bean.Mail;
import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.nearby.GetNearbyMailContent;
import com.wyd.empire.protocol.data.nearby.GetNearbyMailContentOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 查看附件好友邮件内容
 * 
 * @author zguoqiu
 */
public class GetNearbyMailContentHandler implements IDataHandler {
    private Logger                       log;
    public final static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");

    public GetNearbyMailContentHandler() {
        this.log = Logger.getLogger(GetNearbyMailContentHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        GetNearbyMailContent getNearbyMailContent = (GetNearbyMailContent) data;
        try {
            PlayerInfo playerInfo = (PlayerInfo) ServiceManager.getManager().getPlayerInfoManager().getBean(PlayerInfo.class, getNearbyMailContent.getMyInfoId());
            if (null != playerInfo) {
                Mail mail = (Mail) ServiceManager.getManager().getMailManager().readMail(playerInfo, getNearbyMailContent.getMailId(), session);
                if (null != mail && (mail.getReceivedId() == getNearbyMailContent.getMyInfoId() || mail.getSendId() == getNearbyMailContent.getMyInfoId())) {
                    GetNearbyMailContentOk getNearbyMailContentOk = new GetNearbyMailContentOk();
                    getNearbyMailContentOk.setPlayerId(playerInfo.getPlayerId());
                    getNearbyMailContentOk.setMailId(mail.getId());
                    getNearbyMailContentOk.setContent(new String(mail.getContent(), "utf8"));
                    getNearbyMailContentOk.setSendTime(sdf.format(mail.getSendTime()));
                    getNearbyMailContentOk.setRemark("");
                    getNearbyMailContentOk.setSendId(mail.getSendId());
                    session.send(getNearbyMailContentOk);
                }
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}