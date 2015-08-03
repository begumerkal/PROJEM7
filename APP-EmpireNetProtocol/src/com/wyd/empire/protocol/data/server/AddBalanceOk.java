package com.wyd.empire.protocol.data.server;
import com.wyd.protocol.data.AbstractData;
import com.wyd.empire.protocol.Protocol;
public class AddBalanceOk extends AbstractData {
    private int crystal;

    public AddBalanceOk(int sessionId, int serial) {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_AddBalanceOk, sessionId, serial);
    }

    public AddBalanceOk() {
        super(Protocol.MAIN_SERVER, Protocol.SERVER_AddBalanceOk);
    }

    public int getCrystal() {
        return this.crystal;
    }

    public void setCrystal(int crystal) {
        this.crystal = crystal;
    }
}