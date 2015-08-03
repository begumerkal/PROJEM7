package com.wyd.empire.protocol.data.player;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>GetPlayerSkill</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_PLAYER下子命令PLAYER_GetPlayerSkill(获取玩家技能列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetPlayerSkill extends AbstractData {

    public GetPlayerSkill(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPlayerSkill, sessionId, serial);
    }

    public GetPlayerSkill() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetPlayerSkill);
    }



    

}
