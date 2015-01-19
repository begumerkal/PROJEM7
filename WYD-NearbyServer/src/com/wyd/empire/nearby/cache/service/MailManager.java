package com.wyd.empire.nearby.cache.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wyd.empire.nearby.bean.Mail;
import com.wyd.empire.nearby.bean.PlayerInfo;
import com.wyd.empire.nearby.nenum.BeanStatus;
import com.wyd.empire.nearby.service.factory.ServiceManager;
import com.wyd.empire.protocol.data.nearby.SendNearbyMailCount;
import com.wyd.session.AcceptSession;

public class MailManager extends CacheService{
	public Map<Integer, List<Mail>> playerSendMailList = new HashMap<Integer, List<Mail>>();
	public Map<Integer, List<Mail>> playerReceivedMailList = new HashMap<Integer, List<Mail>>();
	
	private int maxId;
	
	public void init(){
		super.start(10000);
		maxId = ServiceManager.getManager().getNearbyService().getMaxId(PlayerInfo.class);
	}
	
	private synchronized int createId(){
		return maxId++;
	}
	
	/**
	 * 发送邮件
	 * @param sendId
	 * @param receivedId
	 * @param sendName
	 * @param receivedName
	 * @param theme
	 * @param content
	 * @throws UnsupportedEncodingException
	 */
	public void sendMail(int sendId, int receivedId, String sendName, String receivedName, String theme, String content) throws UnsupportedEncodingException {
		Mail mail = new Mail();
		mail.setId(createId());
		mail.setSendId(sendId);
		mail.setReceivedId(receivedId);
		mail.setSendName(sendName);
		mail.setReceivedName(receivedName);
		mail.setSendTime(new Date());
		mail.setTheme(theme);
		mail.setContent(content.getBytes("utf8"));
		mail.setIsRead(false);
		addSendMailList(mail);
		addReceivedMailList(mail);
		setBeanStatus(mail, BeanStatus.Create);
	}
	
	/**
	 * 读取邮件
	 * @param playerId
	 * @param mailId
	 * @return
	 */
	public Mail readMail(PlayerInfo playerInfo, int mailId, AcceptSession session){
		Mail mail = (Mail) getBean(Mail.class, mailId);
		if (null != mail) {
			if (playerInfo.getId().intValue() == mail.getReceivedId() && !mail.getIsRead()) {
				mail.setIsRead(true);
				setBeanStatus(mail, BeanStatus.Update);
				synMailCount(playerInfo, session);
			}
		}
		return mail;
	}
	
	/**
	 * 删除邮件
	 * @param playerId
	 * @param mailId
	 * @return
	 */
	public Mail deleteMail(int playerId, int mailId){
		Mail mail = (Mail) getBean(Mail.class, mailId);
		if (null != mail) {
			//删除标识 0未删除，1收件人删除，2发件人删除,3双方都已删除
			byte deleteMark = mail.getDeleteMark();
			if (playerId == mail.getSendId()) {
			    removeSendMail(mail, playerId);
				switch (deleteMark) {
				case 0:
					deleteMark = 2;
					break;
				case 1:
					deleteMark = 3;
					break;
				}
			}
			if (playerId == mail.getReceivedId()) {
			    removeReceivedMail(mail, playerId);
				switch (deleteMark) {
				case 0:
					deleteMark = 1;
					break;
				case 2:
					deleteMark = 3;
					break;
				}
			}
            if (deleteMark != mail.getDeleteMark()) {
                mail.setDeleteMark(deleteMark);
                setBeanStatus(mail, BeanStatus.Update);
                if(3==deleteMark){
                    removeBean(mail);
                }
            }
		}
		return mail;
	}
	
	/**
	 * 获取玩家接收到另外一个玩家的新邮件数量
	 * @param receivedId
	 * @param sendId
	 * @return
	 */
	public int getPlayerNewSendMailCount(int receivedId, int sendId) {
		List<Mail> mailList = getReceivedMailList(receivedId);
		int count = 0;
		if (null != mailList) {
			synchronized (mailList) {
				for (Mail mail : mailList) {
					if (!mail.getIsRead() && mail.getSendId() == sendId)
						count++;
				}
			}
		}
		return count;
	}
	
	public void addSendMailList(Mail mail){
		List<Mail> sendMailList = getSendMailList(mail.getSendId());
		synchronized (sendMailList) {
			sendMailList.add(mail);
		}
	}
	
	public void addReceivedMailList(Mail mail){
		List<Mail> receivedMailList = getReceivedMailList(mail.getReceivedId());
		synchronized (receivedMailList) {
			receivedMailList.add(mail);
		}
	}
	
	public List<Mail> getSendMailList(int sendId){
	    List<Mail> sendMailList = playerSendMailList.get(sendId);
	    if(null==sendMailList){
	        sendMailList = ServiceManager.getManager().getNearbyService().getSendMailList(sendId);
	        playerSendMailList.put(sendId, sendMailList);
	    }
		return sendMailList;
	}
	
    private void removeSendMail(Mail mail, int sendId) {
        List<Mail> sendMailList = playerSendMailList.get(sendId);
        if (null != sendMailList) {
            for (Mail m : sendMailList) {
                if (m.getId().intValue() == mail.getId().intValue()) {
                    sendMailList.remove(m);
                    break;
                }
            }
        }
    }
	
	public List<Mail> getReceivedMailList(int receivedId){
	    List<Mail> receivedMailList = playerReceivedMailList.get(receivedId);
	    if(null==receivedMailList){
	        receivedMailList = ServiceManager.getManager().getNearbyService().getReceivedMailList(receivedId);
	        playerReceivedMailList.put(receivedId, receivedMailList);
        }
		return receivedMailList;
	}
	
    private void removeReceivedMail(Mail mail, int receivedId) {
        List<Mail> receivedMailList = playerReceivedMailList.get(receivedId);
        if (null != receivedMailList) {
            for (Mail m : receivedMailList) {
                if (m.getId().intValue() == mail.getId().intValue()) {
                    receivedMailList.remove(m);
                    break;
                }
            }
        }
    }
    
    /**
     * 同步玩家的未读邮件数量
     * @param friendInfo
     */
    public void synMailCount(PlayerInfo playerInfo) {
        if (null != playerInfo) {
            ServiceManager.getManager().getSessionService().sendMessageToAllService(getSNMC(playerInfo));
        }
    }

    /**
     * 同步玩家的未读邮件数量
     * @param friendInfo
     */
    public void synMailCount(PlayerInfo playerInfo, AcceptSession session) {
        if (null != playerInfo) {
            session.send(getSNMC(playerInfo));
        }
    }

    private SendNearbyMailCount getSNMC(PlayerInfo playerInfo) {
        List<Mail> mailList = new ArrayList<Mail>(ServiceManager.getManager().getMailManager().getReceivedMailList(playerInfo.getId()));
        int count = 0;
        for (Mail mail : mailList) {
            if (!mail.getIsRead()) {
                count++;
            }
        }
        SendNearbyMailCount snmc = new SendNearbyMailCount();
        snmc.setPlayerId(playerInfo.getPlayerId());
        snmc.setInfoId(playerInfo.getId());
        snmc.setMailCount(count);
        return snmc;
    }
}
