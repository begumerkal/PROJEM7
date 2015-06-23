package com.wyd.empire.world.server.service.base;

import java.util.List;

import com.wyd.db.page.PageList;
import com.wyd.db.service.UniversalManager;
import com.wyd.empire.world.entity.mysql.Friend;
import com.wyd.empire.world.entity.mysql.Mail;
import com.wyd.empire.world.model.player.WorldPlayer;

/**
 * The service interface for the TabConsortia entity.
 */
public interface IMailService extends UniversalManager {

	/**
	 * 清除过期邮件(每隔30天清理一次)
	 */
	public void deleteOverDateMail(int days);

	/**
	 * 保存邮件
	 * 
	 * @param mail
	 */
	public void saveMail(Mail mail, Friend friend);

	/**
	 * 删除邮件
	 * 
	 * @param mailId
	 */
	public void deleteMail(int playerId, int[] mailIds, int level);

	/**
	 * 获得邮件对象
	 * 
	 * @param mailId
	 * @return
	 */
	public Mail getMail(int mailId);

	/**
	 * 根据邮箱ID，将邮件设为已读
	 * 
	 * @param id
	 *            邮件ID
	 */
	public void updateMailStatusById(int id);

	/**
	 * 根据玩家ID及标示获邓收/发件箱邮件(分页版)
	 * 
	 * @param playerId
	 *            玩家ID
	 * @param mark
	 *            标志<tt>true : </tt>表示收件箱<br/>
	 *            <tt>false: </tt>表示发件箱
	 * @return
	 */
	public PageList getMailList(int playerId, boolean mark, int pageNum);

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
	public List<Mail> getMailList(int playerId, boolean mark);

	/**
	 * 根据用户ID，登录时检测是否有未读邮件
	 * 
	 * @param playerId
	 *            用户ID
	 * @return <tt>true :</tt>有未读邮件<br/>
	 *         <tt>false:</tt>没有未读邮件
	 */
	public int checkMailRead(int playerId);

	/**
	 * 查询意见箱
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageList getSuggestionBox(String key, int pageIndex, int pageSize);

	/**
	 * 查询GM工具发出的邮件
	 * 
	 * @param key
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageList getGMMail(String key, int pageIndex, int pageSize);

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
	public void updateMailHandle(String id, String isHandle, String userName);

	/**
	 * 更新意见箱类型
	 * 
	 * @param id
	 *            邮件ID
	 * @param type
	 *            意见箱类型
	 */
	public void updateMailType(String id, int type);

	/**
	 * 更新意见箱置顶状态
	 * 
	 * @param id
	 *            邮件ID
	 * @param type
	 *            置顶状态
	 */
	public void updateMailStick(String id, int type);

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
	public Mail makeMail(int playerId, String itemName, int num);

	/**
	 * 推送邮件状态
	 * 
	 * @param receivedId
	 */
	public void sendMailStatus(WorldPlayer receiver);

	/**
	 * 同步玩家的邮件数量
	 * 
	 * @param player
	 * @param count
	 */
	public void synMailCount(WorldPlayer player, int count);

	/**
	 * 发送玩家收件列表
	 */
	public void receivedMailList(WorldPlayer player, int pageIndex, List<Mail> nMailList);

	/**
	 * 发送玩家发件列表
	 */
	public void sendMailList(WorldPlayer player, int pageIndex, List<Mail> nMailList);
}