package com.app.empire.protocol.data.server;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class DecBalanceOk extends AbstractData {
    private int dec;
    private int value;

    public DecBalanceOk(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_DecBalanceOk, sessionId, serial);
    }

    public DecBalanceOk() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_DecBalanceOk);
    }

    public int getDec() {
        return this.dec;
    }

    public void setDec(int dec) {
        this.dec = dec;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
