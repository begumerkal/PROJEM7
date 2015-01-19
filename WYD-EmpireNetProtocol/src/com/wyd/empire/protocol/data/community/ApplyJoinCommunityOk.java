package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>ApplyJoinCommunityOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ApplyJoinCommunityOk(申请入会成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ApplyJoinCommunityOk extends AbstractData {
	
    public ApplyJoinCommunityOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ApplyJoinCommunityOk, sessionId, serial);
    }

    public ApplyJoinCommunityOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ApplyJoinCommunityOk);
    }
    
}
