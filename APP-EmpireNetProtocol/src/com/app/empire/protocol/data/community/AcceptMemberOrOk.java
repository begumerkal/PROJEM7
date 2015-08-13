package com.app.empire.protocol.data.community;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>IsAcceptMemberOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_IsAcceptMemberOk(设置公会是否接受会员成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class AcceptMemberOrOk extends AbstractData {

    public AcceptMemberOrOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_AcceptMemberOrOk, sessionId, serial);
    }

    public AcceptMemberOrOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_AcceptMemberOrOk);
    }


}
