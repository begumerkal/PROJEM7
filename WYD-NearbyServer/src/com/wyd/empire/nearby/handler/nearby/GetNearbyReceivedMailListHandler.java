package com.wyd.empire.nearby.handler.nearby;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.bean.Mail;
import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.nearby.GetNearbyReceivedMailList;
import com.wyd.empire.protocol.data.nearby.GetNearbyReceivedMailListOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 获取收件箱邮件列表
 * 
 * @author zguoqiu
 */
public class GetNearbyReceivedMailListHandler implements IDataHandler {
    private Logger                       log;
    public final static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");

    public GetNearbyReceivedMailListHandler() {
        this.log = Logger.getLogger(GetNearbyReceivedMailListHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        GetNearbyReceivedMailList getNearbyReceivedMailList = (GetNearbyReceivedMailList) data;
        try {
            GetNearbyReceivedMailListOk getNearbyReceivedMailListOk = new GetNearbyReceivedMailListOk();
            PlayerInfo playerInfo = (PlayerInfo) ServiceManager.getManager().getPlayerInfoManager().getBean(PlayerInfo.class, getNearbyReceivedMailList.getMyInfoId());
            if (null != playerInfo) {
                getNearbyReceivedMailListOk.setPlayerId(playerInfo.getPlayerId());
                List<Mail> mailList = ServiceManager.getManager().getMailManager().getReceivedMailList(getNearbyReceivedMailList.getMyInfoId());
                int pageNum = getNearbyReceivedMailList.getPageNum();
                int pagesize = Integer.parseInt(ServiceManager.getManager().getConfiguration().getValue("pagesize"));
                int totalPage = mailList.size() / pagesize;
                int startSize = (pageNum - 1) * pagesize;
                int endSize = startSize + pagesize;
                endSize = endSize > mailList.size() ? mailList.size() : endSize;
                // mailList = mailList.subList(startSize, endSize);
                int size = mailList.size();
                int[] mailId = new int[size];
                String[] theme = new String[size];
                String[] sendName = new String[size];
                String[] sendTime = new String[size];
                boolean[] isRead = new boolean[size];
                Mail mail;
                for (int i = 0; i < size; i++) {
                    mail = mailList.get(i);
                    mailId[i] = mail.getId();
                    theme[i] = mail.getTheme();
                    sendName[i] = mail.getSendName();
                    sendTime[i] = sdf.format(mail.getSendTime());
                    isRead[i] = mail.getIsRead();
                }
                getNearbyReceivedMailListOk.setMailId(mailId);
                getNearbyReceivedMailListOk.setTheme(theme);
                getNearbyReceivedMailListOk.setSendName(sendName);
                getNearbyReceivedMailListOk.setSendTime(sendTime);
                getNearbyReceivedMailListOk.setIsRead(isRead);
                getNearbyReceivedMailListOk.setPageNum(pageNum);
                getNearbyReceivedMailListOk.setTotalPage(totalPage);
                session.send(getNearbyReceivedMailListOk);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex, ex);
        }
    }
}