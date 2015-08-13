package com.app.empire.protocol.data.mail;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SendMailContent</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_MAIL下子命令MAIL_SendMailContent(发送邮件内容协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendMailContent extends AbstractData {
	
	private String content;
	private String sendTime;
	private String remark;

    public SendMailContent(int sessionId, int serial) {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_SendMailContent, sessionId, serial);
    }

    public SendMailContent() {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_SendMailContent);
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
