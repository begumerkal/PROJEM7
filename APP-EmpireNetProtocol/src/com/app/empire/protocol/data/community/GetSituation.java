package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetSituation</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_GetCommunityList(获取工会列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetSituation extends AbstractData {
	private int communityId; //公会ID

    public GetSituation(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetSituation, sessionId, serial);
    }

    public GetSituation() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetSituation);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}


}
