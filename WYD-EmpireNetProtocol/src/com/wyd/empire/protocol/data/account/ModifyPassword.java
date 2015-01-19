package com.wyd.empire.protocol.data.account;
import com.wyd.empire.protocol.Protocol;
import com.wyd.protocol.data.AbstractData;
/**
 * 修改密码
 * @see AbstractData
 * @author mazheng
 */
public class ModifyPassword extends AbstractData {
    private String oldPassword;
    private String newPassword;
    public ModifyPassword(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_ModifyPassword, sessionId, serial);
    }

    public ModifyPassword() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_ModifyPassword);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
