package com.wyd.empire.protocol.data.server;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 更新个人信息（完善资料）
 * 
 * @see AbstractData
 * @author mazheng
 */
public class UpdateAccountInfo extends AbstractData {
    private int      updateType;
    private int      accountId;
    private int      playerId;
    private String[] values;

    public UpdateAccountInfo(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_UpdateAccountInfo, sessionId, serial);
    }

    public UpdateAccountInfo() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_UpdateAccountInfo);
    }

    public int getUpdateType() {
        return updateType;
    }

    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
