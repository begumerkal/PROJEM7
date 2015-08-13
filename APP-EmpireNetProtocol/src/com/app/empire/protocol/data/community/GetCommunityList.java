package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetCommunityList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_GetCommunityList(获取工会列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetCommunityList extends AbstractData {
	private int pageNumber = 1; //所需的页数

    public GetCommunityList(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetCommunityList, sessionId, serial);
    }

    public GetCommunityList() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetCommunityList);
    }

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
