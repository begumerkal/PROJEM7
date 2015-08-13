package com.app.empire.protocol.data.friend;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>AddFriend</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_AddFriend(添加好友协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class AddFriend extends AbstractData {
	
	private int friendId;

    public AddFriend(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_AddFriend, sessionId, serial);
    }

    public AddFriend() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_AddFriend);
    }


	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	

}
