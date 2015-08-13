package com.app.empire.protocol.data.mail;
import java.util.Date;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 收取邮件提醒
 * 
 * @see AbstractData
 * @author doter
 */
public class NewMail extends AbstractData {
	private int playerId;// 玩家id
	private Date createTime;// 创建时间
	private String title;// 标题
	private String msg;// 内容
	private String item;// 物品（附件）

	public NewMail(int sessionId, int serial) {
		super(Protocol.MAIN_MAIL, Protocol.MAIL_HasNewMail, sessionId, serial);
	}

	public NewMail() {
		super(Protocol.MAIN_MAIL, Protocol.MAIL_HasNewMail);
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

}
