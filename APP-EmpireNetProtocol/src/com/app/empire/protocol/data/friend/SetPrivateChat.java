package com.app.empire.protocol.data.friend;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SetPrivateChat</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_SetPrivateChat(设为私聊协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SetPrivateChat extends AbstractData {
	
	private int friendId;

    public SetPrivateChat(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SetPrivateChat, sessionId, serial);
    }

    public SetPrivateChat() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SetPrivateChat);
    }


	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	

}
