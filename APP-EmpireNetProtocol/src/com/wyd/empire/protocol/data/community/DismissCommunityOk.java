package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>DismissCommunityOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_DismissCommunityOk(解散工会成功)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class DismissCommunityOk extends AbstractData {
	
    public DismissCommunityOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_DismissCommunityOk, sessionId, serial);
    }

    public DismissCommunityOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_DismissCommunityOk);
    }

}
