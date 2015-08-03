package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class RefreshWedding extends AbstractData {

	private	int	playerId;	//来宾玩家id
	private	String	playerName;	//来宾玩家昵称
	private	String[]	playerEquipment;	//来宾身上的装备
	private boolean	sex;				//性别


    public RefreshWedding(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_RefreshWedding , sessionId, serial);
    }

    public RefreshWedding() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_RefreshWedding );
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String[] getPlayerEquipment() {
		return playerEquipment;
	}

	public void setPlayerEquipment(String[] playerEquipment) {
		this.playerEquipment = playerEquipment;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}


}
