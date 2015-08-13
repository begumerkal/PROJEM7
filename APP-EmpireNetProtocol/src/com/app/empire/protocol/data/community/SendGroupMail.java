package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SendGroupMail</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_SendGroupMail(公会群发邮件协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendGroupMail extends AbstractData {
	
	private String mailContent;//邮件内容

    public SendGroupMail(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendGroupMail, sessionId, serial);
    }

    public SendGroupMail() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendGroupMail);
    }

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

}
