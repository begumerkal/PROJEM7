package com.wyd.empire.protocol.data.admin;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 类 <code>Login</code>继承抽象类<code>AbstractData</code>，实现接口主命令Protocol.MAIN_ADMIN下子命令ADMIN_UpdateAccountInfo更新account信息。
 * 
 * @see AbstractData
 * @author mazheng
 */
public class UpdateAccountResult extends AbstractData {
    private int      updateType; // 0完善个人信息,2查询个人信息
    private int      playerId;
    private String[] values;

    public UpdateAccountResult(int sessionId, int serial) {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_UpdateAccountResult, sessionId, serial);
    }

    public UpdateAccountResult() {
        super(Protocol.MAIN_ADMIN, Protocol.ADMIN_UpdateAccountResult);
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
}
