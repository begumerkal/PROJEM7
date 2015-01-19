package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>GetApprovingMemberList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_GetApprovingMemberList(获取公会待审批成员列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetApprovingMemberList extends AbstractData {
	private int communityId;
//	private int pageNumber; //所需的页数

    public GetApprovingMemberList(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetApprovingMemberList, sessionId, serial);
    }

    public GetApprovingMemberList() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetApprovingMemberList);
    }

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

//	public int getPageNumber() {
//		return pageNumber;
//	}
//
//	public void setPageNumber(int pageNumber) {
//		this.pageNumber = pageNumber;
//	}
    
}
