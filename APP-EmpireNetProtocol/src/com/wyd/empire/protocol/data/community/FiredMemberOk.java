package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>FiredMemberOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_FiredMemberOk(开除会员成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class FiredMemberOk extends AbstractData {

    public FiredMemberOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_FiredMemberOk, sessionId, serial);
    }

    public FiredMemberOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_FiredMemberOk);
    }


}
