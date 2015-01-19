package com.wyd.empire.protocol.data.friend;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>GetFriendList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_GetFriendList(获得好友列表协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetFriendList extends AbstractData {
	private int pageNumber = 1;
	private int sex;//0：男性好友，1：女性好友，-1：所有好友

    public GetFriendList(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_GetFriendList, sessionId, serial);
    }

    public GetFriendList() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_GetFriendList);
    }

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}


}
