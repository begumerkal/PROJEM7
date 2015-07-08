package com.wyd.empire.world.server.handler.mail;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.wyd.empire.protocol.data.mail.GetMailContent;
import com.wyd.empire.protocol.data.mail.SendMailContent;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.entity.mysql.Mail;
import com.wyd.empire.world.exception.ErrorMessages;
import com.wyd.empire.world.model.player.WorldPlayer;
import com.wyd.empire.world.service.factory.ServiceManager;
import com.wyd.empire.world.session.ConnectSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.exception.ProtocolException;
import com.wyd.protocol.handler.IDataHandler;

/**
 * 
 * 类 <code> GetMailContentHandler</code>Protocol.MAIL_GetMailContent获得邮件内容协议处理
 * 
 * @since JDK 1.6
 * 
 */
public class GetMailContentHandler implements IDataHandler {
	private Logger log;

	public GetMailContentHandler() {
		this.log = Logger.getLogger(GetMailContentHandler.class);
	}

	public AbstractData handle(AbstractData data) throws Exception {
//		ConnectSession session = (ConnectSession) data.getHandlerSource();
//		GetMailContent getMailContent = (GetMailContent) data;
//		WorldPlayer player = session.getPlayer(data.getSessionId());
//		try {
//			Mail mail = ServiceManager.getManager().getMailService().getMail(getMailContent.getMailId());
//			SendMailContent sendMailContent = new SendMailContent(data.getSessionId(), data.getSerial());
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			sendMailContent.setContent(mail.getContent());
//			sendMailContent.setSendTime(sdf.format(mail.getSendTime()));
//			sendMailContent.setRemark(mail.getRemark());
//			session.write(sendMailContent);
//			if (player.getId() != mail.getSendId() || (player.getId() == mail.getSendId() && player.getId() == mail.getReceivedId())) {
//				ServiceManager.getManager().getMailService().updateMailStatusById(mail.getId());
//			}
//			ServiceManager.getManager().getMailService().sendMailStatus(player);
//		} catch (Exception ex) {
//			if (null == ex.getMessage() || !ex.getMessage().startsWith(Common.ERRORKEY))
//				this.log.error(ex, ex);
//			throw new ProtocolException(ErrorMessages.MAIL_CONTENT_MESSAGE, data.getSerial(), data.getSessionId(), data.getType(),
//					data.getSubType());
//		}
		return null;
	}
}