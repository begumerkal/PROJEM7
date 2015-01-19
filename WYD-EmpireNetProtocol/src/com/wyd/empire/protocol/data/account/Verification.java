package com.wyd.empire.protocol.data.account;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
/**
 * 帐号密码验证
 * @see AbstractData
 * @author mazheng
 */
public class Verification extends AbstractData {
	private byte[] 	accountName;
    private byte[] 	passWord;
    public Verification(int sessionId, int serial) {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_Verification, sessionId, serial);
    }

    public Verification() {
        super(Protocol.MAIN_ACCOUNT, Protocol.ACCOUNT_Verification);
    }

    public byte[] getAccountName() {
        return accountName;
    }

    public void setAccountName(byte[] accountName) {
        this.accountName = accountName;
    }

    public byte[] getPassWord() {
        return passWord;
    }

    public void setPassWord(byte[] passWord) {
        this.passWord = passWord;
    }
}
