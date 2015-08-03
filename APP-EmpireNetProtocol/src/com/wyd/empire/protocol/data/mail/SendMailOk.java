package com.wyd.empire.protocol.data.mail;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>SendMailOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_MAIL下子命令MAIL_SendMailOk(发送邮件成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendMailOk extends AbstractData {
	
    public SendMailOk(int sessionId, int serial) {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_SendMailOk, sessionId, serial);
    }

    public SendMailOk() {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_SendMailOk);
    }

}
