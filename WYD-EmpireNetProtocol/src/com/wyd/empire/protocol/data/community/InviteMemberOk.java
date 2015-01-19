package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>InviteMemberOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_InviteMemberOk(邀请入会成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class InviteMemberOk extends AbstractData {
	
    public InviteMemberOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_InviteMemberOk, sessionId, serial);
    }

    public InviteMemberOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_InviteMemberOk);
    }


    
    
}
