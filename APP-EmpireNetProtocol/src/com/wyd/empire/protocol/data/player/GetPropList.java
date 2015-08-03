package com.wyd.empire.protocol.data.player;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>GetPropList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_PLAYER下子命令PLAYER_GetPropList(获取道具列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetPropList extends AbstractData {

    public GetPropList(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPropList, sessionId, serial);
    }

    public GetPropList() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPropList);
    }



    

}
