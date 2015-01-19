package com.wyd.empire.protocol.data.player;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>GetSkillList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_PLAYER下子命令PLAYER_GetSkillList(获取技能列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetSkillList extends AbstractData {

    public GetSkillList(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetSkillList, sessionId, serial);
    }

    public GetSkillList() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetSkillList);
    }



    

}
