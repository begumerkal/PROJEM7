package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>InviteMember</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_InviteMember(邀请入会协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class InviteMember extends AbstractData {
	private int communityId;
	private int playerId;


    public InviteMember(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_InviteMember, sessionId, serial);
    }

    public InviteMember() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_InviteMember);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}



}
