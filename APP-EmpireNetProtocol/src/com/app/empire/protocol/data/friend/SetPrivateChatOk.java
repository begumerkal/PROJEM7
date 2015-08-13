package com.app.empire.protocol.data.friend;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SetPrivateChatOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_SetPrivateChatOk(设置私聊成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SetPrivateChatOk extends AbstractData {
	

    public SetPrivateChatOk(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SetPrivateChatOk, sessionId, serial);
    }

    public SetPrivateChatOk() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SetPrivateChatOk);
    }


}
