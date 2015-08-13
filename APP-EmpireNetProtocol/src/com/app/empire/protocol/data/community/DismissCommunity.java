package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>DismissCommunity</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_DismissCommunity(解散工会协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class DismissCommunity extends AbstractData {

    public DismissCommunity(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_DismissCommunity, sessionId, serial);
    }

    public DismissCommunity() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_DismissCommunity);
    }

}
