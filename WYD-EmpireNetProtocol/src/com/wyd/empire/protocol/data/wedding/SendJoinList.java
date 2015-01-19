package com.wyd.empire.protocol.data.wedding;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 
 * @see AbstractData
 * @author zhaopeilong
 */
public class SendJoinList extends AbstractData {

	private	int[]	playerId;	//来宾Id
	private	String[]	playerName;	//来宾名称
	private	int[]	level;	//来宾等级
	private	boolean[]	sex;	//来宾性别，false是男，true是女
	private boolean[]	rebirth;	//是否转生

	
    public SendJoinList(int sessionId, int serial) {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendJoinList, sessionId, serial);
    }

    public SendJoinList() {
        super(Protocol.MAIN_WEDDING, Protocol.WEDDING_SendJoinList);
    }

	public int[] getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int[] playerId) {
		this.playerId = playerId;
	}

	public String[] getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String[] playerName) {
		this.playerName = playerName;
	}

	public int[] getLevel() {
		return level;
	}

	public void setLevel(int[] level) {
		this.level = level;
	}

	public boolean[] getSex() {
		return sex;
	}

	public void setSex(boolean[] sex) {
		this.sex = sex;
	}

	public boolean[] getRebirth() {
		return rebirth;
	}

	public void setRebirth(boolean[] rebirth) {
		this.rebirth = rebirth;
	}

}
