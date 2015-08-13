package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>CreateCommunity</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_CreateCommunity(创建公会)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class CreateCommunity extends AbstractData {
	private String communityName;

    public CreateCommunity(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CreateCommunity, sessionId, serial);
    }

    public CreateCommunity() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CreateCommunity);
    }

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

    
}
