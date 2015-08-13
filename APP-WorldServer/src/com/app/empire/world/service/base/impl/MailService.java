package com.app.empire.world.service.base.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.app.empire.protocol.data.mail.NewMail;
import com.app.empire.world.dao.mongo.impl.MailDao;
import com.app.empire.world.entity.mongo.Mail;
import com.app.empire.world.model.player.WorldPlayer;
import com.app.empire.world.service.factory.ServiceManager;

/**
 * 邮件服务
 */
@Service
public class MailService {
	private MailDao dao;

	/**
	 * @param playerId
	 * @param mail
	 * @param isPush
	 *            是否推送
	 */
	public void sendMail(int playerId, Mail mail, boolean isPush) {
		try {
			mail.setPlayerId(playerId);
			mail.setStatus((byte) 1);
			mail = (Mail) dao.insert(mail);
			if (isPush) {
				NewMail newMail = new NewMail();
				// 提醒收件玩家
				WorldPlayer player = ServiceManager.getManager().getPlayerService().getPlayer(playerId);
				if (player != null && player.getPlayer().getIsOnline()) {
					player.sendData(newMail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param playerId
	 * @param skip
	 * @param limit
	 * @return
	 */
	public List<Mail> getMailListByPlayerId(Integer playerId, int skip, int limit) {
		return dao.getMailListByPlayerId(playerId, skip, limit);
	}
	public Mail getMailById(int mailId) {
		return dao.getMailById(mailId);
	}

}