package com.wyd.empire.protocol.data.community;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>CheckRightOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_COMMUNITY下子命令COMMUNITY_CheckRightOk(创建公会验证权限成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class CheckRightOk extends AbstractData {

    public CheckRightOk(int sessionId, int serial) {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CheckRightOk, sessionId, serial);
    }

    public CheckRightOk() {
        super(Protocol.MAIN_COMMUNITY, Protocol.COMMUNITY_CheckRightOk);
    }

}
