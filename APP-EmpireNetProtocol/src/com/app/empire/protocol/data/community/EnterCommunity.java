package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>EnterCommunity</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_EnterCommunity(进入公会)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class EnterCommunity extends AbstractData {
	private int communityId;

    public EnterCommunity(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_EnterCommunity, sessionId, serial);
    }

    public EnterCommunity() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_EnterCommunity);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}


    
}
