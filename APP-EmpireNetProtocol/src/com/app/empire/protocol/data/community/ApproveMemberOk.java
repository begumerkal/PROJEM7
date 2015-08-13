package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>ApproveMemberOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ApproveMemberOk(会员审批成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ApproveMemberOk extends AbstractData {
	
    public ApproveMemberOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ApproveMemberOk, sessionId, serial);
    }

    public ApproveMemberOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ApproveMemberOk);
    }


    
    
}
