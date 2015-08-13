package com.app.empire.protocol.data.friend;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>SearchFriend</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_SearchFriend(搜索好友协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SearchFriend extends AbstractData {
	
	private int playerId;
	private String playerName;

    public SearchFriend(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SearchFriend, sessionId, serial);
    }

    public SearchFriend() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_SearchFriend);
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}
