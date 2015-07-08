package com.wyd.empire.world.service.base.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.wyd.db.mysql.page.PageList;
import com.wyd.db.service.impl.UniversalManagerImpl;
import com.wyd.empire.world.common.util.Common;
import com.wyd.empire.world.dao.mysql.IMailDao;
import com.wyd.empire.world.entity.mysql.Friend;
import com.wyd.empire.world.entity.mysql.Mail;
import com.wyd.empire.world.exception.TipMessages;
import com.wyd.empire.world.logs.GameLogService;
import com.wyd.empire.world.model.player.WorldPlayer;
import com.wyd.empire.world.service.base.IMailService;

/**
 * 加载游戏配置
 */
@Service
public class GameConfigService extends UniversalManagerImpl implements IMailService {
	/**
	 * The dao instance injected by Spring.
	 */
	private IMailDao dao;
	ConcurrentHashMap<String, ConcurrentHashMap> gameConfig = new ConcurrentHashMap<String, ConcurrentHashMap>();
	/**
	 * The service Spring bean id, used in the applicationContext.xml file.
	 */
	private static final String SERVICE_BEAN_ID = "MailService";
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public GameConfigService() {
		super();
	}

	/**
	 * Returns the singleton <code>IConsortiaService</code> instance.
	 */
	public static IMailService getInstance(ApplicationContext context) {
		return (IMailService) context.getBean(SERVICE_BEAN_ID);
	}

	/**
	 * Called by Spring using the injection rules specified in the Spring beans
	 * file "applicationContext.xml".
	 */
	public void setDao(IMailDao dao) {
		super.setDao(dao);
		this.dao = dao;
	}

	public IMailDao getDao() {
		return this.dao;
	}

	/**
	 * 清除过期邮件(每隔30天清理一次)
	 */
	public void deleteOverDateMail(int days) {
		dao.deleteOverDateMail(days);
	}

	/**
	 * 保存邮件
	 * 
	 * @param mail
	 */
	public void saveMail(Mail mail, Friend friend) {
		// try {
		// mail = (Mail) dao.save(mail);
		//
		// if (!mail.getBlackMail()) {
		// if (friend == null || !friend.getBlackList()) {
		// HasNewMail remindSendMailOk = new HasNewMail();
		// // 提醒收件玩家
		// WorldPlayer player =
		// ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(mail.getReceivedId());
		// if (player != null && player.isOnline()) {
		// player.sendData(remindSendMailOk);
		// }
		// }
		// }
		// // 推送接收人邮件状态
		// sendMailStatusToReceiver(mail.getReceivedId());
		// // 记录日志
		// int sendLevel = 0;
		// int receiveLevel = 0;
		// WorldPlayer wp =
		// ServiceManager.getManager().getPlayerService().getLoadPlayer(mail.getSendId());
		// if (null != wp)
		// sendLevel = wp.getLevel();
		// wp =
		// ServiceManager.getManager().getPlayerService().getLoadPlayer(mail.getReceivedId());
		// if (null != wp)
		// receiveLevel = wp.getLevel();
		// if (mail.getSendId() == null) {
		// mail.setSendId(-1);
		// }
		// if (mail.getReceivedId() == null) {
		// mail.setReceivedId(-1);
		// }
		// GameLogService.sendMail(mail.getSendId(), sendLevel,
		// mail.getReceivedId(), receiveLevel, mail.getContent());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 向邮件接收人推送邮件状态 如果接收人不在线则什么都不做
	 * 
	 * @param receivedId
	 */
	private void sendMailStatusToReceiver(Integer receivedId) {
		// if (receivedId == null) {
		// return;
		// }
		// WorldPlayer receiver =
		// ServiceManager.getManager().getPlayerService().getOnlineWorldPlayer(receivedId);
		// if (receiver == null) {
		// return;
		// }
		// sendMailStatus(receiver);
	}

	/**
	 * 向邮件接收人推送邮件状态
	 * 
	 * @param receivedId
	 */
	public void sendMailStatus(WorldPlayer receiver) {
		if (null != receiver) {

			synMailCount(receiver, 0);

		}
	}

	/**
	 * 同步玩家的邮件数量
	 * 
	 * @param player
	 * @param count
	 *            附近好友邮件数量
	 */
	public void synMailCount(WorldPlayer player, int count) {
		// count += checkMailRead(player.getId());
		// LoginCheckMailOk loginCheckMailOk = new LoginCheckMailOk();
		// loginCheckMailOk.setCheckMail(true);
		// loginCheckMailOk.setMailNum(count);
		// player.sendData(loginCheckMailOk);
	}

	/**
	 * 删除邮件
	 * 
	 * @param mailId
	 */
	public void deleteMail(int playerId, int[] mailIds, int level) {
		Mail mail;
		for (int mailId : mailIds) {
			mail = (Mail) dao.get(Mail.class, mailId);
			if (mail.getDeleteMark() == 0) {
				mail.setDeleteMark(playerId);
				dao.update(mail);
			} else {
				dao.remove(mail);
			}
			int sORr = playerId == mail.getSendId() ? 2 : 1;
			GameLogService.deleteMail(playerId, level, mailId, sORr);
		}

	}

	/**
	 * 获得邮件对象
	 * 
	 * @param mailId
	 * @return
	 */
	public Mail getMail(int mailId) {
		return (Mail) dao.get(Mail.class, mailId);
	}

	/**
	 * 根据邮箱ID，将邮件设为已读
	 * 
	 * @param id
	 *            邮件ID
	 */
	public void updateMailStatusById(int id) {
		dao.updateMailStatusById(id);
	}

	/**
	 * 根据玩家ID及标示获邓收/发件箱邮件
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param mark
	 *            标志<tt>true : </tt>表示收件箱<br/>
	 *            <tt>false: </tt>表示发件箱
	 * @return
	 */
	public PageList getMailList(int playerId, boolean mark, int pageNum) {
		return dao.getMailList(playerId, mark, pageNum);
	}

	/**
	 * 根据玩家ID及标示获邓收/发件箱邮件
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param mark
	 *            标志<tt>true : </tt>表示收件箱<br/>
	 *            <tt>false: </tt>表示发件箱
	 * @return
	 */
	public List<Mail> getMailList(int playerId, boolean mark) {
		return dao.getMailList(playerId, mark);
	}

	public List<Mail> getNewMailList(Integer playerId) {
		return dao.getNewMailList(playerId);
	}

	/**
	 * 根据用户ID，登录时检测是否有未读邮件
	 * 
	 * @param playerId
	 *            用户ID
	 * @return <tt>true :</tt>有未读邮件<br/>
	 *         <tt>false:</tt>没有未读邮件
	 */
	public int checkMailRead(int playerId) {
		return dao.checkMailRead(playerId);
	}

	/**
	 * 查询意见箱
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageList getSuggestionBox(String key, int pageIndex, int pageSize) {
		return dao.getSuggestionBox(key, pageIndex, pageSize);
	}

	/**
	 * 查询GM工具发出的邮件
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageList getGMMail(String key, int pageIndex, int pageSize) {
		return dao.getGMMail(key, pageIndex, pageSize);
	}

	/**
	 * 更新邮件处理状态
	 * 
	 * @param id
	 *            邮件ID
	 * @param isHandle
	 *            是否处理
	 * @param userName
	 *            处理人
	 */
	public void updateMailHandle(String id, String isHandle, String userName) {
		dao.updateMailHandle(id, isHandle, userName);
	}

	/**
	 * 更新意见箱类型
	 * 
	 * @param id
	 *            邮件ID
	 * @param type
	 *            意见箱类型
	 */
	public void updateMailType(String id, int type) {
		dao.updateMailType(id, type);
	}

	/**
	 * 更新意见箱置顶状态
	 * 
	 * @param id
	 *            邮件ID
	 * @param type
	 *            置顶状态
	 */
	public void updateMailStick(String id, int type) {
		dao.updateMailStick(id, type);
	}

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

	/**
	 * 发送玩家收件列表
	 */
	public void receivedMailList(WorldPlayer player, int pageIndex, List<Mail> nMailList) {
		// List<Mail> mailList = dao.getMailList(player.getId(), true);
		// if (null != nMailList && nMailList.size() > 0) {
		// mailList.addAll(nMailList);
		// }
		// double size = mailList.size();
		// Mail mail;
		// int readIndex = -1;
		// for (int index = 0; index < size; index++) {
		// mail = mailList.get(index);
		// if (0 > readIndex && mail.getIsRead()) {
		// readIndex = index;
		// }
		// if (!mail.getIsRead() && 0 <= readIndex) {
		// mailList.remove(index);
		// mailList.add(readIndex, mail);
		// readIndex++;
		// }
		// }
		// List<Integer> mailId = new ArrayList<Integer>();
		// List<String> theme = new ArrayList<String>();
		// List<String> senderName = new ArrayList<String>();
		// List<String> time = new ArrayList<String>();
		// List<Integer> mailType = new ArrayList<Integer>();
		// List<Boolean> isRead = new ArrayList<Boolean>();
		// int s = (pageIndex - 1) * Common.PAGESIZE;
		// int e = pageIndex * Common.PAGESIZE;
		// s = s < 0 ? 0 : s;
		// e = e > size ? (int) size : e;
		// for (int i = s; i < e; i++) {
		// mail = mailList.get(i);
		// mailId.add(mail.getId());
		// theme.add(mail.getTheme());
		// if (mail.getSendId() == 0) {
		// senderName.add(TipMessages.SYSNAME_MESSAGE);
		// } else {
		// senderName.add(mail.getSendName());
		// }
		// time.add(DateUtil.format(mail.getSendTime(), "MM-dd HH:mm"));
		// mailType.add(mail.getType());
		// isRead.add(mail.getIsRead());
		// }
		// SendInboxMail sendMailList = new SendInboxMail();
		// sendMailList.setMailId(ServiceUtils.getInts(mailId.toArray()));
		// sendMailList.setTheme(theme.toArray(new String[]{}));
		// sendMailList.setSenderName(senderName.toArray(new String[]{}));
		// sendMailList.setTime(time.toArray(new String[]{}));
		// sendMailList.setMailType(ServiceUtils.getInts(mailType.toArray()));
		// sendMailList.setIsRead(ServiceUtils.getBooleans(isRead.toArray()));
		// sendMailList.setPageNumber(pageIndex);// 服务器页数从零开始，客户端页数从一开始
		// sendMailList.setTotalPage((int) Math.ceil(size / Common.PAGESIZE));
		// player.sendData(sendMailList);
	}

	/**
	 * 发送玩家发件列表
	 */
	public void sendMailList(WorldPlayer player, int pageIndex, List<Mail> nMailList) {
		// List<Mail> mailList = dao.getMailList(player.getId(), false);
		// if (null != nMailList && nMailList.size() > 0) {
		// mailList.addAll(nMailList);
		// }
		// double size = mailList.size();
		// Mail mail;
		// int readIndex = -1;
		// for (int index = 0; index < size; index++) {
		// mail = mailList.get(index);
		// if (0 > readIndex && mail.getIsRead()) {
		// readIndex = index;
		// }
		// if (!mail.getIsRead() && 0 <= readIndex) {
		// mailList.remove(index);
		// mailList.add(readIndex, mail);
		// readIndex++;
		// }
		// }
		// List<Integer> mailId = new ArrayList<Integer>();
		// List<String> theme = new ArrayList<String>();
		// List<String> receciverName = new ArrayList<String>();
		// List<String> time = new ArrayList<String>();
		// List<Integer> mailType = new ArrayList<Integer>();
		// List<Boolean> isRead = new ArrayList<Boolean>();
		// int s = (pageIndex - 1) * Common.PAGESIZE;
		// int e = pageIndex * Common.PAGESIZE;
		// s = s < 0 ? 0 : s;
		// e = e > size ? (int) size : e;
		// for (int i = s; i < e; i++) {
		// mail = mailList.get(i);
		// mailId.add(mail.getId());
		// theme.add(mail.getTheme());
		// if (mail.getReceivedId() == 0) {
		// receciverName.add(TipMessages.SYSNAME_MESSAGE);
		// } else {
		// receciverName.add(mail.getReceivedName());
		// }
		// time.add(DateUtil.format(mail.getSendTime(), "MM-dd HH:mm"));
		// mailType.add(mail.getType());
		// isRead.add(mail.getIsRead());
		// }
		// SendOutboxMail sendMailList = new SendOutboxMail();
		// sendMailList.setMailId(ServiceUtils.getInts(mailId.toArray()));
		// sendMailList.setTheme(theme.toArray(new String[]{}));
		// sendMailList.setRececiverName(receciverName.toArray(new String[]{}));
		// sendMailList.setTime(time.toArray(new String[]{}));
		// sendMailList.setMailType(ServiceUtils.getInts(mailType.toArray()));
		// sendMailList.setIsRead(ServiceUtils.getBooleans(isRead.toArray()));
		// sendMailList.setPageNumber(pageIndex);// 服务器页数从零开始，客户端页数从一开始
		// sendMailList.setTotalPage((int) Math.ceil(size / Common.PAGESIZE));
		// player.sendData(sendMailList);
	}
}