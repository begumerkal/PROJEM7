package com.app.empire.protocol.data.player;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 类 <code>GetPlayerProp</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_PLAYER下子命令PLAYER_GetPlayerProp(获取玩家道具)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetPlayerProp extends AbstractData {

    public GetPlayerProp(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPlayerProp, sessionId, serial);
    }

    public GetPlayerProp() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPlayerProp);
    }



    

}
