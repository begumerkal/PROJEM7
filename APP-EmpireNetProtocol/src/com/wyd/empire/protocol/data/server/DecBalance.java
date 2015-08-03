package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class DecBalance extends AbstractData {
    private String name;
    private String Key;
    private int    value;

    public DecBalance(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_DecBalance, sessionId, serial);
    }

    public DecBalance() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_DecBalance);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return this.Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int v) {
        this.value = v;
    }
}
