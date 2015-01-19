package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class LegacyLoginOk extends AbstractData {
    private int    accountId;
    private int    gameAccountId;
    private String udid;
	private String name;
    private String password;
    private int    tokenAmount;
    private int    channel;  // 渠道ID
    private int    status;  // 登录结果  0成功，1用户名或密码错误，2系统异常

	public LegacyLoginOk(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyLoginOk, sessionId, serial);
    }

    public LegacyLoginOk() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_LegacyLoginOk);
    }

    public int getAccountId() {
        return this.accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getGameAccountId() {
        return gameAccountId;
    }

    public void setGameAccountId(int gameAccountId) {
        this.gameAccountId = gameAccountId;
    }
    
    public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

    public String getName() {
        return this.name;
    }

    public void setName(String accountName) {
        this.name = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTokenAmount(int tokenAmount) {
        this.tokenAmount = tokenAmount;
    }

    public int getTokenAmount() {
        return tokenAmount;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}