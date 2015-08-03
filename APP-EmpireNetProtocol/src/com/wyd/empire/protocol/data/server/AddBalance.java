package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class AddBalance extends AbstractData {
    private String account;
    private int    amount;
    private String key;

    public AddBalance(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_AddBalance, sessionId, serial);
    }

    public AddBalance() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_AddBalance);
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int count) {
        this.amount = count;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}