package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>CheckRight</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_CheckRight(创建公会验证权限协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class CheckRight extends AbstractData {

    public CheckRight(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CheckRight, sessionId, serial);
    }

    public CheckRight() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CheckRight);
    }

}
