package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class PlayerLogout extends AbstractData {
    private int    accountId;
    private int    level;
    private String key;

    public PlayerLogout(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_PlayerLogout, sessionId, serial);
    }

    public PlayerLogout() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_PlayerLogout);
    }

    public int getAccountId() {
        return this.accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
