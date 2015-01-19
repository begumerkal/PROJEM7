package com.wyd.empire.protocol.data.mail;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>RemindSendMailOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_MAIL下子命令MAIL_RemindSendMailOk(发送邮件成功提醒协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class HasNewMail extends AbstractData {
	
    public HasNewMail(int sessionId, int serial) {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_HasNewMail, sessionId, serial);
    }

    public HasNewMail() {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_HasNewMail);
    }

}
