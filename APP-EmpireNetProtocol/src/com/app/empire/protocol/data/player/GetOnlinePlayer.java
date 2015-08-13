package com.app.empire.protocol.data.player;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetOnlinePlayer</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_PLAYER下子命令PLAYER_GetOnlinePlayer(获得在线好友)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetOnlinePlayer extends AbstractData {
	private int pageNumber = 1;

    public GetOnlinePlayer(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetOnlinePlayer, sessionId, serial);
    }

    public GetOnlinePlayer() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetOnlinePlayer);
    }

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
