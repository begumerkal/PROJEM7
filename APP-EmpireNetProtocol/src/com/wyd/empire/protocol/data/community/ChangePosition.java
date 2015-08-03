package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>ChangePosition</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ChangePosition(会员升职协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ChangePosition extends AbstractData {
	private int playerId;
	private boolean isUp;

    public ChangePosition(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ChangePosition, sessionId, serial);
    }

    public ChangePosition() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ChangePosition);
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public boolean getIsUp() {
		return isUp;
	}

	public void setIsUp(boolean isUp) {
		this.isUp = isUp;
	}

}
