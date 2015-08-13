package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>ApproveMember</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ApproveMember(会员审批协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ApproveMember extends AbstractData {
	private int communityId;
	private int[] playerId;
	private boolean isApproved;

    public ApproveMember(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ApproveMember, sessionId, serial);
    }

    public ApproveMember() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ApproveMember);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public int[] getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int[] playerId) {
		this.playerId = playerId;
	}

	public boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	

}
