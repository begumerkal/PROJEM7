package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetCommunityInfo</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_GetCommunityInfo(获取公会信息)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetCommunityInfo extends AbstractData {
	private int communityId;

    public GetCommunityInfo(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetCommunityInfo, sessionId, serial);
    }

    public GetCommunityInfo() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetCommunityInfo);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}


    
}
