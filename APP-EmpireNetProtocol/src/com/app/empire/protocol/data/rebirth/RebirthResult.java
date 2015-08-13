package com.app.empire.protocol.data.rebirth;
import com.app.empire.protocol.Protocol;
import com.app.protocol.data.AbstractData;
public class RebirthResult extends AbstractData {
    private int    status;
    private String message;

    public RebirthResult(int sessionId, int serial) {
        super(Protocol.MAIN_REBIRTH, Protocol.REBIRTH_RebirthResult, sessionId, serial);
    }

    public RebirthResult() {
        super(Protocol.MAIN_REBIRTH, Protocol.REBIRTH_RebirthResult);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
