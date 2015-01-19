package com.wyd.empire.nearby.handler.nearby;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.nearby.bean.Mail;
import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.nearby.GetNearbySendMailList;
import com.wyd.empire.protocol.data.nearby.GetNearbySendMailListOk;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;
import com.wyd.session.AcceptSession;
/**
 * 获取收件箱邮件列表
 * 
 * @author zguoqiu
 */
public class GetNearbySendMailListHandler implements IDataHandler {
    private Logger log;
    public final static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
    public GetNearbySendMailListHandler() {
        this.log = Logger.getLogger(GetNearbySendMailListHandler.class);
    }

    public void handle(AbstractData data) throws Exception {
        AcceptSession session = (AcceptSession) data.getSource();
        GetNearbySendMailList getNearbySendMailList = (GetNearbySendMailList) data;
        try {
            GetNearbySendMailListOk getNearbySendMailListOk = new GetNearbySendMailListOk();
            PlayerInfo playerInfo = (PlayerInfo) ServiceManager.getManager().getPlayerInfoManager().getBean(PlayerInfo.class, getNearbySendMailList.getMyInfoId());
            if (null != playerInfo) {
                getNearbySendMailListOk.setPlayerId(playerInfo.getPlayerId());
                List<Mail> mailList = ServiceManager.getManager().getMailManager().getSendMailList(getNearbySendMailList.getMyInfoId());
                int pageNum = getNearbySendMailList.getPageNum();
                int pagesize = Integer.parseInt(ServiceManager.getManager().getConfiguration().getValue("pagesize"));
                int totalPage = mailList.size() / pagesize;
                int startSize = (pageNum - 1) * pagesize;
                int endSize = startSize + pagesize;
                endSize = endSize > mailList.size() ? mailList.size() : endSize;
//                mailList = mailList.subList(startSize, endSize);
                int size = mailList.size();
                int[] mailId = new int[size];
                String[] theme = new String[size];
                String[] receivedName = new String[size];
                String[] sendTime = new String[size];
                boolean[] isRead = new boolean[size];
                Mail mail;
                for (int i = 0; i < size; i++) {
                    mail = mailList.get(i);
                    mailId[i] = mail.getId();
                    theme[i] = mail.getTheme();
                    receivedName[i] = mail.getReceivedName();
                    sendTime[i] = sdf.format(mail.getSendTime());
                    isRead[i] = mail.getIsRead();
                }
                getNearbySendMailListOk.setMailId(mailId);
                getNearbySendMailListOk.setTheme(theme);
                getNearbySendMailListOk.setReceivedName(receivedName);
                getNearbySendMailListOk.setSendTime(sendTime);
                getNearbySendMailListOk.setIsRead(isRead);
                getNearbySendMailListOk.setPageNum(pageNum);
                getNearbySendMailListOk.setTotalPage(totalPage);
                session.send(getNearbySendMailListOk);
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
    }
}