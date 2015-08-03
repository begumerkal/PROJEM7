package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>SearchNotFound</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_SearchNotFound(搜索公会没找到协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SearchNotFound extends AbstractData {

    public SearchNotFound(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SearchNotFound, sessionId, serial);
    }

    public SearchNotFound() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_SearchNotFound);
    }


}
