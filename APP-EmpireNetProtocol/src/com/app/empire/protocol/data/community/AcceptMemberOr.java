package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>IsAcceptMember</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_IsAcceptMember(设置公会是否接受会员协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class AcceptMemberOr extends AbstractData {
	private int communityId;
	private boolean acceptMember;

    public AcceptMemberOr(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_AcceptMemberOr, sessionId, serial);
    }

    public AcceptMemberOr() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_AcceptMemberOr);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public boolean getAcceptMember() {
		return acceptMember;
	}

	public void setAcceptMember(boolean acceptMember) {
		this.acceptMember = acceptMember;
	}

}
