package com.wyd.empire.protocol.data.friend;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>DeleteFriend</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_DeleteFriend(删除好友协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class DeleteFriend extends AbstractData {
	
	private int friendId;
	private int pageNumber = 1; //所需的页数

    public DeleteFriend(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_DeleteFriend, sessionId, serial);
    }

    public DeleteFriend() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_DeleteFriend);
    }


	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	

}
