package com.app.empire.protocol.data.mail;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetMailContent</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_MAIL下子命令MAIL_GetMailContent(获得邮件内容协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetMailContent extends AbstractData {
	
	private int mailId;

    public GetMailContent(int sessionId, int serial) {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_GetMailContent, sessionId, serial);
    }

    public GetMailContent() {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_GetMailContent);
    }

	public int getMailId() {
		return mailId;
	}

	public void setMailId(int mailId) {
		this.mailId = mailId;
	}
    
}
