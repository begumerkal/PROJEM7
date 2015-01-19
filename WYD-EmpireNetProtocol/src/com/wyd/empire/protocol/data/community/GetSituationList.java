package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>GetSituationList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_GetCommunityList(获取工会列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetSituationList extends AbstractData {
	private int pageNumber = 1; //所需的页数

    public GetSituationList(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetSituationList, sessionId, serial);
    }

    public GetSituationList() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_GetSituationList);
    }

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
