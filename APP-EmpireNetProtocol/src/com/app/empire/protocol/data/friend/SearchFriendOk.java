package com.app.empire.protocol.data.friend;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SearchFriendOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_SearchFriendOk(搜索好友成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SearchFriendOk extends AbstractData {
	
	private int playerId;

    public SearchFriendOk(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SearchFriendOk, sessionId, serial);
    }

    public SearchFriendOk() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SearchFriendOk);
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

}
