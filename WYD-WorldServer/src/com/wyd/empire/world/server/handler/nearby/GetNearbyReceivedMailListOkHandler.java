package com.wyd.empire.world.server.handler.nearby;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.wyd.empire.protocol.data.nearby.GetNearbyReceivedMailListOk;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.player.WorldPlayer;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 获取收件箱邮件列表成功
 * 
 * @author zguoqiu
 */
public class GetNearbyReceivedMailListOkHandler implements IDataHandler {
	private Logger log;
	private final static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");

	public GetNearbyReceivedMailListOkHandler() {
		this.log = Logger.getLogger(GetNearbyReceivedMailListOkHandler.class);
	}

	public void handle(AbstractData data) throws Exception {
		GetNearbyReceivedMailListOk gnrmlo = (GetNearbyReceivedMailListOk) data;
		try {
			WorldPlayer player = ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(gnrmlo.getPlayerId());
			if (null != player) {
				int[] mailId = gnrmlo.getMailId();
				String[] theme = gnrmlo.getTheme();
				String[] sendName = gnrmlo.getSendName();
				String[] sendTime = gnrmlo.getSendTime();
				boolean[] isRead = gnrmlo.getIsRead();
				List<Mail> mailList = new ArrayList<Mail>();
				for (int i = 0; i < mailId.length; i++) {
					Mail mail = new Mail();
					mailList.add(mail);
					mail.setId(mailId[i]);
					mail.setType(10);
					mail.setTheme(theme[i]);
					mail.setSendId(10);
					mail.setSendName(sendName[i]);
					mail.setSendTime(sdf.parse(sendTime[i]));
					mail.setIsRead(isRead[i]);
				}
				ServiceManager.getManager().getMailService().receivedMailList(player, gnrmlo.getPageNum(), mailList);
			}
		} catch (Exception ex) {
			log.error(ex, ex);
		}
	}
}