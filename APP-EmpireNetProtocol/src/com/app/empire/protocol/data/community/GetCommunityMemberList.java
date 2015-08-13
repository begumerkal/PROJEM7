package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetCommunityMemberList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_GetCommunityMemberList(获取公会成员列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetCommunityMemberList extends AbstractData {
	private int communityId;
	private int pageNumber = 1; //所需的页数

    public GetCommunityMemberList(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetCommunityMemberList, sessionId, serial);
    }

    public GetCommunityMemberList() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetCommunityMemberList);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
