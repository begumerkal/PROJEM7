package com.app.empire.protocol.data.friend;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SetBlackList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_SetBlackList(添加好友协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SetBlackList extends AbstractData {
	
	private int friendId;
	private int pageNumber = 1; //所需的页数

    public SetBlackList(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SetBlackList, sessionId, serial);
    }

    public SetBlackList() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SetBlackList);
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
