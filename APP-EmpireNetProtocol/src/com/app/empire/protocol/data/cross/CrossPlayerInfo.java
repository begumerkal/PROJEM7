package com.app.empire.protocol.data.cross;

import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
/**
 * 获取玩家信息
 * @author zengxc
 *
 */
public class CrossPlayerInfo extends AbstractData {
	private int battleId;
    private int playerId;
    private int friendId;

	public CrossPlayerInfo() {
		super(Protocol.MAIN_CROSS, Protocol.CROSS_CrossPlayerInfo);
	}

    public int getBattleId() {
        return battleId;
    }

    public void setBattleId(int battleId) {
        this.battleId = battleId;
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

    
}
