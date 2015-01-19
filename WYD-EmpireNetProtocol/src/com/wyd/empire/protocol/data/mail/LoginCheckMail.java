package com.wyd.empire.protocol.data.mail;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>LoginCheckMail</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_MAIL下子命令MAIL_LoginCheckMail(登陆时检测是否有未读邮件协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class LoginCheckMail extends AbstractData {
	
    public LoginCheckMail(int sessionId, int serial) {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_LoginCheckMail, sessionId, serial);
    }

    public LoginCheckMail() {
        super(Protocol.MAIN_MAIL, Protocol.MAIL_LoginCheckMail);
    }

}
