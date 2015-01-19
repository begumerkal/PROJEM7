package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class AddRecommendBalance extends AbstractData {
    private String name;
    private int    value;

    public AddRecommendBalance(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_AddRecommendBalance, sessionId, serial);
    }

    public AddRecommendBalance() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_AddRecommendBalance);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
