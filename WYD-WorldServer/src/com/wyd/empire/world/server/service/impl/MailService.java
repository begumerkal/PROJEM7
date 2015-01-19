package com.wyd.empire.world.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wyd.empire.protocol.data.admin.SendMail;
import com.wyd.empire.world.bean.Mail;
import com.wyd.empire.world.bean.Player;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.server.service.factory.ServiceManager;

public class MailService implements Runnable {
	private List<SendMail> sendMailList = new ArrayList<SendMail>();

	public void start() {
		Thread t = new Thread(this);
		t.setName("MailService-Thread");
		t.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (MailService.this) {
					if (0 == sendMailList.size()) {
						this.wait();
					}
					SendMail sendMail = sendMailList.get(0);
					sendMailList.remove(0);
					int mailType = sendMail.getMailType();
					int[] playerId = sendMail.getPlayerId();
					String title = sendMail.getTitle();
					String content = sendMail.getContent();
					String sendName = sendMail.getSendName();
					if (1 == mailType) {
						for (int id : playerId) {
							// 保存邮件
							Mail mail = new Mail();
							mail.setContent(content);
							mail.setIsRead(false);
							mail.setSendId(0);
							mail.setSendName(TipMessages.SYSNAME_MESSAGE);
							mail.setSendTime(new Date());
							mail.setTheme(title);
							mail.setType(3);
							mail.setReceivedId(id);
							mail.setBlackMail(false);
							mail.setRemark(sendName);
							mail.setIsStick(Common.IS_STICK);
							ServiceManager.getManager().getMailService().saveMail(mail, null);
						}
					} else {
						List<Player> playerList = ServiceManager.getManager().getPlayerService().getService().getAllPlayer();
						for (Player player : playerList) {
							// 保存邮件
							Mail mail = new Mail();
							mail.setContent(content);
							mail.setIsRead(false);
							mail.setSendId(0);
							mail.setSendTime(new Date());
							mail.setTheme(title);
							mail.setType(3);
							mail.setReceivedId(player.getId());
							mail.setBlackMail(false);
							mail.setRemark(sendName);
							mail.setIsStick(Common.IS_STICK);
							ServiceManager.getManager().getMailService().saveMail(mail, null);
						}
					}
				}
			} catch (Exception e) {
			}
		}
	}

	public void add(SendMail sendMail) {
		synchronized (MailService.this) {
			sendMailList.add(sendMail);
			this.notify();
		}
	}
}
