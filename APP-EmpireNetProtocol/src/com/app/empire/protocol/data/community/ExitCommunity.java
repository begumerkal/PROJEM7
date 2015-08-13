package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>ExitCommunity</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ExitCommunity(退出公会协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ExitCommunity extends AbstractData {

    public ExitCommunity(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ExitCommunity, sessionId, serial);
    }

    public ExitCommunity() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ExitCommunity);
    }


}
