package com.wyd.empire.nearby.handler.nearby;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.bean.Mail;
import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.nearby.GetNearbyMailCount;
import com.wyd.empire.protocol.data.nearby.SendNearbyMailCount;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 获取未读邮件数量
 * @author zguoqiu
 */
public class GetNearbyMailCountHandler implements IDataHandler {
    private Logger                       log;
    public final static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");

    public GetNearbyMailCountHandler() {
        this.log = Logger.getLogger(GetNearbyMailCountHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        GetNearbyMailCount getNearbyMailCount = (GetNearbyMailCount) data;
        try {
            PlayerInfo playerInfo = (PlayerInfo) ServiceManager.getManager().getPlayerInfoManager().getBean(PlayerInfo.class, getNearbyMailCount.getMyInfoId());
            if (null != playerInfo) {
                List<Mail> mailList = new ArrayList<Mail>(ServiceManager.getManager().getMailManager().getReceivedMailList(playerInfo.getId()));
                int count = 0;
                for (Mail mail : mailList) {
                    if (!mail.getIsRead()) {
                        count++;
                    }
                }
                SendNearbyMailCount snmc = new SendNearbyMailCount();
                snmc.setPlayerId(playerInfo.getPlayerId());
                snmc.setInfoId(playerInfo.getId());
                snmc.setMailCount(count);
                session.send(snmc);
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}