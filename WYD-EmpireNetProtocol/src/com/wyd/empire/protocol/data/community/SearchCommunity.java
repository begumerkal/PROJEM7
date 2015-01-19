package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>SearchCommunity</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_SearchCommunity(搜索公会)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SearchCommunity extends AbstractData {
	private int communityId;

    public SearchCommunity(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SearchCommunity, sessionId, serial);
    }

    public SearchCommunity() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SearchCommunity);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}


    
}
