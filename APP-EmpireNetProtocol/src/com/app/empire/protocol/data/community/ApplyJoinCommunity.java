package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>ApplyJoinCommunity</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ApplyJoinCommunity(申请入会协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ApplyJoinCommunity extends AbstractData {
	private int communityId;

    public ApplyJoinCommunity(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ApplyJoinCommunity, sessionId, serial);
    }

    public ApplyJoinCommunity() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ApplyJoinCommunity);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}


    
}
