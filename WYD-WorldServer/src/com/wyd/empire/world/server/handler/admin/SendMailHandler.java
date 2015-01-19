package com.wyd.empire.world.server.handler.admin;

import java.util.Date;

import com.wyd.empire.protocol.data.admin.SendMail;
import com.wyd.empire.protocol.data.admin.SendMailResult;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;
import com.wyd.empire.world.session.AdminSession;
import com.wyd.protocol.data.AbstractData;
import com.wyd.protocol.handler.IDataHandler;

/**
 * gm工具数据查询
 * 
 * @see com.sumsharp.protocol.handler.IDataHandler
 * @author mazheng
 */
public class SendMailHandler implements IDataHandler {
	public void handle(AbstractData data) throws Exception {
		AdminSession session = (AdminSession) data.getHandlerSource();
		SendMail sendMail = (SendMail) data;

		int mailType = sendMail.getMailType();
		int[] playerId = sendMail.getPlayerId();
		String title = sendMail.getTitle();
		String content = sendMail.getContent();
		String sendName = sendMail.getSendName();
		SendMailResult sendMailResult = new SendMailResult(data.getSessionId(), data.getSerial());
		try {
			if (0 == mailType) {
				// 保存邮件
				Mail mail = new Mail();
				mail.setContent(content);
				mail.setIsRead(false);
				mail.setSendId(0);
				mail.setSendName(TipMessages.SYSNAME_MESSAGE);
				mail.setSendTime(new Date());
				mail.setTheme(title);
				mail.setType(3);
				mail.setReceivedId(playerId[0]);
				mail.setBlackMail(false);
				mail.setRemark(sendName);
				mail.setIsStick(Common.IS_STICK);
				ServiceManager.getManager().getMailService().saveMail(mail, null);
			} else {
				ServiceManager.getManager().getSendMailService().add(sendMail);
			}
			sendMailResult.setSuccess(true);
		} catch (Exception e) {
			sendMailResult.setSuccess(false);
		}
		session.write(sendMailResult);
	}
}