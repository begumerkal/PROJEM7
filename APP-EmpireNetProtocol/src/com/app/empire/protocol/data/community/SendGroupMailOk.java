package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SendGroupMailOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_SendGroupMailOk(公会群发邮件成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendGroupMailOk extends AbstractData {

    public SendGroupMailOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendGroupMailOk, sessionId, serial);
    }

    public SendGroupMailOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SendGroupMailOk);
    }


}
