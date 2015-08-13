package com.app.empire.protocol.data.friend;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetCommunityFriendList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_GetCommunityFriendList(获得公会好友协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetCommunityFriendList extends AbstractData {
	private int pageNumber = 1;

    public GetCommunityFriendList(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_GetCommunityFriendList, sessionId, serial);
    }

    public GetCommunityFriendList() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_GetCommunityFriendList);
    }

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


}
