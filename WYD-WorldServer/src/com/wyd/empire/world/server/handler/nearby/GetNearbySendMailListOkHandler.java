package com.wyd.empire.world.server.handler.nearby;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.nearby.GetNearbySendMailListOk;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取发件箱邮件列表成功
 * 
 * @author zguoqiu
 */
public class GetNearbySendMailListOkHandler implements IDataHandler {
	private Logger log;
	private final static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");

	public GetNearbySendMailListOkHandler() {
		this.log = Logger.getLogger(GetNearbySendMailListOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		GetNearbySendMailListOk gnsmlo = (GetNearbySendMailListOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(gnsmlo.getPlayerId());
			if (null != player) {
				int[] mailId = gnsmlo.getMailId();
				String[] theme = gnsmlo.getTheme();
				String[] receivedName = gnsmlo.getReceivedName();
				String[] sendTime = gnsmlo.getSendTime();
				boolean[] isRead = gnsmlo.getIsRead();
				List<Mail> mailList = new ArrayList<Mail>();
				for (int i = 0; i < mailId.length; i++) {
					Mail mail = new Mail();
					mailList.add(mail);
					mail.setId(mailId[i]);
					mail.setType(10);
					mail.setTheme(theme[i]);
					mail.setReceivedId(10);
					mail.setReceivedName(receivedName[i]);
					mail.setSendTime(sdf.parse(sendTime[i]));
					mail.setIsRead(isRead[i]);
				}
				ServiceManager.getManager().getMailService().sendMailList(player, gnsmlo.getPageNum(), mailList);
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}