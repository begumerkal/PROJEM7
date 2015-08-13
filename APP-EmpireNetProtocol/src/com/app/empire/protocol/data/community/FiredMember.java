package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>FiredMember</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_FiredMember(开除会员协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class FiredMember extends AbstractData {
	private int playerId;
	private int pageNumber = 1; //所需的页数

    public FiredMember(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_FiredMember, sessionId, serial);
    }

    public FiredMember() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_FiredMember);
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
