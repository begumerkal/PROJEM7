package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>ExitCommunityOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ExitCommunityOk(退出公会成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ExitCommunityOk extends AbstractData {
	
    public ExitCommunityOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ExitCommunityOk, sessionId, serial);
    }

    public ExitCommunityOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ExitCommunityOk);
    }


    
    
}
