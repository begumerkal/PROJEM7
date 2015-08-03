package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>ChangePresidentOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_ChangePresidentOk(会长让位成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class ChangePresidentOk extends AbstractData {
	
    public ChangePresidentOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ChangePresidentOk, sessionId, serial);
    }

    public ChangePresidentOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_ChangePresidentOk);
    }


    
    
}
