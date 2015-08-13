package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>CreateCommunityOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_CreateCommunityOk(创建公会成功)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class CreateCommunityOk extends AbstractData {
	private int communityId;
	private String communityName;

    public CreateCommunityOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CreateCommunityOk, sessionId, serial);
    }

    public CreateCommunityOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CreateCommunityOk);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

}
