package com.wyd.empire.world.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.entity.mysql.Mail;
import com.wyd.empire.world.exception.TipMessages;

public class TheadPlayerItemsService {

	/**
	 * 制作Mail对象
	 * 
	 * @param playerId
	 *            收件人Id
	 * @param itemName
	 *            装备名称
	 * @param num
	 *            天数，3表示3天后过期，0表示今天过期
	 * @return
	 */
	public Mail makeMail(int playerId, String itemName, int num) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Mail mail = new Mail();
		mail.setReceivedId(playerId);
		mail.setSendId(0);
		mail.setSendName(TipMessages.SYSNAME_MESSAGE);
		mail.setSendTime(new Date());
		mail.setTheme(TipMessages.MAIL_NOTICETHEME);
		if (num == 3) {
			Date overDate = new Date(System.currentTimeMillis() + 3 * Common.ONEDAYLONG);
			mail.setContent(TipMessages.MAIL_NOTICE1 + itemName + TipMessages.MAIL_NOTICE2 + sdf2.format(overDate)
					+ TipMessages.MAIL_NOTICE3);
		} else {
			mail.setContent(TipMessages.MAIL_NOTICE1 + itemName + TipMessages.MAIL_NOTICE2 + TipMessages.TODAY + TipMessages.MAIL_NOTICE3);
		}
		mail.setIsRead(false);
		mail.setType(1);
		mail.setBlackMail(false);
		mail.setIsStick(Common.IS_STICK);
		return mail;
	}

 

}
