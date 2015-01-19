package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class CreateRoleActor extends AbstractData {
    private String playerName;
    private byte   playerSex;
    private String area;

    public CreateRoleActor(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_CreateRoleActor, sessionId, serial);
    }

    public CreateRoleActor() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_CreateRoleActor);
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public byte getPlayerSex() {
        return this.playerSex;
    }

    public void setPlayerSex(byte playerSex) {
        this.playerSex = playerSex;
    }

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
}
