package com.app.empire.protocol.data.friend;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>AddFriendOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_AddFriendOk(添加好友成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class AddFriendOk extends AbstractData {
	

    public AddFriendOk(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_AddFriendOk, sessionId, serial);
    }

    public AddFriendOk() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_AddFriendOk);
    }


	

}
