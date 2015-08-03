package com.wyd.empire.protocol.data.player;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 类 <code>GetSkillList</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_PLAYER下子命令PLAYER_GetBuffList(获取Buff列表)对应数据封装。
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class GetBuffList extends AbstractData {
	private int playerId;

    public GetBuffList(int sessionId, int serial) {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetBuffList, sessionId, serial);
    }

    public GetBuffList() {
        super(Protocol.MAIN_PLAYER, Protocol.PLAYER_GetBuffList);
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

}
