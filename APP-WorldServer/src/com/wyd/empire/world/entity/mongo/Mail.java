package com.wyd.empire.world.entity.mongo;

import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import com.wyd.db.mongo.entity.IEntity;
/**
 * 邮件表
 * 
 * @author doter
 */

@Document(collection = "mail")
public class Mail extends IEntity {
	private int playerId;// 玩家id
	private Date createTime;// 创建时间
	private String title;// 标题
	private String msg;// 内容
	private String item;// 物品（附件）
	private byte status;// 1未读２已读３已领取

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
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}

}
