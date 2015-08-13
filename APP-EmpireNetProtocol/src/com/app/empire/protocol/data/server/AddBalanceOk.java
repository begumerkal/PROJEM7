package com.app.empire.protocol.data.server;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
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