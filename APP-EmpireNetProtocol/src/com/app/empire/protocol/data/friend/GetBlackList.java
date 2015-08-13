package com.app.empire.protocol.data.friend;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetBlackList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_GetBlackList(获得黑名单协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetBlackList extends AbstractData {
	private int pageNumber = 1;

    public GetBlackList(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_GetBlackList, sessionId, serial);
    }

    public GetBlackList() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_GetBlackList);
    }

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}


}
