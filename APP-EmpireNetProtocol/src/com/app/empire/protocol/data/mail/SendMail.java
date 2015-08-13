package com.app.empire.protocol.data.mail;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SendMail</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_MAIL下子命令MAIL_SendMail(发送邮件协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendMail extends AbstractData {
	
	private String	theme;
	private int	sendId;
	private int	receivedId;
	private String reveiverName;
	private int	mailType;
	private String content;


    public SendMail(int sessionId, int serial) {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_SendMail, sessionId, serial);
    }

    public SendMail() {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_SendMail);
    }

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public int getSendId() {
		return sendId;
	}

	public void setSendId(int sendId) {
		this.sendId = sendId;
	}

	public int getReceivedId() {
		return receivedId;
	}

	public void setReceivedId(int receivedId) {
		this.receivedId = receivedId;
	}

	public int getMailType() {
		return mailType;
	}

	public void setMailType(int mailType) {
		this.mailType = mailType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReveiverName() {
		return reveiverName;
	}

	public void setReveiverName(String reveiverName) {
		this.reveiverName = reveiverName;
	}

}
