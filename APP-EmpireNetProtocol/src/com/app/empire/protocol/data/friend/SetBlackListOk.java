package com.app.empire.protocol.data.friend;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SetBlackListOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_SetBlackListOk(添加好友成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SetBlackListOk extends AbstractData {
	
    public SetBlackListOk(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SetBlackListOk, sessionId, serial);
    }

    public SetBlackListOk() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SetBlackListOk);
    }
	

}
