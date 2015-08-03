package com.wyd.empire.protocol.data.mail;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>DeleteMail</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_MAIL下子命令MAIL_DeleteMail(删除邮件协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class DeleteMail extends AbstractData {
	
	private int[] mailId;
	
    public DeleteMail(int sessionId, int serial) {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_DeleteMail, sessionId, serial);
    }

    public DeleteMail() {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_DeleteMail);
    }

	public int[] getMailId() {
		return mailId;
	}

	public void setMailId(int[] mailId) {
		this.mailId = mailId;
	}
    

}
