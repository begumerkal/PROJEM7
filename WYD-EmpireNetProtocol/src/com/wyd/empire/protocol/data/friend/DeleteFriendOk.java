package com.wyd.empire.protocol.data.friend;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>DeleteFriendOk</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_FRIEND下子命令FRIEND_DeleteFriendOk(删除好友成功协议)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class DeleteFriendOk extends AbstractData {
	
    public DeleteFriendOk(int sessionId, int serial) {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_DeleteFriendOk, sessionId, serial);
    }

    public DeleteFriendOk() {
        super(Protocol.MAIN_FRIEND, Protocol.FRIEND_DeleteFriendOk);
    }


}
